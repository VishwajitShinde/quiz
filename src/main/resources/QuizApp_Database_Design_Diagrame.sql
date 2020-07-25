drop table  participant cascade;

drop table  participantinvitation cascade;

drop table questionbank cascade;

drop table questionpaperdetails cascade;

 drop table questionpaperquestions cascade;



CREATE TABLE `user_roles` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `role_name` varchar(100) UNIQUE
);

CREATE TABLE `users` (
  `id` long PRIMARY KEY AUTO_INCREMENT,
  `email` varchar(100) UNIQUE,
  `password` varchar(100),
  `mobile` varchar(20) UNIQUE,
  `roles` varchar(500),
  `first_name` varchar(100),
  `last_name` varchar(100),
  `creation_date` datetime,
  `last_modified_date` datetime,
   UNIQUE KEY `COMPOSITE_UNIQUE` (`email`, `mobile`)
);

CREATE TABLE `question_paper_details` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `exa_setter_id` long,
  `que_paper_title` varchar(255),
  `total_time` int,
  `total_question` int,
  `exam_instruction` varchar(255),
  `launch_time` datetime,
  `creation_time` datetime,
  `last_modified_time` datetime
);

CREATE TABLE `question_paper_questions` (
  `que_paper_id` int,
  `que_id` int
);

CREATE TABLE `participant_invitation` (
  `exa_setter_id` long,
  `que_paper_id` int,
  `name` varchar(255),
  `email` varchar(255),
  `mobileno` int
);

CREATE TABLE `question_bank` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `exa_setter_id` long,
  `question_text` varchar(255),
  `option_1` varchar(255),
  `option_2` varchar(255),
  `option_3` varchar(255),
  `option_4` varchar(255),
  `answer` varchar(255)
);

ALTER TABLE `question_paper_details` ADD FOREIGN KEY (`exa_setter_id`) REFERENCES `users` (`id`);

ALTER TABLE `question_paper_questions` ADD FOREIGN KEY (`que_paper_id`) REFERENCES `users` (`id`);

ALTER TABLE `participant_invitation` ADD FOREIGN KEY (`exa_setter_id`) REFERENCES `users` (`id`);

ALTER TABLE `participant_invitation` ADD FOREIGN KEY (`que_paper_id`) REFERENCES `users` (`id`);

ALTER TABLE `question_paper_questions` ADD FOREIGN KEY (`que_id`) REFERENCES `question_bank` (`id`);

ALTER TABLE `question_bank` ADD FOREIGN KEY (`exa_setter_id`) REFERENCES `users` (`id`);


