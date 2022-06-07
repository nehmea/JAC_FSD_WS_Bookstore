CREATE DATABASE `ws_bookstore_v2` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `customers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `middle_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) NOT NULL,
  `date_of_birth` date NOT NULL,
  `address` varchar(45) NOT NULL,
  `city` varchar(45) NOT NULL,
  `state` char(2) NOT NULL,
  `zipcode` varchar(10) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `registration_date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5016 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `books` (
  `id` int NOT NULL AUTO_INCREMENT,
  `isbn13` varchar(45) NOT NULL,
  `isbn10` varchar(13) DEFAULT NULL,
  `title` varchar(100) NOT NULL,
  `language` varchar(45) NOT NULL,
  `binding` varchar(45) DEFAULT NULL,
  `release_date` datetime DEFAULT NULL,
  `edition` varchar(45) DEFAULT NULL,
  `pages` int DEFAULT NULL,
  `dimensions` varchar(45) DEFAULT NULL,
  `rating` float DEFAULT NULL,
  `publisher` varchar(100) DEFAULT NULL,
  `authors` varchar(500) DEFAULT NULL,
  `copies` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `isbn13_UNIQUE` (`isbn13`),
  UNIQUE KEY `isbn10_UNIQUE` (`isbn10`)
) ENGINE=InnoDB AUTO_INCREMENT=515 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `loans` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `book_id` int NOT NULL,
  `date_out` date NOT NULL,
  `date_in` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_loans_customers_idx` (`customer_id`),
  KEY `fk_loans_books_idx` (`book_id`),
  CONSTRAINT `fk_loans_books` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`),
  CONSTRAINT `fk_loans_customers` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2005 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

