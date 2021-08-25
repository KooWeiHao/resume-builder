CREATE TABLE `smart_sequence` (
    `smart_sequence_id` bigint NOT NULL AUTO_INCREMENT,
    `for_date` date NULL,
    `prefix` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `sequence` int NOT NULL,
    PRIMARY KEY (`smart_sequence_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TRIGGER `smart_sequence_BEFORE_INSERT` BEFORE INSERT ON `smart_sequence` FOR EACH ROW BEGIN
    DECLARE v_lock int;
    DECLARE v_available nvarchar(8);

    SET @v_lock = (SELECT COUNT(smart_sequence_id) FROM `smart_sequence` WHERE prefix = new.prefix FOR SHARE);
    SET @v_available = (SELECT prefix FROM `smart_sequence` WHERE prefix = new.prefix AND (for_date = new.for_date OR for_date IS NULL) LIMIT 1);

    IF @v_available IS NULL THEN
        BEGIN
            SET new.sequence = 1;
        END;
    ELSE
        BEGIN
            SET new.sequence = (SELECT MAX(sequence) + 1 FROM `smart_sequence` WHERE prefix = new.prefix AND (for_date = new.for_date OR for_date IS NULL) GROUP BY prefix);
        END;
    END IF;
END
