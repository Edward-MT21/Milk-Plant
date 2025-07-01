CREATE TABLE IF NOT EXISTS `milk_supplier` (
  `milk_supplier_id` int AUTO_INCREMENT  PRIMARY KEY,
  `person_id` int(15) NOT NULL,
  `communication_sw` BOOLEAN,
  `created_at` date NOT NULL,
  `created_by` varchar(20) NOT NULL,
  `updated_at` date DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL
);