
-- DỮ LIỆU MẪU CHO GenZFashion

-- 1. Addresses
INSERT INTO Addresses (street_address, ward, district, city, province, postal_code)
VALUES
    (N'12 Lê Lợi', N'Phường Bến Nghé', N'Quận 1', 'TP.HCM', 'TP.HCM', '70000'),
    (N'45 Trần Hưng Đạo', N'Phường Cầu Kho', N'Quận 1', 'TP.HCM', 'TP.HCM', '70000'),
    (N'99 Nguyễn Huệ', N'Phường Bến Thành', N'Quận 1', 'TP.HCM', 'TP.HCM', '70000'),
    (N'88 Phạm Văn Đồng', N'Phường Linh Đông', N'Thủ Đức', 'TP.HCM', 'TP.HCM', '70000'),
    (N'17 Hai Bà Trưng', N'Phường 6', N'Quận 3', 'TP.HCM', 'TP.HCM', '70000');

-- 2. Users
INSERT INTO Users (username, email, password_hash, full_name, phone_number, address_id)
VALUES
    ('nguyenvana', 'vana@example.com', 'hashed123', N'Nguyễn Văn A', '0912345678', 1),
    ('tranthib', 'thib@example.com', 'hashed456', N'Trần Thị B', '0923456789', 2),
    ('lequangc', 'quangc@example.com', 'hashed789', N'Lê Quang C', '0934567890', 3),
    ('phamthid', 'thid@example.com', 'hashed321', N'Phạm Thị D', '0945678901', 4),
    ('doanhieue', 'hieue@example.com', 'hashed654', N'Đoàn Hiếu E', '0956789012', 5);

-- 3. UserOAuth
INSERT INTO UserOAuth (user_id, provider, provider_user_id, provider_token)
VALUES
    (1, 'google', 'gg_12345', 'token_gg_123'),
    (2, 'facebook', 'fb_23456', 'token_fb_234'),
    (3, 'google', 'gg_34567', 'token_gg_345'),
    (4, 'facebook', 'fb_45678', 'token_fb_456'),
    (5, 'google', 'gg_56789', 'token_gg_567');

-- 4. UserPoints
INSERT INTO UserPoints (user_id, points_balance)
VALUES
    (1, 150),
    (2, 300),
    (3, 0),
    (4, 500),
    (5, 100);

-- 5. PublicVouchers
INSERT INTO PublicVouchers (code, name, description, discount_type, discount_value, min_purchase_amount, max_discount_amount, required_points, usage_limit, start_date, end_date)
VALUES
    ('FREESHIP', N'Miễn phí vận chuyển', N'Áp dụng cho đơn từ 200K', 'fixed_amount', 30000, 200000, 30000, 0, 100, GETDATE(), DATEADD(DAY, 30, GETDATE())),
    ('SALE10', N'Giảm 10%', N'Giảm giá 10% cho đơn từ 300K', 'percentage', 10, 300000, 50000, 50, 200, GETDATE(), DATEADD(DAY, 30, GETDATE())),
    ('VIP100K', N'Giảm 100K cho VIP', N'Voucher cần điểm', 'fixed_amount', 100000, 500000, 100000, 200, 50, GETDATE(), DATEADD(DAY, 60, GETDATE())),
    ('SUMMER20', N'Giảm 20%', N'Khuyến mãi mùa hè', 'percentage', 20, 0, 100000, 0, 300, GETDATE(), DATEADD(DAY, 15, GETDATE())),
    ('LIMIT50', N'Giảm 50K số lượng giới hạn', N'Chỉ 50 người dùng đầu tiên', 'fixed_amount', 50000, 250000, 50000, 0, 50, GETDATE(), DATEADD(DAY, 10, GETDATE()));

-- (Tiếp tục ở phần sau)

-- ===== PHẦN TIẾP THEO =====

-- 6. Brands
INSERT INTO Brands (brand_name, description, logo_url, website, contact_email, contact_phone)
VALUES
    ('CoolWear', N'Thời trang trẻ trung năng động', 'https://cdn.example.com/logo1.png', 'https://coolwear.vn', 'contact@coolwear.vn', '0909090901'),
    ('UrbanStyle', N'Phong cách thành thị hiện đại', 'https://cdn.example.com/logo2.png', 'https://urbanstyle.vn', 'support@urbanstyle.vn', '0909090902'),
    ('GenZTrend', N'Hợp xu hướng giới trẻ', 'https://cdn.example.com/logo3.png', 'https://genztrend.vn', 'info@genztrend.vn', '0909090903'),
    ('BasicFit', N'Đơn giản, dễ phối đồ', 'https://cdn.example.com/logo4.png', 'https://basicfit.vn', 'hello@basicfit.vn', '0909090904'),
    ('LuxuryVibe', N'Phong cách cao cấp, thanh lịch', 'https://cdn.example.com/logo5.png', 'https://luxuryvibe.vn', 'lux@luxuryvibe.vn', '0909090905');

-- 7. Colors
INSERT INTO Colors (color_name, color_code)
VALUES
    ('Red', '#FF0000'),
    ('Blue', '#0000FF'),
    ('Black', '#000000'),
    ('White', '#FFFFFF'),
    ('Beige', '#F5F5DC');

-- 8. Sizes
INSERT INTO Sizes (size_name, size_order)
VALUES
    ('XS', 1),
    ('S', 2),
    ('M', 3),
    ('L', 4),
    ('XL', 5);

-- 9. Categories
INSERT INTO Categories (name, description)
VALUES
    (N'Áo', N'Các loại áo thời trang'),
    (N'Quần', N'Các loại quần cá tính'),
    (N'Phụ kiện', N'Phụ kiện thời trang đa dạng'),
    (N'Áo thun', N'Áo thun thuộc danh mục Áo'),
    (N'Quần jean', N'Quần jean thuộc danh mục Quần');

-- 10. Products
INSERT INTO Products (sku, barcode, name, description, base_price, category_id, brand_id)
VALUES
    ('SKU001', 'BC001', N'Áo thun trắng basic', N'Chất cotton 100%', 199000, 4, 1),
    ('SKU002', 'BC002', N'Quần jean rách gối', N'Phong cách năng động', 399000, 5, 2),
    ('SKU003', 'BC003', N'Mũ lưỡi trai GenZ', N'Logo nổi bật', 149000, 3, 3),
    ('SKU004', 'BC004', N'Áo sơ mi caro đỏ', N'Phối đồ cực dễ', 299000, 1, 4),
    ('SKU005', 'BC005', N'Quần short kaki', N'Thoải mái ngày hè', 259000, 2, 1);

-- 11. ProductVariants
INSERT INTO ProductVariants (product_id, variant_sku, variant_barcode, size_id, color_id, quantity_in_stock, additional_price, weight)
VALUES
    (1, 'SKU001-RD-M', 'VBC001', 3, 1, 50, 0, 0.25),
    (2, 'SKU002-BL-L', 'VBC002', 4, 2, 30, 20000, 0.5),
    (3, 'SKU003-BK-F', 'VBC003', 3, 3, 100, 0, 0.2),
    (4, 'SKU004-RD-M', 'VBC004', 3, 1, 20, 15000, 0.3),
    (5, 'SKU005-WH-S', 'VBC005', 2, 4, 60, 0, 0.4);

-- 12. ProductImages
INSERT INTO ProductImages (product_id, variant_id, image_url, alt_text, image_type, display_order)
VALUES
    (1, 1, 'https://cdn.example.com/sku001_1.jpg', N'Áo thun trắng M', 'product', 1),
    (2, 2, 'https://cdn.example.com/sku002_1.jpg', N'Quần jean xanh L', 'product', 1),
    (3, 3, 'https://cdn.example.com/sku003_1.jpg', N'Mũ lưỡi trai đen', 'product', 1),
    (4, 4, 'https://cdn.example.com/sku004_1.jpg', N'Sơ mi caro đỏ M', 'product', 1),
    (5, 5, 'https://cdn.example.com/sku005_1.jpg', N'Short trắng S', 'product', 1);

-- 13. Cart
INSERT INTO Cart (user_id)
VALUES
    (1), (2), (3), (4), (5);

-- 14. CartItems
INSERT INTO CartItems (cart_id, variant_id, quantity, unit_price)
VALUES
    (1, 1, 2, 199000),
    (2, 2, 1, 419000),
    (3, 3, 3, 149000),
    (4, 4, 1, 314000),
    (5, 5, 2, 259000);

-- 15. Orders
INSERT INTO Orders (user_id, address_id, subtotal, discount_amount, shipping_fee, total_amount, payment_method)
VALUES
    (1, 1, 398000, 30000, 30000, 398000, 'cod'),
    (2, 2, 419000, 41900, 20000, 397100, 'paypal'),
    (3, 3, 447000, 0, 25000, 472000, 'credit_card'),
    (4, 4, 314000, 31400, 30000, 312600, 'bank_transfer'),
    (5, 5, 518000, 0, 20000, 538000, 'cod');

-- (phần còn lại tiếp theo)

-- ===== PHẦN CUỐI =====

-- 16. OrderItems
INSERT INTO OrderItems (order_id, variant_id, quantity, unit_price)
VALUES
    (1, 1, 2, 199000),
    (2, 2, 1, 419000),
    (3, 3, 3, 149000),
    (4, 4, 1, 314000),
    (5, 5, 2, 259000);

-- 17. Payments
INSERT INTO Payments (order_id, amount, payment_method)
VALUES
    (1, 398000, 'cod'),
    (2, 397100, 'paypal'),
    (3, 472000, 'credit_card'),
    (4, 312600, 'bank_transfer'),
    (5, 538000, 'cod');

-- 18. Shipping
INSERT INTO Shipping (order_id, shipping_method, tracking_number, shipping_fee)
VALUES
    (1, N'Giao hàng nhanh', 'GHN123', 30000),
    (2, N'Giao hàng tiết kiệm', 'GHTK234', 20000),
    (3, 'VNPost', 'VNPT345', 25000),
    (4, 'GrabExpress', 'GRB456', 30000),
    (5, 'ShopeeXpress', 'SPEX567', 20000);

-- 19. Reviews
INSERT INTO Reviews (product_id, user_id, order_id, rating, comment, is_verified_purchase)
VALUES
    (1, 1, 1, 5, N'Áo đẹp, mát mẻ', 1),
    (2, 2, 2, 4, N'Quần chất lượng, đúng mô tả', 1),
    (3, 3, 3, 5, N'Mũ xịn sò', 1),
    (4, 4, 4, 3, N'Áo hơi rộng nhưng ổn', 1),
    (5, 5, 5, 4, N'Vải mịn, giá hợp lý', 1);

-- 20. Wishlists
INSERT INTO Wishlists (user_id)
VALUES
    (1), (2), (3), (4), (5);

-- 21. WishlistItems
INSERT INTO WishlistItems (wishlist_id, product_id)
VALUES
    (1, 2),
    (2, 3),
    (3, 1),
    (4, 5),
    (5, 4);

-- 22. UserVoucherUsage
INSERT INTO UserVoucherUsage (user_id, voucher_id, points_spent, order_id)
VALUES
    (1, 1, 0, 1),
    (2, 2, 0, 2),
    (3, 3, 200, 3),
    (4, 4, 0, 4),
    (5, 5, 0, 5);

-- 23. PointsTransactions
INSERT INTO PointsTransactions (user_id, transaction_type, points, description, reference_type, reference_id)
VALUES
    (1, 'earned', 100, N'Tích điểm mua hàng', 'order', 1),
    (2, 'spent', -200, N'Dùng điểm lấy voucher', 'voucher', 2),
    (3, 'earned', 300, N'Tặng sinh nhật', 'manual', NULL),
    (4, 'adjustment', 50, N'Điều chỉnh thủ công', 'manual', NULL),
    (5, 'expired', -100, N'Điểm hết hạn', 'system', NULL);

-- 24. InventoryLogs
INSERT INTO InventoryLogs (variant_id, change_type, quantity_before, quantity_changed, quantity_after, reason, reference_type, reference_id, changed_by)
VALUES
    (1, 'stock_out', 52, -2, 50, N'Bán hàng', 'order', 1, 1),
    (2, 'stock_out', 31, -1, 30, N'Bán hàng', 'order', 2, 2),
    (3, 'stock_out', 103, -3, 100, N'Bán hàng', 'order', 3, 3),
    (4, 'stock_out', 21, -1, 20, N'Bán hàng', 'order', 4, 4),
    (5, 'stock_out', 62, -2, 60, N'Bán hàng', 'order', 5, 5);

-- 25. ActivityLogs
INSERT INTO ActivityLogs (table_name, action_type, record_id, old_values, new_values, changed_by, ip_address, user_agent)
VALUES
    ('Users', 'UPDATE', 1, '{"is_active":1}', '{"is_active":0}', 1, '192.168.1.1', 'Mozilla/5.0'),
    ('Products', 'INSERT', 2, NULL, N'{"name":"Quần jean rách"}', 2, '192.168.1.2', 'Mozilla/5.0'),
    ('Orders', 'DELETE', 3, '{"status":"pending"}', NULL, 3, '192.168.1.3', 'Mozilla/5.0'),
    ('UserPoints', 'UPDATE', 4, '{"points":450}', '{"points":500}', 4, '192.168.1.4', 'Mozilla/5.0'),
    ('Reviews', 'INSERT', 5, NULL, '{"rating":4}', 5, '192.168.1.5', 'Mozilla/5.0');