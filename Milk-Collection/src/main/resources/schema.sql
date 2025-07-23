CREATE TABLE IF NOT EXISTS `milk_supplier` (
  `milk_supplier_id` int AUTO_INCREMENT  PRIMARY KEY,
  `person_id` int(15) NOT NULL,
  `communication_sw` BOOLEAN,
  `created_at` timestamp NOT NULL,
  `created_by` varchar(20) NOT NULL,
  `updated_at` timestamp DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `milk_collection` (
  `milk_collection_id` int AUTO_INCREMENT  PRIMARY KEY,
  `milk_supplier_id` int(15) NOT NULL,
  `liters_milk` int(15) NOT NULL,
  `created_at` timestamp NOT NULL,
  `created_by` varchar(20) NOT NULL,
  `updated_at` timestamp DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL
);