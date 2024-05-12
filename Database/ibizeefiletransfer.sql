-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 09, 2024 at 10:20 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ibizeefiletransfer`
--

-- --------------------------------------------------------

--
-- Table structure for table `file_transfer`
--

CREATE TABLE `file_transfer` (
  `transfer_id` int(10) NOT NULL,
  `file_description` varchar(250) DEFAULT NULL,
  `department_id` int(10) DEFAULT NULL,
  `file_password` varchar(100) DEFAULT NULL,
  `upload_url` varchar(250) DEFAULT NULL,
  `uploaded_timestamp` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `file_transfer`
--

INSERT INTO `file_transfer` (`transfer_id`, `file_description`, `department_id`, `file_password`, `upload_url`, `uploaded_timestamp`) VALUES
(3, 'Daily sales reports', 3, '8&X19T*A', 'https://drive.google.com/uc?export=view&id=1Iwzm3Xt1pk3yJTNk64ZUNS4nowj8XjWq', '2024-05-08 22:54:25'),
(4, 'My Image files 2024', 1, 'D]40H8*A', 'https://drive.google.com/uc?export=view&id=11kMPHR0HwlY-AGLeoGeU_54Yucr_tAur', '2024-05-09 06:01:12'),
(5, 'Name check upload', 2, 'NNYP]HGE', 'https://drive.google.com/uc?export=view&id=1rF7paF-8_g3LemIcDazA0F19qOgXmrIM', '2024-05-09 06:56:24');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(10) NOT NULL,
  `full_name` varchar(250) NOT NULL,
  `user_email` varchar(200) NOT NULL,
  `department_id` int(2) NOT NULL,
  `user_password` varchar(250) NOT NULL,
  `created_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `full_name`, `user_email`, `department_id`, `user_password`, `created_date`) VALUES
(1, 'John doe', 'test@gmail.com', 1, 'test', '2024-04-20'),
(2, 'Archana test', 'progammasl@gmail.com', 2, '12345', '2024-04-28'),
(3, 'Archaan test 2', 'test2@gmail.com', 3, '12345', '2024-04-28'),
(4, 'Archaan test 2', 'test3@gmail.com', 1, 'asdfg', '2024-04-28');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `file_transfer`
--
ALTER TABLE `file_transfer`
  ADD PRIMARY KEY (`transfer_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `file_transfer`
--
ALTER TABLE `file_transfer`
  MODIFY `transfer_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
