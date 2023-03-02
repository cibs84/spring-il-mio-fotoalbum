CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_46ccwnsi9409t36lurvtyljak` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `author` varchar(30) NOT NULL,
  `content` varchar(400) NOT NULL,
  `photo_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6i6p8dtfc155jdrdwy8fmsxe2` (`photo_id`),
  CONSTRAINT `FK6i6p8dtfc155jdrdwy8fmsxe2` FOREIGN KEY (`photo_id`) REFERENCES `photo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `photo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(300) NOT NULL,
  `tag` varchar(100) NOT NULL,
  `title` varchar(30) NOT NULL,
  `url` varchar(400) NOT NULL,
  `visible` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_j1ws10368ol351yjkmr07a37f` (`title`),
  UNIQUE KEY `UK_kigvc1s15qmrka7nuw4nfpgnk` (`url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `photo_categories` (
  `photos_id` bigint NOT NULL,
  `categories_id` bigint NOT NULL,
  KEY `FK8byd2aqnhwk4resgilci64io8` (`categories_id`),
  KEY `FKg9iu0691xdbwao72ofwrpsywf` (`photos_id`),
  CONSTRAINT `FK8byd2aqnhwk4resgilci64io8` FOREIGN KEY (`categories_id`) REFERENCES `category` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKg9iu0691xdbwao72ofwrpsywf` FOREIGN KEY (`photos_id`) REFERENCES `photo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;