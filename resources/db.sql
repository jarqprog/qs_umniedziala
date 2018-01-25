CREATE TABLE IF NOT EXISTS roles (
    id_role INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL);

CREATE TABLE IF NOT EXISTS users (
    id_user INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    password TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    id_role INTEGER NOT NULL,
    FOREIGN KEY('id_role') REFERENCES 'roles'('id_role'));

CREATE TABLE IF NOT EXISTS quests (
    id_quest INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    value INTEGER NOT NULL,
    description TEXT NOT NULL,
    type TEXT NOT NULL,
    category TEXT NOT NULL);

CREATE TABLE IF NOT EXISTS levels (
    id_level INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    coins_limit INTEGER NOT NULL);

CREATE TABLE IF NOT EXISTS artifacts (
    id_artifact INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    value INTEGER NOT NULL,
    description TEXT NOT NULL,
    type TEXT NOT NULL);

CREATE TABLE IF NOT EXISTS teams (
    id_team INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL);

CREATE TABLE IF NOT EXISTS codecool_classes (
    id_codecool_class INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL);

CREATE TABLE IF NOT EXISTS students_in_teams (
    id_student_in_team INTEGER PRIMARY KEY AUTOINCREMENT,
    id_team INTEGER NOT NULL,
    id_student INTEGER NOT NULL UNIQUE,
    FOREIGN KEY('id_team') REFERENCES 'teams'('id_team') ON DELETE CASCADE,
    FOREIGN KEY('id_student') REFERENCES 'users'('id_user') ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS mentors_in_classes (
    id_mentor_in_class INTEGER PRIMARY KEY AUTOINCREMENT,
    id_codecool_class INTEGER NOT NULL,
    id_mentor INTEGER NOT NULL UNIQUE,
    FOREIGN KEY('id_codecool_class') REFERENCES 'codecool_classes'('id_codecool_class') ON DELETE CASCADE,
    FOREIGN KEY('id_mentor') REFERENCES 'users'('id_user') ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS wallets (
    id_student INTEGER PRIMARY KEY,
    all_coins INTEGER NOT NULL,
	available_coins INTEGER NOT NULL,
	foreign key('id_student') references users('id_user')
		on delete cascade);

CREATE TABLE IF NOT EXISTS students_in_classes (
    id_student_in_class INTEGER PRIMARY KEY AUTOINCREMENT,
    id_codecool_class INTEGER NOT NULL,
    id_student INTEGER NOT NULL UNIQUE,
    FOREIGN KEY('id_codecool_class') REFERENCES 'codecool_classes'('id_codecool_class') ON DELETE CASCADE,
    FOREIGN KEY('id_student') REFERENCES 'users'('id_user') ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS quests_of_students (
    id_quest_of_student INTEGER PRIMARY KEY AUTOINCREMENT,
    id_quest INTEGER NOT NULL,
    id_student INTEGER NOT NULL,
    FOREIGN KEY('id_quest') REFERENCES 'quests'('id_quest') ON DELETE CASCADE,
    FOREIGN KEY('id_student') REFERENCES 'users'('id_user') ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS artifacts_in_wallets (
    id_artifact_in_wallet INTEGER PRIMARY KEY AUTOINCREMENT,
    id_artifact INTEGER NOT NULL,
    id_student INTEGER NOT NULL,
    FOREIGN KEY('id_artifact') REFERENCES 'artifacts'('id_artifact') ON DELETE CASCADE,
    FOREIGN KEY('id_student') REFERENCES 'wallets'('id_student') ON DELETE CASCADE);

INSERT INTO roles VALUES (?, 'admin');
INSERT INTO roles VALUES (?, 'mentor');
INSERT INTO roles VALUES (?, 'student');

INSERT INTO users VALUES (?, 'Jerzy Jeżyk', 'jerzy', 'jerzy@codecool.com', 1);

INSERT INTO users VALUES (?, 'Dominik Starzyk', 'dominik', 'dominik@codecool.com', 2);
INSERT INTO users VALUES (?, 'Piotr Tomaszewski', 'piotr', 'piotr@codecool.com', 2);
INSERT INTO users VALUES (?, 'Konrad Gadzina', 'konrad', 'konrad@codecool.com', 2);

INSERT INTO users VALUES (?, 'Marta Stąporek', 'marta', 'marta@codecool.com', 3);
INSERT INTO users VALUES (?, 'Filip Hartman', 'filip', 'filip@codecool.com', 3);
INSERT INTO users VALUES (?, 'Joanna Baran', 'joanna', 'joanna@codecool.com', 3);

INSERT INTO quests VALUES (?, 'Solving the magic puzzle', 100, 'Finishing an SI assignment', 'individual', 'basic');
INSERT INTO quests VALUES (?, 'Slaying a dragon', 100, 'Passing a Checkpoint', 'individual', 'basic');
INSERT INTO quests VALUES (?, 'Spot trap', 50, 'Spot a major mistake in the assignment', 'individual', 'extra');

INSERT INTO levels VALUES (?, 'Apprentice', 0);
INSERT INTO levels VALUES (?, 'Private', 100);
INSERT INTO levels VALUES (?, 'Soldier', 500);
INSERT INTO levels VALUES (?, 'Wizard', 2000);
INSERT INTO levels VALUES (?, 'Master', 1000000);

INSERT INTO wallets VALUES (1, 1000, 100);
INSERT INTO wallets VALUES (2, 2000, 200);
INSERT INTO wallets VALUES (3, 3000, 300);

INSERT INTO artifacts VALUES (?, 'Combat training', 50, 'Private mentoring', 'individual');
INSERT INTO artifacts VALUES (?, 'Sanctuary', 300, 'You can spend a day in home office', 'individual');
INSERT INTO artifacts VALUES (?, 'Time travel', 500, 'Extend SI week assignment deadline by one day', 'individual');

INSERT INTO teams VALUES (?, 'Umniedziala');
INSERT INTO teams VALUES (?, 'Ziemniaki');
INSERT INTO teams VALUES (?, 'Cytryny');
INSERT INTO teams VALUES (?, 'Wiaderka');

INSERT INTO codecool_classes VALUES (?, '2017-1-a');
INSERT INTO codecool_classes VALUES (?, '2017-1-b');
INSERT INTO codecool_classes VALUES (?, '2017-2-a');
INSERT INTO codecool_classes VALUES (?, '2017-2-b');

INSERT INTO students_in_teams VALUES (?, 1, 1);
INSERT INTO students_in_teams VALUES (?, 1, 2);
INSERT INTO students_in_teams VALUES (?, 2, 3);

INSERT INTO mentors_in_classes VALUES (?, 1, 1);
INSERT INTO mentors_in_classes VALUES (?, 1, 2);
INSERT INTO mentors_in_classes VALUES (?, 2, 3);

INSERT INTO students_in_classes VALUES (?, 1, 1);
INSERT INTO students_in_classes VALUES (?, 1, 2);
INSERT INTO students_in_classes VALUES (?, 2, 3);

INSERT INTO quests_of_students VALUES (?, 1, 1);
INSERT INTO quests_of_students VALUES (?, 2, 2);
INSERT INTO quests_of_students VALUES (?, 3, 3);

INSERT INTO artifacts_in_wallets VALUES (?, 1, 1);
INSERT INTO artifacts_in_wallets VALUES (?, 2, 2);
INSERT INTO artifacts_in_wallets VALUES (?, 2, 3);

