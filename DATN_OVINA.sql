
-- ====================================================
-- CHẠY TỪ 1-3 
-- ====================================================
ALTER TABLE PRODUCT
ADD is_deleted BIT NOT NULL DEFAULT 0;

IF DB_ID('DATN_OVINA') IS NOT NULL
BEGIN
   ALTER DATABASE DATN_OVINA SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
   DROP DATABASE DATN_OVINA;
END
GO

CREATE DATABASE DATN_OVINA;
GO

USE DATN_OVINA;
GO

-- =========================
-- 1. CREATE TABLES (parents first, minimal FK to allow data load)
-- =========================

--1 ROLE
CREATE TABLE dbo.ROLE (
    role_id INT IDENTITY(1,1) PRIMARY KEY,
    role_name NVARCHAR(50) NOT NULL UNIQUE,
    role_description NVARCHAR(255) NULL
);
GO

--2 AUTHORITIES
CREATE TABLE dbo.AUTHORITIES (
    authority_id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    employee_id INT NOT NULL
);
GO



--3 USER (use bracket because USER is reserved-ish)
CREATE TABLE dbo.[USER] (
    user_id INT IDENTITY(1,1) PRIMARY KEY,
    -- computed persisted code
    --user_code AS ('US' + RIGHT('0000' + CAST(user_id AS NVARCHAR(4)),4)) PERSISTED,
    email NVARCHAR(100) NOT NULL UNIQUE,
    password NVARCHAR(255) NOT NULL,
    first_name NVARCHAR(30) NULL,
    last_name  NVARCHAR(30) NULL,
    phone NVARCHAR(20) NULL,
    avatar_url NVARCHAR(255) NULL,
    gender NVARCHAR(10) NULL,
    dob DATE NULL,
    created_at DATETIME2 DEFAULT SYSUTCDATETIME() NOT NULL,
    role_id INT NOT NULL,
    verified BIT DEFAULT 0,
    login_attempts INT DEFAULT 0,
    aes_key NVARCHAR(MAX) NULL
);
GO

--4 USER_POINT
CREATE TABLE dbo.USER_POINT (
    user_point_id INT IDENTITY(1,1) PRIMARY KEY,   -- Khớp với userPointID
    user_id INT NOT NULL,                          -- FK đến USERS
    order_id INT NULL,                             -- FK đến ORDER (nếu điểm từ đơn hàng)
    point INT NOT NULL,                            -- Số điểm cộng/trừ
    [date] DATE DEFAULT GETDATE(),                 -- Ngày ghi nhận
    [status] NVARCHAR(50) DEFAULT 'active'        -- Trạng thái (active, expired, used...)
);
GO
--5 EMPLOYEE
CREATE TABLE dbo.EMPLOYEE (
    employee_id INT IDENTITY(1,1) PRIMARY KEY,
    -- computed persisted code
    --employee_code AS ('EM' + RIGHT('0000' + CAST(user_id AS NVARCHAR(4)),4)) PERSISTED,
    email NVARCHAR(100) NOT NULL UNIQUE,
    password NVARCHAR(255) NOT NULL,
    first_name NVARCHAR(30) NULL,
    last_name  NVARCHAR(30) NULL,
    phone NVARCHAR(20) NULL,
    avatar_url NVARCHAR(255) NULL,
    gender NVARCHAR(10) NULL,
    dob DATE NULL,
    created_at DATETIME2 DEFAULT SYSUTCDATETIME() NOT NULL,
    role_id INT NOT NULL,
    verified BIT DEFAULT 0,
    login_attempts INT DEFAULT 0,
    aes_key NVARCHAR(MAX) NULL
);
GO

--6 ADDRESS
CREATE TABLE dbo.ADDRESS (
    address_id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT NOT NULL,
    receiver_name NVARCHAR(150) NOT NULL,
    phone NVARCHAR(20) NOT NULL,
    province NVARCHAR(100) NOT NULL,
    district NVARCHAR(100) NOT NULL,
    ward NVARCHAR(100) NULL,
    detail_address NVARCHAR(255) NOT NULL,
    is_default BIT DEFAULT 0
);
GO

--7 CATEGORY
CREATE TABLE dbo.CATEGORY (
    category_id INT IDENTITY(1,1) PRIMARY KEY,
    category_name NVARCHAR(150) NOT NULL UNIQUE,
    description NVARCHAR(255) NULL,
    created_at DATETIME2 DEFAULT SYSUTCDATETIME()
);
GO

--8 SUBCATEGORY
CREATE TABLE dbo.SUBCATEGORY (
    subcategory_id INT IDENTITY(1,1) PRIMARY KEY,
    subcategory_name NVARCHAR(150) NOT NULL,
    category_id INT NOT NULL,
    created_at DATETIME2 DEFAULT SYSUTCDATETIME()
);
GO

--9 PRODUCT (has product_code computed)
CREATE TABLE dbo.PRODUCT (
    product_id INT IDENTITY(1,1) PRIMARY KEY,
    --product_code AS ('PD' + RIGHT('0000' + CAST(product_id AS NVARCHAR(4)),4)) PERSISTED,
    category_id INT NULL,
    subcategory_id INT NULL,
    product_name NVARCHAR(255) NOT NULL,
    slug NVARCHAR(255) NULL,
    description NVARCHAR(MAX) NULL,
    status NVARCHAR(20) DEFAULT 'active',
    created_at DATETIME2 DEFAULT SYSUTCDATETIME(),
    updated_at DATETIME2 NULL
);
GO

--10 PRODUCT_VARIANT
CREATE TABLE dbo.PRODUCT_VARIANT (
    variant_id INT IDENTITY(1,1) PRIMARY KEY,
    product_id INT NOT NULL,
    sku NVARCHAR(50) NULL UNIQUE,
    size NVARCHAR(30) NULL,
    color NVARCHAR(50) NULL,
    price DECIMAL(12,2) NOT NULL,
    cost_price DECIMAL(12,2) NULL,
    stock_quantity INT DEFAULT 0,
    weight DECIMAL(8,2) NULL,
    created_at DATETIME2 DEFAULT SYSUTCDATETIME()
);
GO

--11 IMAGE
CREATE TABLE dbo.IMAGE (
    image_id INT IDENTITY(1,1) PRIMARY KEY,
    product_id INT NOT NULL,
    image_url NVARCHAR(255) NOT NULL,
    is_primary BIT DEFAULT 0,
    position INT DEFAULT 0
);
GO

--12 WISHLIST
CREATE TABLE dbo.WISHLIST (
    wishlist_id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT NOT NULL,
    product_id INT NOT NULL
);
GO

--13 CART
CREATE TABLE dbo.CART (
    cart_id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT UNIQUE NOT NULL,
    created_at DATETIME2 DEFAULT SYSUTCDATETIME(),
    updated_at DATETIME2 NULL
);
GO

--14 CART_ITEM
CREATE TABLE dbo.CART_ITEM (
    cart_item_id INT IDENTITY(1,1) PRIMARY KEY,
    cart_id INT NOT NULL,
    variant_id INT NOT NULL,
    quantity INT DEFAULT 1 NOT NULL,
    price_snapshot DECIMAL(12,2) NOT NULL,
    added_at DATETIME2 DEFAULT SYSUTCDATETIME()
);
GO

--15 DELIVERY
CREATE TABLE dbo.DELIVERY (
    delivery_id INT IDENTITY(1,1) PRIMARY KEY,
    shipping_provider NVARCHAR(100) NULL,
    tracking_number NVARCHAR(100) NULL UNIQUE,
    shipping_fee DECIMAL(10,2) DEFAULT 0,
    delivery_status NVARCHAR(20) DEFAULT 'preparing',
    estimated_date DATE NULL,
    created_at DATETIME2 DEFAULT SYSUTCDATETIME()
);
GO

--16 DISCOUNT
CREATE TABLE dbo.DISCOUNT (
    discount_id INT IDENTITY(1,1) PRIMARY KEY,
    code NVARCHAR(50) UNIQUE NOT NULL,
    discount_name NVARCHAR(150) NULL,
    discount_type NVARCHAR(10) NULL,
    value DECIMAL(12,2) NOT NULL,
    min_order_value DECIMAL(12,2) DEFAULT 0,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    usage_limit INT NULL,
    per_user_limit INT NULL,
    is_active BIT DEFAULT 1,
    created_at DATETIME2 DEFAULT SYSUTCDATETIME()
);
GO

--17 DISCOUNT_SP
CREATE TABLE dbo.DISCOUNT_SP (
    discountsp_id INT IDENTITY(1,1) PRIMARY KEY,
    product_id INT NOT NULL,
    discount_type NVARCHAR(10) NOT NULL,
    value DECIMAL(12,2) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    is_active BIT DEFAULT 1
);
GO

--18 ORDER (SalesOrder)
CREATE TABLE dbo.[ORDER] (
    order_id INT IDENTITY(1,1) PRIMARY KEY,
    order_number NVARCHAR(20) NULL,
    user_id INT NOT NULL,
    address_id INT NULL,
    delivery_id INT NULL,
    discount_id INT NULL,
    order_date DATETIME2 DEFAULT SYSUTCDATETIME(),
    status NVARCHAR(20) DEFAULT 'pending',
    total_amount DECIMAL(12,2) NOT NULL,
    delivery_fee DECIMAL(10,2) DEFAULT 0,      -- ✅ Thêm cột phí giao hàng
    used_points INT DEFAULT 0,
    payment_method NVARCHAR(50) NOT NULL,
    note NVARCHAR(500) NULL
);
GO

--19 ORDER_DETAIL
CREATE TABLE dbo.ORDER_DETAIL (
    order_detail_id INT IDENTITY(1,1) PRIMARY KEY,
    order_id INT NOT NULL,
    variant_id INT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(12,2) NOT NULL,
    subtotal AS (quantity * unit_price) PERSISTED
);
GO

--20 TRANSACTION
CREATE TABLE dbo.[TRANSACTION] (
    transaction_id INT IDENTITY(1,1) PRIMARY KEY,
    order_id INT NOT NULL,
    transaction_code NVARCHAR(100) UNIQUE NULL,
    payment_gateway NVARCHAR(50) NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    transaction_status NVARCHAR(20) DEFAULT 'pending',
    created_at DATETIME2 DEFAULT SYSUTCDATETIME(),
    paid_at DATETIME2 NULL
);
GO


--21 REVIEW
CREATE TABLE dbo.REVIEW (
    review_id INT IDENTITY(1,1) PRIMARY KEY,
    product_id INT NOT NULL,
    user_id INT NOT NULL,
    rating INT NOT NULL,
    comment NVARCHAR(MAX) NULL,
    created_at DATETIME2 DEFAULT SYSUTCDATETIME()
);
GO

--22 SUPPORT
CREATE TABLE dbo.SUPPORT (
    support_id INT IDENTITY(1,1) PRIMARY KEY,
    user_id INT NULL,
    subject NVARCHAR(200) NOT NULL,
    message NVARCHAR(MAX) NOT NULL,
    status NVARCHAR(20) DEFAULT 'open',
    created_at DATETIME2 DEFAULT SYSUTCDATETIME(),
    response NVARCHAR(MAX) NULL,
    responded_at DATETIME2 NULL
);
GO

-- =========================
--  2 DỮ LIỆU MẪU HỢP LỆ
-- =========================

-- ROLES
INSERT INTO dbo.ROLE (role_name, role_description) VALUES
(N'ADMIN', N'Quản trị hệ thống'),
(N'EMPLOYEE', N'Nhân viên vận hành'),
(N'USER', N'Khách hàng');
GO

-- EMPLOYEE (tạo 1 nhân viên mẫu để ràng buộc AUTHORITIES)
INSERT INTO dbo.EMPLOYEE (email, password, first_name, last_name, role_id, verified)
VALUES ('employee@ovina.test', 'employee@123', N'Dương', N'Luật', 2, 1);
GO

-- USERS
INSERT INTO dbo.[USER] (email, password, first_name, last_name, phone, role_id, verified)
VALUES
('admin@ovina.test', 'Admin@123', N'Admin', N'Ovina', '0123456789', 1, 1),
('user1@ovina.test', 'User1@123', N'AN', N'Nguyễn Văn', '0901111222', 3, 1),
('user2@ovina.test', 'User2@123', N'Thu', N'Trần Thị ', '0901111333', 3, 1),
('user3@ovina.test', 'User3@123', N'Chính', N'Phạm Văn', '0901111444', 3, 0);
GO

-- AUTHORITIES (gán quyền cho người dùng và nhân viên)
INSERT INTO dbo.AUTHORITIES (user_id, role_id, employee_id)
VALUES
(1, 1, 1),  -- Admin có role ADMIN, liên kết employee_id 1
(2, 3, 1),  -- User1 role USER, managed by employee 1
(3, 3, 1),
(4, 3, 1);
GO

-- USER_POINT
INSERT INTO dbo.USER_POINT (user_id, point, [status])
VALUES
(2, 1200, 'active'),
(3, 350, 'active'),
(4, 0, 'active');
GO

-- CATEGORY
INSERT INTO dbo.CATEGORY (category_name, description) VALUES
(N'Áo', N'Các loại áo thun, áo sơ mi, hoodie'),
(N'Quần', N'Quần jeans, quần kaki'),
(N'Phụ kiện', N'Nón, thắt lưng, phụ kiện khác');
GO

-- SUBCATEGORY
INSERT INTO dbo.SUBCATEGORY (subcategory_name, category_id) VALUES
(N'Áo Thun', 1),
(N'Hoodie', 1),
(N'Quần Jeans', 2),
(N'Quần Kaki', 2),
(N'Nón Snapback', 3),
(N'Tất', 3);
GO

-- PRODUCT
INSERT INTO dbo.PRODUCT (category_id, subcategory_id, product_name, slug, description)
VALUES
(1, 1, N'Áo Thun Basic Trắng', 'ao-thun-basic-trang', N'Áo thun cotton basic, thoáng mát.'),
(1, 1, N'Áo Thun Đen', 'ao-thun-den', N'Áo cotton in Hades.'),
(1, 2, N'Hoodie Xám', 'hoodie-xam', N'Hoodie nỉ dày, ấm áp.'),
(1, 1, N'Áo Thun Oversize Đen', 'ao-thun-oversize-den', N'Form rộng, phong cách streetwear.'),
(2, 3, N'Quần Jeans Rách Gối', 'quan-jeans-rach-goi', N'Quần jeans slimfit, rách gối.'),
(2, 4, N'Quần Kaki Basic', 'quan-kaki-basic', N'Quần kaki công sở.'),
(3, 5, N'Nón Snapback', 'non-snapback', N'Nón snapback phong cách.'),
(3, 6, N'Tất', 'tat', N'Vớ cao cổ, thấm hút tốt.');
GO

-- PRODUCT_VARIANT
INSERT INTO dbo.PRODUCT_VARIANT (product_id, sku, size, color, price, cost_price, stock_quantity)
VALUES
(1, 'HT-001-WHT-S', 'S', 'Trắng', 199000, 80000, 50),
(1, 'HT-001-WHT-M', 'M', 'Trắng', 199000, 80000, 60),
(2, 'HT-002-BLK-M', 'M', 'Đen', 249000, 100000, 40),
(3, 'HD-003-GRY-L', 'L', 'Xám', 499000, 200000, 25),
(4, 'OV-004-BLK-L', 'L', 'Đen', 259000, 110000, 35),
(5, 'QN-005-BLU-32', '32', 'Xanh', 399000, 180000, 40),
(6, 'QN-006-KKI-32', '32', 'Kaki', 289000, 120000, 45),
(7, 'PK-007-SB-ONE', 'ONE', 'Đen', 199000, 70000, 80),
(8, 'PK-008-SOCK-ONE', 'ONE', 'Trắng', 99000, 30000, 100);
GO

-- IMAGE (chỉ dùng image_url, không thumb_url)
INSERT INTO dbo.IMAGE (product_id, image_url, is_primary, position) VALUES
(1, 'https://product.hstatic.net/1000306633/product/080425.hd5675_369f4e7868bd420d99f4eb79a055298e.jpg', 1, 1),
(2, 'https://product.hstatic.net/1000306633/product/080425.hd5709_dc1a85b9bcc24951a248a2f49cac1c11.jpg', 1, 1),
(3, 'https://cdn.hstatic.net/products/1000306633/_dsf0967_3c4b1ab50b9545cbae6c5efe4f7bb7d5.jpg', 1, 1),
(4, 'https://cdn.hstatic.net/products/1000306633/dsc03012_large_349b52d4917b4a29a018ddfb0ec78b3c.jpeg', 1, 1),
(5, 'https://product.hstatic.net/1000306633/product/hades0085_429826df460d4fedb5eb6d9b47004760.jpg', 1, 1),
(6, 'https://cdn.hstatic.net/products/1000306633/150925.hades2935_large_7bba2cd9d18e47c8975e8701d3ff659a.jpeg', 1, 1),
(7, 'https://product.hstatic.net/1000306633/product/dsc09253_e6e3a6cbcab14090b9953a3090f4fa9f.jpg', 1, 1),
(8, 'https://product.hstatic.net/1000306633/product/hd_t6.2461_1_a270a42cb6bb407b8dc4a13f7ae95a55.jpg', 1, 1);
GO

-- DISCOUNT
INSERT INTO dbo.DISCOUNT (code, discount_name, discount_type, value, min_order_value, start_date, end_date)
VALUES
('WELCOME10', N'Giảm 10% lần đầu', 'percent', 10, 0, GETDATE(), DATEADD(day,30,GETDATE())),
('SUMMER50', N'Giảm 50k', 'fixed', 50000, 200000, GETDATE(), DATEADD(day,10,GETDATE()));
GO

-- DELIVERY
INSERT INTO dbo.DELIVERY (shipping_provider, tracking_number, shipping_fee, delivery_status, estimated_date)
VALUES
('GHN', 'GHN123456789', 30000, 'shipping', DATEADD(day,3,GETDATE())),
('GHTK', 'GHTK987654321', 25000, 'preparing', DATEADD(day,4,GETDATE()));
GO

-- WISHLIST
INSERT INTO dbo.WISHLIST (user_id, product_id)
VALUES (2, 1), (2, 5), (3, 2), (4, 6);
GO

-- CART
INSERT INTO dbo.CART (user_id) VALUES (2);
GO

-- CART_ITEM
INSERT INTO dbo.CART_ITEM (cart_id, variant_id, quantity, price_snapshot)
VALUES (1, 1, 2, 199000), (1, 5, 1, 399000);
GO
-- ====================================================
-- 4. SAMPLE ORDERS + ORDER_DETAIL + TRANSACTION + REVIEW
-- ====================================================

-- 🧾 Đơn hàng 1: user_id = 3 (Nguyễn Văn A) – mua 2 áo thun + 1 quần jeans
INSERT INTO dbo.[ORDER] 
(user_id, address_id, delivery_id, discount_id, total_amount, payment_method, note)
VALUES 
(3, NULL, 1, NULL, 199000*2 + 399000, N'VNPAY', N'Đơn test pipeline - user 3');
GO

-- 🧾 Đơn hàng 2: user_id = 4 (Trần Thị B) – mua 1 hoodie
INSERT INTO dbo.[ORDER] 
(user_id, address_id, delivery_id, discount_id, total_amount, payment_method, note)
VALUES 
(4, NULL, 2, NULL, 499000, N'COD', N'Đơn thử pipeline - user 4');
GO


-- ====================================================
-- ORDER_DETAIL
-- ====================================================

-- Đơn hàng 1 gồm 2 áo thun + 1 quần jeans
INSERT INTO dbo.ORDER_DETAIL (order_id, variant_id, quantity, unit_price)
VALUES 
(1, 1, 2, 199000),   -- Áo Thun Basic Trắng (variant 1)
(1, 7, 1, 399000);   -- Quần Jeans Rách Gối (variant 7)
GO

-- Đơn hàng 2 gồm 1 hoodie
INSERT INTO dbo.ORDER_DETAIL (order_id, variant_id, quantity, unit_price)
VALUES 
(2, 4, 1, 499000);   -- Hoodie OVINA Xám (variant 4)
GO


-- ====================================================
-- TRANSACTION (Thanh toán)
-- ====================================================

INSERT INTO dbo.[TRANSACTION] 
(order_id, transaction_code, payment_gateway, amount, transaction_status, created_at, paid_at)
VALUES
(1, 'TRANS_0001', 'VNPAY', 199000*2 + 399000, 'success', DATEADD(day, -5, GETDATE()), DATEADD(day, -5, GETDATE())),
(2, 'TRANS_0002', 'COD', 499000, 'pending', GETDATE(), NULL);
GO


-- ====================================================
-- REVIEW (Đánh giá sản phẩm sau khi mua)
-- ====================================================

INSERT INTO dbo.REVIEW (product_id, user_id, rating, comment, created_at)
VALUES
(1, 3, 5, N'Áo thun chất lượng, vải mịn và mát. Đáng tiền.', DATEADD(day, -3, GETDATE())),
(7, 3, 4, N'Quần jeans ôm vừa, phong cách cá tính.', DATEADD(day, -2, GETDATE())),
(3, 4, 5, N'Hoodie rất ấm và dày, form chuẩn như ảnh.', DATEADD(day, -1, GETDATE()));
GO

ALTER TABLE product_variant
ADD color_code NVARCHAR(20) NULL;

-- ====================================================
-- 3. Add FOREIGN KEYS (after parent data seeded)
-- ====================================================
	
	-- ROLE_AUTHORITIES FKs
	ALTER TABLE dbo.AUTHORITIES
	ADD CONSTRAINT FK_AUTH_ROLE FOREIGN KEY (role_id) REFERENCES dbo.ROLE(role_id),
	    CONSTRAINT FK_AUTH_USER FOREIGN KEY (user_id) REFERENCES dbo.[USER](user_id),
	    CONSTRAINT FK_AUTH_EMP FOREIGN KEY (employee_id) REFERENCES dbo.EMPLOYEE(employee_id);
	GO

	
	-- USER.role_id -> ROLE
	ALTER TABLE dbo.[USER] ADD CONSTRAINT FK_USER_ROLE FOREIGN KEY (role_id) REFERENCES dbo.ROLE(role_id);
	GO
	
	-- USER_POINT.user_id -> USER
	ALTER TABLE dbo.USER_POINT ADD CONSTRAINT FK_USERPOINT_USER FOREIGN KEY (user_id) REFERENCES dbo.[USER](user_id);
	GO
	
	-- ADDRESS.user_id -> USER
	ALTER TABLE dbo.ADDRESS ADD CONSTRAINT FK_ADDRESS_USER FOREIGN KEY (user_id) REFERENCES dbo.[USER](user_id);
	GO
	
	-- SUBCATEGORY.category_id -> CATEGORY
	ALTER TABLE dbo.SUBCATEGORY ADD CONSTRAINT FK_SUBCAT_CAT FOREIGN KEY (category_id) REFERENCES dbo.CATEGORY(category_id);
	GO
	
	-- PRODUCT.category_id, subcategory_id
	ALTER TABLE dbo.PRODUCT
	ADD CONSTRAINT FK_PRODUCT_CATEGORY FOREIGN KEY (category_id) REFERENCES dbo.CATEGORY(category_id),
	    CONSTRAINT FK_PRODUCT_SUBCATEGORY FOREIGN KEY (subcategory_id) REFERENCES dbo.SUBCATEGORY(subcategory_id);
	GO
	
	-- PRODUCT_VARIANT.product_id
	ALTER TABLE dbo.PRODUCT_VARIANT ADD CONSTRAINT FK_VARIANT_PRODUCT FOREIGN KEY (product_id) REFERENCES dbo.PRODUCT(product_id);
	GO
	
	-- IMAGE.product_id
	ALTER TABLE dbo.IMAGE ADD CONSTRAINT FK_IMAGE_PRODUCT FOREIGN KEY (product_id) REFERENCES dbo.PRODUCT(product_id);
	GO
	
	-- WHISHLIST.product_id, user_id
	ALTER TABLE dbo.WISHLIST
	ADD CONSTRAINT FK_WISHLIST_USER FOREIGN KEY (user_id) REFERENCES dbo.[USER](user_id) ON DELETE CASCADE,
	    CONSTRAINT FK_WISHLIST_PRODUCT FOREIGN KEY (product_id) REFERENCES dbo.PRODUCT(product_id);
	GO

	
	-- CART.user_id
	ALTER TABLE dbo.CART ADD CONSTRAINT FK_CART_USER FOREIGN KEY (user_id) REFERENCES dbo.[USER](user_id);
	GO
	
	-- CART_ITEM.cart_id, variant_id
	ALTER TABLE dbo.CART_ITEM
	ADD CONSTRAINT FK_CIT_CART FOREIGN KEY (cart_id) REFERENCES dbo.CART(cart_id),
	    CONSTRAINT FK_CIT_VARIANT FOREIGN KEY (variant_id) REFERENCES dbo.PRODUCT_VARIANT(variant_id);
	GO
	
	-- DISCOUNT_SP.product_id
	ALTER TABLE dbo.DISCOUNT_SP ADD CONSTRAINT FK_DISCOUNTSP_PRODUCT FOREIGN KEY (product_id) REFERENCES dbo.PRODUCT(product_id);
	GO
	
	-- ORDER.user_id, address_id, delivery_id, discount_id
	ALTER TABLE dbo.[ORDER]
	ADD CONSTRAINT FK_ORDER_USER FOREIGN KEY (user_id) REFERENCES dbo.[USER](user_id),
	    CONSTRAINT FK_ORDER_ADDRESS FOREIGN KEY (address_id) REFERENCES dbo.ADDRESS(address_id),
	    CONSTRAINT FK_ORDER_DELIVERY FOREIGN KEY (delivery_id) REFERENCES dbo.DELIVERY(delivery_id),
	    CONSTRAINT FK_ORDER_DISCOUNT FOREIGN KEY (discount_id) REFERENCES dbo.DISCOUNT(discount_id);
	GO
	
	-- ORDER_DETAIL.order_id, variant_id
	ALTER TABLE dbo.ORDER_DETAIL
	ADD CONSTRAINT FK_OD_ORDER FOREIGN KEY (order_id) REFERENCES dbo.[ORDER](order_id),
	    CONSTRAINT FK_OD_VARIANT FOREIGN KEY (variant_id) REFERENCES dbo.PRODUCT_VARIANT(variant_id);
	GO
	
	-- TRANSACTION.order_id
	ALTER TABLE dbo.[TRANSACTION] ADD CONSTRAINT FK_TRANS_ORDER FOREIGN KEY (order_id) REFERENCES dbo.[ORDER](order_id);
	GO
	
	-- REVIEW.product_id, user_id
	ALTER TABLE dbo.REVIEW
	ADD CONSTRAINT FK_REVIEW_PRODUCT FOREIGN KEY (product_id) REFERENCES dbo.PRODUCT(product_id),
	    CONSTRAINT FK_REVIEW_USER FOREIGN KEY (user_id) REFERENCES dbo.[USER](user_id);
	GO
	
	-- SUPPORT.user_id
	ALTER TABLE dbo.SUPPORT ADD CONSTRAINT FK_SUPPORT_USER FOREIGN KEY (user_id) REFERENCES dbo.[USER](user_id);
	GO


-- ====================================================
-- KHÔNG CHẠY TỪ 4-9 PHẦN NÀY TEST NÊN KHÔNG CẦN
-- ====================================================



-- ====================================================
-- 4. SAMPLE ORDER + ORDER_ITEM + TRANSACTION (demo pipeline)
-- ====================================================

-- Tạo đơn hàng mẫu cho user_id = 3
INSERT INTO dbo.[ORDER] (user_id, address_id, delivery_id, discount_id, total_amount, payment_method, note, order_date)
VALUES (3, NULL, 1, NULL, (199000 * 2 + 399000), 'VNPAY', N'Đơn thử hệ thống', GETDATE());
GO

DECLARE @lastOrderId INT;
SELECT @lastOrderId = SCOPE_IDENTITY();

-- ORDER_ITEM: thêm 2 áo thun (variant_id = 1) và 1 quần (variant_id = 6)
INSERT INTO dbo.ORDER_ITEM (order_id, variant_id, quantity, unit_price)
VALUES (@lastOrderId, 1, 2, 199000),
       (@lastOrderId, 6, 1, 399000);
GO

-- TRANSACTION: tạo bản ghi thanh toán
INSERT INTO dbo.[TRANSACTION] (order_id, transaction_code, payment_gateway, amount, transaction_status, transaction_date)
VALUES (@lastOrderId, 'VNPay_ABC_0001', 'VNPay', (199000 * 2 + 399000), 'success', GETDATE());
GO


-- ====================================================
-- 5. TRIGGERS
-- ====================================================

-- Trigger 1: Gán order_number tự động sau khi insert
CREATE OR ALTER TRIGGER trg_SetOrderNumber
ON dbo.[ORDER]
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;
    UPDATE o
    SET order_number = 'OD-' + CONVERT(CHAR(8), o.order_date, 112) + '-' +
                       RIGHT('000' + CAST(o.order_id % 1000 AS VARCHAR(3)), 3)
    FROM dbo.[ORDER] o
    INNER JOIN inserted i ON o.order_id = i.order_id;
END;
GO


-- Trigger 2: Giảm tồn kho sau khi thêm ORDER_ITEM
-- Có xử lý rollback nếu tồn kho âm (multi-row safe)
CREATE OR ALTER TRIGGER trg_UpdateStockAfterOrderItem
ON dbo.ORDER_ITEM
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    -- Giảm tồn kho
    UPDATE pv
    SET pv.stock_quantity = pv.stock_quantity - i.quantity
    FROM dbo.PRODUCT_VARIANT pv
    INNER JOIN inserted i ON pv.variant_id = i.variant_id;

    -- Kiểm tra tồn kho âm
    IF EXISTS (SELECT 1 FROM dbo.PRODUCT_VARIANT WHERE stock_quantity < 0)
    BEGIN
        -- Hoàn lại tồn kho
        UPDATE pv
        SET pv.stock_quantity = pv.stock_quantity + i.quantity
        FROM dbo.PRODUCT_VARIANT pv
        INNER JOIN inserted i ON pv.variant_id = i.variant_id;

        RAISERROR(N'Không đủ tồn kho cho một hoặc nhiều sản phẩm. Giao dịch bị hủy.', 16, 1);
        ROLLBACK TRANSACTION;
    END
END;
GO


-- ====================================================
-- 6. STORED PROCEDURES
-- ====================================================

-- Lấy thông tin user theo email
CREATE OR ALTER PROCEDURE dbo.sp_GetUserByEmail
    @Email NVARCHAR(100)
AS
BEGIN
    SET NOCOUNT ON;
    SELECT user_id, user_code, email, full_name, phone, role_id, verified, created_at
    FROM dbo.[USER]
    WHERE email = @Email;
END;
GO


-- Lấy thông tin chi tiết sản phẩm + các biến thể
CREATE OR ALTER PROCEDURE dbo.sp_GetProductDetail
    @ProductCode NVARCHAR(20)
AS
BEGIN
    SET NOCOUNT ON;
    SELECT p.product_id, p.product_code, p.product_name, p.description, p.status,
           v.variant_id, v.sku, v.size, v.color, v.price, v.stock_quantity
    FROM dbo.PRODUCT p
    LEFT JOIN dbo.PRODUCT_VARIANT v ON p.product_id = v.product_id
    WHERE p.product_code = @ProductCode;
END;
GO


-- Lấy danh sách đơn hàng của 1 user
CREATE OR ALTER PROCEDURE dbo.sp_GetUserOrders
    @UserCode NVARCHAR(20)
AS
BEGIN
    SET NOCOUNT ON;
    SELECT o.order_id, o.order_number, o.order_date, o.status, o.total_amount
    FROM dbo.[ORDER] o
    INNER JOIN dbo.[USER] u ON o.user_id = u.user_id
    WHERE u.user_code = @UserCode
    ORDER BY o.order_date DESC;
END;
GO


-- Thêm review sản phẩm (nếu user và product tồn tại)
CREATE OR ALTER PROCEDURE dbo.sp_AddReview
    @UserCode NVARCHAR(20),
    @ProductCode NVARCHAR(20),
    @Rating INT,
    @Comment NVARCHAR(MAX)
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @uid INT, @pid INT;

    SELECT @uid = user_id FROM dbo.[USER] WHERE user_code = @UserCode;
    SELECT @pid = product_id FROM dbo.PRODUCT WHERE product_code = @ProductCode;

    IF @uid IS NULL OR @pid IS NULL
    BEGIN
        RAISERROR(N'User hoặc sản phẩm không tồn tại.', 16, 1);
        RETURN;
    END

    INSERT INTO dbo.REVIEW (product_id, user_id, rating, comment, review_date)
    VALUES (@pid, @uid, @Rating, @Comment, GETDATE());
END;
GO


-- Kiểu dữ liệu bảng để truyền danh sách item vào sp_CreateOrder
IF TYPE_ID(N'dbo.OrderItemType') IS NULL
BEGIN
    CREATE TYPE dbo.OrderItemType AS TABLE
    (
        variant_id INT,
        quantity INT,
        unit_price DECIMAL(12,2)
    );
END;
GO


-- Tạo đơn hàng (transactional)
CREATE OR ALTER PROCEDURE dbo.sp_CreateOrder
    @UserCode NVARCHAR(20),
    @AddressId INT = NULL,
    @PaymentMethod NVARCHAR(50),
    @Note NVARCHAR(500) = NULL,
    @DiscountCode NVARCHAR(50) = NULL,
    @Items dbo.OrderItemType READONLY
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY
        BEGIN TRANSACTION;

        DECLARE @uid INT = (SELECT user_id FROM dbo.[USER] WHERE user_code = @UserCode);
        IF @uid IS NULL
        BEGIN
            RAISERROR(N'User không tồn tại.', 16, 1);
            ROLLBACK TRANSACTION;
            RETURN;
        END

        -- Kiểm tra tồn kho
        IF EXISTS (
            SELECT 1
            FROM @Items i
            JOIN dbo.PRODUCT_VARIANT pv ON i.variant_id = pv.variant_id
            WHERE pv.stock_quantity < i.quantity
        )
        BEGIN
            RAISERROR(N'Không đủ tồn kho cho một hoặc nhiều sản phẩm.', 16, 1);
            ROLLBACK TRANSACTION;
            RETURN;
        END

        -- Tính tổng tiền
        DECLARE @total DECIMAL(12,2);
        SELECT @total = SUM(quantity * unit_price) FROM @Items;

        -- Lấy discount_id nếu có
        DECLARE @discount_id INT = NULL;
        IF @DiscountCode IS NOT NULL
            SELECT @discount_id = discount_id
            FROM dbo.DISCOUNT
            WHERE code = @DiscountCode AND is_active = 1
              AND start_date <= GETDATE() AND end_date >= GETDATE();

        -- Tạo order
        INSERT INTO dbo.[ORDER] (user_id, address_id, discount_id, total_amount, payment_method, note, order_date)
        VALUES (@uid, @AddressId, @discount_id, @total, @PaymentMethod, @Note, GETDATE());

        DECLARE @newOrderId INT = SCOPE_IDENTITY();

        -- Thêm các sản phẩm vào ORDER_ITEM
        INSERT INTO dbo.ORDER_ITEM (order_id, variant_id, quantity, unit_price)
        SELECT @newOrderId, variant_id, quantity, unit_price FROM @Items;

        -- Tạo bản ghi TRANSACTION (đang chờ thanh toán)
        INSERT INTO dbo.[TRANSACTION] (order_id, payment_gateway, amount, transaction_status, transaction_date)
        VALUES (@newOrderId, @PaymentMethod, @total, 'pending', GETDATE());

        COMMIT TRANSACTION;
    END TRY
    BEGIN CATCH
        IF XACT_STATE() <> 0 ROLLBACK TRANSACTION;
        DECLARE @msg NVARCHAR(4000) = ERROR_MESSAGE();
        RAISERROR(N'Lỗi khi tạo đơn hàng: %s', 16, 1, @msg);
    END CATCH
END;
GO


-- ====================================================
-- 7. CONSTRAINTS & FINAL CLEANUP
-- ====================================================

-- Gán order_number cho các đơn cũ (nếu chưa có)
UPDATE dbo.[ORDER]
SET order_number = 'OD-' + CONVERT(CHAR(8), order_date, 112) + '-' +
                   RIGHT('000' + CAST(order_id % 1000 AS VARCHAR(3)), 3)
WHERE order_number IS NULL;
GO

-- Đảm bảo order_number duy nhất
IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'UQ_ORDER_ORDERNUMBER')
    ALTER TABLE dbo.[ORDER] ADD CONSTRAINT UQ_ORDER_ORDERNUMBER UNIQUE (order_number);
GO

-- Đảm bảo mỗi order có duy nhất 1 transaction
IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'UQ_TRANS_ORDERID')
    ALTER TABLE dbo.[TRANSACTION] ADD CONSTRAINT UQ_TRANS_ORDERID UNIQUE (order_id);
GO

-- ====================================================
-- 8. Example usage (how to call sp_CreateOrder)
-- ====================================================
/*
-- Example:
DECLARE @items dbo.OrderItemType;
INSERT INTO @items (variant_id, quantity, unit_price)
VALUES (1, 1, 199000), (6,1,399000);

EXEC dbo.sp_CreateOrder @user_code = 'US0003', @address_id = NULL, @payment_method = 'VNPay', @note = N'Đơn demo', @discount_code = NULL, @items = @items;
*/

-- ====================================================
-- 9. End of script
-- ====================================================
PRINT 'DATN_OVINA database and sample data created.';
GO
