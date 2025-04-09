CREATE DATABASE`classroom_management`;
USE `classroom_management`;
CREATE TABLE `students` (
  `student_id` int(11) NOT NULL,
  `student_fname` varchar(50) DEFAULT NULL,
  `student_lname` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`student_id`)
)
