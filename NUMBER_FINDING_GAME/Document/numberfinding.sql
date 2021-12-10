-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th12 10, 2021 lúc 06:29 AM
-- Phiên bản máy phục vụ: 10.4.11-MariaDB
-- Phiên bản PHP: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `numberfinding`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbldetailmatch`
--

CREATE TABLE `tbldetailmatch` (
  `UID` varchar(255) NOT NULL,
  `IdRoom` varchar(255) NOT NULL,
  `playerColor` varchar(255) NOT NULL,
  `point` int(11) NOT NULL,
  `KetQua` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `tbldetailmatch`
--

INSERT INTO `tbldetailmatch` (`UID`, `IdRoom`, `playerColor`, `point`, `KetQua`) VALUES
('3118410100', 'Lobby1639114046395', '#7eff45', 0, ''),
('311841101', 'Lobby1639114046395', '#ff00eb', 0, ''),
('311841102', 'Lobby1639114046395', '#ff0000', 0, '');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tblmatch`
--

CREATE TABLE `tblmatch` (
  `idRoom` varchar(255) NOT NULL,
  `matchTime` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbluseraccount`
--

CREATE TABLE `tbluseraccount` (
  `UID` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `passwd` varchar(255) NOT NULL,
  `nameinf` varchar(255) NOT NULL,
  `gender` varchar(255) NOT NULL,
  `dayofbirth` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `tbluseraccount`
--

INSERT INTO `tbluseraccount` (`UID`, `username`, `passwd`, `nameinf`, `gender`, `dayofbirth`) VALUES
('3118410043', 'HiamKaito@gmail.com', '40b9a58e558918adbe41489bdef1d0db274afc3aa9d982be27170a5cadc743ed', 'Tăng Chí Chung', 'Nam', '2000-01-18'),
('3118410100', 'Test@gmail.com', '3b51673850e675023faf2f34e1515632253896e30b680fde341e62d0fdccf8df', 'Test', 'Nữ', '2001-02-14'),
('311841101', 'TestTest@gmail.com', '3b51673850e675023faf2f34e1515632253896e30b680fde341e62d0fdccf8df', 'Test', 'Nữ', '2015-10-01'),
('311841102', 'TestTestTest@gmail.com', '3b51673850e675023faf2f34e1515632253896e30b680fde341e62d0fdccf8df', 'TestTT', 'Khác', '2010-10-06');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `tbldetailmatch`
--
ALTER TABLE `tbldetailmatch`
  ADD PRIMARY KEY (`UID`,`IdRoom`);

--
-- Chỉ mục cho bảng `tbluseraccount`
--
ALTER TABLE `tbluseraccount`
  ADD PRIMARY KEY (`UID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
