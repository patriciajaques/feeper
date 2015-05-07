-- --------------------------------------------------------
-- Servidor:                     127.0.0.1
-- Versão do servidor:           5.6.20 - MySQL Community Server (GPL)
-- OS do Servidor:               Win64
-- HeidiSQL Versão:              9.1.0.4921
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Copiando estrutura do banco de dados para feeper
CREATE DATABASE IF NOT EXISTS `feeper` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `feeper`;


-- Copiando estrutura para tabela feeper.configuracaosistema
DROP TABLE IF EXISTS `configuracaosistema`;
CREATE TABLE IF NOT EXISTS `configuracaosistema` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `EnderecoSistemaCorretorJava` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.configuracaosistema: ~0 rows (aproximadamente)
DELETE FROM `configuracaosistema`;
/*!40000 ALTER TABLE `configuracaosistema` DISABLE KEYS */;
INSERT INTO `configuracaosistema` (`ID`, `EnderecoSistemaCorretorJava`) VALUES
	(1, 'http://localhost:8084/feeper.CorretorJava/rest/efetuaCorrecao');
/*!40000 ALTER TABLE `configuracaosistema` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.conquista
DROP TABLE IF EXISTS `conquista`;
CREATE TABLE IF NOT EXISTS `conquista` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(45) NOT NULL,
  `PontosParaConquista` int(11) NOT NULL,
  `PontosIncremento` int(11) NOT NULL DEFAULT '1',
  `Formula` varchar(1000) NOT NULL,
  `DataCadastro` datetime NOT NULL,
  `Ativo` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.conquista: ~0 rows (aproximadamente)
DELETE FROM `conquista`;
/*!40000 ALTER TABLE `conquista` DISABLE KEYS */;
/*!40000 ALTER TABLE `conquista` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.exercicio
DROP TABLE IF EXISTS `exercicio`;
CREATE TABLE IF NOT EXISTS `exercicio` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(50) NOT NULL,
  `IdAutor` int(11) NOT NULL,
  `DataCadastro` datetime NOT NULL,
  `Ativo` tinyint(1) NOT NULL DEFAULT '1',
  `UsaDescricaoPDF` tinyint(1) NOT NULL DEFAULT '0',
  `DescricaoHtml` text,
  `Descricao` mediumblob,
  PRIMARY KEY (`ID`),
  KEY `FK_Pessoa_Exercicio` (`IdAutor`),
  CONSTRAINT `FK_Pessoa_Exercicio` FOREIGN KEY (`IdAutor`) REFERENCES `pessoa` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.exercicio: ~3 rows (aproximadamente)
DELETE FROM `exercicio`;
/*!40000 ALTER TABLE `exercicio` DISABLE KEYS */;
INSERT INTO `exercicio` (`ID`, `Nome`, `IdAutor`, `DataCadastro`, `Ativo`, `UsaDescricaoPDF`, `DescricaoHtml`, `Descricao`) VALUES
	(20, 'Pontos e Reta', 8, '2015-03-04 03:02:05', 1, 0, '<p style="margin-bottom: 1em; font-family: Tahoma, Tahoma, Helvetica; color: rgb(43, 51, 114);"><span style="line-height: 1.428571429;">1) Crie a classe</span><span style="line-height: 1.428571429;">&nbsp;</span><b style="line-height: 1.428571429;">Ponto</b><span style="line-height: 1.428571429;">. Um ponto conterá como atributos as coordenadas x e y, ambas do tipo inteiro.</span><br></p><p style="margin-bottom: 1em; color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica;">a) Crie métodos de acesso e modificação para os atributos da classe.</p><p style="margin-bottom: 1em; color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica;">b) Crie um método construtor que não receba nenhum argumento.</p><p style="margin-bottom: 1em; color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica;">c) Crie um método construtor que inicialize os atributos da classe.</p><p class="MsoNormal">d) Crie o método toString(). Esse método deve retornar uma String contendo as coordenadas x e y do Ponto na seguinte forma “(x, y)”.<o:p></o:p></p><p style="margin-bottom: 1em; color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica;">&nbsp;</p><p style="margin-bottom: 1em; color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica;">2) Cria a classe&nbsp;<b>Reta</b>. Uma reta conterá como atributo dois pontos: p e q, ambos instâncias da classe Ponto.&nbsp; Esses pontos representam os pontos extremos de uma reta.</p><p style="margin-bottom: 1em; color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica;">a) Crie métodos de acesso e modificação para os atributos da classe.</p><p style="margin-bottom: 1em; color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica;">b) Crie um método construtor que não receba nenhum argumento.</p><p style="margin-bottom: 1em; color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica;">c) Crie um método construtor que inicialize os atributos da classe.</p><p style="margin-bottom: 1em; color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica;">d) Crie o método getDistancia. Esse método retorna um&nbsp;<i style="color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica; margin-bottom: 1em;">double</i>&nbsp;representando a largura da reta. Para calcular a largura da reta, utilize a fórmula da&nbsp;<a style="color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica; margin-bottom: 1em;">distância euclidiana</a>.</p><p style="margin-bottom: 1em; color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica;"><img src="http://upload.wikimedia.org/math/a/2/d/a2dc1161e6c38d4b66b9d10f0cd2ddb3.png" style="color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica; margin-bottom: 1em;"></p><p style="margin-bottom: 1em; color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica;"><br></p><p style="margin-bottom: 1em; color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica;"><br></p><p style="margin-bottom: 1em; color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica;">Você vai precisar das seguintes fórmulas Java:</p><p style="margin-bottom: 1em; color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica;">double b = Math.pow (a,2);&nbsp;&nbsp;&nbsp;&nbsp; // retorna o resultado de a<span style="position: relative; line-height: 0; vertical-align: baseline; top: -0.5em;">2</span></p><p style="margin-bottom: 1em; color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica;">double c = Math.sqrt(res);&nbsp;&nbsp; // retorna a raiz quadrada de res</p>', NULL),
	(24, 'Classe Ponto', 8, '2015-03-06 03:00:56', 1, 0, 'Crie uma classe ponto com as propriedades x e y seus getters e setters e um método toString que imprime os dados do ponto em formato (x,y)', NULL);
/*!40000 ALTER TABLE `exercicio` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.exerciciocasoteste
DROP TABLE IF EXISTS `exerciciocasoteste`;
CREATE TABLE IF NOT EXISTS `exerciciocasoteste` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IdExercicio` int(11) NOT NULL,
  `ativo` tinyint(4) NOT NULL DEFAULT '1',
  `MensagemPersonalizada` varchar(5000) DEFAULT NULL,
  `MensagemCompilacao` varchar(5000) DEFAULT NULL,
  `ordem` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Teste_Exercicio_idx` (`IdExercicio`),
  CONSTRAINT `FK_Teste_Exercicio` FOREIGN KEY (`IdExercicio`) REFERENCES `exercicio` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.exerciciocasoteste: ~33 rows (aproximadamente)
DELETE FROM `exerciciocasoteste`;
/*!40000 ALTER TABLE `exerciciocasoteste` DISABLE KEYS */;
INSERT INTO `exerciciocasoteste` (`ID`, `IdExercicio`, `ativo`, `MensagemPersonalizada`, `MensagemCompilacao`, `ordem`) VALUES
	(75, 20, 1, 'Os métodos setX/getX  da Classe ponto não apresentaram o comportamento correto!', NULL, 1),
	(76, 20, 1, 'Os métodos setY/getY da Classe ponto não apresentaram o comportamento correto!', NULL, 2),
	(77, 20, 1, 'Problemas ao iniciar o Ponto utilizando o construtor com parâmetros, certifique-se de utilizar o acessor "this" ao setar as propriedades do ponto', NULL, 4),
	(78, 20, 1, 'Problemas no método toString() da Classe Ponto Certifique-se de retornar os dados no formato (x,y)', NULL, 4),
	(79, 20, 1, 'Problemas ao iniciar a Reta utilizando o construtor com parâmetros, certifique-se de utilizar o acessor "this" ao setar as propriedades da reta', NULL, 6),
	(80, 20, 1, 'Problemas ao calcular a distância da reta, certifique-se de aplicar a fórmula euclidana corretamente, com x para a ao quadrado(invenção minha)', NULL, 6),
	(111, 24, 1, '', 'Você não inseriu um construtor com 0 parâmetros na classe Ponto', 1),
	(112, 24, 1, NULL, 'Você não inseriu um construtor com 2 parâmetros na classe Ponto', 2),
	(113, 24, 1, 'O método setX não está setando o valor do atributo x. Verifique o método setX!', ' O método de modificação do atributo x não possui a assinatura esperada. Revise o nome(setX), parâmetros recebidos e retornados desse método!', 3),
	(114, 24, 1, 'O método setY não está setando o valor do atributo y. Verifique o método setY!', ' O método de modificação do atributo y não possui a assinatura esperada. Revise o nome(setY), parâmetros recebidos e retornados desse método!', 4),
	(115, 24, 1, 'O método getX() deveria ter retornado o valor do atributo x , mas retornou outro valor. Verifique esse método!', 'O método de acesso ao atributo x não possui a assinatura esperada(getX()).Revise esse método!', 5),
	(116, 24, 1, 'O método getY() deveria ter retornado o valor do atributo y , mas retornou outro valor. Verifique esse método!', 'O método de acesso ao atributo y não possui a assinatura esperada(getY()).Revise esse método!', 6),
	(117, 24, 1, NULL, 'Você deveria ter implementado um método toString que recebe 0 parâmetros e retorna um valor do tipo String!', 7),
	(118, 24, 1, NULL, 'Você deveria ter implementado um método imprimeAlgo que recebe 3 parâmetros!', 8),
	(119, 24, 1, 'TESTE LAÇO', 'TESTE LAÇO', 9);
/*!40000 ALTER TABLE `exerciciocasoteste` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.exerciciocasotestepasso
DROP TABLE IF EXISTS `exerciciocasotestepasso`;
CREATE TABLE IF NOT EXISTS `exerciciocasotestepasso` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `idCasoTeste` int(11) NOT NULL,
  `Ordem` int(11) NOT NULL,
  `OperationType` int(11) DEFAULT NULL,
  `ExpectedOutputType` varchar(200) DEFAULT NULL,
  `ExpectedOutputName` varchar(200) DEFAULT NULL,
  `ComparisionType` int(11) NOT NULL,
  `ObjectName` varchar(200) DEFAULT NULL,
  `MethodName` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_casoTestePasso_idx` (`idCasoTeste`),
  CONSTRAINT `fk_casoTestePasso` FOREIGN KEY (`idCasoTeste`) REFERENCES `exerciciocasoteste` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=623 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.exerciciocasotestepasso: ~228 rows (aproximadamente)
DELETE FROM `exerciciocasotestepasso`;
/*!40000 ALTER TABLE `exerciciocasotestepasso` DISABLE KEYS */;
INSERT INTO `exerciciocasotestepasso` (`ID`, `idCasoTeste`, `Ordem`, `OperationType`, `ExpectedOutputType`, `ExpectedOutputName`, `ComparisionType`, `ObjectName`, `MethodName`) VALUES
	(249, 75, 1, 1, 'Ponto', 'ponto1', 0, 'Ponto', NULL),
	(250, 75, 2, 2, NULL, NULL, 0, 'ponto1', 'setX'),
	(251, 75, 3, 3, 'int', '666', 0, 'ponto1', 'getX'),
	(252, 76, 1, 1, 'Ponto', 'ponto1', 0, 'Ponto', NULL),
	(253, 76, 2, 2, NULL, NULL, 0, 'ponto1', 'setY'),
	(254, 76, 3, 3, 'int', '666', 0, 'ponto1', 'getY'),
	(255, 77, 1, 1, 'Ponto', 'p', 0, 'Ponto', ''),
	(256, 77, 2, 3, 'Integer', '6', 0, 'p', 'getX'),
	(257, 77, 3, 3, 'Integer', '8', 0, 'p', 'getY'),
	(258, 78, 1, 1, 'Ponto', 'p', 0, 'Ponto', ''),
	(259, 78, 3, 3, 'String', '(6,8)', 0, 'p', 'toString'),
	(260, 79, 1, 1, 'Ponto', 'p', 0, 'Ponto', ''),
	(261, 79, 2, 1, 'Ponto', 'q', 0, 'Ponto', ''),
	(262, 79, 3, 1, 'Reta', 'r', 0, 'Reta', ''),
	(263, 79, 4, 3, '', 'p', 0, 'r', 'getP'),
	(264, 79, 5, 3, '', 'q', 0, 'r', 'getQ'),
	(265, 80, 1, 1, 'Ponto', 'p', 0, 'Ponto', ''),
	(266, 80, 2, 1, 'Ponto', 'q', 0, 'Ponto', ''),
	(267, 80, 3, 1, 'Reta', 'r', 0, 'Reta', ''),
	(268, 80, 4, 3, 'Double', '4', 0, 'r', 'getDistancia'),
	(445, 111, 1, 1, 'Ponto', 'ponto1', 0, 'Ponto', NULL),
	(446, 112, 1, 1, 'Ponto', 'ponto1', 0, 'Ponto', NULL),
	(447, 113, 1, 1, 'Ponto', 'ponto1', 0, 'Ponto', NULL),
	(448, 113, 2, 2, NULL, NULL, 0, 'ponto1', 'setX'),
	(449, 113, 3, 3, 'int', '666', 0, 'ponto1', 'get_Private_Field_Acessor'),
	(450, 114, 1, 1, 'Ponto', 'ponto1', 0, 'Ponto', NULL),
	(451, 114, 2, 2, NULL, NULL, 0, 'ponto1', 'setY'),
	(452, 114, 3, 3, 'int', '666', 0, 'ponto1', 'get_Private_Field_Acessor'),
	(453, 115, 1, 1, 'Ponto', 'ponto1', 0, 'Ponto', NULL),
	(454, 115, 2, 2, NULL, NULL, 0, 'ponto1', 'setX'),
	(455, 115, 3, 3, 'int', '666', 0, 'ponto1', 'getX'),
	(456, 116, 1, 1, 'Ponto', 'ponto1', 0, 'Ponto', NULL),
	(457, 116, 2, 2, NULL, NULL, 0, 'ponto1', 'setY'),
	(458, 116, 3, 3, 'int', '666', 0, 'ponto1', 'getY'),
	(459, 117, 1, 1, 'Ponto', 'ponto1', 0, 'Ponto', NULL),
	(460, 117, 2, 1, 'String', 'string1', 0, 'ponto1', 'toString'),
	(461, 118, 1, 1, 'Ponto', 'ponto1', 0, 'Ponto', NULL),
	(462, 118, 2, 2, NULL, NULL, 0, 'ponto1', 'imprimeAlgo'),
	(463, 119, 1, 1, 'Integer', 'i', 0, '0', NULL),
	(464, 119, 2, 1, 'Ponto', 'p1', 0, 'Ponto', NULL),
	(465, 119, 3, 4, '', 'i', 6, '5', NULL),
	(470, 119, 4, 4, '', 'i', 6, '5', NULL),
	(471, 119, 5, 1, '', 'p1', 0, 'Ponto', NULL),
	(474, 119, 6, 1, '', 'i', 0, 'i+1', NULL),
	(475, 119, 7, 5, '', '', 0, '', ''),
	(476, 119, 8, 5, '', '', 0, '', ''),
	(477, 119, 9, 3, 'Integer', '5', 0, 'p1', 'getX'),
	(478, 119, 10, 3, 'Integer', '5', 0, 'p1', 'getY'),
	(615, 119, 11, 1, 'Integer', 'count', 0, '0', NULL),
	(616, 119, 12, 4, '', 'count', 5, 'p1', 'getX'),
	(617, 119, 13, 1, '', 'count', 0, 'count+1', NULL),
	(618, 119, 14, 5, '', '', 0, '', ''),
	(619, 119, 15, 3, 'Integer', '5', 0, 'count', NULL);
/*!40000 ALTER TABLE `exerciciocasotestepasso` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.exerciciocasotestepassoparametro
DROP TABLE IF EXISTS `exerciciocasotestepassoparametro`;
CREATE TABLE IF NOT EXISTS `exerciciocasotestepassoparametro` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `idPasso` int(11) NOT NULL,
  `Ordem` int(11) NOT NULL,
  `ObjectType` varchar(200) DEFAULT NULL,
  `ObjectName` varchar(200) DEFAULT NULL,
  `ObjectValue` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_casoTestePassoParametro_idx` (`idPasso`),
  CONSTRAINT `fk_casoTestePassoParametro` FOREIGN KEY (`idPasso`) REFERENCES `exerciciocasotestepasso` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=281 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.exerciciocasotestepassoparametro: ~109 rows (aproximadamente)
DELETE FROM `exerciciocasotestepassoparametro`;
/*!40000 ALTER TABLE `exerciciocasotestepassoparametro` DISABLE KEYS */;
INSERT INTO `exerciciocasotestepassoparametro` (`ID`, `idPasso`, `Ordem`, `ObjectType`, `ObjectName`, `ObjectValue`) VALUES
	(85, 250, 1, 'int', NULL, '666'),
	(86, 253, 1, 'int', NULL, '666'),
	(87, 255, 1, 'Integer', NULL, '6'),
	(88, 255, 2, 'Integer', NULL, '8'),
	(89, 258, 1, 'Integer', NULL, '6'),
	(90, 258, 2, 'Integer', NULL, '8'),
	(91, 260, 1, 'Integer', NULL, '0'),
	(92, 260, 2, 'Integer', NULL, '0'),
	(93, 261, 1, 'Integer', NULL, '4'),
	(94, 261, 2, 'Integer', NULL, '0'),
	(95, 262, 1, '', NULL, 'p'),
	(96, 262, 2, '', NULL, 'q'),
	(97, 265, 1, 'Integer', NULL, '0'),
	(98, 265, 2, 'Integer', NULL, '0'),
	(99, 266, 1, 'Integer', NULL, '4'),
	(100, 266, 2, 'Integer', NULL, '0'),
	(101, 267, 1, '', NULL, 'p'),
	(102, 267, 2, '', NULL, 'q'),
	(264, 446, 1, 'int', NULL, '666'),
	(265, 446, 2, 'int', NULL, '666'),
	(266, 448, 1, 'int', NULL, '666'),
	(267, 449, 1, 'String', NULL, 'x'),
	(268, 451, 1, 'int', NULL, '666'),
	(269, 452, 1, 'String', NULL, 'y'),
	(270, 454, 1, 'int', NULL, '666'),
	(271, 457, 1, 'int', NULL, '666'),
	(272, 462, 1, 'String', NULL, 'teste'),
	(273, 462, 2, 'int', NULL, '666'),
	(274, 462, 3, 'Double', NULL, '666'),
	(275, 464, 1, '', NULL, 'i'),
	(276, 464, 2, '', NULL, 'i'),
	(279, 471, 1, '', NULL, 'i'),
	(280, 471, 2, '', NULL, 'i');
/*!40000 ALTER TABLE `exerciciocasotestepassoparametro` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.exercicioclasse
DROP TABLE IF EXISTS `exercicioclasse`;
CREATE TABLE IF NOT EXISTS `exercicioclasse` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IdExercicio` int(11) NOT NULL,
  `IdAluno` int(11) NOT NULL,
  `NomeClasse` varchar(200) DEFAULT NULL,
  `Codigo` mediumtext,
  `CodigoAnterior` mediumtext,
  `DataCadastro` timestamp NULL DEFAULT NULL,
  `Favorito` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `fk_exercicioSolucaoClasseExercicio_idx` (`IdExercicio`),
  KEY `fk_exercicioSolucaoClasseAluno_idx` (`IdAluno`),
  CONSTRAINT `fk_exercicioSolucaoClasseAluno` FOREIGN KEY (`IdAluno`) REFERENCES `pessoa` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_exercicioSolucaoClasseExercicio` FOREIGN KEY (`IdExercicio`) REFERENCES `exercicio` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.exercicioclasse: ~11 rows (aproximadamente)
DELETE FROM `exercicioclasse`;
/*!40000 ALTER TABLE `exercicioclasse` DISABLE KEYS */;
INSERT INTO `exercicioclasse` (`ID`, `IdExercicio`, `IdAluno`, `NomeClasse`, `Codigo`, `CodigoAnterior`, `DataCadastro`, `Favorito`) VALUES
	(2, 20, 10, 'Ponto', '/**\r\n *\r\n * @author gilvani\r\n */\r\npublic class Ponto {\r\n\r\n    private int x;\r\n    private int y;\r\n    \r\n    public Ponto()\r\n    {\r\n    }\r\n    \r\n    public Ponto(int x, int y) {\r\n        this.x = x;\r\n        this.y = y;\r\n    }\r\n\r\n    public int getX() {\r\n        return x;\r\n    }\r\n\r\n    public void setX(int x) {\r\n        this.x = x;\r\n    }\r\n\r\n    public int getY() {\r\n        return y;\r\n    }\r\n\r\n    public void setY(int y) {\r\n        this.y = y;\r\n    }\r\n\r\n    @Override\r\n    public String toString() {\r\n        return "(" + x + "," + y + \')\';\r\n    }\r\n}\r\n', '/**\r\n *\r\n * @author gilvani\r\n */\r\npublic class Ponto {\r\n\r\n    //private int x;\r\n    private int y;\r\n    \r\n    public Ponto()\r\n    {\r\n    }\r\n    \r\n    public Ponto(int x, int y) {\r\n        this.x = x;\r\n        this.y = y;\r\n    }\r\n\r\n    public int getX() {\r\n        return x;\r\n    }\r\n\r\n    public void setX(int x) {\r\n        this.x = x;\r\n    }\r\n\r\n    public int getY() {\r\n        return y;\r\n    }\r\n\r\n    public void setY(int y) {\r\n        this.y = y;\r\n    }\r\n\r\n    @Override\r\n    public String toString() {\r\n        return "(" + x + "," + y + \')\';\r\n    }\r\n}\r\n', '2015-03-08 20:47:43', 1),
	(3, 20, 10, 'Reta', '/*\r\n * To change this license header, choose License Headers in Project Properties.\r\n * To change this template file, choose Tools | Templates\r\n * and open the template in the editor.\r\n */\r\n\r\n/**\r\n *\r\n * @author gilvani\r\n */\r\npublic class Reta {\r\n\r\n    private Ponto p;\r\n    private Ponto q;\r\n\r\n    public Reta() {\r\n    }\r\n\r\n    public Reta(Ponto p, Ponto q) {\r\n        this.p = p;\r\n        this.q = q;\r\n    }\r\n\r\n    public Ponto getP() {\r\n        return p;\r\n    }\r\n\r\n    public void setP(Ponto p) {\r\n        this.p = p;\r\n    }\r\n\r\n    public Ponto getQ() {\r\n        return q;\r\n    }\r\n\r\n    public void setQ(Ponto q) {\r\n        this.q = q;\r\n    }\r\n\r\n    public Double getDistancia() {\r\n        return 4.0;\r\n    }\r\n}\r\n', '/*\r\n * To change this license header, choose License Headers in Project Properties.\r\n * To change this template file, choose Tools | Templates\r\n * and open the template in the editor.\r\n */\r\n\r\n/**\r\n *\r\n * @author gilvani\r\n */\r\npublic class Reta {\r\n\r\n    private Ponto p;\r\n    private Ponto q;\r\n\r\n    public Reta() {\r\n    }\r\n\r\n    public Reta(Ponto p, Ponto q) {\r\n        this.p = p;\r\n        this.q = q;\r\n    }\r\n\r\n    public Ponto getP() {\r\n        return p;\r\n    }\r\n\r\n    public void setP(Ponto p) {\r\n        this.p = p;\r\n    }\r\n\r\n    public Ponto getQ() {\r\n        return q;\r\n    }\r\n\r\n    public void setQ(Ponto q) {\r\n        this.q = q;\r\n    }\r\n\r\n    public Double getDistancia() {\r\n        return 4.0;\r\n    }\r\n}\r\n', '2015-03-08 09:45:39', 0),
	(12, 24, 10, 'Ponto', '/* package qualquer; // Não coloque nome no package */\r\n\r\nimport java.util.*;\r\nimport java.lang.*;\r\nimport java.io.*;\r\n\r\nclass Ponto\r\n{\r\n   // Coloque aqui o seu código\r\n   \r\n   int x,y;\r\n   \r\n   public Ponto()\r\n   {\r\n   }\r\n   \r\n   public Ponto(int x, int y)\r\n   {\r\n       this.x=x;\r\n       this.y=y;\r\n   }\r\n   \r\n   public int getX()\r\n   {\r\n       return x;\r\n   }\r\n   \r\n   public int getY()\r\n   {\r\n        return y;\r\n   }\r\n   \r\n   public void setX(int x)\r\n   {\r\n       this.x=x;\r\n   }\r\n   \r\n   public void setY(int y)\r\n   {\r\n       this.y=y;\r\n   }\r\n   \r\n   @Override\r\n   public String toString(){\r\n    return "("+x+","+y+")";\r\n   }\r\n   \r\n   public void imprimeAlgo(String a, int b, Double c)\r\n   {\r\n   }\r\n   \r\n}', '/* package qualquer; // Não coloque nome no package */\r\n\r\nimport java.util.*;\r\nimport java.lang.*;\r\nimport java.io.*;\r\n\r\nclass Ponto\r\n{\r\n   // Coloque aqui o seu código\r\n   \r\n   int x,y;\r\n   \r\n   public Ponto()\r\n   {\r\n   }\r\n   \r\n   public Ponto(int x, int y)\r\n   {\r\n       this.x=x;\r\n       this.y=y;\r\n   }\r\n   \r\n   public int getX()\r\n   {\r\n       return 0/44;\r\n   }\r\n   \r\n   public int getY()\r\n   {\r\n        return y;\r\n   }\r\n   \r\n   public void setX(int x)\r\n   {\r\n       this.x=x;\r\n   }\r\n   \r\n   public void setY(int y)\r\n   {\r\n       this.y=y;\r\n   }\r\n   \r\n   @Override\r\n   public String toString(){\r\n    return "("+x+","+y+")";\r\n   }\r\n   \r\n   public void imprimeAlgo(String a, int b, Double c)\r\n   {\r\n   }\r\n   \r\n}', '2015-04-15 22:53:04', 0);
/*!40000 ALTER TABLE `exercicioclasse` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.exercicioclasseauxiliar
DROP TABLE IF EXISTS `exercicioclasseauxiliar`;
CREATE TABLE IF NOT EXISTS `exercicioclasseauxiliar` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IdExercicio` int(11) NOT NULL,
  `NomeClasse` varchar(200) DEFAULT NULL,
  `Codigo` mediumtext,
  `MostrarParaAluno` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `fk_exercicioSolucaoClasseExercicio_idx` (`IdExercicio`),
  CONSTRAINT `exercicioclasseauxiliar_Exercicio` FOREIGN KEY (`IdExercicio`) REFERENCES `exercicio` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

-- Copiando dados para a tabela feeper.exercicioclasseauxiliar: ~5 rows (aproximadamente)
DELETE FROM `exercicioclasseauxiliar`;
/*!40000 ALTER TABLE `exercicioclasseauxiliar` DISABLE KEYS */;
INSERT INTO `exercicioclasseauxiliar` (`ID`, `IdExercicio`, `NomeClasse`, `Codigo`, `MostrarParaAluno`) VALUES
	(1, 20, 'Ponto', '/**\r\n *\r\n * @author gilvani\r\n */\r\npublic class Ponto {\r\n\r\n    private int x;\r\n    private int y;\r\n    \r\n    public Ponto()\r\n    {\r\n    }\r\n    \r\n    public Ponto(int x, int y) {\r\n        this.x = x;\r\n        this.y = y;\r\n    }\r\n\r\n    public int getX() {\r\n        return x;\r\n    }\r\n\r\n    public void setX(int x) {\r\n        this.x = x;\r\n    }\r\n\r\n    public int getY() {\r\n        return y;\r\n    }\r\n\r\n    public void setY(int y) {\r\n        this.y = y;\r\n    }\r\n\r\n    @Override\r\n    public String toString() {\r\n        return "(" + x + "," + y + \')\';\r\n    }\r\n}\r\n', 0),
	(2, 20, 'Reta', '/*\r\n * To change this license header, choose License Headers in Project Properties.\r\n * To change this template file, choose Tools | Templates\r\n * and open the template in the editor.\r\n */\r\n\r\n/**\r\n *\r\n * @author gilvani\r\n */\r\npublic class Reta {\r\n\r\n    private Ponto p;\r\n    private Ponto q;\r\n\r\n    public Reta() {\r\n    }\r\n\r\n    public Reta(Ponto p, Ponto q) {\r\n        this.p = p;\r\n        this.q = q;\r\n    }\r\n\r\n    public Ponto getP() {\r\n        return p;\r\n    }\r\n\r\n    public void setP(Ponto p) {\r\n        this.p = p;\r\n    }\r\n\r\n    public Ponto getQ() {\r\n        return q;\r\n    }\r\n\r\n    public void setQ(Ponto q) {\r\n        this.q = q;\r\n    }\r\n\r\n    public Double getDistancia() {\r\n        return 0.0;\r\n    }\r\n}\r\n', 0),
	(6, 24, 'Ponto', '/* package qualquer; // Não coloque nome no package */\n\nimport java.util.*;\nimport java.lang.*;\nimport java.io.*;\n\nclass Ponto\n{\n   // Coloque aqui o seu código\n   \n   int x,y;\n   \n   public Ponto()\n   {\n   }\n   \n   public Ponto(int x, int y)\n   {\n       this.x=x;\n       this.y=y;\n   }\n   \n   public int getX()\n   {\n       return x;\n   }\n   \n   public int getY()\n   {\n        return y;\n   }\n   \n   public void setX(int x)\n   {\n   }\n   \n   public void setY(int y)\n   {\n   }\n   \n   @Override\n   public String toString(){\n    return "";\n   }\n   \n   public void imprimeAlgo(String a, int b, Double c)\n   {\n   }\n   \n}', 1);
/*!40000 ALTER TABLE `exercicioclasseauxiliar` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.exercicioclassemarcacao
DROP TABLE IF EXISTS `exercicioclassemarcacao`;
CREATE TABLE IF NOT EXISTS `exercicioclassemarcacao` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IdExercicioClasse` int(11) NOT NULL,
  `IdAutor` int(11) NOT NULL,
  `IdTipoMarcacao` int(11) NOT NULL,
  `LinhaInicio` int(11) NOT NULL,
  `LinhaFim` int(11) NOT NULL,
  `Anotacao` varchar(500) NOT NULL,
  `DataCadastro` datetime NOT NULL,
  `Ativo` tinyint(1) NOT NULL DEFAULT '1',
  `IdNovidade` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_TipoMarcacao_ClasseMarcacao` (`IdTipoMarcacao`),
  KEY `FK_Novidade_ClasseMarcacao` (`IdNovidade`),
  KEY `FK_Pessoa_ClasseMarcacao` (`IdAutor`),
  KEY `FK_Classe_ClasseMarcacao` (`IdExercicioClasse`),
  CONSTRAINT `FK_Classe_ClasseMarcacao` FOREIGN KEY (`IdExercicioClasse`) REFERENCES `exercicioclasse` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_Novidade_ClasseMarcacao` FOREIGN KEY (`IdNovidade`) REFERENCES `novidade` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_Pessoa_ClasseMarcacao` FOREIGN KEY (`IdAutor`) REFERENCES `pessoa` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_TipoMarcacao_ClasseMarcacao` FOREIGN KEY (`IdTipoMarcacao`) REFERENCES `tipomarcacao` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.exercicioclassemarcacao: ~5 rows (aproximadamente)
DELETE FROM `exercicioclassemarcacao`;
/*!40000 ALTER TABLE `exercicioclassemarcacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `exercicioclassemarcacao` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.exerciciosolucao
DROP TABLE IF EXISTS `exerciciosolucao`;
CREATE TABLE IF NOT EXISTS `exerciciosolucao` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IdExercicio` int(11) NOT NULL,
  `IdAluno` int(11) NOT NULL,
  `IdStatus` int(11) NOT NULL,
  `DataCadastro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ErrosCount` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_ExercicioSolucao_idx` (`IdExercicio`),
  KEY `fk_ExercicioSolucaoAluno_idx` (`IdAluno`),
  KEY `fk_ExercicioSolucaoStatus_idx` (`IdStatus`),
  CONSTRAINT `fk_ExercicioSolucao` FOREIGN KEY (`IdExercicio`) REFERENCES `exercicio` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_ExercicioSolucaoAluno` FOREIGN KEY (`IdAluno`) REFERENCES `pessoa` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_ExercicioSolucaoStatus` FOREIGN KEY (`IdStatus`) REFERENCES `statussolucao` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.exerciciosolucao: ~131 rows (aproximadamente)
DELETE FROM `exerciciosolucao`;
/*!40000 ALTER TABLE `exerciciosolucao` DISABLE KEYS */;
/*!40000 ALTER TABLE `exerciciosolucao` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.exerciciosolucaoclasse
DROP TABLE IF EXISTS `exerciciosolucaoclasse`;
CREATE TABLE IF NOT EXISTS `exerciciosolucaoclasse` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IdSolucao` int(11) NOT NULL,
  `NomeClasse` varchar(200) NOT NULL,
  `Codigo` mediumtext NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_SolucaoClasse_Solucao` (`IdSolucao`),
  CONSTRAINT `fk_SolucaoClasse_Solucao` FOREIGN KEY (`IdSolucao`) REFERENCES `exerciciosolucao` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=187 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.exerciciosolucaoclasse: ~168 rows (aproximadamente)
DELETE FROM `exerciciosolucaoclasse`;
/*!40000 ALTER TABLE `exerciciosolucaoclasse` DISABLE KEYS */;
/*!40000 ALTER TABLE `exerciciosolucaoclasse` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.exerciciosolucaoclassemarcacao
DROP TABLE IF EXISTS `exerciciosolucaoclassemarcacao`;
CREATE TABLE IF NOT EXISTS `exerciciosolucaoclassemarcacao` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IdExercicioSolucaoClasse` int(11) NOT NULL,
  `IdAutor` int(11) NOT NULL,
  `IdTipoMarcacao` int(11) NOT NULL,
  `LinhaInicio` int(11) NOT NULL,
  `LinhaFim` int(11) NOT NULL,
  `Anotacao` varchar(500) NOT NULL,
  `DataCadastro` datetime NOT NULL,
  `Ativo` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  KEY `FK_RespostaClasse_RespostaClasseMarcacao_idx` (`IdExercicioSolucaoClasse`),
  KEY `FK_Pessoa_RespostaClasseMarcacao_idx` (`IdAutor`),
  KEY `FK_TipoMarcacao_RespostaClasseMarcacao_idx` (`IdTipoMarcacao`),
  CONSTRAINT `FK_ExercicioSolucaoClasse_ExercicioSolucaoClasseMarcacao` FOREIGN KEY (`IdExercicioSolucaoClasse`) REFERENCES `exerciciosolucaoclasse` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_Pessoa_ExercicioSolucaoClasseMarcacao` FOREIGN KEY (`IdAutor`) REFERENCES `pessoa` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_TipoMarcacao_ExercicioSolucaoClasseMarcacao` FOREIGN KEY (`IdTipoMarcacao`) REFERENCES `tipomarcacao` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.exerciciosolucaoclassemarcacao: ~0 rows (aproximadamente)
DELETE FROM `exerciciosolucaoclassemarcacao`;
/*!40000 ALTER TABLE `exerciciosolucaoclassemarcacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `exerciciosolucaoclassemarcacao` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.exerciciosolucaoerro
DROP TABLE IF EXISTS `exerciciosolucaoerro`;
CREATE TABLE IF NOT EXISTS `exerciciosolucaoerro` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IdSolucao` int(11) NOT NULL,
  `IdCasoTeste` int(11) NOT NULL,
  `MensagemPersonalizada` varchar(5000) DEFAULT NULL,
  `ErrorType` int(11) NOT NULL,
  `MensagemErro` varchar(5000) DEFAULT NULL,
  `StaticErrorType` varchar(200) DEFAULT NULL,
  `LinhaErro` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_Erro_Solucao` (`IdSolucao`),
  CONSTRAINT `fk_Erro_Solucao` FOREIGN KEY (`IdSolucao`) REFERENCES `exerciciosolucao` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=152 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.exerciciosolucaoerro: ~133 rows (aproximadamente)
DELETE FROM `exerciciosolucaoerro`;
/*!40000 ALTER TABLE `exerciciosolucaoerro` DISABLE KEYS */;
/*!40000 ALTER TABLE `exerciciosolucaoerro` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.filanovasenha
DROP TABLE IF EXISTS `filanovasenha`;
CREATE TABLE IF NOT EXISTS `filanovasenha` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IdPessoa` int(11) NOT NULL,
  `Chave` varchar(36) NOT NULL,
  `DataCadastro` datetime NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Pessoa_FilaNovaSenha_idx` (`IdPessoa`),
  CONSTRAINT `FK_Pessoa_FilaNovaSenha` FOREIGN KEY (`IdPessoa`) REFERENCES `pessoa` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.filanovasenha: ~0 rows (aproximadamente)
DELETE FROM `filanovasenha`;
/*!40000 ALTER TABLE `filanovasenha` DISABLE KEYS */;
/*!40000 ALTER TABLE `filanovasenha` ENABLE KEYS */;


-- Copiando estrutura para função feeper.GET_TIMEDURATION
DROP FUNCTION IF EXISTS `GET_TIMEDURATION`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` FUNCTION `GET_TIMEDURATION`(d1 datetime) RETURNS varchar(100) CHARSET latin1
BEGIN

    DECLARE period VARCHAR(100);
    DECLARE secsago INT;
    SET period = '';
    SET secsago = TIMESTAMPDIFF(SECOND, d1, NOW());

    IF (secsago <= 0) THEN
       SET period = '1 segundo';
    ELSEIF (secsago < 60) THEN
       SET period = CONCAT(secsago, CASE WHEN secsago > 1 THEN ' segundos' ELSE ' segundo' END);
    ELSEIF (secsago < 3600) THEN
       SET period = ROUND(secsago/60);
       SET period = CONCAT(period, CASE WHEN period > 1 THEN ' minutos' ELSE ' minuto' END);
    ELSEIF (secsago < 86400) THEN
       SET period = ROUND(secsago/3600);
       SET period = CONCAT(period, CASE WHEN period > 1 THEN ' horas' ELSE ' hora' END);
    ELSEIF (secsago < 604800) THEN
       SET period = ROUND(secsago/86400);
       SET period = CONCAT(period, CASE WHEN period > 1 THEN ' dias' ELSE ' dia' END);
    ELSEIF (secsago < 2419200) THEN
       SET period = ROUND(secsago/604800);
       SET period = CONCAT(period, CASE WHEN period > 1 THEN ' semanas' ELSE ' semana' END);
    ELSEIF (secsago < 29030400) THEN
       SET period = ROUND(secsago/2419200);
       SET period = CONCAT(period, CASE WHEN period > 1 THEN ' meses' ELSE ' mês' END);
    ELSE
       SET period = ROUND(secsago/29030400);
       SET period = CONCAT(period, CASE WHEN period > 1 THEN ' anos' ELSE ' ano' END);
    END IF;
    
    RETURN period;

  END//
DELIMITER ;


-- Copiando estrutura para tabela feeper.grade
DROP TABLE IF EXISTS `grade`;
CREATE TABLE IF NOT EXISTS `grade` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IdTurma` int(11) NOT NULL,
  `IdAluno` int(11) NOT NULL,
  `IdExercicio` int(11) NOT NULL,
  `Nota` decimal(5,2) NOT NULL DEFAULT '0.00',
  `Visivel` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `FK_Turma_Grade` (`IdTurma`),
  KEY `FK_Pessoa_Grade` (`IdAluno`),
  KEY `FK_Exercicio_Grade` (`IdExercicio`),
  CONSTRAINT `FK_Exercicio_Grade` FOREIGN KEY (`IdExercicio`) REFERENCES `exercicio` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_Pessoa_Grade` FOREIGN KEY (`IdAluno`) REFERENCES `pessoa` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_Turma_Grade` FOREIGN KEY (`IdTurma`) REFERENCES `turma` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.grade: ~0 rows (aproximadamente)
DELETE FROM `grade`;
/*!40000 ALTER TABLE `grade` DISABLE KEYS */;
/*!40000 ALTER TABLE `grade` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.log
DROP TABLE IF EXISTS `log`;
CREATE TABLE IF NOT EXISTS `log` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `IdPessoa` int(11) NOT NULL,
  `Mensagem` varchar(500) NOT NULL,
  `IdTipoLog` int(11) NOT NULL,
  `DataCadastro` datetime NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Pessoa_Log` (`IdPessoa`),
  KEY `FK_TipoLog_Log` (`IdTipoLog`),
  CONSTRAINT `FK_Pessoa_Log` FOREIGN KEY (`IdPessoa`) REFERENCES `pessoa` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_TipoLog_Log` FOREIGN KEY (`IdTipoLog`) REFERENCES `tipolog` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.log: ~0 rows (aproximadamente)
DELETE FROM `log`;
/*!40000 ALTER TABLE `log` DISABLE KEYS */;
/*!40000 ALTER TABLE `log` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.mensagem
DROP TABLE IF EXISTS `mensagem`;
CREATE TABLE IF NOT EXISTS `mensagem` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `IdMensagemCabecalho` bigint(20) NOT NULL,
  `IdPessoa` int(11) NOT NULL,
  `Texto` varchar(500) NOT NULL,
  `DataCadastro` datetime NOT NULL,
  `Ativo` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  KEY `FK_Pessoa_Mensagem` (`IdPessoa`),
  KEY `FK_MensagemCabecalho_Mensagem` (`IdMensagemCabecalho`),
  CONSTRAINT `FK_MensagemCabecalho_Mensagem` FOREIGN KEY (`IdMensagemCabecalho`) REFERENCES `mensagemcabecalho` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_Pessoa_Mensagem` FOREIGN KEY (`IdPessoa`) REFERENCES `pessoa` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.mensagem: ~8 rows (aproximadamente)
DELETE FROM `mensagem`;
/*!40000 ALTER TABLE `mensagem` DISABLE KEYS */;
/*!40000 ALTER TABLE `mensagem` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.mensagemcabecalho
DROP TABLE IF EXISTS `mensagemcabecalho`;
CREATE TABLE IF NOT EXISTS `mensagemcabecalho` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `IdExercicioClasseMarcacao` int(11) DEFAULT NULL,
  `IdExercicioSolucaoClasseMarcacao` int(11) DEFAULT NULL,
  `Publico` tinyint(1) NOT NULL,
  `DataCadastro` datetime NOT NULL,
  `Ativo` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  KEY `FK_MensagemCabecalho_ExercicioClasseMarcacao` (`IdExercicioClasseMarcacao`),
  KEY `FK_MensagemCabecalho_ExercicioSolucaoClasseMarcacao` (`IdExercicioSolucaoClasseMarcacao`),
  CONSTRAINT `FK_MensagemCabecalho_ExercicioClasseMarcacao` FOREIGN KEY (`IdExercicioClasseMarcacao`) REFERENCES `exercicioclassemarcacao` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_MensagemCabecalho_ExercicioSolucaoClasseMarcacao` FOREIGN KEY (`IdExercicioSolucaoClasseMarcacao`) REFERENCES `exerciciosolucaoclassemarcacao` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.mensagemcabecalho: ~5 rows (aproximadamente)
DELETE FROM `mensagemcabecalho`;
/*!40000 ALTER TABLE `mensagemcabecalho` DISABLE KEYS */;
/*!40000 ALTER TABLE `mensagemcabecalho` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.mensagemcomentario
DROP TABLE IF EXISTS `mensagemcomentario`;
CREATE TABLE IF NOT EXISTS `mensagemcomentario` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `IdMensagemCabecalho` bigint(20) NOT NULL,
  `IdAutor` int(11) NOT NULL,
  `DataCadastro` datetime NOT NULL,
  `Ativo` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  KEY `FK_MensagemCabecalho_MensagemComentario` (`IdMensagemCabecalho`),
  KEY `FK_Pessoa_MensagemComentario` (`IdAutor`),
  CONSTRAINT `FK_MensagemCabecalho_MensagemComentario` FOREIGN KEY (`IdMensagemCabecalho`) REFERENCES `mensagemcabecalho` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_Pessoa_MensagemComentario` FOREIGN KEY (`IdAutor`) REFERENCES `pessoa` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.mensagemcomentario: ~0 rows (aproximadamente)
DELETE FROM `mensagemcomentario`;
/*!40000 ALTER TABLE `mensagemcomentario` DISABLE KEYS */;
/*!40000 ALTER TABLE `mensagemcomentario` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.mensagemcurtir
DROP TABLE IF EXISTS `mensagemcurtir`;
CREATE TABLE IF NOT EXISTS `mensagemcurtir` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `IdMensagemCabecalho` bigint(20) DEFAULT NULL,
  `IdMensagemComentario` bigint(20) DEFAULT NULL,
  `IdAutor` int(11) NOT NULL,
  `DataCadastro` datetime NOT NULL,
  `Ativo` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  KEY `FK_MensagemCabecalho_MensagemCurtir` (`IdMensagemCabecalho`),
  KEY `FK_MensagemComentario_MensagemCurtir` (`IdMensagemComentario`),
  KEY `FK_Pessoa_MensagemCurtir` (`IdAutor`),
  CONSTRAINT `FK_MensagemCabecalho_MensagemCurtir` FOREIGN KEY (`IdMensagemCabecalho`) REFERENCES `mensagemcabecalho` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_MensagemComentario_MensagemCurtir` FOREIGN KEY (`IdMensagemComentario`) REFERENCES `mensagemcomentario` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_Pessoa_MensagemCurtir` FOREIGN KEY (`IdAutor`) REFERENCES `pessoa` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.mensagemcurtir: ~0 rows (aproximadamente)
DELETE FROM `mensagemcurtir`;
/*!40000 ALTER TABLE `mensagemcurtir` DISABLE KEYS */;
/*!40000 ALTER TABLE `mensagemcurtir` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.mensagemleitor
DROP TABLE IF EXISTS `mensagemleitor`;
CREATE TABLE IF NOT EXISTS `mensagemleitor` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `IdMensagemCabecalho` bigint(20) NOT NULL,
  `IdLeitor` int(11) NOT NULL,
  `TipoLeitor` char(1) NOT NULL COMMENT '''R'': Remetente\n''D'': Destinatário\n''P'': Público',
  `DataUltimaLeitura` datetime DEFAULT NULL,
  `Ativo` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  KEY `FK_Pessoa_MensagemLeitor` (`IdLeitor`),
  KEY `FK_MensagemCabecalho_MensagemLeitor` (`IdMensagemCabecalho`),
  CONSTRAINT `FK_MensagemCabecalho_MensagemLeitor` FOREIGN KEY (`IdMensagemCabecalho`) REFERENCES `mensagemcabecalho` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_Pessoa_MensagemLeitor` FOREIGN KEY (`IdLeitor`) REFERENCES `pessoa` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.mensagemleitor: ~10 rows (aproximadamente)
DELETE FROM `mensagemleitor`;
/*!40000 ALTER TABLE `mensagemleitor` DISABLE KEYS */;
/*!40000 ALTER TABLE `mensagemleitor` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.mensagempredefinida
DROP TABLE IF EXISTS `mensagempredefinida`;
CREATE TABLE IF NOT EXISTS `mensagempredefinida` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IdAutor` int(11) NOT NULL,
  `MensagemPredefinida` varchar(10000) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_mensagemPersonalizada_Autor` (`IdAutor`),
  CONSTRAINT `fk_mensagemPersonalizada_Autor` FOREIGN KEY (`IdAutor`) REFERENCES `pessoa` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.mensagempredefinida: ~0 rows (aproximadamente)
DELETE FROM `mensagempredefinida`;
/*!40000 ALTER TABLE `mensagempredefinida` DISABLE KEYS */;
/*!40000 ALTER TABLE `mensagempredefinida` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.novidade
DROP TABLE IF EXISTS `novidade`;
CREATE TABLE IF NOT EXISTS `novidade` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `IdTipoNovidade` int(11) NOT NULL,
  `Resumo` varchar(100) DEFAULT NULL,
  `DataCadastro` datetime NOT NULL,
  `DataLeitura` datetime DEFAULT NULL,
  `IdDestinatario` int(11) DEFAULT NULL,
  `Publico` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `FK_TipoNovidade_Novidade` (`IdTipoNovidade`),
  KEY `FK_Pessoa_Novidade` (`IdDestinatario`),
  CONSTRAINT `FK_Pessoa_Novidade` FOREIGN KEY (`IdDestinatario`) REFERENCES `pessoa` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_TipoNovidade_Novidade` FOREIGN KEY (`IdTipoNovidade`) REFERENCES `tiponovidade` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.novidade: ~0 rows (aproximadamente)
DELETE FROM `novidade`;
/*!40000 ALTER TABLE `novidade` DISABLE KEYS */;
/*!40000 ALTER TABLE `novidade` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.perfil
DROP TABLE IF EXISTS `perfil`;
CREATE TABLE IF NOT EXISTS `perfil` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.perfil: ~3 rows (aproximadamente)
DELETE FROM `perfil`;
/*!40000 ALTER TABLE `perfil` DISABLE KEYS */;
INSERT INTO `perfil` (`ID`, `Descricao`) VALUES
	(1, 'Admin'),
	(2, 'Professor'),
	(3, 'Aluno');
/*!40000 ALTER TABLE `perfil` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.pessoa
DROP TABLE IF EXISTS `pessoa`;
CREATE TABLE IF NOT EXISTS `pessoa` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(100) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Senha` varchar(50) NOT NULL,
  `DataCadastro` datetime NOT NULL,
  `DataUltimoAcesso` datetime DEFAULT NULL,
  `Ativo` tinyint(1) NOT NULL DEFAULT '1',
  `IdPerfil` int(11) NOT NULL,
  `PossuiFoto` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `FK_Perfil_Pessoa` (`IdPerfil`),
  CONSTRAINT `FK_Perfil_Pessoa` FOREIGN KEY (`IdPerfil`) REFERENCES `perfil` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.pessoa: ~9 rows (aproximadamente)
DELETE FROM `pessoa`;
/*!40000 ALTER TABLE `pessoa` DISABLE KEYS */;
INSERT INTO `pessoa` (`ID`, `Nome`, `Email`, `Senha`, `DataCadastro`, `DataUltimoAcesso`, `Ativo`, `IdPerfil`, `PossuiFoto`) VALUES
	(2, 'Patrícia Jaques', 'pjaques@gmail.com', 'e1adc3949ba59abbe56e057f2f883e', '2013-12-27 00:59:00', '2014-05-18 01:24:58', 1, 2, 1),
	(8, 'Gilvani Administrador', 'gilschneider90@gmail.com', 'a6d414ac4f293187dd42025834925f7', '2014-05-18 17:40:30', '2015-04-15 02:16:47', 1, 1, 1),
	(9, 'Gilvani Professor', 'gilvani_schneider@hotmail.com', 'e1adc3949ba59abbe56e057f2f883e', '2015-03-04 11:35:44', '2015-04-15 23:07:14', 1, 2, 0),
	(10, 'Gilvani Aluno', 'gilvani_schneider@hotmail.com', 'a6d414ac4f293187dd42025834925f7', '2015-03-04 11:36:22', '2015-04-15 23:08:09', 1, 3, 0);
/*!40000 ALTER TABLE `pessoa` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.pessoaconquista
DROP TABLE IF EXISTS `pessoaconquista`;
CREATE TABLE IF NOT EXISTS `pessoaconquista` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IdPessoa` int(11) NOT NULL,
  `IdConquista` int(11) NOT NULL,
  `DataConquista` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Pessoa_PessoaConquista` (`IdPessoa`),
  KEY `FK_Conquista_PessoaConquista` (`IdConquista`),
  CONSTRAINT `FK_Conquista_PessoaConquista` FOREIGN KEY (`IdConquista`) REFERENCES `conquista` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_Pessoa_PessoaConquista` FOREIGN KEY (`IdPessoa`) REFERENCES `pessoa` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.pessoaconquista: ~0 rows (aproximadamente)
DELETE FROM `pessoaconquista`;
/*!40000 ALTER TABLE `pessoaconquista` DISABLE KEYS */;
/*!40000 ALTER TABLE `pessoaconquista` ENABLE KEYS */;


-- Copiando estrutura para função feeper.REMOVE_HTML_TAGS
DROP FUNCTION IF EXISTS `REMOVE_HTML_TAGS`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` FUNCTION `REMOVE_HTML_TAGS`(`content` TEXT) RETURNS text CHARSET utf8
    DETERMINISTIC
BEGIN
  DECLARE iStart, iEnd, iLength int;
    WHILE Locate( '<', content ) > 0 And Locate( '>', content, Locate( '<', content )) > 0 DO
      BEGIN
        SET iStart = Locate( '<', content ), iEnd = Locate( '>', content, Locate('<', content ));
        SET iLength = ( iEnd - iStart) + 1;
        IF iLength > 0 THEN
          BEGIN
            SET content = Insert( content, iStart, iLength, '');
          END;
        END IF;
      END;
    END WHILE;
    RETURN content;
END//
DELIMITER ;


-- Copiando estrutura para tabela feeper.statussolucao
DROP TABLE IF EXISTS `statussolucao`;
CREATE TABLE IF NOT EXISTS `statussolucao` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(45) NOT NULL,
  `DataCadastro` datetime NOT NULL,
  `Ativo` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.statussolucao: ~4 rows (aproximadamente)
DELETE FROM `statussolucao`;
/*!40000 ALTER TABLE `statussolucao` DISABLE KEYS */;
INSERT INTO `statussolucao` (`ID`, `Nome`, `DataCadastro`, `Ativo`) VALUES
	(1, 'Aguardando', '2014-02-03 00:00:00', 1),
	(2, 'Erro de Compilação ', '2014-02-03 00:00:00', 1),
	(3, 'Resultado Inválido', '2014-02-03 00:00:00', 1),
	(4, 'Exercício Resolvido', '2014-02-03 00:00:00', 1);
/*!40000 ALTER TABLE `statussolucao` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.tela
DROP TABLE IF EXISTS `tela`;
CREATE TABLE IF NOT EXISTS `tela` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Descricao` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.tela: ~91 rows (aproximadamente)
DELETE FROM `tela`;
/*!40000 ALTER TABLE `tela` DISABLE KEYS */;
INSERT INTO `tela` (`ID`, `Descricao`) VALUES
	(1, 'classes'),
	(2, 'colegas'),
	(3, 'conquistas'),
	(4, 'exercicios'),
	(5, 'exercicios/add'),
	(6, 'exercicios/saveadd'),
	(7, 'exercicios/edit'),
	(8, 'exercicios/saveedit'),
	(9, 'exercicios/savecasosteste'),
	(10, 'exercicios/delete'),
	(11, 'exercicios/uploaddescricao'),
	(12, 'exercicios/meusexercicios'),
	(13, 'exercicios/solucionar'),
	(14, 'mensagens'),
	(15, 'notas'),
	(16, 'novidades'),
	(17, 'pessoa/add'),
	(18, 'pessoa/saveadd'),
	(19, 'pessoa/edit'),
	(20, 'pessoa/saveedit'),
	(24, 'pessoa/search'),
	(25, 'turma'),
	(26, 'turma/add'),
	(27, 'turma/saveadd'),
	(28, 'turma/edit'),
	(29, 'turma/saveedit'),
	(30, 'turma/delete'),
	(31, 'turma/details'),
	(32, 'classes/list'),
	(33, 'colegas/list'),
	(34, 'conquistas/list'),
	(35, 'exercicios/list'),
	(36, 'mensagens/list'),
	(37, 'notas/list'),
	(38, 'novidades/list'),
	(39, 'turma/list'),
	(40, 'turma/change'),
	(41, 'exercicios/enviarcorrecao'),
	(42, 'exercicios/download'),
	(43, 'conquistas/minhasconquistas'),
	(44, 'exercicios/showclassecode'),
	(45, 'exercicios/saveclasse'),
	(46, 'exercicios/deleteclasse'),
	(47, 'exercicios/showquestion'),
	(48, 'exercicios/savequestion'),
	(49, 'exercicios/saveannotation'),
	(50, 'exercicios/showannotation'),
	(51, 'exercicios/uploadclass'),
	(52, 'classes/show'),
	(53, 'solucoes/results'),
	(54, 'classes/listsolucaoclasses'),
	(55, 'classes/showversion'),
	(56, 'exercicios/getstatus'),
	(57, 'notas/results'),
	(58, 'classes/downloadfileversion'),
	(59, 'classes/downloadpkgversion'),
	(60, 'classes/savefavorite'),
	(61, 'novidades/inicio'),
	(62, 'pessoa/list'),
	(63, 'pessoa'),
	(64, 'turma/addaluno'),
	(65, 'turma/saveaddaluno'),
	(66, 'turma/deletealuno'),
	(67, 'log'),
	(68, 'log/list'),
	(69, 'pessoa/perfil'),
	(70, 'pessoa/saveperfil'),
	(71, 'pessoa/uploadphoto'),
	(73, 'turma/deleteexercicio'),
	(74, 'turma/addexercicio'),
	(75, 'turma/saveaddexercicio'),
	(76, 'turma/exerciciovisivel'),
	(77, 'turma/enviarconvites'),
	(78, 'classes/showversionquestion'),
	(79, 'classes/saveversionquestion'),
	(80, 'classes/showversionannotation'),
	(81, 'classes/saveversionannotation'),
	(82, 'turma/enviarconvite'),
	(84, 'exercicios/uploadClasseAuxiliar'),
	(85, 'exercicios/getjson'),
	(86, 'exercicios/savejson'),
	(87, 'solucoes/listerros'),
	(88, 'exercicios/verdescricao'),
	(89, 'configuracoes/edit'),
	(90, 'configuracoes/getJson'),
	(91, 'configuracoes/saveJson'),
	(92, 'mensagenspredefinidas/edit'),
	(93, 'mensagenspredefinidas/getJson'),
	(94, 'mensagenspredefinidas/saveJson'),
	(95, 'mensagenspredefinidas/search'),
	(96, 'exercicios/carregaAssinaturas'),
	(97, 'plagiarism/getJson'),
	(98, 'plagiarism/performCheck'),
	(99, 'plagiarism/list'),
	(100, 'classes/diffclasses');
/*!40000 ALTER TABLE `tela` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.telaperfil
DROP TABLE IF EXISTS `telaperfil`;
CREATE TABLE IF NOT EXISTS `telaperfil` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IdTela` int(11) NOT NULL,
  `IdPerfil` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Tela_TelaPerfil` (`IdTela`),
  KEY `FK_Perfil_TelaPerfil` (`IdPerfil`),
  CONSTRAINT `FK_Perfil_TelaPerfil` FOREIGN KEY (`IdPerfil`) REFERENCES `perfil` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_Tela_TelaPerfil` FOREIGN KEY (`IdTela`) REFERENCES `tela` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=221 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.telaperfil: ~187 rows (aproximadamente)
DELETE FROM `telaperfil`;
/*!40000 ALTER TABLE `telaperfil` DISABLE KEYS */;
INSERT INTO `telaperfil` (`ID`, `IdTela`, `IdPerfil`) VALUES
	(1, 1, 1),
	(2, 2, 1),
	(3, 3, 1),
	(4, 4, 1),
	(5, 5, 1),
	(6, 6, 1),
	(7, 7, 1),
	(8, 8, 1),
	(9, 9, 1),
	(10, 10, 1),
	(11, 11, 1),
	(12, 12, 1),
	(13, 13, 1),
	(14, 14, 1),
	(15, 15, 1),
	(16, 16, 1),
	(17, 17, 1),
	(18, 18, 1),
	(19, 19, 1),
	(20, 20, 1),
	(24, 24, 1),
	(25, 25, 1),
	(26, 26, 1),
	(27, 27, 1),
	(28, 28, 1),
	(29, 29, 1),
	(30, 30, 1),
	(31, 31, 1),
	(32, 32, 1),
	(33, 33, 1),
	(34, 34, 1),
	(35, 35, 1),
	(36, 36, 1),
	(37, 37, 1),
	(38, 38, 1),
	(39, 39, 1),
	(40, 1, 2),
	(41, 2, 2),
	(42, 3, 2),
	(43, 4, 2),
	(44, 5, 2),
	(45, 6, 2),
	(46, 7, 2),
	(47, 8, 2),
	(48, 9, 2),
	(49, 10, 2),
	(50, 11, 2),
	(51, 12, 2),
	(52, 13, 2),
	(53, 14, 2),
	(54, 15, 2),
	(55, 16, 2),
	(56, 17, 2),
	(57, 18, 2),
	(58, 19, 2),
	(59, 20, 2),
	(61, 24, 2),
	(62, 32, 2),
	(63, 33, 2),
	(64, 34, 2),
	(65, 35, 2),
	(66, 36, 2),
	(67, 37, 2),
	(68, 38, 2),
	(71, 1, 3),
	(72, 2, 3),
	(73, 3, 3),
	(74, 4, 3),
	(75, 12, 3),
	(76, 13, 3),
	(77, 14, 3),
	(78, 15, 3),
	(79, 16, 3),
	(80, 24, 3),
	(81, 32, 3),
	(82, 33, 3),
	(83, 34, 3),
	(84, 36, 3),
	(85, 37, 3),
	(86, 38, 3),
	(102, 40, 2),
	(103, 40, 3),
	(104, 41, 3),
	(105, 42, 2),
	(106, 42, 3),
	(107, 43, 3),
	(108, 44, 3),
	(109, 45, 3),
	(110, 46, 3),
	(111, 47, 2),
	(112, 47, 3),
	(113, 48, 2),
	(114, 48, 3),
	(115, 49, 2),
	(116, 49, 3),
	(117, 50, 3),
	(118, 50, 2),
	(119, 51, 3),
	(120, 52, 2),
	(121, 52, 3),
	(122, 53, 2),
	(123, 53, 3),
	(124, 54, 2),
	(125, 54, 3),
	(126, 55, 2),
	(127, 55, 3),
	(128, 56, 2),
	(129, 56, 3),
	(130, 57, 2),
	(131, 57, 3),
	(132, 58, 2),
	(133, 58, 3),
	(134, 59, 2),
	(135, 59, 3),
	(136, 60, 3),
	(137, 60, 2),
	(138, 61, 3),
	(139, 61, 2),
	(140, 61, 1),
	(141, 62, 1),
	(142, 63, 1),
	(143, 64, 1),
	(144, 65, 1),
	(145, 66, 1),
	(146, 67, 1),
	(147, 68, 1),
	(148, 69, 1),
	(149, 69, 2),
	(150, 69, 3),
	(151, 70, 1),
	(152, 70, 2),
	(153, 70, 3),
	(154, 71, 3),
	(155, 71, 2),
	(156, 71, 1),
	(159, 73, 1),
	(160, 73, 2),
	(161, 74, 1),
	(162, 74, 2),
	(163, 75, 1),
	(165, 75, 2),
	(166, 76, 1),
	(167, 76, 2),
	(168, 28, 2),
	(169, 29, 2),
	(170, 31, 2),
	(171, 39, 2),
	(172, 64, 2),
	(173, 65, 2),
	(174, 66, 2),
	(175, 77, 1),
	(176, 77, 2),
	(177, 78, 2),
	(178, 79, 2),
	(179, 80, 2),
	(180, 81, 2),
	(181, 78, 3),
	(182, 79, 3),
	(183, 80, 3),
	(184, 81, 3),
	(185, 82, 1),
	(186, 82, 2),
	(188, 84, 1),
	(189, 84, 2),
	(191, 85, 1),
	(192, 85, 2),
	(194, 86, 1),
	(195, 86, 2),
	(198, 87, 1),
	(199, 87, 3),
	(200, 87, 2),
	(201, 88, 1),
	(202, 88, 2),
	(203, 88, 3),
	(204, 89, 1),
	(206, 90, 1),
	(209, 91, 1),
	(210, 92, 2),
	(211, 93, 2),
	(212, 94, 2),
	(213, 95, 2),
	(214, 96, 2),
	(215, 96, 1),
	(216, 97, 2),
	(217, 98, 2),
	(218, 99, 2),
	(220, 100, 2);
/*!40000 ALTER TABLE `telaperfil` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.tipolog
DROP TABLE IF EXISTS `tipolog`;
CREATE TABLE IF NOT EXISTS `tipolog` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.tipolog: ~29 rows (aproximadamente)
DELETE FROM `tipolog`;
/*!40000 ALTER TABLE `tipolog` DISABLE KEYS */;
INSERT INTO `tipolog` (`ID`, `Nome`) VALUES
	(1, 'Login'),
	(2, 'Realizar Pergunta'),
	(3, 'Responder Pergunta'),
	(4, 'Melhor Resposta'),
	(5, 'Código Enviado'),
	(6, 'Código Compartilhado'),
	(7, 'Código Favorito'),
	(8, 'Exercício Solucionado'),
	(9, 'Exercício com Erro'),
	(10, 'Exercício Respondido'),
	(11, 'Código Comentado'),
	(12, 'Código Baixado'),
	(13, 'Curtir Pergunta'),
	(14, 'Curtir Resposta'),
	(15, 'Curtir Novidade'),
	(16, 'Editar Perfil'),
	(17, 'Obter Conquista'),
	(18, 'Cadastrar Turma'),
	(19, 'Cadastrar Pessoa'),
	(20, 'Cadastrar Exercício'),
	(21, 'Alterar Turma'),
	(22, 'Excluir Turma'),
	(23, 'Alterar Pessoa'),
	(24, 'Excluir Pessoa'),
	(25, 'Alterar Exercício'),
	(26, 'Excluir Exercício'),
	(27, 'Upload Exercício'),
	(28, 'Código Excluído'),
	(29, 'Código Salvo');
/*!40000 ALTER TABLE `tipolog` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.tipomarcacao
DROP TABLE IF EXISTS `tipomarcacao`;
CREATE TABLE IF NOT EXISTS `tipomarcacao` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(45) NOT NULL,
  `DataCadastro` datetime NOT NULL,
  `Ativo` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.tipomarcacao: ~3 rows (aproximadamente)
DELETE FROM `tipomarcacao`;
/*!40000 ALTER TABLE `tipomarcacao` DISABLE KEYS */;
INSERT INTO `tipomarcacao` (`ID`, `Nome`, `DataCadastro`, `Ativo`) VALUES
	(1, 'Anotação', '2014-02-23 00:00:00', 1),
	(2, 'Dúvida', '2014-02-23 00:00:00', 1),
	(3, 'Erro', '2014-02-23 00:00:00', 1);
/*!40000 ALTER TABLE `tipomarcacao` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.tiponovidade
DROP TABLE IF EXISTS `tiponovidade`;
CREATE TABLE IF NOT EXISTS `tiponovidade` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(45) NOT NULL,
  `DataCadastro` datetime NOT NULL,
  `Ativo` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.tiponovidade: ~0 rows (aproximadamente)
DELETE FROM `tiponovidade`;
/*!40000 ALTER TABLE `tiponovidade` DISABLE KEYS */;
/*!40000 ALTER TABLE `tiponovidade` ENABLE KEYS */;


-- Copiando estrutura para função feeper.TRUNCATE_TEXT
DROP FUNCTION IF EXISTS `TRUNCATE_TEXT`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` FUNCTION `TRUNCATE_TEXT`(`content` TEXT, `len` INT) RETURNS text CHARSET utf8
BEGIN

    IF LENGTH(content) <= len THEN
    	RETURN content;
    ELSE 
    	RETURN CONCAT(LEFT(content, len), '...');
    END IF;

END//
DELIMITER ;


-- Copiando estrutura para tabela feeper.turma
DROP TABLE IF EXISTS `turma`;
CREATE TABLE IF NOT EXISTS `turma` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(45) NOT NULL,
  `DataCadastro` datetime NOT NULL,
  `DataEncerramento` datetime DEFAULT NULL,
  `Ativo` tinyint(1) NOT NULL DEFAULT '1',
  `IdProfessor` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Pessoa_Turma` (`IdProfessor`),
  CONSTRAINT `FK_Pessoa_Turma` FOREIGN KEY (`IdProfessor`) REFERENCES `pessoa` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.turma: ~3 rows (aproximadamente)
DELETE FROM `turma`;
/*!40000 ALTER TABLE `turma` DISABLE KEYS */;
/*!40000 ALTER TABLE `turma` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.turmaexercicio
DROP TABLE IF EXISTS `turmaexercicio`;
CREATE TABLE IF NOT EXISTS `turmaexercicio` (
  `IdTurma` int(11) NOT NULL,
  `IdExercicio` int(11) NOT NULL,
  `Visivel` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`IdTurma`,`IdExercicio`),
  KEY `FK_Turma_TurmaExercicio` (`IdTurma`),
  KEY `FK_Exercicio_TurmaExercicio` (`IdExercicio`),
  CONSTRAINT `FK_Exercicio_TurmaExercicio` FOREIGN KEY (`IdExercicio`) REFERENCES `exercicio` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_Turma_TurmaExercicio` FOREIGN KEY (`IdTurma`) REFERENCES `turma` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.turmaexercicio: ~5 rows (aproximadamente)
DELETE FROM `turmaexercicio`;
/*!40000 ALTER TABLE `turmaexercicio` DISABLE KEYS */;
/*!40000 ALTER TABLE `turmaexercicio` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.turmapessoa
DROP TABLE IF EXISTS `turmapessoa`;
CREATE TABLE IF NOT EXISTS `turmapessoa` (
  `IdTurma` int(11) NOT NULL,
  `IdPessoa` int(11) NOT NULL,
  PRIMARY KEY (`IdTurma`,`IdPessoa`),
  KEY `FK_Turma_TurmaPessoa` (`IdTurma`),
  KEY `FK_Pessoa_TurmaPessoa` (`IdPessoa`),
  CONSTRAINT `FK_Pessoa_TurmaPessoa` FOREIGN KEY (`IdPessoa`) REFERENCES `pessoa` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `FK_Turma_TurmaPessoa` FOREIGN KEY (`IdTurma`) REFERENCES `turma` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.turmapessoa: ~5 rows (aproximadamente)
DELETE FROM `turmapessoa`;
/*!40000 ALTER TABLE `turmapessoa` DISABLE KEYS */;
/*!40000 ALTER TABLE `turmapessoa` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.uploadtemp
DROP TABLE IF EXISTS `uploadtemp`;
CREATE TABLE IF NOT EXISTS `uploadtemp` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Arquivo` mediumblob NOT NULL,
  `GUID` varchar(36) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.uploadtemp: ~0 rows (aproximadamente)
DELETE FROM `uploadtemp`;
/*!40000 ALTER TABLE `uploadtemp` DISABLE KEYS */;
/*!40000 ALTER TABLE `uploadtemp` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
