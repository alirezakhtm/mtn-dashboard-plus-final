CREATE TABLE `tbl_all_user` (
  `indx` int(11) NOT NULL AUTO_INCREMENT,
  `username` text COLLATE utf8_persian_ci,
  `password` text COLLATE utf8_persian_ci,
  `periority` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`indx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_persian_ci;

CREATE TABLE `tbl_simple_user` (
  `indx` int(11) NOT NULL AUTO_INCREMENT,
  `username_simple` text COLLATE utf8_persian_ci,
  `username_admin` text COLLATE utf8_persian_ci,
  `service_name` text COLLATE utf8_persian_ci,
  PRIMARY KEY (`indx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_persian_ci;

-- CREATE TABLE `tbl_content_file` (
--   `indx` int(11) NOT NULL AUTO_INCREMENT,
--   `username` text COLLATE utf8_persian_ci,
--   `file_address` text COLLATE utf8_persian_ci,
--   `upload_date` text COLLATE utf8_persian_ci,
--   `status` int(11) DEFAULT NULL,
--   `review_date` text COLLATE utf8_persian_ci,
--   PRIMARY KEY (`indx`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_persian_ci;
-- 
-- ALTER TABLE `mobtakerandb`.`tbl_content_file` 
-- ADD COLUMN `admin_username` TEXT NULL AFTER `review_date`,
-- ADD COLUMN `service_name` TEXT NULL AFTER `admin_username`;

CREATE TABLE `tbl_content_file` (
  `indx` int(11) NOT NULL AUTO_INCREMENT,
  `username` text COLLATE utf8_persian_ci,
  `file_address` text COLLATE utf8_persian_ci,
  `upload_date` text COLLATE utf8_persian_ci,
  `status` int(11) DEFAULT NULL,
  `review_date` text COLLATE utf8_persian_ci,
  `admin_username` text COLLATE utf8_persian_ci,
  `service_name` text COLLATE utf8_persian_ci,
  PRIMARY KEY (`indx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_persian_ci;


CREATE TABLE `tbl_notification` (
  `indx` int(11) NOT NULL AUTO_INCREMENT,
  `username_from` text COLLATE utf8_persian_ci,
  `username_to` text COLLATE utf8_persian_ci,
  `message` text COLLATE utf8_persian_ci,
  `is_read` int(11) DEFAULT NULL,
  `date_send` text COLLATE utf8_persian_ci,
  `date_seen` text COLLATE utf8_persian_ci,
  PRIMARY KEY (`indx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_persian_ci;

-- CREATE TABLE `tbl_service_tables` (
--   `indx` int(11) NOT NULL AUTO_INCREMENT,
--   `serviceCode` int(11) DEFAULT NULL,
--   `tableName` text COLLATE utf8_persian_ci,
--   PRIMARY KEY (`indx`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_persian_ci;
-- 
-- ALTER TABLE `mobtakerandb`.`tbl_service_tables` 
-- ADD COLUMN `admin_username` TEXT NULL AFTER `tableName`;

CREATE TABLE `tbl_service_tables` (
  `indx` int(11) NOT NULL AUTO_INCREMENT,
  `serviceCode` int(11) DEFAULT NULL,
  `tableName` text COLLATE utf8_persian_ci,
  `admin_username` text COLLATE utf8_persian_ci,
  PRIMARY KEY (`indx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_persian_ci;
