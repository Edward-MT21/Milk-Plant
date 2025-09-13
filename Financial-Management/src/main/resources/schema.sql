CREATE TABLE IF NOT EXISTS `product` (
    `product_id` INT PRIMARY KEY,
    `name` VARCHAR(50) NOT NULL UNIQUE,
    `description` VARCHAR(50) NOT NULL,
    `price` INT(15) NOT NULL,
    `created_at` TIMESTAMP NOT NULL,
    `created_by` VARCHAR(20) NOT NULL,
    `updated_at` TIMESTAMP DEFAULT NULL,
    `updated_by` VARCHAR(20) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `milk_supplier_payment` (
    `milk_supplier_payment_id` int AUTO_INCREMENT  PRIMARY KEY,
    `milk_supplier_id` int(15) NOT NULL,
    `start_date` date NOT NULL,
    `end_date` date NOT NULL,
    `total_liters_milk` int(15) NOT NULL,
    `price_per_liter` decimal(15,2) NOT NULL,
    `total_amount` decimal(15,2) NOT NULL,
    `payment_status_enum` varchar(20) NOT NULL,
    `payment_date` timestamp DEFAULT NULL,
    `created_at` timestamp NOT NULL,
    `created_by` varchar(20) NOT NULL,
    `updated_at` timestamp DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL
);


INSERT INTO product (product_id, name, description, price, created_at, created_by)
SELECT 1, 'RAW_MILK', 'Raw Milk', 1800, CURRENT_TIMESTAMP, 'admin'
WHERE NOT EXISTS (
    SELECT 1 FROM product WHERE name = 'RAW_MILK'
);
INSERT INTO product (product_id, name, description, price, created_at, created_by)
SELECT 2, 'CURD', 'Curd',32000, CURRENT_TIMESTAMP, 'admin'
WHERE NOT EXISTS (
    SELECT 1 FROM product WHERE name = 'CURD'
);
INSERT INTO product (product_id, name, description, price, created_at, created_by)
SELECT 3, 'GROUND_CHEESE', 'Ground Cheese', 8000, CURRENT_TIMESTAMP, 'admin'
WHERE NOT EXISTS (
    SELECT 1 FROM product WHERE name = 'GROUND_CHEESE'
);
INSERT INTO product (product_id, name, description, price, created_at, created_by)
SELECT 4, 'DOUBLE_CREAM_CHEESE', 'Double Cream Cheese', 8000, CURRENT_TIMESTAMP, 'admin'
WHERE NOT EXISTS (
    SELECT 1 FROM product WHERE name = 'DOUBLE_CREAM_CHEESE'
);



