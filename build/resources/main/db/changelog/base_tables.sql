SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `game` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `session_id` int NOT NULL,
  `round_id` int DEFAULT NULL,
  `winner_id` int DEFAULT NULL,
  `is_played` tinyint(1) NOT NULL DEFAULT '0',
  `user_id` int DEFAULT NULL,
  `user_choice` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `session_id` (`session_id`),
  KEY `winner_id` (`winner_id`),
  CONSTRAINT `game_ibfk_1` FOREIGN KEY (`session_id`) REFERENCES `rooms` (`id`),
  CONSTRAINT `game_ibfk_2` FOREIGN KEY (`winner_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `messages` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `message` varchar(512) DEFAULT NULL,
  `session_code` varchar(255) DEFAULT NULL,
  `created_at` bigint DEFAULT NULL,
  `is_fetched` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_messages_username` (`username`),
  KEY `fk_messages_session_code` (`session_code`),
  CONSTRAINT `fk_messages_session_code` FOREIGN KEY (`session_code`) REFERENCES `rooms` (`session_code`),
  CONSTRAINT `fk_messages_username` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `password` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userId` int DEFAULT NULL,
  `digest` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  KEY `FKqp5403kox4hg3jhjqifd7dfpj` (`user_id`),
  CONSTRAINT `FKqp5403kox4hg3jhjqifd7dfpj` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `password_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE `rooms` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user1_id` int DEFAULT NULL,
  `user2_id` int DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '1',
  `is_vacant` tinyint(1) DEFAULT '1',
  `session_code` varchar(20) DEFAULT NULL,
  `winner_id` int DEFAULT NULL,
  `user1_score` int DEFAULT '0',
  `user2_score` int DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_session_code` (`session_code`),
  KEY `user1_id` (`user1_id`),
  KEY `user2_id` (`user2_id`),
  KEY `fk_winner_id` (`winner_id`),
  CONSTRAINT `fk_winner_id` FOREIGN KEY (`winner_id`) REFERENCES `users` (`id`),
  CONSTRAINT `rooms_ibfk_1` FOREIGN KEY (`user1_id`) REFERENCES `users` (`id`),
  CONSTRAINT `rooms_ibfk_2` FOREIGN KEY (`user2_id`) REFERENCES `users` (`id`),
  CONSTRAINT `rooms_chk_1` CHECK ((`user1_id` <> `user2_id`))
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `created_at` bigint DEFAULT NULL,
  `digest` varchar(512) NOT NULL,
  `salt` varchar(512) NOT NULL,
  `score` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


SET FOREIGN_KEY_CHECKS = 1;