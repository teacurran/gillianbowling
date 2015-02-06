-- // rename configuration_property table
-- Migration SQL that makes the change goes here.

RENAME TABLE `configuration_property` TO `configurations`;

-- //@UNDO
-- SQL to undo the change goes here.

RENAME TABLE `configurations` TO `configuration_property`;

