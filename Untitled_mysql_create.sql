CREATE TABLE `User_blog` (
	`user_blog_id` INT NOT NULL,
	`user_name` varchar(50) NOT NULL,
	`blog_name` varchar(50) NOT NULL,
	`post_count` INT NOT NULL DEFAULT '0',
	PRIMARY KEY (`user_blog_id`)
);

CREATE TABLE `blog_category` (
	`user_blog_id` INT NOT NULL,
	`category_id` INT NOT NULL
);

CREATE TABLE `Category` (
	`category_id` INT NOT NULL,
	`category_name` varchar(50) NOT NULL,
	PRIMARY KEY (`category_id`)
);

CREATE TABLE `Post` (
	`post_id` INT NOT NULL,
	`post_name` varchar(50) NOT NULL,
	`post_text` TEXT(10000) NOT NULL,
	PRIMARY KEY (`post_id`)
);

CREATE TABLE `Category_Post` (
	`category_id` INT NOT NULL,
	`post_id` INT NOT NULL
);

ALTER TABLE `blog_category` ADD CONSTRAINT `blog_category_fk0` FOREIGN KEY (`user_blog_id`) REFERENCES `User_blog`(`user_blog_id`);

ALTER TABLE `blog_category` ADD CONSTRAINT `blog_category_fk1` FOREIGN KEY (`category_id`) REFERENCES `Category`(`category_id`);

ALTER TABLE `Category_Post` ADD CONSTRAINT `Category_Post_fk0` FOREIGN KEY (`category_id`) REFERENCES `Category`(`category_id`);

ALTER TABLE `Category_Post` ADD CONSTRAINT `Category_Post_fk1` FOREIGN KEY (`post_id`) REFERENCES `Post`(`post_id`);

