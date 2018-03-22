
CREATE TABLE IF NOT EXISTS `teams` (
	`id_team`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT NOT NULL UNIQUE,
	`available_coins`	INTEGER NOT NULL
);
INSERT INTO `teams` VALUES (1,'Umniedziala',150);
INSERT INTO `teams` VALUES (2,'Ziemniaki',55);
INSERT INTO `teams` VALUES (3,'Cytryny',0);
INSERT INTO `teams` VALUES (4,'Wiaderka',80);

CREATE TABLE IF NOT EXISTS `quests` (
	`id_quest`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT NOT NULL UNIQUE,
	`value`	INTEGER NOT NULL,
	`description`	TEXT NOT NULL,
	`type`	TEXT NOT NULL,
	`category`	TEXT NOT NULL
);

INSERT INTO `quests` VALUES (1,'Exploring a dungeon',100,'Finishing a Teamwork week','team','basic');
INSERT INTO `quests` VALUES (2,'Solving the magic puzzle',100,'Finishing an SI assignment','individual','basic');
INSERT INTO `quests` VALUES (3,'Slaying a dragon',500,'Passing a Checkpoint in the first attempt','individual','basic');
INSERT INTO `quests` VALUES (4,'Spot trap',50,'Spot a major mistake in the assignment','individual','extra');
INSERT INTO `quests` VALUES (5,'Taming a pet',100,'Doing a demo about a pet project','team','extra');
INSERT INTO `quests` VALUES (6,'Recruiting some n00bs',100,'Taking part in the student screening process','individual','extra');
INSERT INTO `quests` VALUES (7,'Forging weapons',400,'Organizing a workshop for other students','team','extra');
INSERT INTO `quests` VALUES (8,'Master the mornings',300,'Attend 1 months without being late','individual','extra');
INSERT INTO `quests` VALUES (9,'Fast as an unicorn',500,'deliver 4 consecutive SI week assignments on time','individual','extra');
INSERT INTO `quests` VALUES (10,'Achiever',1000,'set up a SMART goal accepted by a mentor','team','extra');
INSERT INTO `quests` VALUES (11,'Fortune',500,'students choose the best project of the week. Selected team scores','individual','extra');
INSERT INTO `quests` VALUES (12,'Creating an enchanted scroll',500,'Creating extra material for the current TW/SI topic (should be revised by mentors)','individual','extra');
INSERT INTO `quests` VALUES (13,'Enter the arena',500,'Do a presentation on a meet-up','team','extra');


CREATE TABLE IF NOT EXISTS `artifacts` (
	`id_artifact`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT NOT NULL UNIQUE,
	`value`	INTEGER NOT NULL,
	`description`	TEXT NOT NULL,
	`type`	TEXT NOT NULL
);

INSERT INTO `artifacts` VALUES (1,'Combat training',50,'Private mentoring','individual');
INSERT INTO `artifacts` VALUES (2,'Sanctuary',1000,'You can spend a day in home office','individual');
INSERT INTO `artifacts` VALUES (3,'Time Travel',750,'extend SI week assignment deadline by one day','individual');
INSERT INTO `artifacts` VALUES (4,'Circle of Sorcery',53,'60 min workshop by a mentor(s) of the chosen topic','team');
INSERT INTO `artifacts` VALUES (5,'Summon Code Elemental',2500,'mentor joins a students'' team for a one hour','team');
INSERT INTO `artifacts` VALUES (6,'Tome of knowledge',1500,'Extra material for the current topic','individual');
INSERT INTO `artifacts` VALUES (7,'Transform mentors',5000,'All mentors should dress up as pirates (or just funny) for the day','individual');
INSERT INTO `artifacts` VALUES (8,'Teleport',30000,'The whole course goes to an off-school program instead for a day','team');


CREATE TABLE IF NOT EXISTS `levels` (
	`id_level`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT NOT NULL,
	`coins_limit`	INTEGER NOT NULL UNIQUE
);

INSERT INTO `levels` VALUES (1,'Apprentice',0);
INSERT INTO `levels` VALUES (2,'Private',100);
INSERT INTO `levels` VALUES (3,'Soldier',500);
INSERT INTO `levels` VALUES (4,'Wizard',2000);
INSERT INTO `levels` VALUES (5,'Master',1000000);


CREATE TABLE IF NOT EXISTS `artifacts_in_wallets` (
	`id_artifact_in_wallet`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`id_artifact`	INTEGER NOT NULL,
	`id_student`	INTEGER NOT NULL,
	`status`	TEXT NOT NULL,
	FOREIGN KEY(`id_artifact`) REFERENCES `artifacts`(`id_artifact`) ON DELETE CASCADE,
	FOREIGN KEY(`id_student`) REFERENCES `wallets`(`id_student`) ON DELETE CASCADE
);

INSERT INTO `artifacts_in_wallets` VALUES (7,1,5,'new');
INSERT INTO `artifacts_in_wallets` VALUES (8,2,5,'used');
INSERT INTO `artifacts_in_wallets` VALUES (9,3,6,'new');
INSERT INTO `artifacts_in_wallets` VALUES (10,1,6,'used');
INSERT INTO `artifacts_in_wallets` VALUES (11,2,7,'new');
INSERT INTO `artifacts_in_wallets` VALUES (12,3,7,'used');


CREATE TABLE IF NOT EXISTS `quests_of_students` (
	`id_quest_of_student`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`id_quest`	INTEGER NOT NULL,
	`id_student`	INTEGER NOT NULL,
	FOREIGN KEY(`id_student`) REFERENCES `users`(`id_user`) ON DELETE CASCADE,
	FOREIGN KEY(`id_quest`) REFERENCES `quests`(`id_quest`) ON DELETE CASCADE
);
DROP TABLE IF EXISTS `students_in_classes`;
CREATE TABLE IF NOT EXISTS `students_in_classes` (
	`id_student_in_class`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`id_codecool_class`	INTEGER NOT NULL,
	`id_student`	INTEGER NOT NULL UNIQUE,
	FOREIGN KEY(`id_codecool_class`) REFERENCES `codecool_classes`(`id_codecool_class`) ON DELETE CASCADE,
	FOREIGN KEY(`id_student`) REFERENCES `users`(`id_user`) ON DELETE CASCADE
);
INSERT INTO `students_in_classes` VALUES (1,1,5);
INSERT INTO `students_in_classes` VALUES (2,1,6);
INSERT INTO `students_in_classes` VALUES (3,2,7);
INSERT INTO `students_in_classes` VALUES (4,1,8);
INSERT INTO `students_in_classes` VALUES (5,1,9);
INSERT INTO `students_in_classes` VALUES (6,4,10);
INSERT INTO `students_in_classes` VALUES (7,1,11);

CREATE TABLE IF NOT EXISTS `wallets` (
	`id_student`	INTEGER,
	`all_coins`	INTEGER NOT NULL,
	`available_coins`	INTEGER NOT NULL,
	FOREIGN KEY(`id_student`) REFERENCES `users`(`id_user`) on delete cascade,
	PRIMARY KEY(`id_student`)
);

INSERT INTO `wallets` VALUES (5,1000,1000);
INSERT INTO `wallets` VALUES (6,2000,200);
INSERT INTO `wallets` VALUES (7,3000,300);
INSERT INTO `wallets` VALUES (8,4000,400);
INSERT INTO `wallets` VALUES (9,5000,500);
INSERT INTO `wallets` VALUES (10,0,0);
INSERT INTO `wallets` VALUES (11,0,0);


CREATE TABLE IF NOT EXISTS `mentors_in_classes` (
	`id_mentor_in_class`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`id_codecool_class`	INTEGER NOT NULL,
	`id_mentor`	INTEGER NOT NULL UNIQUE,
	FOREIGN KEY(`id_codecool_class`) REFERENCES `codecool_classes`(`id_codecool_class`) ON DELETE CASCADE,
	FOREIGN KEY(`id_mentor`) REFERENCES `users`(`id_user`) ON DELETE CASCADE
);

INSERT INTO `mentors_in_classes` VALUES (1,1,2);
INSERT INTO `mentors_in_classes` VALUES (2,1,3);
INSERT INTO `mentors_in_classes` VALUES (3,2,4);
DROP TABLE IF EXISTS `students_in_teams`;
CREATE TABLE IF NOT EXISTS `students_in_teams` (
	`id_student_in_team`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`id_team`	INTEGER NOT NULL,
	`id_student`	INTEGER NOT NULL UNIQUE,
	FOREIGN KEY(`id_student`) REFERENCES `users`(`id_user`) ON DELETE CASCADE,
	FOREIGN KEY(`id_team`) REFERENCES `teams`(`id_team`) ON DELETE CASCADE
);

INSERT INTO `students_in_teams` VALUES (6,1,5);
INSERT INTO `students_in_teams` VALUES (7,1,6);
INSERT INTO `students_in_teams` VALUES (8,2,7);
INSERT INTO `students_in_teams` VALUES (9,1,8);
INSERT INTO `students_in_teams` VALUES (10,1,9);

CREATE TABLE IF NOT EXISTS `codecool_classes` (
	`id_codecool_class`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT NOT NULL
);

INSERT INTO `codecool_classes` VALUES (1,'2017-1-a');
INSERT INTO `codecool_classes` VALUES (2,'2017-1-b');
INSERT INTO `codecool_classes` VALUES (3,'2017-2-a');
INSERT INTO `codecool_classes` VALUES (4,'2017-2-b');
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
	`id_user`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT NOT NULL,
	`password`	TEXT NOT NULL,
	`email`	TEXT NOT NULL UNIQUE,
	`id_role`	INTEGER NOT NULL,
	FOREIGN KEY(`id_role`) REFERENCES `roles`(`id_role`)
);

INSERT INTO `users` VALUES (1,'Jerzy Jeżyk','jerzy','jerzy@cc.com',1);
INSERT INTO `users` VALUES (10,'Adam Mad','adam','adam@cc.com',1);
INSERT INTO `users` VALUES (2,'Dominik Starzyk','dominik','dominik@cc.com',2);
INSERT INTO `users` VALUES (3,'Piotr Tomaszewski','piotr','piotr@cc.com',2);
INSERT INTO `users` VALUES (4,'Konrad Gadzina','konrad','konrad@cc.com',2);
INSERT INTO `users` VALUES (5,'Marta Stąporek','marta','marta@cc.com',3);
INSERT INTO `users` VALUES (6,'Filip Hartman','filip','filip@cc.com',3);
INSERT INTO `users` VALUES (7,'Joanna Baran','joanna','joanna@cc.com',3);
INSERT INTO `users` VALUES (8,'Jadzia Kot','jadzia','jadzia@cc.com',3);
INSERT INTO `users` VALUES (9,'Mariusz Trąbalski','mariusz','mariusz@cc.com',3);

DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
	`id_role`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT NOT NULL
);

INSERT INTO `roles` VALUES (1,'admin');
INSERT INTO `roles` VALUES (2,'mentor');
INSERT INTO `roles` VALUES (3,'student');
