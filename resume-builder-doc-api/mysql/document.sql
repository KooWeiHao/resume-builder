CREATE TABLE `document` (
    `document_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `created_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `created_date` datetime NOT NULL,
    `data` longblob NOT NULL,
    `name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `size` bigint NOT NULL,
    `type` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `updated_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `updated_date` datetime NOT NULL,
    PRIMARY KEY (`document_id`),
    UNIQUE KEY `UK_elt3kiqdmmm5fwqfxsxk9lvh0` (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
