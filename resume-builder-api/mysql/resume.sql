CREATE TABLE `resume` (
    `resume_id` bigint NOT NULL AUTO_INCREMENT,
    `career_objective` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `created_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `created_date` datetime NOT NULL,
    `date_of_birth` date NOT NULL,
    `email` varchar(254) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `mobile_number` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `nationality` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `status` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `title` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
    `updated_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `updated_date` datetime NOT NULL,
    PRIMARY KEY (`resume_id`),
    UNIQUE KEY `UK_cvnnfoduofogy31ej9kjn9hpg` (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
