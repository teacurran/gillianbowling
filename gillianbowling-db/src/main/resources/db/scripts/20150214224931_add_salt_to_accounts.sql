-- // add salt to accounts
-- Migration SQL that makes the change goes here.

ALTER TABLE `accounts`
ADD COLUMN `salt` varchar(200);

-- //@UNDO
-- SQL to undo the change goes here.

ALTER TABLE `accounts`
DROP COLUMN `salt`;

