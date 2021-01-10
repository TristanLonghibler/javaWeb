-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 01, 2020 at 11:20 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `browserdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `surveyresults`
--

CREATE TABLE `surveyresults` (
  `id` int(11) NOT NULL,
  `browser_name` varchar(255) NOT NULL,
  `hit_count` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `surveyresults`
--

INSERT INTO `surveyresults` (`id`, `browser_name`, `hit_count`) VALUES
(1, 'Brave', 0),
(2, 'Chrome', 0),
(3, 'Edge', 0),
(4, 'FireFox', 1),
(5, 'InternetExplorer', 0),
(6, 'Godzilla', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `surveyresults`
--
ALTER TABLE `surveyresults`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `browser_name` (`browser_name`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `surveyresults`
--
ALTER TABLE `surveyresults`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
