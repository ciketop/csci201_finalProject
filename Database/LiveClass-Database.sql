DROP DATABASE IF EXISTS LiveClass;
CREATE DATABASE LiveClass;
USE LiveClass;

CREATE TABLE User (
	userID INT(11) PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(20) NOT NULL,
    password VARCHAR(20) NOT NULL,
    firstName VARCHAR(20) NOT NULL,
    lastName VARCHAR(20) NOT NULL,
    email VARCHAR(50) NOT NULL
);

CREATE TABLE Course (
	courseID INT(11) PRIMARY KEY AUTO_INCREMENT,
    coursePrefix VARCHAR(10) NOT NULL,
    courseNumber VARCHAR(10) NOT NULL,
    courseName VARCHAR(100) NOT NULL
);

CREATE TABLE Privilege (
	privilegeID INT(11) PRIMARY KEY AUTO_INCREMENT,
    privilegeNumber INT(11) NOT NULL
);

CREATE TABLE CoursePrivilege (
	courseID INT(11) NOT NULL,
    userID INT(11) NOT NULL,
    privilegeID INT(11) NOT NULL,
    PRIMARY KEY (courseID, userID),
    FOREIGN KEY fk1(courseID) REFERENCES Course(courseID),
    FOREIGN KEY fk2(userID) REFERENCES User(userID),
    FOREIGN KEY fk3(privilegeID) REFERENCES Privilege(privilegeID)
);

CREATE TABLE LoginToken (
	tokenID INT(11) PRIMARY KEY AUTO_INCREMENT,
    userID INT(11) NOT NULL,
    tokenNumber VARCHAR(50) NOT NULL,
    FOREIGN KEY fk1(userID) REFERENCES User(userID)
);

INSERT INTO User (username, password, firstName, lastName, email)
	VALUES ('blue', 'pass1', 'Harry', 'Potter', 'hp@usc.edu'),
				   ('green', 'pass2', 'Ron', 'Weasley', 'ronW@ucla.edu'),
                   ('red', 'pass3', 'Hermione', 'Granger', 'herRed@usc.edu'),
                   ('gryffindor', 'pass4', 'Minerva', 'McGonagall', 'mgonagall@usc.edu'),
                   ('slytherin', 'pass5', 'Severus', 'Snape', 'ssnape@usc.edu');
                   
INSERT INTO Course (coursePrefix, courseNumber, courseName)
	VALUES ('CSCI', '103', 'Intro to Programming'),
				   ('CSCI', '104', 'Data Structures'),
                   ('CSCI', '201L', 'Principles of Software Development'),
                   ('EE', '109', 'Intro to Embedded System');
                   
INSERT INTO LoginToken (userID, tokenNumber)
	VALUES (1, '2cbf77b3-c11c-4d81-bd9e-ab209e1f3810'),
				   (1, '31dac624-9599-4ca0-8492-a52928f0725b');
                   
INSERT INTO Privilege (privilegeNumber)
	VALUES (0),
				   (6),
                   (26);