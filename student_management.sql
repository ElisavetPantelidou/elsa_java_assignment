CREATE DATABASE `student_management`;
USE `student_management`;
DROP TABLE IF EXISTS `students`;
CREATE TABLE `students` (
  `STUDENT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `STUDENT_FNAME` varchar(50) DEFAULT NULL,
  `STUDENT_LNAME` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`STUDENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
