-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: db
-- Tempo de geração: 26-Maio-2020 às 23:13
-- Versão do servidor: 5.7.30
-- versão do PHP: 7.4.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `cap`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `funcionarios`
--

CREATE TABLE `funcionarios` (
  `id` bigint(20) NOT NULL,
  `celular` bigint(20) NOT NULL,
  `cliente_alocado` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `funcao` varchar(255) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `usuario_github` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `funcionarios`
--

INSERT INTO `funcionarios` (`id`, `celular`, `cliente_alocado`, `email`, `funcao`, `nome`, `usuario_github`) VALUES
(1, 32988725880, 'Next', 'fabio.nascimento@capgemini.com', 'Analista', 'Fabio', 'bimnascimento'),
(2, 21313, 'bb', 'cc', 'ddd', 'asd', 'ddasdadd'),
(14, 32988725881, 'NEXT', 'fabio.nascimento@capgemini.com', 'Analista de Sistemas Sr', 'Fabio', 'bimnascimento@gmail.com'),
(16, 32988725882, 'NEXT', 'fabio.nascimento@capgemini.com', 'Analista de Sistemas Sr', 'Fabio', 'bimnascimento@gmail.com'),
(17, 1198888888, 'Cliente Alocado', 'Email', 'Funcao', 'Nome', 'Email GitHub'),
(19, 1198888889, 'Cliente Alocado', 'Email', 'Funcao', 'Nome', 'Email GitHub');

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `funcionarios`
--
ALTER TABLE `funcionarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_l1habhd3438o6cp4r4whigrxu` (`celular`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `funcionarios`
--
ALTER TABLE `funcionarios`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
