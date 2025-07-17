CREATE DATABASE FashionEcommerce;
GO
USE FashionEcommerce;
GO

-- =============================================
-- ADDRESS MANAGEMENT
-- =============================================
CREATE TABLE Addresses
(
    address_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    street_address VARCHAR(255) NOT NULL,
    ward VARCHAR(100),
    district VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    province VARCHAR(100) NOT NULL,
    postal_code VARCHAR(20),
    country VARCHAR(50) DEFAULT 'Vietnam',
    is_active BIT DEFAULT 1,
    created_at DATETIME DEFAULT GETDATE()
);

-- =============================================
-- USERS MANAGEMENT (UPDATED)
-- =============================================
CREATE TABLE Users
(
    user_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(100),
    phone_number VARCHAR(15),
    address_id BIGINT NULL FOREIGN KEY REFERENCES Addresses(address_id),
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE(),
    deleted_at DATETIME NULL,
    role VARCHAR(10) DEFAULT 'customer' CHECK (role IN ('admin', 'staff', 'customer')),
    is_active BIT DEFAULT 1
);

CREATE INDEX idx_users_email ON Users(email);
CREATE INDEX idx_users_username ON Users(username);

-- =============================================
-- OAUTH (UNCHANGED)
-- =============================================
CREATE TABLE UserOAuth
(
    oauth_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT FOREIGN KEY REFERENCES Users(user_id) ON DELETE CASCADE,
    provider VARCHAR(20) NOT NULL CHECK (provider IN ('google', 'facebook')),
    provider_user_id VARCHAR(100) NOT NULL,
    provider_token VARCHAR(MAX),
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE(),
    CONSTRAINT UQ_UserOAuth_Provider_ID UNIQUE (provider, provider_user_id)
);

-- =============================================
-- USER POINTS (UNCHANGED)
-- =============================================
CREATE TABLE UserPoints
(
    point_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT FOREIGN KEY REFERENCES Users(user_id) ON DELETE CASCADE,
    points_balance INT DEFAULT 0,
    last_updated DATETIME DEFAULT GETDATE()
);

-- =============================================
-- VOUCHER SYSTEM (NEW - IMPROVED)
-- =============================================
-- General Vouchers (Public)
CREATE TABLE PublicVouchers
(
    voucher_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    code VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    discount_type VARCHAR(20) NOT NULL CHECK (discount_type IN ('percentage', 'fixed_amount')),
    discount_value DECIMAL(10,2) NOT NULL,
    min_purchase_amount DECIMAL(10,2) DEFAULT 0,
    max_discount_amount DECIMAL(10,2),
    usage_limit INT,
    -- Total usage limit
    used_count INT DEFAULT 0,
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL,
    is_active BIT DEFAULT 1,
    created_at DATETIME DEFAULT GETDATE()
);

-- Member Exclusive Vouchers
CREATE TABLE MemberVouchers
(
    member_voucher_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    code VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    discount_type VARCHAR(20) NOT NULL CHECK (discount_type IN ('percentage', 'fixed_amount')),
    discount_value DECIMAL(10,2) NOT NULL,
    min_purchase_amount DECIMAL(10,2) DEFAULT 0,
    max_discount_amount DECIMAL(10,2),
    required_points INT NOT NULL,
    -- Points required to use
    usage_limit_per_user INT DEFAULT 1,
    start_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL,
    is_active BIT DEFAULT 1,
    created_at DATETIME DEFAULT GETDATE()
);

-- User Voucher Usage Tracking
CREATE TABLE UserVoucherUsage
(
    usage_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT FOREIGN KEY REFERENCES Users(user_id) ON DELETE CASCADE,
    voucher_type VARCHAR(20) NOT NULL CHECK (voucher_type IN ('public', 'member')),
    voucher_id BIGINT NOT NULL,
    -- ID from PublicVouchers or MemberVouchers
    used_at DATETIME DEFAULT GETDATE(),
    order_id BIGINT NULL
    -- Will be linked when Orders table is created
);

-- =============================================
-- BRAND MANAGEMENT (NEW)
-- =============================================
CREATE TABLE Brands
(
    brand_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    brand_name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(MAX),
    logo_url VARCHAR(MAX),
    website VARCHAR(255),
    contact_email VARCHAR(100),
    contact_phone VARCHAR(20),
    is_active BIT DEFAULT 1,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE()
);

-- =============================================
-- PRODUCT ATTRIBUTES (NEW)
-- =============================================
-- Colors
CREATE TABLE Colors
(
    color_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    color_name VARCHAR(50) NOT NULL UNIQUE,
    color_code VARCHAR(10),
    -- Hex code: #FF0000
    is_active BIT DEFAULT 1,
    created_at DATETIME DEFAULT GETDATE()
);

-- Sizes
CREATE TABLE Sizes
(
    size_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    size_name VARCHAR(20) NOT NULL UNIQUE,
    -- XS, S, M, L, XL, XXL
    size_order INT,
    -- For sorting: 1, 2, 3, 4, 5, 6
    is_active BIT DEFAULT 1,
    created_at DATETIME DEFAULT GETDATE()
);

-- =============================================
-- CATEGORY MANAGEMENT (UNCHANGED)
-- =============================================
CREATE TABLE Categories
(
    category_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(MAX),
    parent_id BIGINT NULL FOREIGN KEY REFERENCES Categories(category_id) ON DELETE NO ACTION,
    is_active BIT DEFAULT 1,
    created_at DATETIME DEFAULT GETDATE(),
    deleted_at DATETIME NULL
);

-- =============================================
-- PRODUCT MANAGEMENT (UPDATED)
-- =============================================
CREATE TABLE Products
(
    product_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    sku VARCHAR(50) NOT NULL UNIQUE,
    -- Main product SKU
    barcode VARCHAR(100) UNIQUE,
    -- Main product barcode
    name VARCHAR(100) NOT NULL,
    description VARCHAR(MAX),
    base_price DECIMAL(10,2) NOT NULL,
    category_id BIGINT NULL FOREIGN KEY REFERENCES Categories(category_id) ON DELETE SET NULL,
    brand_id BIGINT NULL FOREIGN KEY REFERENCES Brands(brand_id) ON DELETE SET NULL,
    is_active BIT DEFAULT 1,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE(),
    deleted_at DATETIME NULL
);

CREATE INDEX idx_products_category ON Products(category_id);
CREATE INDEX idx_products_brand ON Products(brand_id);
CREATE INDEX idx_products_sku ON Products(sku);
CREATE INDEX idx_products_barcode ON Products(barcode);

-- =============================================
-- PRODUCT VARIANTS (IMPROVED)
-- =============================================
CREATE TABLE ProductVariants
(
    variant_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    product_id BIGINT NOT NULL FOREIGN KEY REFERENCES Products(product_id) ON DELETE CASCADE,
    variant_sku VARCHAR(50) NOT NULL UNIQUE,
    -- Specific variant SKU
    variant_barcode VARCHAR(100) UNIQUE,
    -- Specific variant barcode
    size_id BIGINT NOT NULL FOREIGN KEY REFERENCES Sizes(size_id),
    color_id BIGINT NOT NULL FOREIGN KEY REFERENCES Colors(color_id),
    quantity_in_stock INT DEFAULT 0,
    additional_price DECIMAL(10,2) DEFAULT 0.00,
    -- Extra cost for this variant
    weight DECIMAL(8,2),
    -- Weight in kg for shipping
    is_active BIT DEFAULT 1,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE()
);

CREATE INDEX idx_variants_product ON ProductVariants(product_id);
CREATE INDEX idx_variants_sku ON ProductVariants(variant_sku);
CREATE INDEX idx_variants_barcode ON ProductVariants(variant_barcode);

-- =============================================
-- PRODUCT IMAGES
-- =============================================
CREATE TABLE ProductImages
(
    image_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    product_id BIGINT NOT NULL FOREIGN KEY REFERENCES Products(product_id) ON DELETE NO ACTION,
    variant_id BIGINT NULL FOREIGN KEY REFERENCES ProductVariants(variant_id) ON DELETE SET NULL,
    image_url VARCHAR(MAX) NOT NULL,
    alt_text VARCHAR(255),
    image_type VARCHAR(20) DEFAULT 'product' CHECK (image_type IN ('product', 'variant', 'thumbnail', 'detail')),
    display_order INT DEFAULT 1,
    is_active BIT DEFAULT 1,
    created_at DATETIME DEFAULT GETDATE()
);

CREATE INDEX idx_images_product ON ProductImages(product_id);
CREATE INDEX idx_images_variant ON ProductImages(variant_id);

-- =============================================
-- CART MANAGEMENT (UPDATED)
-- =============================================
CREATE TABLE Cart
(
    cart_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT NOT NULL FOREIGN KEY REFERENCES Users(user_id) ON DELETE CASCADE,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE()
);

CREATE TABLE CartItems
(
    cart_item_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    cart_id BIGINT NOT NULL FOREIGN KEY REFERENCES Cart(cart_id) ON DELETE CASCADE,
    variant_id BIGINT NOT NULL FOREIGN KEY REFERENCES ProductVariants(variant_id),
    quantity INT NOT NULL CHECK (quantity > 0),
    unit_price DECIMAL(10,2) NOT NULL,
    -- Price at time of adding to cart
    total_price AS (quantity * unit_price) PERSISTED,
    -- Computed column
    added_at DATETIME DEFAULT GETDATE()
);

-- =============================================
-- ORDER ADDRESSES (NEW)
-- =============================================
CREATE TABLE OrderAddresses
(
    order_address_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    street_address VARCHAR(255) NOT NULL,
    ward VARCHAR(100),
    district VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    province VARCHAR(100) NOT NULL,
    postal_code VARCHAR(20),
    country VARCHAR(50) DEFAULT 'Vietnam',
    recipient_name VARCHAR(100) NOT NULL,
    recipient_phone VARCHAR(20) NOT NULL,
    created_at DATETIME DEFAULT GETDATE()
);

-- =============================================
-- ORDERS (UPDATED)
-- =============================================
CREATE TABLE Orders
(
    order_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT NOT NULL FOREIGN KEY REFERENCES Users(user_id) ON DELETE CASCADE,
    order_address_id BIGINT NOT NULL FOREIGN KEY REFERENCES OrderAddresses(order_address_id),
    order_date DATETIME DEFAULT GETDATE(),
    subtotal DECIMAL(10,2) NOT NULL,
    -- Before discount
    discount_amount DECIMAL(10,2) DEFAULT 0.00,
    shipping_fee DECIMAL(10,2) DEFAULT 0.00,
    total_amount DECIMAL(10,2) NOT NULL,
    -- Final total
    order_status VARCHAR(20) DEFAULT 'pending' CHECK (order_status IN ('pending', 'confirmed', 'processing', 'shipped', 'delivered', 'cancelled')),
    payment_method VARCHAR(20) NOT NULL CHECK (payment_method IN ('cod', 'credit_card', 'paypal', 'bank_transfer')),
    payment_status VARCHAR(20) DEFAULT 'unpaid' CHECK (payment_status IN ('unpaid', 'paid', 'partial', 'refunded')),
    voucher_code VARCHAR(20),
    -- Applied voucher
    notes VARCHAR(MAX),
    cancelled_at DATETIME NULL,
    cancelled_reason VARCHAR(255)
);

CREATE INDEX idx_orders_user ON Orders(user_id);
CREATE INDEX idx_orders_status ON Orders(order_status);
CREATE INDEX idx_orders_date ON Orders(order_date);

-- =============================================
-- ORDER ITEMS (UNCHANGED)
-- =============================================
CREATE TABLE OrderItems
(
    order_item_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    order_id BIGINT NOT NULL FOREIGN KEY REFERENCES Orders(order_id) ON DELETE CASCADE,
    variant_id BIGINT NOT NULL FOREIGN KEY REFERENCES ProductVariants(variant_id),
    quantity INT NOT NULL CHECK (quantity > 0),
    unit_price DECIMAL(10,2) NOT NULL,
    total_price AS (quantity * unit_price) PERSISTED
);

-- =============================================
-- PAYMENTS (UPDATED)
-- =============================================
CREATE TABLE Payments
(
    payment_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    order_id BIGINT NOT NULL FOREIGN KEY REFERENCES Orders(order_id) ON DELETE CASCADE,
    payment_date DATETIME DEFAULT GETDATE(),
    amount DECIMAL(10,2) NOT NULL,
    -- Keep this for tracking partial payments
    payment_method VARCHAR(20) NOT NULL CHECK (payment_method IN ('cod', 'credit_card', 'paypal', 'bank_transfer')),
    payment_status VARCHAR(20) DEFAULT 'completed' CHECK (payment_status IN ('pending', 'completed', 'failed', 'refunded')),
    transaction_id VARCHAR(100),
    gateway_response VARCHAR(MAX),
    -- Store payment gateway response
    created_at DATETIME DEFAULT GETDATE()
);

-- =============================================
-- SHIPPING (UPDATED)
-- =============================================
CREATE TABLE Shipping
(
    shipping_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    order_id BIGINT NOT NULL FOREIGN KEY REFERENCES Orders(order_id) ON DELETE CASCADE,
    shipping_method VARCHAR(50) NOT NULL,
    tracking_number VARCHAR(100),
    shipping_status VARCHAR(20) DEFAULT 'processing' CHECK (shipping_status IN ('processing', 'shipped', 'in_transit', 'delivered', 'failed')),
    shipping_fee DECIMAL(10,2) NOT NULL,
    estimated_delivery_date DATE,
    shipped_at DATETIME,
    notes VARCHAR(255),
    created_at DATETIME DEFAULT GETDATE()
);

-- =============================================
-- REVIEWS (UNCHANGED)
-- =============================================
CREATE TABLE Reviews
(
    review_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    product_id BIGINT NOT NULL FOREIGN KEY REFERENCES Products(product_id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL FOREIGN KEY REFERENCES Users(user_id) ON DELETE CASCADE,
    order_id BIGINT NULL FOREIGN KEY REFERENCES Orders(order_id),
    -- Link to verified purchase
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comment VARCHAR(MAX),
    is_verified_purchase BIT DEFAULT 0,
    created_at DATETIME DEFAULT GETDATE(),
    updated_at DATETIME DEFAULT GETDATE()
);

-- =============================================
-- WISHLISTS (UNCHANGED)
-- =============================================
CREATE TABLE Wishlists
(
    wishlist_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    user_id BIGINT NOT NULL FOREIGN KEY REFERENCES Users(user_id) ON DELETE CASCADE,
    created_at DATETIME DEFAULT GETDATE()
);

CREATE TABLE WishlistItems
(
    wishlist_item_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    wishlist_id BIGINT NOT NULL FOREIGN KEY REFERENCES Wishlists(wishlist_id) ON DELETE CASCADE,
    product_id BIGINT NOT NULL FOREIGN KEY REFERENCES Products(product_id),
    added_at DATETIME DEFAULT GETDATE()
);

-- =============================================
-- INVENTORY LOGS (IMPROVED)
-- =============================================
CREATE TABLE InventoryLogs
(
    log_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    variant_id BIGINT NOT NULL FOREIGN KEY REFERENCES ProductVariants(variant_id),
    change_type VARCHAR(20) NOT NULL CHECK (change_type IN ('stock_in', 'stock_out', 'adjustment', 'damaged', 'returned')),
    quantity_before INT NOT NULL,
    quantity_changed INT NOT NULL,
    quantity_after INT NOT NULL,
    reason VARCHAR(255),
    reference_type VARCHAR(20),
    -- 'order', 'return', 'adjustment', etc.
    reference_id BIGINT,
    -- order_id, return_id, etc.
    changed_by BIGINT FOREIGN KEY REFERENCES Users(user_id),
    changed_at DATETIME DEFAULT GETDATE()
);

-- =============================================
-- ACTIVITY LOGS (IMPROVED)
-- =============================================
CREATE TABLE ActivityLogs
(
    log_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    table_name VARCHAR(50) NOT NULL,
    action_type VARCHAR(10) NOT NULL CHECK (action_type IN ('INSERT', 'UPDATE', 'DELETE')),
    record_id BIGINT NOT NULL,
    old_values VARCHAR(MAX),
    -- JSON format
    new_values VARCHAR(MAX),
    -- JSON format
    changed_by BIGINT FOREIGN KEY REFERENCES Users(user_id),
    changed_at DATETIME DEFAULT GETDATE(),
    ip_address VARCHAR(45),
    user_agent VARCHAR(MAX)
);

-- =============================================
-- ADDITIONAL CONSTRAINTS AND INDEXES
-- =============================================

-- Foreign key for UserVoucherUsage order_id (added after Orders table creation)
ALTER TABLE UserVoucherUsage
    ADD CONSTRAINT FK_UserVoucherUsage_Orders
        FOREIGN KEY (order_id) REFERENCES Orders(order_id);

-- Additional useful indexes
CREATE INDEX idx_product_variants_stock ON ProductVariants(quantity_in_stock);
CREATE INDEX idx_orders_payment_status ON Orders(payment_status);
CREATE INDEX idx_cart_items_variant ON CartItems(variant_id);
CREATE INDEX idx_inventory_logs_variant ON InventoryLogs(variant_id);
CREATE INDEX idx_inventory_logs_date ON InventoryLogs(changed_at);