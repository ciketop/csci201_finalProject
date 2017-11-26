DROP DATABASE IF EXISTS LiveClass;
CREATE DATABASE LiveClass;
USE LiveClass;

CREATE TABLE User (
  userID    INT(11) PRIMARY KEY AUTO_INCREMENT,
  username  VARCHAR(20)  NOT NULL,
  password  VARCHAR(100) NOT NULL,
  firstName VARCHAR(20)  NOT NULL,
  lastName  VARCHAR(20)  NOT NULL,
  email     VARCHAR(50)  NOT NULL,
  salt      VARCHAR(100) NOT NULL
);

CREATE TABLE Course (
  courseID     INT(11) PRIMARY KEY AUTO_INCREMENT,
  coursePrefix VARCHAR(10)  NOT NULL,
  courseNumber VARCHAR(10)  NOT NULL,
  courseName   VARCHAR(100) NOT NULL,
  accessCode   VARCHAR(100) NOT NULL
);

CREATE TABLE Privilege (
  privilegeID     INT(11) PRIMARY KEY AUTO_INCREMENT,
  privilegeNumber INT(11) NOT NULL
);

CREATE TABLE CoursePrivilege (
  courseID    INT(11) NOT NULL,
  userID      INT(11) NOT NULL,
  privilegeID INT(11) NOT NULL,
  PRIMARY KEY (courseID, userID),
  FOREIGN KEY fk1(courseID) REFERENCES Course (courseID),
  FOREIGN KEY fk2(userID) REFERENCES User (userID),
  FOREIGN KEY fk3(privilegeID) REFERENCES Privilege (privilegeID)
);

CREATE TABLE LoginToken (
  tokenID     INT(11) PRIMARY KEY AUTO_INCREMENT,
  userID      INT(11)     NOT NULL,
  tokenNumber VARCHAR(50) NOT NULL,
  FOREIGN KEY fk1(userID) REFERENCES User (userID)
);

CREATE TABLE PublicCourse (
  courseID INT(11) PRIMARY KEY,
  FOREIGN KEY fk1(courseID) REFERENCES Course (courseID)
);

INSERT INTO Course (coursePrefix, courseNumber, courseName, accessCode)
VALUES ('CSCI', '103', 'Intro to Programming', '2cbf77b3-c11c-4d81-bd9e-ab209e1f3810'),
  ('CSCI', '104', 'Data Structures', '31dac624-9599-4ca0-8492-a52928f0725b'),
  ('CSCI', '201L', 'Principles of Software Development', '31dac784-9ks9-4ca0-8492-a52nd8f0725b'),
  ('EE', '109', 'Intro to Embedded System', '8qd1624-9599-4ca0-8492-a52928f0725b'),
  ('CSCI', '270', 'Algorithms', '31dac624-jl99-4ca0-8492-a52920725b'),
  ('CSCI', '170', 'Discrete Methods', '7-dac624-9599-4ca0-8492-a52928f0725b'),
  ('SOCI', '150', 'Social Problems', '31dac624-9cd9-4ca0-8492-a5lds28f0725b'),
  ('CSCI', '356', 'Intro to Computer Systems', '96dac624-9mls9-4ca0-8492-a52928dmk25b');


INSERT INTO Privilege (privilegeNumber)
VALUES (0),
  (6),
  (26);


INSERT INTO PublicCourse (courseID)
VALUES (5),
  (6),
  (7),
  (8);

INSERT INTO User (username, password, firstName, lastName, email, salt)
VALUES
  ('blue', 'c9e47b1561bf00d29af301dfbcb3d86c99b188876393d99aa140fec824fa45e6', 'Harry', 'Potter', 'hp@usc.edu', 'yxkm'),
  ('green', '2cdeb723c553dbba18873041daccd5434c4a7de4882ac2c7715576002450014b', 'Ron', 'Weasley', 'ronW@ucla.edu',
   'qrky'),
  ('red', '9261b32d2221737ea7921fc5679f1d8fc600b29b56622949839c5ab2d9fcf2d0', 'Hermione', 'Granger', 'herRed@usc.edu',
   'esk{'),
  ('gryffindor', '95656449f9bcb7b96fbcc3ceb0e4e00128ffdbbd7b48592161d4f4728211bd9f', 'Minerva', 'McGonagall',
   'mgonagall@usc.edu', 'ikjn'),
  ('slytherin', '65c291d2a7e39ad765b1ad24a0d04c9357fac4c2acb4b5a30021cc696a3843c7', 'Severus', 'Snape',
   'ssnape@usc.edu', 'rbgb');

INSERT INTO CoursePrivilege (courseID, userID, privilegeID)
VALUES (1, 4, 1),
  (2, 5, 1),
  (2, 1, 2),
  (2, 2, 2),
  (2, 3, 2),
  (3, 1, 2),
  (3, 2, 2),
  (3, 3, 2),
  (4, 3, 2);

INSERT INTO LoginToken (userID, tokenNumber)
VALUES (1, '2cbf77b3-c11c-4d81-bd9e-ab209e1f3810'),
  (1, '31dac624-9599-4ca0-8492-a52928f0725b');