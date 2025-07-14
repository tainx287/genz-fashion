CREATE DATABASE GenZFashion;
GO
USE GenZFashion;
GO


-- QUẢN LÝ ĐỊA CHỈ

-- Bảng Addresses: Lưu trữ địa chỉ giao hàng và địa chỉ của người dùng
-- Tác dụng: Quản lý thông tin địa chỉ cho việc giao hàng, hỗ trợ địa chỉ Việt Nam với phường/xã, quận/huyện, tỉnh/thành phố
CREATE TABLE Addresses
(
    address_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    street_address NVARCHAR(255) NOT NULL,
    ward NVARCHAR(100),
    district NVARCHAR(100) NOT NULL,
    city NVARCHAR(100) NOT NULL,
    province NVARCHAR(100) NOT NULL,
    postal_code NVARCHAR(20),
    country NVARCHAR(50) DEFAULT 'Vietnam',
    is_active BIT DEFAULT 1,
    created_at DATETIME DEFAULT GETDATE()
);


-- QUẢN LÝ NGƯỜI DÙNG

-- Bảng Users: Lưu trữ thông tin tài khoản người dùng trong hệ thống
-- Tác dụng: Quản lý đăng ký, đăng nhập, phân quyền (admin/staff/customer), thông tin cá nhân
CREATE TABLE Users
(
    user_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    username NVARCHAR(50) NOT NULL UNIQUE,
    email NVARCHAR(100) NOT NULL UNIQUE,
    password_hash NVARCHAR(255) NOT NULL,
    full_name NVARCHAR(100),
    phone_number NVARCHAR(15),
    address_id BIGINT NULL FOREIGN KEY REFERENCES Addresses(address_id),
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE(),
    deleted_at DATETIME NULL,
    role NVARCHAR(10) DEFAULT 'customer' CHECK (role IN ('admin', 'staff', 'customer')),
    is_active BIT DEFAULT 1
);

-- Tạo index để tăng tốc độ tìm kiếm theo email và username
CREATE INDEX idx_users_email ON Users(email);
CREATE INDEX idx_users_username ON Users(username);


-- ĐĂNG NHẬP OAUTH

-- Bảng UserOAuth: Lưu trữ thông tin đăng nhập qua mạng xã hội
-- Tác dụng: Cho phép người dùng đăng nhập bằng Google, Facebook thay vì tạo tài khoản mới
CREATE TABLE UserOAuth
(
    oauth_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT FOREIGN KEY REFERENCES Users(user_id) ON DELETE CASCADE,
    provider NVARCHAR(20) NOT NULL CHECK (provider IN ('google', 'facebook')),
    provider_user_id NVARCHAR(100) NOT NULL,
    provider_token NVARCHAR(1024),
    -- Token OAuth thường không vượt quá 1024 ký tự
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE(),
    CONSTRAINT UQ_UserOAuth_Provider_ID UNIQUE (provider, provider_user_id)
);


-- ĐIỂM TÍCH LŨY NGƯỜI DÙNG

-- Bảng UserPoints: Lưu trữ số điểm tích lũy hiện tại của từng người dùng
-- Tác dụng: Quản lý hệ thống điểm thưởng, khách hàng kiếm điểm khi mua hàng và dùng điểm để đổi voucher
CREATE TABLE UserPoints
(
    point_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT FOREIGN KEY REFERENCES Users(user_id) ON DELETE CASCADE,
    points_balance INT DEFAULT 0,
    last_updated DATETIME DEFAULT GETDATE()
);


-- HỆ THỐNG VOUCHER (THỐNG NHẤT VỚI ĐIỂM)

-- Bảng PublicVouchers: Lưu trữ tất cả voucher giảm giá trong hệ thống
-- Tác dụng: Quản lý voucher miễn phí và voucher premium (cần điểm), hỗ trợ giảm giá % hoặc số tiền cố định
CREATE TABLE PublicVouchers
(
    voucher_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    code NVARCHAR(20) NOT NULL UNIQUE,
    name NVARCHAR(100) NOT NULL,
    description NVARCHAR(500),
    -- Tăng độ dài để mô tả tốt hơn
    discount_type NVARCHAR(20) NOT NULL CHECK (discount_type IN ('percentage', 'fixed_amount')),
    discount_value DECIMAL(10,2) NOT NULL,
    min_purchase_amount DECIMAL(10,2) DEFAULT 0,
    max_discount_amount DECIMAL(10,2),
    required_points INT DEFAULT 0,
    -- Điểm cần thiết để sử dụng (0 = miễn phí cho tất cả)
    usage_limit INT,
    -- Giới hạn sử dụng tổng
    usage_limit_per_user INT DEFAULT 1,
    -- Giới hạn sử dụng mỗi người
    used_count INT DEFAULT 0,
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL,
    is_active BIT DEFAULT 1,
    created_at DATETIME DEFAULT GETDATE()
);

-- Bảng UserVoucherUsage: Ghi lại việc sử dụng voucher của từng người dùng
-- Tác dụng: Theo dõi ai đã dùng voucher nào, bao nhiêu điểm đã tiêu, ngăn chặn lạm dụng voucher
CREATE TABLE UserVoucherUsage
(
    usage_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT FOREIGN KEY REFERENCES Users(user_id) ON DELETE CASCADE,
    voucher_id BIGINT FOREIGN KEY REFERENCES PublicVouchers(voucher_id) ON DELETE CASCADE,
    points_spent INT DEFAULT 0,
    -- Điểm đã tiêu để lấy voucher này
    used_at DATETIME DEFAULT GETDATE(),
    order_id BIGINT NULL
    -- Sẽ được liên kết khi bảng Orders được tạo
);

-- Bảng PointsTransactions: Ghi lại tất cả giao dịch điểm của người dùng
-- Tác dụng: Theo dõi lịch sử kiếm điểm, tiêu điểm, điểm hết hạn - tạo báo cáo và audit trail
CREATE TABLE PointsTransactions
(
    transaction_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT FOREIGN KEY REFERENCES Users(user_id) ON DELETE CASCADE,
    transaction_type NVARCHAR(20) NOT NULL CHECK (transaction_type IN ('earned', 'spent', 'expired', 'adjustment')),
    points INT NOT NULL,
    -- Dương cho kiếm được, âm cho tiêu
    description NVARCHAR(255),
    reference_type NVARCHAR(20),
    -- 'order', 'voucher', 'manual', v.v.
    reference_id BIGINT,
    -- order_id, voucher_id, v.v.
    created_at DATETIME DEFAULT GETDATE()
);


-- QUẢN LÝ THƯƠNG HIỆU

-- Bảng Brands: Lưu trữ thông tin các thương hiệu thời trang
-- Tác dụng: Quản lý nhà cung cấp, brand logo, thông tin liên hệ, phân loại sản phẩm theo thương hiệu
CREATE TABLE Brands
(
    brand_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    brand_name NVARCHAR(100) NOT NULL UNIQUE,
    description NVARCHAR(max),
    -- Phù hợp hơn cho văn bản dài
    logo_url NVARCHAR(2048),
    -- URL thường không vượt quá 2048 ký tự
    website NVARCHAR(255),
    contact_email NVARCHAR(100),
    contact_phone NVARCHAR(20),
    is_active BIT DEFAULT 1,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE()
);


-- THUỘC TÍNH SẢN PHẨM

-- Bảng Colors: Lưu trữ danh sách màu sắc chuẩn cho sản phẩm
-- Tác dụng: Tiêu chuẩn hóa màu sắc, hỗ trợ filter và search theo màu, quản lý mã màu hex
CREATE TABLE Colors
(
    color_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    color_name NVARCHAR(50) NOT NULL UNIQUE,
    color_code NVARCHAR(10),
    -- Mã màu hex: #FF0000
    -- Hex code: #FF0000
    is_active BIT DEFAULT 1,
    created_at DATETIME DEFAULT GETDATE()
);

-- Bảng Sizes: Lưu trữ danh sách kích thước chuẩn cho sản phẩm thời trang
-- Tác dụng: Tiêu chuẩn hóa size (XS, S, M, L, XL), hỗ trợ sắp xếp và filter theo kích thước
CREATE TABLE Sizes
(
    size_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    size_name NVARCHAR(20) NOT NULL UNIQUE,
    -- XS, S, M, L, XL, XXL
    size_order INT,
    -- Để sắp xếp: 1, 2, 3, 4, 5, 6
    is_active BIT DEFAULT 1,
    created_at DATETIME DEFAULT GETDATE()
);


-- QUẢN LÝ DANH MỤC

-- Bảng Categories: Lưu trữ cây danh mục sản phẩm theo cấu trúc phân cấp
-- Tác dụng: Tổ chức sản phẩm theo danh mục (Áo > Áo thun, Quần > Quần jean), hỗ trợ navigation và filter
CREATE TABLE Categories
(
    category_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(50) NOT NULL,
    description NVARCHAR(max),
    -- Phù hợp hơn cho văn bản dài
    parent_id BIGINT NULL FOREIGN KEY REFERENCES Categories(category_id) ON DELETE NO ACTION,
    is_active BIT DEFAULT 1,
    created_at DATETIME DEFAULT GETDATE(),
    deleted_at DATETIME NULL
);


-- QUẢN LÝ SẢN PHẨM

-- Bảng Products: Lưu trữ thông tin cơ bản của từng sản phẩm
-- Tác dụng: Quản lý catalog sản phẩm, SKU, giá gốc, mô tả, phân loại theo brand và category
CREATE TABLE Products
(
    product_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    sku NVARCHAR(50) NOT NULL UNIQUE,
    -- SKU sản phẩm chính
    barcode NVARCHAR(100) UNIQUE,
    -- Mã vạch sản phẩm chính
    name NVARCHAR(100) NOT NULL,
    description NVARCHAR(max),
    -- Phù hợp hơn cho mô tả sản phẩm dài
    base_price DECIMAL(10,2) NOT NULL,
    category_id BIGINT NULL FOREIGN KEY REFERENCES Categories(category_id) ON DELETE SET NULL,
    brand_id BIGINT NULL FOREIGN KEY REFERENCES Brands(brand_id) ON DELETE SET NULL,
    is_active BIT DEFAULT 1,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE(),
    deleted_at DATETIME NULL
);

-- Tạo index để tăng tốc độ truy vấn sản phẩm
CREATE INDEX idx_products_category ON Products(category_id);
-- Tìm theo danh mục
CREATE INDEX idx_products_brand ON Products(brand_id);
-- Tìm theo thương hiệu
CREATE INDEX idx_products_sku ON Products(sku);
-- Tìm theo SKU
CREATE INDEX idx_products_barcode ON Products(barcode);
-- Tìm theo mã vạch


-- BIẾN THỂ SẢN PHẨM (CẢI TIẾN)

-- Bảng ProductVariants: Lưu trữ các biến thể của sản phẩm (kết hợp màu sắc + kích thước)
-- Tác dụng: Quản lý tồn kho chi tiết, giá bán thực tế, theo dõi từng variant cụ thể (Áo thun đỏ size M)
CREATE TABLE ProductVariants
(
    variant_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    product_id BIGINT NOT NULL FOREIGN KEY REFERENCES Products(product_id) ON DELETE CASCADE,
    variant_sku NVARCHAR(50) NOT NULL UNIQUE,
    -- SKU biến thể cụ thể
    variant_barcode NVARCHAR(100) UNIQUE,
    -- Mã vạch biến thể cụ thể
    size_id BIGINT NOT NULL FOREIGN KEY REFERENCES Sizes(size_id),
    color_id BIGINT NOT NULL FOREIGN KEY REFERENCES Colors(color_id),
    quantity_in_stock INT DEFAULT 0,
    additional_price DECIMAL(10,2) DEFAULT 0.00,
    -- Chi phí thêm cho biến thể này
    weight DECIMAL(8,2),
    -- Cân nặng tính bằng kg để tính phí ship
    is_active BIT DEFAULT 1,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE()
);

-- Index cho biến thể sản phẩm
CREATE INDEX idx_variants_product ON ProductVariants(product_id);
-- Tìm biến thể theo sản phẩm
CREATE INDEX idx_variants_sku ON ProductVariants(variant_sku);
-- Tìm theo SKU biến thể
CREATE INDEX idx_variants_barcode ON ProductVariants(variant_barcode);
-- Tìm theo mã vạch biến thể


-- HÌNH ẢNH SẢN PHẨM

-- Bảng ProductImages: Lưu trữ hình ảnh của sản phẩm và các biến thể
-- Tác dụng: Quản lý gallery ảnh, ảnh thumbnail, ảnh chi tiết cho từng sản phẩm và variant
CREATE TABLE ProductImages
(
    image_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    product_id BIGINT NOT NULL FOREIGN KEY REFERENCES Products(product_id) ON DELETE NO ACTION,
    variant_id BIGINT NULL FOREIGN KEY REFERENCES ProductVariants(variant_id) ON DELETE SET NULL,
    image_url NVARCHAR(2048) NOT NULL,
    -- URL thường không vượt quá 2048 ký tự
    alt_text NVARCHAR(255),
    image_type NVARCHAR(20) DEFAULT 'product' CHECK (image_type IN ('product', 'variant', 'thumbnail', 'detail')),
    display_order INT DEFAULT 1,
    is_active BIT DEFAULT 1,
    created_at DATETIME DEFAULT GETDATE()
);

-- Index cho hình ảnh sản phẩm
CREATE INDEX idx_images_product ON ProductImages(product_id);
-- Tìm hình ảnh theo sản phẩm
CREATE INDEX idx_images_variant ON ProductImages(variant_id);
-- Tìm hình ảnh theo biến thể


-- QUẢN LÝ GIỎ HÀNG (ĐÃ CẬP NHẬT)

-- Bảng Cart: Lưu trữ giỏ hàng của từng người dùng
-- Tác dụng: Duy trì giỏ hàng khi người dùng chưa thanh toán, cho phép thêm/xóa/sửa sản phẩm
CREATE TABLE Cart
(
    cart_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT NOT NULL FOREIGN KEY REFERENCES Users(user_id) ON DELETE CASCADE,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE()
);

-- Bảng CartItems: Lưu trữ chi tiết từng sản phẩm trong giỏ hàng
-- Tác dụng: Quản lý số lượng, giá tại thời điểm thêm vào giỏ, tính tổng tiền từng item
CREATE TABLE CartItems
(
    cart_item_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    cart_id BIGINT NOT NULL FOREIGN KEY REFERENCES Cart(cart_id) ON DELETE CASCADE,
    variant_id BIGINT NOT NULL FOREIGN KEY REFERENCES ProductVariants(variant_id),
    quantity INT NOT NULL CHECK (quantity > 0),
    unit_price DECIMAL(10,2) NOT NULL,
    -- Giá tại thời điểm thêm vào giỏ
    total_price AS (quantity * unit_price) PERSISTED,
    -- Cột tính toán tự động
    added_at DATETIME DEFAULT GETDATE()
);


-- ĐƠN HÀNG

-- Bảng Orders: Lưu trữ thông tin đơn hàng đã được đặt
-- Tác dụng: Quản lý quy trình từ đặt hàng đến giao hàng, theo dõi trạng thái, thanh toán, voucher
CREATE TABLE Orders
(
    order_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT NOT NULL FOREIGN KEY REFERENCES Users(user_id) ON DELETE CASCADE,
    address_id BIGINT NOT NULL FOREIGN KEY REFERENCES Addresses(address_id),
    order_date DATETIME DEFAULT GETDATE(),
    subtotal DECIMAL(10,2) NOT NULL,
    -- Trước khi giảm giá
    discount_amount DECIMAL(10,2) DEFAULT 0.00,
    shipping_fee DECIMAL(10,2) DEFAULT 0.00,
    total_amount DECIMAL(10,2) NOT NULL,
    -- Tổng cuối cùng
    order_status NVARCHAR(20) DEFAULT 'pending' CHECK (order_status IN ('pending', 'confirmed', 'processing', 'shipped', 'delivered', 'cancelled')),
    payment_method NVARCHAR(20) NOT NULL CHECK (payment_method IN ('cod', 'credit_card', 'paypal', 'bank_transfer')),
    payment_status NVARCHAR(20) DEFAULT 'unpaid' CHECK (payment_status IN ('unpaid', 'paid', 'partial', 'refunded')),
    voucher_code NVARCHAR(20),
    -- Voucher đã áp dụng
    notes NVARCHAR(max),
    -- Phù hợp hơn cho ghi chú dài
    cancelled_at DATETIME NULL,
    cancelled_reason NVARCHAR(255)
);

-- Index cho đơn hàng
CREATE INDEX idx_orders_user ON Orders(user_id);
-- Tìm đơn hàng theo người dùng
CREATE INDEX idx_orders_status ON Orders(order_status);
-- Lọc theo trạng thái đơn hàng
CREATE INDEX idx_orders_date ON Orders(order_date);
-- Sắp xếp theo ngày đặt hàng


-- CHI TIẾT ĐƠN HÀNG

-- Bảng OrderItems: Lưu trữ chi tiết từng sản phẩm trong đơn hàng
-- Tác dụng: Ghi lại chính xác sản phẩm nào, số lượng, giá bán tại thời điểm đặt hàng
CREATE TABLE OrderItems
(
    order_item_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    order_id BIGINT NOT NULL FOREIGN KEY REFERENCES Orders(order_id) ON DELETE CASCADE,
    variant_id BIGINT NOT NULL FOREIGN KEY REFERENCES ProductVariants(variant_id),
    quantity INT NOT NULL CHECK (quantity > 0),
    unit_price DECIMAL(10,2) NOT NULL,
    total_price AS (quantity * unit_price) PERSISTED
);


-- THANH TOÁN

-- Bảng Payments: Lưu trữ thông tin giao dịch thanh toán
-- Tác dụng: Theo dõi thanh toán COD, thẻ tín dụng, PayPal, chuyển khoản - hỗ trợ thanh toán từng phần
CREATE TABLE Payments
(
    payment_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    order_id BIGINT NOT NULL FOREIGN KEY REFERENCES Orders(order_id) ON DELETE CASCADE,
    payment_date DATETIME DEFAULT GETDATE(),
    amount DECIMAL(10,2) NOT NULL,
    -- Giữ lại để theo dõi thanh toán từng phần
    payment_method NVARCHAR(20) NOT NULL CHECK (payment_method IN ('cod', 'credit_card', 'paypal', 'bank_transfer')),
    payment_status NVARCHAR(20) DEFAULT 'completed' CHECK (payment_status IN ('pending', 'completed', 'failed', 'refunded')),
    transaction_id NVARCHAR(100),
    gateway_response NVARCHAR(max),
    -- Phù hợp hơn cho phản hồi JSON
    -- Lưu trữ phản hồi từ cổng thanh toán
    created_at DATETIME DEFAULT GETDATE()
);


-- VẬN CHUYỂN

-- Bảng Shipping: Lưu trữ thông tin vận chuyển và giao hàng
-- Tác dụng: Theo dõi mã vận đơn, trạng thái giao hàng, phí ship, ngày giao dự kiến
CREATE TABLE Shipping
(
    shipping_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    order_id BIGINT NOT NULL FOREIGN KEY REFERENCES Orders(order_id) ON DELETE CASCADE,
    shipping_method NVARCHAR(50) NOT NULL,
    tracking_number NVARCHAR(100),
    shipping_status NVARCHAR(20) DEFAULT 'processing' CHECK (shipping_status IN ('processing', 'shipped', 'in_transit', 'delivered', 'failed')),
    shipping_fee DECIMAL(10,2) NOT NULL,
    estimated_delivery_date DATE,
    shipped_at DATETIME,
    notes NVARCHAR(255),
    created_at DATETIME DEFAULT GETDATE()
);


-- ĐÁNH GIÁ

-- Bảng Reviews: Lưu trữ đánh giá và nhận xét của khách hàng về sản phẩm
-- Tác dụng: Quản lý feedback khách hàng, xếp hạng sao, xác minh người mua thật, cải thiện chất lượng
CREATE TABLE Reviews
(
    review_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    product_id BIGINT NOT NULL FOREIGN KEY REFERENCES Products(product_id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL FOREIGN KEY REFERENCES Users(user_id) ON DELETE CASCADE,
    order_id BIGINT NULL FOREIGN KEY REFERENCES Orders(order_id),
    -- Liên kết đến giao dịch mua đã xác minh
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comment NVARCHAR(max),
    -- Phù hợp hơn cho bình luận dài
    is_verified_purchase BIT DEFAULT 0,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE()
);


-- DANH SÁCH YÊU THÍCH

-- Bảng Wishlists: Lưu trữ danh sách yêu thích của từng người dùng
-- Tác dụng: Cho phép khách hàng lưu sản phẩm quan tâm để mua sau, tăng tương tác và retention
CREATE TABLE Wishlists
(
    wishlist_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT NOT NULL FOREIGN KEY REFERENCES Users(user_id) ON DELETE CASCADE,
    created_at DATETIME DEFAULT GETDATE()
);

-- Bảng WishlistItems: Lưu trữ chi tiết từng sản phẩm trong danh sách yêu thích
-- Tác dụng: Quản lý danh sách sản phẩm mà khách hàng muốn mua trong tương lai
CREATE TABLE WishlistItems
(
    wishlist_item_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    wishlist_id BIGINT NOT NULL FOREIGN KEY REFERENCES Wishlists(wishlist_id) ON DELETE CASCADE,
    product_id BIGINT NOT NULL FOREIGN KEY REFERENCES Products(product_id),
    added_at DATETIME DEFAULT GETDATE()
);


-- NHẬT KÝ TỒN KHO

-- Bảng InventoryLogs: Ghi lại mọi thay đổi về số lượng tồn kho
-- Tác dụng: Audit trail cho inventory, theo dõi nhập/xuất/hỏng/trả hàng, phát hiện sai sót
CREATE TABLE InventoryLogs
(
    log_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    variant_id BIGINT NOT NULL FOREIGN KEY REFERENCES ProductVariants(variant_id),
    change_type NVARCHAR(20) NOT NULL CHECK (change_type IN ('stock_in', 'stock_out', 'adjustment', 'damaged', 'returned')),
    quantity_before INT NOT NULL,
    quantity_changed INT NOT NULL,
    quantity_after INT NOT NULL,
    reason NVARCHAR(255),
    reference_type NVARCHAR(20),
    -- 'order', 'return', 'adjustment', v.v.
    reference_id BIGINT,
    -- order_id, return_id, v.v.
    changed_by BIGINT FOREIGN KEY REFERENCES Users(user_id),
    changed_at DATETIME DEFAULT GETDATE()
);


-- NHẬT KÝ HOẠT ĐỘNG

-- Bảng ActivityLogs: Ghi lại tất cả thay đổi dữ liệu trong hệ thống
-- Tác dụng: Audit trail toàn hệ thống, theo dõi ai thay đổi gì khi nào, bảo mật và compliance
CREATE TABLE ActivityLogs
(
    log_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    table_name NVARCHAR(50) NOT NULL,
    action_type NVARCHAR(10) NOT NULL CHECK (action_type IN ('INSERT', 'UPDATE', 'DELETE')),
    record_id BIGINT NOT NULL,
    old_values NVARCHAR(max),
    -- Định dạng JSON
    new_values NVARCHAR(max),
    -- Định dạng JSON
    changed_by BIGINT FOREIGN KEY REFERENCES Users(user_id),
    changed_at DATETIME DEFAULT GETDATE(),
    ip_address NVARCHAR(45),
    user_agent NVARCHAR(1024)
    -- User agent thường không vượt quá 1024 ký tự
);


-- RÀNG BUỘC VÀ INDEX BỔ SUNG


-- Khóa ngoại cho order_id trong UserVoucherUsage (thêm sau khi tạo bảng Orders)
ALTER TABLE UserVoucherUsage
    ADD CONSTRAINT FK_UserVoucherUsage_Orders
        FOREIGN KEY (order_id) REFERENCES Orders(order_id);

-- Các index bổ sung hữu ích để tối ưu hiệu suất
CREATE INDEX idx_product_variants_stock ON ProductVariants(quantity_in_stock);
-- Quản lý tồn kho
CREATE INDEX idx_orders_payment_status ON Orders(payment_status);
-- Lọc trạng thái thanh toán
CREATE INDEX idx_cart_items_variant ON CartItems(variant_id);
-- Tối ưu giỏ hàng
CREATE INDEX idx_inventory_logs_variant ON InventoryLogs(variant_id);
-- Theo dõi tồn kho
CREATE INDEX idx_inventory_logs_date ON InventoryLogs(changed_at); -- Sắp xếp theo thời gian

