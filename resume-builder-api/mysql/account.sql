CREATE TABLE `account` (
    `account_id` bigint NOT NULL AUTO_INCREMENT,
    `created_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `created_date` datetime NOT NULL,
    `updated_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `updated_date` datetime NOT NULL,
    `username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `auth_user_id` varchar(36) NOT NULL,
    PRIMARY KEY (`account_id`),
    UNIQUE KEY `UK_gex1lmaqpg0ir5g1f5eftyaa1` (`username`),
    UNIQUE KEY `UK_lk49gusswx16dx8rcu93ere65` (`auth_user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
