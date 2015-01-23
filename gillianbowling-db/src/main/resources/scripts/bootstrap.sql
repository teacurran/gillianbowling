-- // Bootstrap.sql

-- This is the only SQL script file that is NOT
-- a valid migration and will not be run or tracked
-- in the changelog.  There is no @UNDO section.

-- // Do I need this file?

-- New projects likely won't need this file.
-- Existing projects will likely need this file.
-- It's unlikely that this bootstrap should be run
-- in the production environment.

-- // Purpose

-- The purpose of this file is to provide a facility
-- to initialize the database to a state before MyBatis
-- SQL migrations were applied.  If you already have
-- a database in production, then you probably have
-- a script that you run on your developer machine
-- to initialize the database.  That script can now
-- be put in this bootstrap file (but does not have
-- to be if you are comfortable with your current process.

-- // Running

-- The bootstrap SQL is run with the "migrate bootstrap"
-- command.  It must be run manually, it's never run as
-- part of the regular migration process and will never
-- be undone. Variables (e.g. ${variable}) are still
-- parsed in the bootstrap SQL.

-- After the boostrap SQL has been run, you can then
-- use the migrations and the changelog for all future
-- database change management.


create table authors (
    `id` integer not null auto_increment,
    `created` datetime,
    `modified` datetime,
    `first_name` varchar(100),
    `last_name` varchar(100) not null,
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table book_authors (
    `main` bit not null,
    `type` integer,
    `author_id` integer not null,
    `book_id` integer not null,
    primary key (`author_id`, `book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table book_book_categories (
    `book_id` integer not null,
    `book_category_id` integer not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table book_categories (
    `id` integer not null auto_increment,
    `image` varchar(200),
    `name` varchar(100) not null,
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table book_countries (
    `book_id` integer not null,
    `country_id` integer not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table book_country_excludes (
    `book_id` integer not null,
    `country_id` integer not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table book_country_includes (
    `book_id` integer not null,
    `country_id` integer not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table book_region_includes (
    `book_id` integer not null,
    `region_id` integer not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table book_tag_types (
    `id` integer not null auto_increment,
    `created` datetime,
    `modified` datetime,
    `name` varchar(200) not null unique,
    `single` bit not null,
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table book_tags (
    `created` datetime,
    `modified` datetime,
    `value` varchar(100) not null,
    `book_id` integer not null,
    `book_tag_type_id` integer not null,
    primary key (`book_id`, `book_tag_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table books (
    `id` integer not null auto_increment,
    `created` datetime,
    `modified` datetime,
    `author_names` varchar(200) not null,
    `description` longtext,
    `fiction` bit,
    `full_id` varchar(8),
    `grl` varchar(4),
    `image_url` varchar(500),
    `in_lit_pro_library` bit not null,
    `lexile` integer,
    `lexile_code` varchar(4),
    `src` bit not null,
    `src_points` integer,
    `sri` bit not null,
    `title` varchar(200) not null,
    `word_count` integer,
    `interest_level_id` integer,
    `language_id` integer,
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table countries (
    `id` integer not null auto_increment,
    `created` datetime,
    `modified` datetime,
    `code` varchar(3),
    `name` varchar(75),
    `region_id` integer,
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table grades (
    `id` integer not null auto_increment,
    `created` datetime,
    `modified` datetime,
    `code` varchar(2) not null,
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table import_category_mappings (
    `id` integer not null auto_increment,
    `created` datetime,
    `modified` datetime,
    `from_category` varchar(200) not null,
    `source` varchar(20) not null,
    `to_category` varchar(200),
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table interest_levels (
    `id` integer not null auto_increment,
    `name` varchar(75),
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table languages (
    `id` integer not null auto_increment,
    `code` varchar(8),
    `name` varchar(200),
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table publications (
    `id` integer not null auto_increment,
    `created` datetime,
    `modified` datetime,
    `isbn10` varchar(10),
    `isbn13` varchar(13),
    `note` varchar(200),
    `source` varchar(200),
    `book_id` integer,
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table regions (
    `id` integer not null auto_increment,
    `name` varchar(255),
    `slz_id` varchar(200) not null,
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table server_settings (
    `id` integer not null auto_increment,
    `created` datetime,
    `modified` datetime,
    `code` varchar(200),
    `server` varchar(200),
    `value` varchar(200),
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table src_quiz_countries (
    `src_quiz_id` integer not null,
    `country_id` integer not null,
    primary key (`src_quiz_id`, `country_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table src_quiz_country_excludes (
    `src_quiz_id` integer not null,
    `country_id` integer not null,
    primary key (`src_quiz_id`, `country_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table src_quiz_country_includes (
    `src_quiz_id` integer not null,
    `country_id` integer not null,
    primary key (`src_quiz_id`, `country_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table src_quiz_questions (
    `id` integer not null auto_increment,
    `created` datetime,
    `modified` datetime,
    `active` bit not null,
    `answer2` varchar(200),
    `answer3` varchar(200),
    `answer4` varchar(200),
    `answer_correct` varchar(200),
    `question` varchar(200),
    `src_quiz_id` integer,
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table src_quiz_region_includes (
    `src_quiz_id` integer not null,
    `region_id` integer not null,
    primary key (`src_quiz_id`, `region_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table src_quizzes (
    `id` integer not null auto_increment,
    `created` datetime,
    `modified` datetime,
    `description` longtext,
    `enabled` bit not null,
    `name` varchar(200),
    `book_id` integer,
    `language_id` integer,
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table book_authors 
    add index FKCBA3F8F2BA51E422 (`book_id`), 
    add constraint FKCBA3F8F2BA51E422 
    foreign key (`book_id`) 
    references books (`id`);

alter table book_authors 
    add index FKCBA3F8F24B5426E2 (`author_id`), 
    add constraint FKCBA3F8F24B5426E2 
    foreign key (`author_id`) 
    references authors (`id`);

alter table book_book_categories 
    add index FK59543DC55ACD1B5 (`book_category_id`), 
    add constraint FK59543DC55ACD1B5 
    foreign key (`book_category_id`) 
    references book_categories (`id`);

alter table book_book_categories 
    add index FK59543DCBA51E422 (`book_id`), 
    add constraint FK59543DCBA51E422 
    foreign key (`book_id`) 
    references books (`id`);

alter table book_countries 
    add index FK6B3500DEBA51E422 (`book_id`), 
    add constraint FK6B3500DEBA51E422 
    foreign key (`book_id`) 
    references books (`id`);

alter table book_countries 
    add index FK6B3500DE9BA3BDF2 (`country_id`), 
    add constraint FK6B3500DE9BA3BDF2 
    foreign key (`country_id`) 
    references countries (`id`);

alter table book_country_excludes 
    add index FK2C2016F8BA51E422 (`book_id`), 
    add constraint FK2C2016F8BA51E422 
    foreign key (`book_id`) 
    references books (`id`);

alter table book_country_excludes 
    add index FK2C2016F89BA3BDF2 (`country_id`), 
    add constraint FK2C2016F89BA3BDF2 
    foreign key (`country_id`) 
    references countries (`id`);

alter table book_country_includes 
    add index FKBAA679EABA51E422 (`book_id`), 
    add constraint FKBAA679EABA51E422 
    foreign key (`book_id`) 
    references books (`id`);

alter table book_country_includes 
    add index FKBAA679EA9BA3BDF2 (`country_id`), 
    add constraint FKBAA679EA9BA3BDF2 
    foreign key (`country_id`) 
    references countries (`id`);

alter table book_region_includes 
    add index FK507F7C60BA51E422 (`book_id`), 
    add constraint FK507F7C60BA51E422 
    foreign key (`book_id`) 
    references books (`id`);

alter table book_region_includes 
    add index FK507F7C6013E9C242 (`region_id`), 
    add constraint FK507F7C6013E9C242 
    foreign key (`region_id`) 
    references regions (`id`);

create index ix_book_tags_value on book_tags (`value`);

create index ix_book_tags_type_value on book_tags (`book_tag_type_id`, `value`);

alter table book_tags 
    add index FK78B01BAFBA51E422 (`book_id`), 
    add constraint FK78B01BAFBA51E422 
    foreign key (`book_id`) 
    references books (`id`);

alter table book_tags 
    add index FK78B01BAFEC3EAB08 (`book_tag_type_id`), 
    add constraint FK78B01BAFEC3EAB08 
    foreign key (`book_tag_type_id`) 
    references book_tag_types (`id`);

alter table books 
    add index FK59922AA4EBD819D (`interest_level_id`), 
    add constraint FK59922AA4EBD819D 
    foreign key (`interest_level_id`) 
    references interest_levels (`id`);

alter table books 
    add index FK59922AAA0BA95C2 (`language_id`), 
    add constraint FK59922AAA0BA95C2 
    foreign key (`language_id`) 
    references languages (`id`);

alter table countries 
    add index FK509F9AB413E9C242 (`region_id`), 
    add constraint FK509F9AB413E9C242 
    foreign key (`region_id`) 
    references regions (`id`);

create index ix_grades_code on grades (`code`);

create index ix_import_category_mapping_source on import_category_mappings (`source`);

create index ix_publication_isbn10 on publications (`isbn10`);

create index ix_publication_isbn13 on publications (`isbn13`);

alter table publications 
    add index FK37B8A3C7BA51E422 (`book_id`), 
    add constraint FK37B8A3C7BA51E422 
    foreign key (`book_id`) 
    references books (`id`);

create index ix_regions_slz_id on regions (`slz_id`);

alter table src_quiz_countries 
    add index FK96853D059BA3BDF2 (`country_id`), 
    add constraint FK96853D059BA3BDF2 
    foreign key (`country_id`) 
    references countries (`id`);

alter table src_quiz_countries 
    add index FK96853D05E07F323B (`src_quiz_id`), 
    add constraint FK96853D05E07F323B 
    foreign key (`src_quiz_id`) 
    references src_quizzes (`id`);

alter table src_quiz_country_excludes 
    add index FK40A230F19BA3BDF2 (`country_id`), 
    add constraint FK40A230F19BA3BDF2 
    foreign key (`country_id`) 
    references countries (`id`);

alter table src_quiz_country_excludes 
    add index FK40A230F1E07F323B (`src_quiz_id`), 
    add constraint FK40A230F1E07F323B 
    foreign key (`src_quiz_id`) 
    references src_quizzes (`id`);

alter table src_quiz_country_includes 
    add index FKCF2893E39BA3BDF2 (`country_id`), 
    add constraint FKCF2893E39BA3BDF2 
    foreign key (`country_id`) 
    references countries (`id`);

alter table src_quiz_country_includes 
    add index FKCF2893E3E07F323B (`src_quiz_id`), 
    add constraint FKCF2893E3E07F323B 
    foreign key (`src_quiz_id`) 
    references src_quizzes (`id`);

alter table src_quiz_questions 
    add index FKDBAAE39EE07F323B (`src_quiz_id`), 
    add constraint FKDBAAE39EE07F323B 
    foreign key (`src_quiz_id`) 
    references src_quizzes (`id`);

alter table src_quiz_region_includes 
    add index FKC4C5BF4713E9C242 (`region_id`), 
    add constraint FKC4C5BF4713E9C242 
    foreign key (`region_id`) 
    references regions (`id`);

alter table src_quiz_region_includes 
    add index FKC4C5BF47E07F323B (`src_quiz_id`), 
    add constraint FKC4C5BF47E07F323B 
    foreign key (`src_quiz_id`) 
    references src_quizzes (`id`);

alter table src_quizzes 
    add index FKB5BF58D8BA51E422 (`book_id`), 
    add constraint FKB5BF58D8BA51E422 
    foreign key (`book_id`) 
    references books (`id`);

alter table src_quizzes 
    add index FKB5BF58D8A0BA95C2 (`language_id`), 
    add constraint FKB5BF58D8A0BA95C2 
    foreign key (`language_id`) 
    references languages (`id`);

