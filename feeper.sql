-- --------------------------------------------------------
-- Servidor:                     127.0.0.1
-- Versão do servidor:           5.6.20 - MySQL Community Server (GPL)
-- OS do Servidor:               Win64
-- HeidiSQL Versão:              9.1.0.4896
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Copiando estrutura do banco de dados para feeper
DROP DATABASE IF EXISTS `feeper`;
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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.exercicio: ~1 rows (aproximadamente)
/*!40000 ALTER TABLE `exercicio` DISABLE KEYS */;
INSERT INTO `exercicio` (`ID`, `Nome`, `IdAutor`, `DataCadastro`, `Ativo`, `UsaDescricaoPDF`, `DescricaoHtml`, `Descricao`) VALUES
	(17, 'Classe Aluno', 8, '2015-02-06 23:48:10', 1, 0, 'Crie uma classe aluno com as seguintes propriedades:<div><br></div><div>nome : Texto</div><div><span style="line-height: 1.428571429;">matricula : Inteiro</span></div><div><span style="line-height: 1.428571429;">possuiBolsa : Booleano</span></div><div><div><br></div><div>Crie um metodo chamado "ImprimeMedia" que recebe uma lista de notas do aluno e imprime a média entre elas.</div></div>', NULL),
	(19, 'Array de Pontos', 8, '2015-03-04 02:36:46', 1, 0, '<p class="Enunciado1">1)<span style="font-family: \'Times New Roman\'; font-size: 7pt; line-height: normal;">&nbsp;&nbsp;</span>Escreva uma classe&nbsp;<b>ArrayDePontos</b>.<o:p></o:p></p><p class="enunciadoa">a)&nbsp;Essa classe contém como atributo um array de&nbsp;<b>objetos Ponto</b>.</p><p class="enunciadoa">b)&nbsp;Além disso, ela também deve ter como atributo uma variável int chamada&nbsp;<b>tam</b>,que conte quantos elementos estão guardados no array.&nbsp;<i>tam</i>&nbsp;deve inicialmente valer zero (0).<o:p></o:p></p><p class="enunciadoa">c)&nbsp;Construtor, que recebe como argumento um número inteiro contendo o número máximo de objetos Ponto que serão guardados no array, ou seja, a capacidade do array.<o:p></o:p></p><p class="enunciadoa">d)&nbsp;<i>getTam</i>, que retorna o número máximo de objetos Ponto guardados no array,<o:p></o:p></p><p class="enunciadoa">e)&nbsp;<i>getCapacidade</i>, que retorna a capacidade do array, o seu tamanho.<o:p></o:p></p><p class="enunciadoa">f)&nbsp;<i>insere</i>, que recebe como argumento uma instância da classe Ponto (um objeto do tipo Ponto), e insere o objeto no final da array (primeira posição não ocupada).&nbsp;<i>tam</i>&nbsp;deve ser incrementado após a inserção.<o:p></o:p></p><p class="enunciadoa">g)&nbsp;<i>pegaElementoNaPosicao</i>, que recebe como argumento um valor inteiro(posição) e retorna a instância armazenada naquela posição do array. Esse método retorna null, caso seja passada uma posição inválida.<o:p></o:p></p><p class="enunciadoa">h)&nbsp;&nbsp;<i>toString</i>,que retorna uma única string contendo todas os valores das instâncias no array encapsulado na classe. Use os métodos toString da classe Ponto.<o:p></o:p></p><p class="Enunciado1">2)<span style="font-family: \'Times New Roman\'; font-size: 7pt; line-height: normal;">&nbsp;&nbsp;</span>Escreva para a classe&nbsp;<b>ArrayDePontos</b>&nbsp;acima os outros seguintes&nbsp;<b>métodos</b>:<o:p></o:p></p><p class="enunciadoa">a)&nbsp;método&nbsp;<i>obtemPontoSuperior&nbsp;</i>que retorne o ponto cuja coordenada y é a maior dentre os pontos guardados no array;<o:p></o:p></p><p class="enunciadoa">b)&nbsp;método&nbsp;<i>obtemPontoInferior&nbsp;</i>que retorne o ponto cuja coordenada y é a menor dentre os pontos guardados no array;<o:p></o:p></p><p class="enunciadoa">c)&nbsp;método&nbsp;<i>obtemPontoMaisAEsquerda </i>que retorne o ponto cuja coordenada x é a menor dentre os pontos guardados no array;<o:p></o:p></p><p class="enunciadoa">d)&nbsp;método&nbsp;<i>obtemPontoMaisADireita&nbsp;</i>que retorne o ponto cuja coordenada x é a maior dentre os pontos guardados no array.</p>', NULL),
	(20, 'Pontos e Reta', 8, '2015-03-04 03:02:05', 1, 0, '<p style="margin-bottom: 1em; font-family: Tahoma, Tahoma, Helvetica; color: rgb(43, 51, 114);"><span style="line-height: 1.428571429;">1) Crie a classe</span><span style="line-height: 1.428571429;">&nbsp;</span><b style="line-height: 1.428571429;">Ponto</b><span style="line-height: 1.428571429;">. Um ponto conterá como atributos as coordenadas x e y, ambas do tipo inteiro.</span><br></p><p style="margin-bottom: 1em; color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica;">a) Crie métodos de acesso e modificação para os atributos da classe.</p><p style="margin-bottom: 1em; color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica;">b) Crie um método construtor que não receba nenhum argumento.</p><p style="margin-bottom: 1em; color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica;">c) Crie um método construtor que inicialize os atributos da classe.</p><p class="MsoNormal">d) Crie o método toString(). Esse método deve retornar uma String contendo as coordenadas x e y do Ponto na seguinte forma “(x, y)”.<o:p></o:p></p><p style="margin-bottom: 1em; color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica;">&nbsp;</p><p style="margin-bottom: 1em; color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica;">2) Cria a classe&nbsp;<b>Reta</b>. Uma reta conterá como atributo dois pontos: p e q, ambos instâncias da classe Ponto.&nbsp; Esses pontos representam os pontos extremos de uma reta.</p><p style="margin-bottom: 1em; color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica;">a) Crie métodos de acesso e modificação para os atributos da classe.</p><p style="margin-bottom: 1em; color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica;">b) Crie um método construtor que não receba nenhum argumento.</p><p style="margin-bottom: 1em; color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica;">c) Crie um método construtor que inicialize os atributos da classe.</p><p style="margin-bottom: 1em; color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica;">d) Crie o método getDistancia. Esse método retorna um&nbsp;<i style="color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica; margin-bottom: 1em;">double</i>&nbsp;representando a largura da reta. Para calcular a largura da reta, utilize a fórmula da&nbsp;<a style="color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica; margin-bottom: 1em;">distância euclidiana</a>.</p><p style="margin-bottom: 1em; color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica;"><img src="http://upload.wikimedia.org/math/a/2/d/a2dc1161e6c38d4b66b9d10f0cd2ddb3.png" style="color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica; margin-bottom: 1em;"></p><p style="margin-bottom: 1em; color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica;"><br></p><p style="margin-bottom: 1em; color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica;"><br></p><p style="margin-bottom: 1em; color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica;">Você vai precisar das seguintes fórmulas Java:</p><p style="margin-bottom: 1em; color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica;">double b = Math.pow (a,2);&nbsp;&nbsp;&nbsp;&nbsp; // retorna o resultado de a<span style="position: relative; line-height: 0; vertical-align: baseline; top: -0.5em;">2</span></p><p style="margin-bottom: 1em; color: rgb(43, 51, 114); font-family: Tahoma, Tahoma, Helvetica;">double c = Math.sqrt(res);&nbsp;&nbsp; // retorna a raiz quadrada de res</p>', NULL);
/*!40000 ALTER TABLE `exercicio` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.exerciciocasoteste
DROP TABLE IF EXISTS `exerciciocasoteste`;
CREATE TABLE IF NOT EXISTS `exerciciocasoteste` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IdExercicio` int(11) NOT NULL,
  `ativo` tinyint(4) NOT NULL DEFAULT '1',
  `MensagemPersonalizada` varchar(5000) DEFAULT NULL,
  `ordem` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_Teste_Exercicio_idx` (`IdExercicio`),
  CONSTRAINT `FK_Teste_Exercicio` FOREIGN KEY (`IdExercicio`) REFERENCES `exercicio` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.exerciciocasoteste: ~4 rows (aproximadamente)
/*!40000 ALTER TABLE `exerciciocasoteste` DISABLE KEYS */;
INSERT INTO `exerciciocasoteste` (`ID`, `IdExercicio`, `ativo`, `MensagemPersonalizada`, `ordem`) VALUES
	(59, 17, 1, 'Os métodos setNome/getNome não apresentaram o comportamento correto!', 4),
	(60, 17, 1, 'Os métodos setMatricula/getMatricula não apresentaram o comportamento correto!', 5),
	(61, 17, 1, 'O método computaMedia não imprimiu o valor esperado!', 7),
	(62, 17, 1, 'O método computaMedia não guardou o valor esperado na propriedade média!', 7);
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
  `ExpectedOutputValue` varchar(200) DEFAULT NULL,
  `ObjectName` varchar(200) DEFAULT NULL,
  `MethodName` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_casoTestePasso_idx` (`idCasoTeste`),
  CONSTRAINT `fk_casoTestePasso` FOREIGN KEY (`idCasoTeste`) REFERENCES `exerciciocasoteste` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=224 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.exerciciocasotestepasso: ~26 rows (aproximadamente)
/*!40000 ALTER TABLE `exerciciocasotestepasso` DISABLE KEYS */;
INSERT INTO `exerciciocasotestepasso` (`ID`, `idCasoTeste`, `Ordem`, `OperationType`, `ExpectedOutputType`, `ExpectedOutputName`, `ExpectedOutputValue`, `ObjectName`, `MethodName`) VALUES
	(200, 59, 1, 1, 'Aluno', 'aluno1', NULL, 'Aluno', NULL),
	(201, 59, 2, 2, NULL, NULL, NULL, 'aluno1', 'setNome'),
	(202, 59, 3, 3, 'String', NULL, 'nome_Teste', 'aluno1', 'getNome'),
	(203, 60, 1, 1, 'Aluno', 'aluno1', NULL, 'Aluno', NULL),
	(204, 60, 2, 2, NULL, NULL, NULL, 'aluno1', 'setMatricula'),
	(205, 60, 3, 3, 'Integer', NULL, '666', 'aluno1', 'getMatricula'),
	(206, 61, 1, 1, 'Aluno', 'a1', NULL, 'Aluno', NULL),
	(207, 61, 2, 1, 'Double[]', 'notas', NULL, 'Double[5]', NULL),
	(208, 61, 3, 1, '', 'notas[0]', '', '1', NULL),
	(209, 61, 4, 1, '', 'notas[1]', '', '6', NULL),
	(210, 61, 5, 1, '', 'notas[2]', '', '8', NULL),
	(211, 61, 6, 1, '', 'notas[3]', '', '9', NULL),
	(212, 61, 7, 1, '', 'notas[4]', '', '10', NULL),
	(213, 61, 8, 2, '', '', '', 'a1', 'computaMedia'),
	(214, 61, 9, 3, 'Double', NULL, '6.8', 'System.Out', ''),
	(215, 62, 1, 1, 'Aluno', 'a1', NULL, 'Aluno', NULL),
	(216, 62, 2, 1, 'Double[]', 'notas', NULL, 'Double[5]', NULL),
	(217, 62, 3, 1, '', 'notas[0]', '', '1', NULL),
	(218, 62, 4, 1, '', 'notas[1]', '', '6', NULL),
	(219, 62, 5, 1, '', 'notas[2]', '', '8', NULL),
	(220, 62, 6, 1, '', 'notas[3]', '', '9', NULL),
	(221, 62, 7, 1, '', 'notas[4]', '', '10', NULL),
	(222, 62, 8, 2, '', '', '', 'a1', 'computaMedia'),
	(223, 62, 9, 3, 'Double', NULL, '6.8', 'a1', 'getMedia');
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
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.exerciciocasotestepassoparametro: ~6 rows (aproximadamente)
/*!40000 ALTER TABLE `exerciciocasotestepassoparametro` DISABLE KEYS */;
INSERT INTO `exerciciocasotestepassoparametro` (`ID`, `idPasso`, `Ordem`, `ObjectType`, `ObjectName`, `ObjectValue`) VALUES
	(63, 201, 1, 'String', NULL, 'nome_Teste'),
	(64, 204, 1, 'Integer', NULL, '666'),
	(65, 213, 1, '', NULL, 'notas'),
	(66, 222, 1, '', NULL, 'notas');
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.exercicioclasse: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `exercicioclasse` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

-- Copiando dados para a tabela feeper.exercicioclasseauxiliar: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `exercicioclasseauxiliar` DISABLE KEYS */;
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
  CONSTRAINT `FK_Novidade_ClasseMarcacao` FOREIGN KEY (`IdNovidade`) REFERENCES `novidade` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Pessoa_ClasseMarcacao` FOREIGN KEY (`IdAutor`) REFERENCES `pessoa` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_TipoMarcacao_ClasseMarcacao` FOREIGN KEY (`IdTipoMarcacao`) REFERENCES `tipomarcacao` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.exercicioclassemarcacao: ~0 rows (aproximadamente)
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
  CONSTRAINT `fk_ExercicioSolucaoStatus` FOREIGN KEY (`idStatus`) REFERENCES `statussolucao` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.exerciciosolucao: ~0 rows (aproximadamente)
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.exerciciosolucaoclasse: ~0 rows (aproximadamente)
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
  CONSTRAINT `FK_Pessoa_ExercicioSolucaoClasseMarcacao` FOREIGN KEY (`IdAutor`) REFERENCES `pessoa` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_TipoMarcacao_ExercicioSolucaoClasseMarcacao` FOREIGN KEY (`IdTipoMarcacao`) REFERENCES `tipomarcacao` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.exerciciosolucaoclassemarcacao: ~0 rows (aproximadamente)
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
  `MensagemErro` mediumtext,
  `StaticErrorType` varchar(200) DEFAULT NULL,
  `LinhaErro` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_Erro_Solucao` (`IdSolucao`),
  CONSTRAINT `fk_Erro_Solucao` FOREIGN KEY (`IdSolucao`) REFERENCES `exerciciosolucao` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.exerciciosolucaoerro: ~0 rows (aproximadamente)
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
  CONSTRAINT `FK_Pessoa_FilaNovaSenha` FOREIGN KEY (`IdPessoa`) REFERENCES `pessoa` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.filanovasenha: ~0 rows (aproximadamente)
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
  CONSTRAINT `FK_Exercicio_Grade` FOREIGN KEY (`IdExercicio`) REFERENCES `exercicio` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Pessoa_Grade` FOREIGN KEY (`IdAluno`) REFERENCES `pessoa` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Turma_Grade` FOREIGN KEY (`IdTurma`) REFERENCES `turma` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.grade: ~0 rows (aproximadamente)
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
) ENGINE=InnoDB AUTO_INCREMENT=1725 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.log: ~2 rows (aproximadamente)
/*!40000 ALTER TABLE `log` DISABLE KEYS */;
INSERT INTO `log` (`ID`, `IdPessoa`, `Mensagem`, `IdTipoLog`, `DataCadastro`) VALUES
	(1723, 8, 'IP: 0:0:0:0:0:0:0:1', 1, '2015-03-03 21:45:00'),
	(1724, 8, 'IP: 0:0:0:0:0:0:0:1', 1, '2015-03-03 22:37:54');
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
  CONSTRAINT `FK_Pessoa_Mensagem` FOREIGN KEY (`IdPessoa`) REFERENCES `pessoa` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.mensagem: ~0 rows (aproximadamente)
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.mensagemcabecalho: ~0 rows (aproximadamente)
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
  CONSTRAINT `FK_Pessoa_MensagemComentario` FOREIGN KEY (`IdAutor`) REFERENCES `pessoa` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.mensagemcomentario: ~0 rows (aproximadamente)
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
  CONSTRAINT `FK_MensagemComentario_MensagemCurtir` FOREIGN KEY (`IdMensagemComentario`) REFERENCES `mensagemcomentario` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Pessoa_MensagemCurtir` FOREIGN KEY (`IdAutor`) REFERENCES `pessoa` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.mensagemcurtir: ~0 rows (aproximadamente)
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
  CONSTRAINT `FK_Pessoa_MensagemLeitor` FOREIGN KEY (`IdLeitor`) REFERENCES `pessoa` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.mensagemleitor: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `mensagemleitor` DISABLE KEYS */;
/*!40000 ALTER TABLE `mensagemleitor` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.mensagempersonalizada
DROP TABLE IF EXISTS `mensagempersonalizada`;
CREATE TABLE IF NOT EXISTS `mensagempersonalizada` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IdAutor` int(11) NOT NULL,
  `MensagemPersonalizada` varchar(10000) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_mensagemPersonalizada_Autor` (`IdAutor`),
  CONSTRAINT `fk_mensagemPersonalizada_Autor` FOREIGN KEY (`IdAutor`) REFERENCES `pessoa` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.mensagempersonalizada: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `mensagempersonalizada` DISABLE KEYS */;
/*!40000 ALTER TABLE `mensagempersonalizada` ENABLE KEYS */;


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
  CONSTRAINT `FK_Pessoa_Novidade` FOREIGN KEY (`IdDestinatario`) REFERENCES `pessoa` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_TipoNovidade_Novidade` FOREIGN KEY (`IdTipoNovidade`) REFERENCES `tiponovidade` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.novidade: ~0 rows (aproximadamente)
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.pessoa: ~4 rows (aproximadamente)
/*!40000 ALTER TABLE `pessoa` DISABLE KEYS */;
INSERT INTO `pessoa` (`ID`, `Nome`, `Email`, `Senha`, `DataCadastro`, `DataUltimoAcesso`, `Ativo`, `IdPerfil`, `PossuiFoto`) VALUES
	(1, 'Fábio Alves', 'arnistrong@gmail.com', '202cb962ac5975b964b7152d234b70', '2013-12-27 00:59:00', '2014-05-18 19:16:28', 1, 1, 1),
	(2, 'Patrícia Jaques', 'pjaques@gmail.com', '202cb962ac5975b964b7152d234b70', '2013-12-27 00:59:00', '2014-05-18 01:24:58', 1, 2, 1),
	(8, 'Gilvani Admin', 'gilschneider90@gmail.com', 'a6d414ac4f293187dd42025834925f7', '2014-05-18 17:40:30', '2015-03-03 22:37:54', 1, 1, 1);
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
  CONSTRAINT `FK_Conquista_PessoaConquista` FOREIGN KEY (`IdConquista`) REFERENCES `conquista` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Pessoa_PessoaConquista` FOREIGN KEY (`IdPessoa`) REFERENCES `pessoa` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.pessoaconquista: ~0 rows (aproximadamente)
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

-- Copiando dados para a tabela feeper.statussolucao: ~5 rows (aproximadamente)
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
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.tela: ~79 rows (aproximadamente)
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
	(92, 'mensagenspersonalizadas/edit'),
	(93, 'mensagenspersonalizadas/getJson'),
	(94, 'mensagenspersonalizadas/saveJson'),
	(95, 'mensagenspersonalizadas/search'),
	(96, 'exercicios/carregaAssinaturas');
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
  CONSTRAINT `FK_Perfil_TelaPerfil` FOREIGN KEY (`IdPerfil`) REFERENCES `perfil` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Tela_TelaPerfil` FOREIGN KEY (`IdTela`) REFERENCES `tela` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=217 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.telaperfil: ~164 rows (aproximadamente)
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
	(215, 96, 1);
/*!40000 ALTER TABLE `telaperfil` ENABLE KEYS */;


-- Copiando estrutura para tabela feeper.tipolog
DROP TABLE IF EXISTS `tipolog`;
CREATE TABLE IF NOT EXISTS `tipolog` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.tipolog: ~29 rows (aproximadamente)
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
  CONSTRAINT `FK_Pessoa_Turma` FOREIGN KEY (`IdProfessor`) REFERENCES `pessoa` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.turma: ~1 rows (aproximadamente)
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

-- Copiando dados para a tabela feeper.turmaexercicio: ~1 rows (aproximadamente)
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

-- Copiando dados para a tabela feeper.turmapessoa: ~0 rows (aproximadamente)
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
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=latin1;

-- Copiando dados para a tabela feeper.uploadtemp: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `uploadtemp` DISABLE KEYS */;
INSERT INTO `uploadtemp` (`ID`, `Arquivo`, `GUID`) VALUES
	(35, _binary 0xFFD8FFE000104A46494600010200000100010000FFFE00042A00FFE2021C4943435F50524F46494C450001010000020C6C636D73021000006D6E74725247422058595A2007DC00010019000300290039616373704150504C0000000000000000000000000000000000000000000000000000F6D6000100000000D32D6C636D7300000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000A64657363000000FC0000005E637072740000015C0000000B777470740000016800000014626B70740000017C000000147258595A00000190000000146758595A000001A4000000146258595A000001B80000001472545243000001CC0000004067545243000001CC0000004062545243000001CC0000004064657363000000000000000363320000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000074657874000000004642000058595A20000000000000F6D6000100000000D32D58595A20000000000000031600000333000002A458595A200000000000006FA2000038F50000039058595A2000000000000062990000B785000018DA58595A2000000000000024A000000F840000B6CF63757276000000000000001A000000CB01C903630592086B0BF6103F15511B3421F1299032183B92460551775DED6B707A0589B19A7CAC69BF7DD3C3E930FFFFFFDB004300080606070605080707070909080A0C140D0C0B0B0C1912130F141D1A1F1E1D1A1C1C20242E2720222C231C1C2837292C30313434341F27393D38323C2E333432FFDB0043010909090C0B0C180D0D1832211C213232323232323232323232323232323232323232323232323232323232323232323232323232323232323232323232323232FFC200110802DA03C003002200011101021101FFC4001B00000301010101010000000000000000000001020304050607FFC400190101010101010100000000000000000000000102030405FFC400190101010101010100000000000000000000000102030405FFDA000C03000001110211000001FA321FA393D237343AB2C6A67A6638F4CF6D44B6518CEEAB9DEE854AA116CC8D609299343280954D890C554554B8BDDC606A88A7423479B89B221D04AB441621524D3134129AB992CA69CA0048CB07326AA54A9E6B79D1198674B513295515051A66E658B2522E2D65CF9EA74CF2E7DB95721D19EA62EA9337AD2E7D0AF3766AB3A2345182E85591624372AF1BCEB19D235988736122A137123937715655491DD3CFB675A3BC633EBE5D4EA79DE352A95446B3666DCD8380D25D12DB5453880CACD8CECB25CA59594DA7285108725914AC4C04D4048D0034C012B4C492814DA255AB204ACA8B54038CE755A41619DD5466F598C2CAA5694AD2554F3D229321148902965BAAE4CFBB3B3934D66C74AA5609692451129B19B5BCEE088A8B338B8D4CD54D929A072C22D0EA5DCDDC692D56959D54EE66F2506A74543CDD13A9616AE307A6759E5BAD6574E579D242428B5CF0EA84E37AE7BC9A674BD1A65AF3D2A4453972B404B0B010343136400D50D00310C000400008950A9B0954906980C596C12A082958931633D67533D65A589CAE49139D2CA4CCD91828D33A9496A527212A685259551668E166BCEA6B38ACB51438B126AC6E596E6A6B5CFAA24CED683A9D25DAF3D3355E64BA65414AD054D4349C0AC2168CCAAC130966352B15ABB39DEC2676DCAA3443612A6980021880354C43219426A004942000872512C620040C19250012525294E2AA927280129CD82A2902121D8AA45D0950EB27551504E778EB353136519AAD1E41B3C4363125DCC82E1B308E89B3037AAC2BA1E6F39D28C3A38F6B3613955AA289BCD99D5996AE44E556B7CCA3B16446F5CFACBA11528D425193AD5E3A4534E51343130014004C104D0098340E5676279C6F3D2B983ADF238EB7CB72F418D4BA24E543125B0090B252D98BB2F0DB3A4E4D4759B829594259A86A9881C8A86246E5AB8158218A1C0B3ACF5266A35948043006086D536C34579AA749228D49BB335144BC4AEBA626AAE55A33369A714E5AA0639B2216C1CF1D4ECE2D77466EA898D94B06889A6453C92EAB17668449B2C9A68492DA9284E2CB792359CCB1B48A879D129D90E8A569C365CA6D9DE6DB8ACD692292094E751DE746928CD85ACEA64E16A6CF271A9096D441B2C6ECD1E6E5A9481256591631A973575660B7179A7A5273BD8AC6B57199B25C574339EB6A8C2B5A5C73EAC48DB2D12A4254855CDD1CBD371AA5335ACE6CD1E365B0855056A428BAC19B3CAA5A4925B8174AC59A2821CC4EA5199A96E195291559D1A3872D5438D08A9673BCEC911ACD2628A912DA2AF223450559986A641BD66F36A6825E7366CB20D0CCAB5306A622699CCE96F349D0B252ECB166A66CD0C99AACC34522D3993A2722345115A3C74290A06952BCA8D4851AD61ACB7724B6F1A2B3A0C2C565894349573F473776B30AD67594E9364DA69B124AE492D42A69459B5F3D9D178E99D0A94029AB9211A92C6860D21A0A2A196F371ABCDCBABC834CD00E556B39A2E666CA992CA202882AEF166D58D65AD73A5E858A4DDE01A4CBA620124122B049551025B86A0C84DD2C1612C0A7352B4382A58F3B95CAC9B348945385555909B98B976AC6E5EA9C6F36A9304A4B910EA281347274655ACEDA736B9BA4CB526A8834A315D371C55A96613D39D98D056AE1E6D56346B040E49B0115440510559025A80A7216F20DCC5C6CB3A5A1B89562E73B45999A064B62CC0D919BBA5CD6F32646A1916EB234844E5568661702101631E92E75A54B34CCEA934AA824914D350ACD260AB3249AAC956EB10D082294954A404D92AD06B1B4B7B63A6354A64ACC105335AE9CD6742466F3115D31579B97A2F0DF368B33A2B0AB35864B09963815402B9AB8B962748B1698E83CAA481AD444AB292064A2CCCB347986A66E5D1CD8556D9D6376A581C94A159495106B90892CD1E6CB3346D5CEE377833698021C5812AC6914C4155992ECF166D58566EEB21758CE6B459AB2D4A4A525340898D64D6E5E77D2A399F4239D74B394DE2A1B0ADF1ACDB1A968906244C68567A30BACEF378AF37D31BEBC9BE6DDCA977793CDAC76E6D4E8D396A3A1F3E915968AB148D674D72DB3A796B9934A4D2216A6D317191B6860FA1CBCB1D935C13DF1ACF0AE9CF59C9D1617352E9D186D8DEC6779D54B2338DA6CC8B8B1CA5426C4B52329E8AAE53A253277229ACEC6653A9B2C84B5055290A242890B245A25C340000000C15360308ABCC976880A219779395C0A9526363826D123621A1050EA6E59133875E77D39F4BC349AE889ACAAF9DD75CE159B1353BCB701BEFCBB675B66DE6C5C5D8E0CCB795D1379A512EB4D32D73AD09ACD480151629B442D1982E867357422280604A868596A59CC74459955D12EC964B4000D32599756631D2ACE48ED55C4BBE538577BAF34F491E7BF40382BA88E637CEB25A2B335A1666AD13400EAA5C8DEE5E63A9CBCEFA439DF412E06C189A8665A22D5C0AA55C52485734C48AACE86544BE5543F479EDC397578D4BA9933678DCBA4A6256C5A439767811B5F3D2E93364DC86A159B9BD433AB41AE372D24E5012313A048B2594910C4531034000000218000D3430648DC4145219026D64A4800A4D3215A21B404BA6E5C0984AA7592D524DC50C6A5690524C1A4342012B1A4039639A421D19CEC8C4D1D655609529785BADE2168EB27A866B51327AB32AA04E9CB9F1FA3E3475BF97DE3E9F2F17D05ECECF3BD0341B946C1269189A8311A40C1144B1885602306A8A64964B21641AC125B3334811546653131438D02159537240ADAE4E9590B4087A44A826C7354626D262B49B06494201809582A6E5C67556468AA1362CB1152E0449544A4B211664568A20DA729B363013A1F3A586AAC6A78CEEBF33D15B72E4A25AD0916263E4EAC63E0F93D3F3B1AE95CAE5FA0FA3F90EA4FB1AF177D4F42FE7FA4F61F9FE757D1DF8DC71F4C7CCFBCBD064CD0F2788FA37F3FE69F64BC7B3D57F39D49ED2F0733E897CFFBCBA389375CFF00267D81F9DC27E8F5F0FF00575DA6152E8661A3C9A33363AC9D6AE496880B7909A2C82C965885A2480929C8D2168CC6AC2284348054A902126AC090A52551016A034330B5092C845A87549359284928895A2308F13C55FB4E1F9523DFEFF96E83ECEBE3BB0FA4E3F138A3EA383E77A25F67DEF9BB3E83C8C38CAF0E73971D34CADD74C0CBE875F9FD317D5C38DE5D7CD5859D1844F4CEFE8F9147E8F8FCB79FD33ED78DC7186F9CCCB7583AB8D74AE5D856F67579B966FDEF6FE75B5CFDF7E7BDDE5D676A65BF4BCE49ECFD0FC26F67E89A7C07B167D42F37AF53671469592CEB69C8B2E474010003722E866A35985668661A29468F10D8C45D5490C1164234330A7995A21CA2646668A90D0868534A94D890502289655A2152B096D7F33BDE33672B89AD6F1BC5DB5E513BB0E68B3D5E2C617A74E4ACAD42A98D5E9851B195A835AC2E5DEB3787412F35E5A4D99E5D13ACE2CC7534CF4AA8149537455224BD7961AEEE2D629DD6B359E1DAA679D74C3319EE573BDF19569395745F32AF6FDAF89F6359FA7F47E07E86BDCF1FDAF92AF4BDAFCCFEA8FA319095E633C9E84EF56950C10021824D92C298880188A165800228921B90620040D08684342A6245240D206843407E5FB45675AD659CBA4519B4F2B8A994207A0EE64AA9A855326AA3342A2A95B608896AB3D2B458D46CF30A8ACECD564D28CE886E6CBBCBA1B85D266BA992E23099E8AE4D6CDAB1586D58D589DD4B8E5DCDAF3A7B71D39F498DE6BD1F32ABDAE0C1C98F7F0CEAFE8F5F9F75DCFDBFC957979AFAFCBD17EF7BBE0BEA753D53C5E297E9F1F94E46BECBA3E068FBAD3CFEED73622562298819243043114C40C40D08624310008680040D2004002A1027E6CD570E96872CBA46639D1CDA21A54C9134709765928643D4608BACA13AEB92A3A329A0A90D25A09955759B2DE959475F36CD6C8D5AC0D78A66E26586A72AD2F3BA5AE399D918B93A35E0DE373179D74D71EB9AB9BB79F4E7DAA779B999AD240CD8EB5DB1599D1CBB317D1782CF7A3C8EFD5E4D3A2F1E8F2F3F5DAD7D3FC86FD3CFF43AF83E8EB3BF4DF21BF9DC6E35ECF3398FA65F31DCBEC1E2A97DA9F191F433E2F2D9EEEDF35ED1DA0221140200434218801160206847E7C967E6E9728B688556449A19D683AD1398D512AA6DA59CC898530E9398F6FBA6FE5DFD3E137E04FD1E379F84FDEF62E7E375FD03E553C6C5A6AAF4E8CCC76D27959ACCB9EAF5FC0EBDBB3C8E8E6DCCF9BAB9554EA93334C9691B267A64E2EA6A46442F4BC75CDDB4E7D31621CEF159DE3BB4017AF395D6B9EE4CD216AA65767C9A57BCB15CFD2FAF8FB0CB83DAF2F7CB8EB7A94ECC79CF62B8FB3A79C5791CFE5FADC78EDCAFB266F8F5DB55D3A677DF1D3D0F33D34F46F1BB99CFA11CC793E5AFD3F95F37CA7DA7A1F9FF00527DB4FC6A3EB2FE343ED2BE13AEBEBCF92EB8F999473E97D7C7D58D635D9DFB78397DAF815E4552675CEE515AD8E78EA8305E9705BBFA42C74EE3CEEA5C31853352A6E6AB2D534E8F17D1D67D8C3CBFA9EBE6F8FE6FBEF239FAFC0D7DBBE7D7C17EE79D1C4B78D73CAB78B83DDE7E8D3CDF2BE973AF99DBD5E98F178FDDF0C456867D5E8E29E6C6F895A67D4615D5D36F1F570F147D173F17D327CAE5F4DE2DBC0BBB2399ED81B5CD659AE9D0CA7B44F13AFB2D576F3ED9EB7A79FDD3A7A1E7FAB975E1F33A774F3DE387A0F39EABE3ABCFA9728AB8BBCCEF39D85CFAAD6E2B86CF57A98CEE71EF9D9B74F98ACF7F9383A0F138BECB8F3AF9C3A31E3AC96C8CDDA32D00104BC2BA97A39F07A5C7E8AE3C474F0D7D469E0FD1FA39F8BE4FD8F2CBF1FE8E1EA47878756368FD9F413C1AFABD2CF1BD220EEC39314E5F13D4E39ACB0DC978B6AD33BE1F6314DF576792A6BD55E3E39EBEE73F8F173DBC3A6978F474F933AE5DDC9A609EEF4F8A93E82BE7A4FA48F003DEF272EDD5F33A6623DAF339B3B2E2661FA7E775A6E79E1D9E27AF9CB5ED79569EAFCCF73CB6F3FADC72F3FA6CE67D2F0E7BD99CEFA05D3CBFA3F03D2FA3F39773AF8DD5DB38E8FE7BD18EDC3DBD55EE79397B3E478F52071314D3403EACF5EB339B9C2BA393D6DBB3CDEEF3F72EB2E597D8CBCAEC2D7565A87471695DFE5F4EB1F373F47E372D7281CF4953332E6B32B4F6792AF1DF4F3BAFB3D5E3DBE37EC70CF73D7AF1B825FA5E7F98ECCEFD5EDF9BC1BFA7CBE4F8E5FB3E7F9026FE9B93C295F54F2EF1D3D05C7373D99F195D59E3B429733A533A3379AFD5DE70F31FA4E71E17D8EE7817A04711DAECE37D6A394E90E63AA4C1EC8C8D44C8B643A05481B90BACD46A641A98A35320D5E28DCC05D8C44D34E7DF6EBF13D9F23D8AF6BC356FA9E239CA3A39ED3E8B5E0E8ECEDF3FBF3E77C92A3C068035CB5DB7E7EBC3526A672D7B78EF4FA43E67D2DCE9E6F55DBF359FD070E6E5D79459D7CE6F5CBAEDC67569C7AD9CFE47D45E6FC89DBC3C741A7A29E6CC16D3925DE732B5F37AF98F3CEFDB5AF377ED79D731D2E5F397A64BE73F414705768CF1D7509CC7409955A410338E1DB95EF845CF59DBD7F3FECF3E1B989CDB3C1C9B1915A1986840964068425D14AAB258C10C486211A602642182541253A92824A221D153A469ABD1E65717AEF769E7CEA7D8E7F21B5CE391963AFB7DFE1FBBDB1D3B73C66E1C88F056832BD5E1D2765F9FB749D1CFBEB51C9E9F9B94D66B9DDFD6F0B6D4FAD5F2DE9F49DBCDE9AD5F1AFAB3239FA6170D3ABAD9DB1E9C2B5E7E4F34FA1E6F96E49690FCBA10EA55073737A2F4C0E8239DF4062F523235466EC215A2168AB35A8991A9589B9184F5339DF43D39F4D5F366F4A8C9E84905952DB496D40C14184B6221BA4D0B4862068860809692062BB20EDDF4F30F676D3C05F49AD7CC6BF4D82F8B876F993B70736DD7DB1E647B9E7F4C723EAD4F30E9E6CF5DFD7F1BB6E7D2D79B7F2F6E64E71C5B493BB93A38F734599869D3C3D3B7A91E79DA75F0F5A97CFBF438B12DE3EA187A9D5D1D5533C5A9DF7F3BC32FD370FCDE12FB3C1C52BA6429666C3B44F8659245014C445081898C015270953A91B89768834086C965B69252114D659443A1114D628400580D00D40009B140436A69B0016EB81EA7774CFCF75FD067B9E6F55E1A9D93E772AFB0FE67AB9F6F6F9F0E5B3D0C7C6897D5E5E7AA88EFEAD63CFBEEF3F59E1C6E3AC914C6597642E3DFCFE9E95D3C9D7E3F4E38ED973E2B48E9D6239F67581D99D9CFE9707ADD1E7F6FD06DB9C7E873E55D98799E7C7D1737CC73AFD079DE544D75E18CAE93131A2CD1B3CD1A18B36320EE72FCF1B4C4C006492DB25D3246C96DC000D0A98829E62E8F20D4CEA1C816425B33668F20B2295B481A76830CDD8905C88A110311DFE974C789E97ABCFD15B79B8EA7A3CFC932EB9CD10746A7257559E6F37B9335F3D5F45527CE9F476CFCBCFD535F963EAEABE679BEC3C6DBE6B0ECE3D94C684BD21ABF43C4DE6BDB4A3CBD7E9FD3F3ABD1E6F4F9F8E6CEDF94CB05E68DF75E0FB3F9FF007535E2F3BCECDF438F9A5767CC8EA514342804534024C833D05C56E1CEBA51D4E4F346D03A945B86369164059216F36AC40C4140280A18325BAA81B9554B18A53449D024B6132524C4D3B1890DC1177E97D375CF83EFED1DB3CDE37D0706E78AB9F2AEE5C1AC6E94F3BAD72646F86786AF42E6AD4EADB93AB2EE7E36387BD1E3566FAD963D9D279D3B724BA7A7E275ED7CBEDF0F679D1D7D31E373F463CF797473F7F3D7573D63C77894BA717EBF06BD2715CC66F42C0AF67D5E3BD63C1E8E6E99BCF37C91B23ACE77A4C929A54002004E6A90A188B58893763F3E86E925372A15530210D854058A6A848293864B1BCD960E59B402A449420D28D08554405CC96590D06E882BD8AF3BEAFB6BD19D3389DCB984952919FCCFD3AAF8DD7A788E85473933A25CA352B05A96E7E8F17A95F37CBF47A47CE6BEFD65E777D457065D331CDE7FBDE0DDFA95E0F3747B7E5E3BA56983C7A0F63C8F6395E77C7A4C7624B971EDE7EAE3F4C99D278D8D635D3D5E7DE7BE7C4CBD172F938FB54BE57A3A899AD14B9AB0C96A8C677472E7DB0739D41CABA92F32E849AD45F9B6C8A1374B2E9267742A1B41CCC369A1490DCB134C680B52D52A5209CD8354354133A225B609B49A40DBFABD5C7DB88F4E2A626CB99934501440540072740626C19BB512D949A92A4439644CDCAC4E99D658ED8A61F25F47F337465D9C62EAE6B58A9EE9BE8EAEEF3BCFD7C7F47C9FA8EFCFBEBA69CB9CE82CC0D91856888552445CA671A4AE51AC46654AC8943491482A635805A2335AA335A06550FCBBA12AA704684328400344A988A086340094A92ADE625A822C945A906D014D2B4E65A1AA1B99292F4ADF5FD5CE3D7CF49CD59732289C96A4289064894922885169034800041234A56A660B8CF35AC639D79FE77D7F26B5E6B848BCB45BFAAF99F6B1D7D0F27A7CFC6FCEFB8F88FAFEBCBBDE4EF2B7C1CB37EC2CEAE1A41391E64D7A8B9F44A869266919AB442B44142C14898D244AD125212A0E526BCDB1814E5C8A93B40992CCAC6249448ADA722A011402A4A142A299152E0405394508A1B0648AFD7F1BB353E94E2BF4E3AD73D26D32139740BC71DE35E6E7EB12F8D1EDA6BC2CFDCE2CF4F363D5735E54FAE1E34FB5375E247BD31E14FB90BE24FB59AF8E7A897CB5E9667979FA789C99EFCDD3C8855796369AD7A1E6A9AF4B8F1A5D3DAF2FD76ABAF879DCBA5F8D8CF47D4CF9BA5E1EDEBC32CF93C4A79FD1B720D25651026AF049D35C633DAF84B3B8E113D2E9F33BAF2C1F9EA74F49F9AA4F51F9457B2E67871D091583258E097524B4EA908A70929A65264A8A956D22886169234D2D5449A66266E6A606D34C735372CF47AFC5DFB4F634F336E99EF7C975D2F9A8E83069B98B36320D78BA1AF14F792F9D3E90BE52F5D2F8EBD952F8CBDA0F11FB21E32F666BC78F6A0F132F7313E6787D6F27584D4946F8020152F4D5FAD779D2A124F3F4A5B78A4DB1488549A49CC12D129A5954889D0329DD181B82D5D5C702E81BE63A4979975229C3E1CF4248A725326E596092D949A0A72E1928A70D296751A564CD5668D14514898AAC98C6ED4A909800E53499D1655CD95AE38EAFA5AF93B74CFA75E66BA77D70DD9DAF8E8EC7C949D4F99D749CCCE87CE1D0601D0B0137316BA19896A52B92058DE2BE179DEEF9B37C4BB1D9D5C7EADA7CEAF6F91AF3FD8E4F513D1ACE98D080D0865128A2429483490D215084102A4D2081440213001001400E02A7CDD293A11544021B9A08A5085480DC2495CD248A24152060C02A04E0B6914E2968999558AE6E6688D61D8D5CDA355353499239B1D4BAD2F94D3B6B80D3D1AF3AACF42B82ACED7C2EBBCE16771CA1D8F8DD9D672B3A4E74744E28D31594D4F3F42E7DB9A75CECDAE1EF919EDCD8E97A43C6FB6B1AEFE7D1E61A99A4D4C85D0CD96A04B2116425B528A202948529514494DC34A252D901666D79E32AF36B521ADACC92D54ABA60E0200554A9266F5086804E56D4D2053895459255C28420DA1890D82B25D8C4976CC64ADA2A6740CE8252928A21550455CA249C3734CB40D2EF94AEC7CE2753E35677BE02DEF381D76AE30EBCF9D2F7E937CBAF373EFC9A9BBC5F4E7B2CAF3ADA6B2E7D3A2F91F5E1D672BB3A9F23AEA391AF51CA1D2B9D2741CC8EA5CC4BD07386E738742C11D0B9C3A173A3A0E766E73A5E839D2749CC95973E7D3BCC92C825D20762A12B7495468915A768394514C9A752450E1272CD80A2B95495582685698404228B25531396B4E028815B255C8D91A4AE34946F3935986955002A2A6A5252817450D5B99B5CD090D696FAADE4EBC9C5D195CC2D26E27AF97A5AE8C76E7CEF9D66F7CEDE493639E6CEA7E6BD3D330EA4C88C9773CE67A2B1D6188B1A4E0481A91743146E73A3A972A3AD72B8E9391DBDEB4AE371AB258D4A453A294A7212CA562428B516568E4126342072ED7A66B2B9B2A25A8B525960A469459A2CD964D41525D48E695298B21A0E156AF171A4C08D228109AC92AC9468A55D5252CDA45AC9514E183856DEFCFDCBDFCBD5E6BA73CCCB9E90A6CD3A78FAA6BAB9BA785AC966EE1A26C682B1571B876727451AEBCD1CC0EA9C98B4931B4A1A153040C06000384D3A430F5859F0D68C2C1A50DA00CD9A4C4A58EAD15CAD21255438B25A2056D244509239A2598A11692A98A4D62F3A45A3365109A2C54A853254C5583556C8E64759688A29DB99534C1170D030A9689246EA66A544C57F41E4FD175CF3F85F45F372F3BCDE6B1115D7C5EB5B5E6FB9E0CB9C85C812500187445654CD449D19D539646C4DA8687098134C24A552EC491A950C102AF5159CB52A8933B2926E6964AA20D12A6F32D381BA946E5469204D4A4A06B464E95C285600095945489A54AA5CCD19968E0516A91572654D9336AC139218925B56A6D254CB240B01880C54AA55475FA3A787EDF6F4EA6BB615D718FC87DA7C7E35CF5272D936A9FD072FB1D33C1E07D97CA59C8EAB96F277248D9254A34992DB15264941253215C03056801BA8CD859281690C0157A75272BA19A8D52042D259B22AE0258A62399714E0B290C1391B80A655B0B4A32549289609A02A207536B698B3DD105143CE8A9508C10D9449350B3B9B2D3466EE6C4AA515A150320A6A4D09EC7A9F1EFA4FB3F2FC5C6BD3D7C7CE3E87C3CD09866A67D2595D7D9CBD267F39E8F8B9B936B149D249349492D2C9484D8202443421BA9AA950A9091D3254302C13165D48261DDA2AE7B9295173509A3272D510C841692A9AB94D02F32D38B6B4950E42C2D05102A2D5446EA41544BA4D2ACEE66CDA618C64AE1BA85424BA413A4153AE439D22C609696B88F1AD4C1D84E7A483BC59BCDE9663AB72C3CED4549339D66CCE95AAEEE59AEFBF2F4839ED12D841422601528A14836824A586DD4B04A139AB83392A29692C054599AA9A45A549A8F42918D55E449A4CCC6AF37011A541556AA5448234528B52E041645D8A3BCC49CA0D6832F25AAC1A53CAE8250E5D0EE52A498135626D4ADCDD833334715113AA5336CD33DB1A6AE8C4A198D24265CA132CA9D92E5A294D31B1ACE848D36867B6667A0EA0B225344B1D09904E99C032C4A8216858AA69A969290524B681B05368964D54D8B0C9AB8B51D34AB169E70699E496CCDD9A75F23354DC2B60DE776A4A9201ADE99EA28B85BACED21689235CA4D7395559D69136EA495A428AEAB274D739D20B502567245BC99A5E76A4A450925C8EA6E217759125DE28DE244A4954DD0434A56DE5654B6937348896D27216A4896E6C7350520800542CCD1E459B29654A7622D4A9344B268B4929095CB2A81CAE1C2D2684F3AB9DEAA31BA9A7666F4904EE15C318E69DE16681B5B0690AC9B91AC8348C714F4A78FAE5499A3959E56DBA9A6EA71D338E9CA1E5A92E89AC92F24AD5A53885A867A260D38113415051329A1834DD26D480889D08D626B479CC5C176A229042B1D2425444E931654BB97310134E21DC90EDD4CE8CCE6A6991524B742862086AE54AB63B1274A9B91C88A255699B989A6ECEA8D633B778B934CF559B1A37A64E5C32CD0BC68AAA9A75344A232ABC74335D18D379D665354465A3D2DC49BCC256E2A999D73682CB1749C35E93443A5489105545524922A6B49864567565E7608756C56910634AC74666F91666F44428AA2F3EB4C53C25D0729359EAAF3B133A7B125C3523CA46B52CC5744D6779D4AA952E6AD266F3B996A6A9022A76CC9AB925690A45E35AC023402685A4D27FFFC4002F100001030303040201050002030100000000010211031012042021131430314041220515233250334224344360FFDA0008010000010502926D04708841164BC1045917C5046E823741178F8B365D922EE44208B28975B2ED822EDDAAD31DCBB249277A0D5E4981391F669045A08208DABB6764592D0626241046C8FF00022E9E083131158602A6C82044F22F990CA5A22C0E74A27B4BC6E9F833F163E041890479E056980AD23C33B57E02288A40E4B208BB54823641045D49249249B47F870411B57C09E2831237C92229375F8102209E8913C2A966102ED54D8827CF822D1F05362D93CB3B116EB75F1A0964420568887A276C1168B4EFC48BB7FD94F1CF8D7C30445909B25E08208F0C1046E820C48B2FFF008092492764924EC5422D041041041EC5422E9793232FF1A7E64EC5F2CDD45277CDA49276410410418988D5DA84937417D229908EBC93BA4927E2ADA493232249F80A4ED8F2C7F808A4ED8208205208BE46422DE7E4C8AB792492493224927C324DE3E14F9A08208208B46D4423647960C4C4C14C56C8A492493E29B4DE6D2492492493B56F24DD3CABE39F3626241062626262626262624189041041026D83131812D1E583122F046C9249D924F8E2EB78208208236CF866CB69249DB379DC9F0A2D165B26D4136CDA49DB24DA4926D24ED5F8524EC8DD24924EE9276C924EE9DB3BD0926D24EE9249D89B56C9B5979249B26F9249F0C936927CABE09BC924EE9DD379B49249379B49249369249F2CDD364EF5DCD112EB74F0492489BE7E54936924927649919124F8E6F3B249F1AA5E49B492493B13D4F8DA25D537CEF4F8D24ED9249249F1493F0E0820C4C4C482374F8E49249BC789A493E08315208208B27C59249DF0410410411E69249DD04102348431313123C12492493E46F89176A293B2083122CBEFE149379F8B1783130208208F81369D92492493E19DE88212493B644DC9681136CEC5BA594927E141046C9249F249249375FF000208237278A04DE825B83D124F8E08105BAA79249D9046F823C124924F8E4924924C8C8C8927C70411B20C4C48DD3E4422F365321149D8A4DD081776262429898180AC31237488A22EC831208B4EC8F1493E59F812493B1549DF3E56AEC9B2F0A8A64646448A922A5D362936926C9B60560AD313122E82293E4820C4C0C4C4C48205F972492493E48313122D0411B644204249323224724DE6C821EC510926D368DA8BE0820C0C0C0C48237C6F9249DB02B4C0C0C0C4820820820820C4C7E045A0C4C4C48208208F3C88E321149249249DA8A22882DA3C69F1F1236411E483131431431210E2F0418A9898904117C4C54C4C0C0C4823E225E49B492492492493B514932249276C1898907AB22FFA90410418A18218A7CB822F0411E69DCA278E49F990478A7FC18B411E282083120823C2FAB83B3E32824AD53A6CA0AAE65A3E5479636F1B22F046D8F32ED823CF249369F141041045B589C36BF1DD70CD52AD57EA1B146BB1567E625A3646E927C124DE7E44924924DE6D36924939DD3B6510CBC75DAD753ACC563A4A4E1D5154D1BA1789B2B91A4F87838F0493B72334992492774DA49B4ED9BC93E0926F3BB9DB24924F8A4927C4AB09A895631EE7D59DD3B2A2C32AAA2AAF04DA8D66B1ACD429D57F47A8B4E9BF529D5EF98375745EA9AC7335755EAAB4B5CBD1D26B5113AA99646456D63285477EA94C77EA94916A7EA8FCE96BA9AD3ABAEA54D3F76A59A7EA5A753F74A19D5FD49A8EA7AFA8FA88A4990AE359A8AF4355DED65A9DED68D26BFA453A8DA8CE0932323224927764493692764DA4932F049369249F04924924FC057B5A2AA39AFAADE950A98369BD5ACB4A1DD5347F77487EA12B369BF528AD9C55DC2EADCC57BE86A0A88A8B6411DCD0D4431959856AC8F3351F515EAF77E4B55CA23B957146BFF2A2CA54AA9498ED7B1C3DFD4155614C8CA509244A8B146AB9951BAEAB4EBB3F50A4AD6D7A6F2A3D3A6F7B9E4DBD14F5F5D852D654CEA6B69B11AECDB7926F379BF272737924926F24924924F8277411E48208DB4F5ED4A15B50EA8E654720EA8A32B0F7C329EA9ED75575446B1BD56755CC55A9252D43E9B69EB6A2356B55733AF4DCCAD5198AAF390E56AB11455B23893226CEF64112E74D35A1AE774DF59EF156047995E4C9218C47A2C4A9F9222A8DA9C53AEB45D5354F7B252DF736F4B9494F53529143F52E135749457B53C5CF9A49DD249379F93C889691A2FB91CEE1BA846B7AAA2BA9F4E4C84A8E154576EF627025D3D596CB2229913B16C89C62A412839EE7B06A72E6E2B0B8E2A96831178B488E250916AAB87EBDEABA7D7255669351D765B55AD4A2ACFD41AFABF160820823E63D7F29E27215150476C5B2A93B78312088489D884924933B15A408D1505B498DA4CA04708A235CE11845E08BE263CC6DD0391B568565A55B4D5EAD579FA9CB6BE6E69FA76A5F55D77BDB4DB4FF0050A6F5A1ACA55D7FCECB98C84844CA4585F07A249250E0E041DC9CDB1524F76924FA9B49245A6CB0B6924917912988D44B4A190AE26D9191F502A1060B69B644943555288ED44BEA39EFB69754ED33E86A19A8675A1EAF54353A97D571A7D42D07E9F54CD43763AAD361DCD11151472C7F8D82188AA24DA3C1C5A1A41048AEBC8A9C20AA22DA49DB24DD6C8D5554A67A244260524911DB11C7BB25B01D4C8BA0965B32ABA9ABB5751CB4B58ACA4FFC4C965D0691F857C8755815EE72BD1882BD105A853D43E93A9D64AD4BFC585114F768F1A12A84CA4C1336C893311C7022376C8ABB604491188978938432B4A9327B205F6642AF28EB499191239B0459149383D8A8A40834C79869F6D34DAB6358AEC97EBF3AA76A83A8AB2DA1585C90EE29CE49095D8ABC8E54623B58D3BF829EAA8D532432433699B4EA34EAB6CE546369D6A759324F891B57C5248B645D93B249B63B51A288D5129B944A2E3B670EA380F51A662B8912DF44DA604532E56C82F29889C0AB74E055B62A365132E514C9A6487B4654C552AB5ED64329F518A66C1ED47334B0D576A1C25763DEDA198D62352B57460BF93A2CFA0D70CACEA6BD443A8D3AAC3AEC3AEC1BA8468F7CAD2A551EEA741C8339A7F050820F569B4ED5D923A3C1169BA122F37C5446A9021231EA8317245A392BDA8AAA88D149B48BB1081381445B722107DDB8D88B064839252F303D78A4EFC9D4DAF6D2A2882D097536235129CBDCAB3C8952A8C5A8A62425A0852A264DC04A62D33A6319F964B931888239CC195FA94DBC3456EC7391A8EFD4293569EB68D4BAAC236BD370EA8C6AA2A2EDCB6493B10E0E044145DCCA152A0DFD35E3747A7622D5D353175A77CAE30D35419A1A4AB4B43A744FFC5D3B35DAEA55D26CC6DA49BA28CAB06A57AA5263B37B6152EADE045B288A4CD96C8A6569BBBD12969BCC0E5105B6433FB4AAB3A8AD499568CCA6A362A4188D4E5CE7AAD37736CFF35E4F4EBB5B23111110528321324526FAAD2D6554D556A6DAB55F55446AA0C7D6432A854AF55E35F514A8CAC35F51A771A8444D4D7417595A156EDA79355B0EC473764F166DE082969EA551BFA708CD3501DAA70EACF3F25314B2A7224A0957012B2E2ED3E9EB95F4D528390423748CCE69545737509F9E2E4608D5C571E89164491B45715FC499B35B2AD49735B90B4F129B29AAF6CC7B1DA47A22A5E6D908728AE4104A6F2AB56920C18F85544538446736AED966675150C9613272CA44A44A1920AA3735A9C102325631138106266F72E1491550EA2B84ACAC1350C51164ADA6A758ADA25A674910C208303A66063274C83131BC945D0EACD346ACA88FD331C95F48E6189062AA4102421ECC79651C8722B5DA7D43FA4E5A8E7F4DD976FAB447E53F52A2AC1908A66AA3133D35359752A54454A34D3A5A494A7A53A7A7516969D13A74947D3C44FECAC29A2294D31444E569FF002D4D3A3D5745894E960DA9411C442A0D62AAD3A38151ED473F17089662734D8ADAB49BD24A8F5515C517A945CAF657A392274B2D551E92B5A98BDB0408D391AD97608C5EA2E5522A9D2A625241B415EFACC562A14CD3BB9AD4E9345C1A22B5D7822D041041022237650A70DAAEC9C62A4591CA82557095114ABA46B87D37317C2DA68A8B49211BCBE931B4917363556856A6ECD8AD4547E91A8B568E220DD37519569BA9386A49E958A374D56B2D2FD2AA0CD269A91D66B07577A959A8A3BDB1616A21C4357F8DB45EE5A0C5A62506208F446A35AE5F4AB5876A107D75712A853E4A90952ABBF1A4B0F4724A292751A2AC9C0E1D4592EA3CD2A4B25444CD588707D69A1457A64B51B8FE38AF2ED122AD5A95FA1569EA1B55EAC4C9EA95342DE12521BFDA52EAB2B6A34F31D51CABA56F474B51AAA23463147AF4282395574FA6A6C6BF4B41C3E9AD35F035B8B4924A4CCDEF760C9B75958B953AA3A9392DC88E11F02AB2A255D2AB45B4ED938C91C8295BFF00568BE1D59929A0AD7A9411E95683A93A8AC50AF516AB9AD57147415DC8DFD3A920C6E9E90B59E2AAADB2415E85645A83A5057B4C91EDC50FBC8CD8759A8770E16B3D457292498A9820B2D5CA44E5A89F96C47AB4CDC755EA22B707D6E7ACF3AAF2556C88A4149F8320C154FF00E7814F2A6B532AAE4A6A748E91D23A4874D0C1A628421097FF008F4F30EA355EEA8BEB01CE6D24A9A86D46E971EBABE04591FF009336C5A9B76E93F2655A6F55B2AF23350F608ACAA2B150924E1445568FA54EB1568BA9AEE5A4E57745446AE0F6654BB45431544E69D4A153AB4ACAD6BD134D4E1BA2D33445C115554C9A82D6620BAAA482EBE98ED7C9DDB94CEA3931553A68628839CD8CB244995D92231CE3A2251411BC602D33A08748E99D33A4748E91D13A07450E89D143A287450E8A1D143A4874D0E9A18218A1041046DE0E0E0E0E0E0E06E32EA8AFA47A731E951B535303D5CF77DD2FF964451796D44DB231321EA8728644DB4FAAE9B58F6BD1D4DAF3A588EA08A398E6DA8A7E2862D70E62A5914F64F1534A8A2A2A6D970D57292F2486A5444A4E47E929BC6319A51FFA8D260EFD545FD4ABB867EA10D5FD41CA2EB2AB8A95EA9D57A88AA2A9221F625473515CABB26475A0652CC6D163084210E084DF24926464499299292B6953939BCDA4924927C6D4FC1CA8CA0DE4708F7352085B5358A88EE114628A839B8BAD164A88D30C908B52FC9EF8C9155159AC5419518F456228AC847D04531C116447488F15AD78BC2A288E18DC8A9A76D54ADA67D25194AA3C67E9F51477BBD34457FA5EEAB30AAFA4F55E4C444413043A88755E3B279829D371D371D271D171D053A076E76E741A74299D261D368EA4440836AAD35A75D1E493E19F9104104104116952BF34E9AF2AE629451A95955AF1F42960E5E517962C883781FEAA3D152D227E4AD7BE93A29395EAE4169F0514817DC923155AB4F56A831ED7A2B114735C84130B284C37DD9BCAB5316ABEA4A64A89A7A2D72BDAD1DA94424926C8A48A3958861918350E0E2F24DA6D249249919191919190EF5468BB50FAD45D45D4FF00BC924DA4927CBC7C767BA9539CA17A8C9EAC1D4689A872239E8A494DCA8F6F27F52AD4966CA29F9BB954581955CC3F8D472AB4C7F8552F9A488AA8B4F58A832A36A23A9A387D2543E9EFC46B69D43B5929E9D2992883AB3507EAC7EB076A545ACAB79249321EAAA8D6AA3B2532719292A4A92A4A92A4A9249249249C9C9C9C9C9C9C945CFA2B556A577A5346FCEE37C5E244A551C76B587A60D70BFD9E993B053115AA62B6E618F5569EDBB19F8D0BCC14EA2B55EAD1EAA89D196BB8312445B536BA7BC462B5C8E45A48E3A34CCD8D1DA9441FAB1FAB1DA85516A2A93B60820831314312108F141041041168208208B41041168B47C9445709A6AEE1345544D1209A4A28251D3B44E934EAC0ED4152B2A8F5144A68C6D4A4894E0818DC8AAD87492515E10417DDEA2E2C9249B5049AB55FFC8CACE608AC79855A82E9DD0A82355559A4AAE1BA66A35194E98EAED41DAB1FAB1DA95516AAA8AE55D90411F0236C106262410847C08F85F6DD356709A251BA4A482329B0757C0EB4998AAA64E525505AC883AB48B505A8235F5466961DAB48A6AAB12492B0E5BB1161967FB9246264FAAB2FD941AAD6AF4E69E9AABCA7A4C4E10951CC6B8CA9B076A9A83F583F56A2D7553A8644924FC0823FCF6527BC6E85C374B450E182D469D716BB85A8A54954829673D4C47EA85AAE712E317A9D11B4E8B4C92DADFF00D7477E129645915A90E6A614925CE48A343962A8EBD14E55AE55E8D453B67A1345A3B550516752953A34E8A64643ABB5A3F5883F56AA3ABB9457A8AB7823620AA492493E68208208208F2CDE6D3BD67C4C63EA0DD128CD3D2A62BD05AC882D6515CAA644DA0C47CA0AE7A90AA626242ECE4C9E56655A88A9082928872E117A6B4DE88F5ADF833F162A9A3D335F43B6A67458839A8B5F515D9A6655D7D5AA2B9CA62AA32964FA0B8B175703F5C3F56AA2D672999978A2D062410A42FCF9D9C9CFC1A7A5A8F19A5A4C32845AA82D55532526F8894DC749C749E741E76EE3B493B33B43B469DAB0ED691DBD243A544C681FC06A5FF8E7F83950E546B081EB90A852FC9F666B18D677ED3BF69DCA23EBD5EB568B7FF1A0DFC7FE95215552EB64F9B3E78DB1B67C68D572D0FD3D5C328D26254A42D73293F13838D8B59E83B53550EF2A1DDD53BBAA26A2AAAF52B12A64C33A62D5A4751B8BA5473910EA34EAB4D2AA2BABD34654C05446928A549814D3B7F2551CBC64A64A22AB9D51D0B2254532424670C7AFF1AFB6B5CA4189891F0A6F24FC19BA6C9DF379DD2849ECA1A0A954A5A7A74524524AFA76D647B1D49D91928D5B48AA2A922A189037DAA398547F39492494FF00A6508F77E4BC3BEF44F8D4D7A7D66F45CD4C1557F8E8B5F5D6B28A69D3F170BC9898941BFCB58822D1CC7FE46A1D14A4CC728E742B1F90AD313123E74DA7671B3DF9277E252A2FAABA7D1328924EC92A536D56D6A2EA2E1AA4DA0820820813F15D422AD2AE488A494796FF00D1655551C42C67D3AA9AD68BAFA63FF501F5AA5556BD620728C4C69B96062CB6D4DB0D7F2B0410536CD4672ED53B83A83DFC35246D485F69B60C4C4C548521C727249265BE374924EF8F91A6D1BEA8C6369B649276FA1C88E4D4695698C4237C18A8DFCB46911FC76E445A83BAB8E154E9D51B4DC3D115D53D09CAA8DE155646264F5F559C505E2D9B5ADF77918E85A49C56A5D44ED39ED1B3DAD33A34D0C13741046F8208D93B66D178DFF7046C926D3B38F0C888AABA5D0A309249F07AB76D4A7A148E8D343A6C430618B484F1A8AA3DD0C5522DF73C4F391A6A638A8E97E8588FABDB515134D4511285143A348E9533A74CE953306ED8208F2410410411B604423C72493E15F32264BA5D2A5149249F1F27273BA3C2B6D52FF0889C3BDB3DFB14D369FACB8A352B2E2DFBFD3D914FE4A79649D93E4F57924CAD27BF1E8B4C94DB2493B1167E4A8A6A97F8CFFABFDFD27A4E5D4D5071A9FF008D8D573A9B706781784EA21EF746C8DB1BE7CD3B64927C50413783EC9B68E9752AE4493F2250C90C9A66C3AAC3AD4C757A42EA291A8A8DA8288A2DBEA8FF00CAC5FCBAC6A2A22B58E56399A8A6E324424ABA96D3135C928E4725DF518C16A3324AB4E33699213FE14DBDF9A6DC124EC438346B0B9191249266754EE510EED0EF50EFD0FDC10FDC4FDC54FDC9C7EE350FDC2B1DFD63BDAE77958EEAB1DCD53AF50EAD433719A9929249375B20BB51617AEDC7A8D15D929832A339A8A8FA7429F5E92AA6A2894ABA395F51D51D4F86495EA67526F264A66E3A8F3AAF3AD50EBD43B879DC3CEE5E772E3BA70CD42B9CF7E2DEE4EE4EE50EE10EE1A75DA4924EFC89F325D52F36F7E1A0FC551C644924924924DF821A62C1CDA4D4EA68D49D12986894E8E90EDF4A769A63B3A076344EC299FB7B4FDBD0FDBC5FD394FDB9C7EDEF3B0A82E86A8BA2AA769547B1D4F62FBD883525CF87EA29F55B4952AB99518E638D2AAD36B58E44A0D6D366A6A614E6D2493E4A3FF257FE9E19F146F9DD2A4F164B4F1B276228CA823C471249249249379BAE9682AF6740EC681D8513F6FA67EDEC3F6F3B171D9543B4AC76DA83A1AA3A5AC31D69FF009A65AD3A9AB16B6A45AF5CAEF73EF27BDA869DCC6B9B5E834EE691DCD21D534F511A9A56AF5E91D7A475A90FE8545E8E98E8E9CE869CEDE81DB513B6A476B4CED5876AD3B543B43B53B553B553B553B6529D1563AAB15E9DB38ED9C76CF3B679DBBCEDDE4F8E4932BC1175B4DD3742F911E23CCCC8C8924924924924926F2493E4514D67F6BB53F87669188E7252A6874A99D1A674291DBD23B6A476B48ED691DA523B4A476948ED299DA30ED18768D3B569DAA1DA9DB1DB29DBB8E83CE8D43A554E9D53A754635E83DAF530AC6358C6B915C8AE457DD3E3F44ED427C522F2410B3B26DEC9811E23CCCC8C8932323232323232249249249276493B545357FF0025DACFE27D1737668BFC38F1C786777A24932249F1F17A9237244C8CCC8C8C8C8C8C892492492492493232249F0295A9A3C5A47486D2FCAD529B5C76E744A0D46FF87046C8F0A5A3C124EC8BCECE66CAB064A4F0493B31B4A999999191919191919191919124924924924924DA451C40A37DD9DE8819EF7CFF00A1CED410527645BED76409B679552764189C92A666664644924926464646464646464646448AA7B15070DDDF72493FE07D213B67C3C9169DABB208E62D1792777D5A577C93B54C9E87594EB21D54333232323232323232323224919E870849249F764524924924924924924C89249249249249249249249249249249B493B3988DC9B552D3BA2EB65256364995F9BC1165B25F122D24927368314169B4E988D22DC92A4A9919999999190CFEA3C9249321B7C8C8C8C8C8C8C8C8C8C8C8C8C8932322492493224C8C8C8C8C8C8C8C8C8C8C8CC8BFD5A76C45D2DCDE148208B46DF7B544DF2492493B649B2916F44ED9B4DA4926F0288220DFEAA545B7369529D94577399999999D43A8750C8C8CCCCEA1D4333333333333323232254FC8FC88710E21C4290707E27E165249B25A6F1B67673B26D3E0823C1179B7DEE9F1AF8646FF0061C3B9DB4FD28A2FBDAA22AA0DAE4B5C39B07FD2CD56A19B0EA30EAB4EAA1D643AC758EB29D471D5719B8C94CB7CDE786ACDA4E364924EE9B7E4259444E22D36C89B48BBFDDA4FBB4EE5BC9379249B4DA6F24CDA49B53FEE3D451053D12531476C9BADFF00EB2A47E3F15082108B2225E2F023762B8953DED8520839DD175391544B21C137926D1F88A25A6F1BBD0BB38D904ECD3A4AB8A8A6569BD214A8493B552F4EA624B147BEF3F13EAD046C9B642B89B2211B27645A6EA9B2443DDF8DABBD7C1EADCDE49B27826DA4A48B46A5372157D931B28FB70FF7B97E6CDA49DD040891E34B473B26D3E255DB24AEE9D9C1F446D9277222AAB685457AE9B032795DD954DBA46A391D48A9FDF745A08B4104795374104104D93DDBD8BC5E36656E6D296552777B178BFBB42A8891B14E6C88420AE393D90411B26CA45A36CDA4813D45956C869E862EF4DFA7C28EFED65B27252D345356544157923E746E82108B7ADCA410446D5BC5B2B4EC9B4F826D164422372A116E5444BFDEC8DA9A7A8E13F4DACA94748DA4AE7E3491CDA94D151E2C526D5FF9366859F93D5557FEB5D9855DA845A6F1BD77FD78608BC927B12EA4A888A4DA6F379B7B3D5905410E0F4291B253649045A6D22AED53EAF045FD91B20814D3EB1316BD8E1AF621A7AB80E7742BA5697BDC90E5C9F78572E9E8AB198C13FC7A95CAAF823C53B16F378B46CE0927671693939BCDE4C8F7B62D1B3DEC9264920C482082237656F569424FEDE2E76FAB40DC9165C40B695D8892BA6D2A3157A58BA71A8E6B07BB276C8BC78E0823C13BA04B478676C7816C9B26C9ED10822CB224C124932410449047368E6DEC810820E0E145F76E6F3CAF242A590FB208DAD96AA6B2A9DCBA7BA722BEA2B976A5F8DB1E78F0F168F04189041025A52D16F44DBD195E0E2F3645B2924DB815A9643EEC845979D8A9753D2CD950E48208D8A4EE8D9F7F5BA7C127D6CC6CAB69DBEC5B4ED912F365D896F7B66D912644EF8D91B16C82C0AB6932BA26DC7954112E88408841E8FAD9F76E44BA93745D9337F5B23C49BE08F0A78649B23556C865B3D124A13784BADA6C978DBF449E9483D1169B7225A0F76E05BA7174E45BA6C8531E2DC882DE0F47D5A36A9174D904189C6E8F1AECF625E6D266662577E3265645D9891BA4FA822D16FB539DDF7B250822C8BB262DC5916093283292764D90709EA383DA5BED44F2492493BA76A6D4DE845A49B25E49B2A708C20E4422CA9B10853122F026D553E922C8D3D59508DCB7CAF225BD59512D1C8A82A089695DC9EC513DAD905E48B4D9395854B49169DB02F862EA4EC8BC0A271BBD5FDEC9B45D1C491262425A2DF56C84F56E2D1683810553311E8A396551C2ADD4526D8C91646A109793E8F5B62F164B7DFAB41CEE53DD954E6F365585BA10421EADF6BE05DE969B2F87EC46DD083159C4F578B4F0722A8AAA3154926D265CDA0F5654208823883D122AADB03D08B78208B26D8E4426F27D448A4936F67DCDA56CA45D445817939BC1C5912D0707041EEEB09BA577A916F5B205D91643E84B222ECE6EA4A0D54951508105BAF261CEF53E8938520F449C5E49B21127A26FE891508B221C8896F491245B8B2210717932E4FAFB8911B68E053115A42DA48208208DB37520F5795BCCDA764ED4B7D0B653E92C97416C9ED4417DDD077B1A3BDA7A51BB3E94512CB7FAB7D2896FA5F775B3ACA34FBFFB09EBED767FD545B2FA4F765B2596CBEF727B16CA7D597D209FD6EB753E905DDFFFC4002A11000201030304020202030100000000000011010210201213300321314050512261415214236260FFDA0008010211013F01F450AC842F590BD1768F8A42F763FF001CACF95DDE6B81FA132318C63C9FC12162C631FBEB142CA3E05DD8FD78F7D8F89713E54217A6B042CD70BF825C2C79B18C63C508421733E14211A4578E042BB18FD963BBBC72A162859211A4D269349A442C50842E3EF67931E0BE3D084215D0842E55F02C631F0C60AEEEC631DD8E46318F0631E6B89F0210ACAEB1815D71AB3B31E4C6318EEC63BAE642C5F13BE93B598AE8440ACBE118F8341108686F08113182C229349A49A7E1E260D64D5C0ED22CA0882620AA3BE28D2210BD44474E64D9269567C0F18BCF916504292499D528D04C2B408888934C4133821084210AD4C14D5A7B544CC4F8CA22983717827A9323B4F7827F13A54D55C1B126D49544C7A4ED455698526B2659A444F7225135327BDBB0AD13F676B2159C8CAE1C6AB3BB18ED1029349B64740DB9FB269882BAE23C1333393B3BBBF7148842B74C9944C91CBE089C17545D5FB3A754D31F95B4C91D3AA4D8ACD823A108DAA4A68834C9A048A67B13522AEABF039FB140A05028141F89D8EC763B1D8703819A86318C76687C6F27662BC915223A86EFE8DFFD1FE449BF59BB59B951AE4D5274FAABC9D4EAEAF5E62DA4D36533C4F2D3CAC779C17A111324508AAF1E0A6D22B3E04217ACAC856421084761DA17F246927A702A609A89AC7841055E49C1D9085EA21085C6F164566B918C78534B2222D2215A45C2C62B21084210B9D9A87C8CFE2EE52829853DCAADFCFC0BC6679E99EC4AB74A2113E4ABBE4C79BBC62EEECC767C0FD4A6A9FE0F104F8E25CCF29F550A4D3269915FA65525768E9D530EF11322917AC859CE0E074FD11551FD4D7D3FEA535513E283551FD0DCE9FF005377A7F46F74FE8DFE99BDD337BA7F66EF4FECDCA3ECEA4B9BC4A19D4B282A4FB14C394530A1590A0D30698345268A4AA9868DBA4DB836E3854F03C66338998F06E57F66ED7F66F57F66F566F546F49BBFA373F46B8FA27BE2E99F27E03A4FF59F847893547D9AA3FB1ABFE8D5FF0046AFD9ABF66A9FB35CFD8E59AA7ECD526A9C5713C2477EDE84522B2269F655BC5FBDDE0C78C77B2E3A7C155A8927C7B4B257EF656764460F35782AB5369F49F2ABBE064E0EE858CDA3955959722E042CA39230827E126EB35659CD488AE66ED13544FCAB819305379EE53EDAF4960857EC2191762F91569B45D61DF079CFAEECB9DF077B77CBBFC5A239160F89FB0B29E05C11CCB9638DFB339CE3FFC4002711000201040202020301000300000000000011010210122021303140035013415161223271FFDA0008010111013F01B31D98C7D2CC86318C63D56C85ABD2247664DDF5AB4FBCC631DD6AC63EF7AB1DA6F1379F7E3D763B318FDF5742B2D18C632774215D0BAA2046262622D911ED3D98C7663B210842BA16F3BBD10B76318C6318F59BAD10AE84216F1BA15D0AE84210AC85742ED9D959762D16A84217A8BA245A6431F62168C6318C6318F67BAB2DA755A4F7AD18ECACF44216C84210B56318C7D537810AF3AB1991150ED24EEC631D90BB97621590AF369205D0C63D58F666526466666664318F4664318C7D6B642D18FDE7DAF46318EECCCC87DEFDF4210BA66D12863EC564210B55D0F75742DE64C8664318C769B724B299BE4399D26CC99BE488AB45B210842BA10AEF4777AD5E48DA66CAF03D95A6A3232B33C903922463B31FB0C9E769B2B3FEDD088D2463221885666645717E6CC99B643321FA9367B2BA16D95B126042B21134889A4A6A1ED9113789D98C7D6ACB65770643B220993267253574CC088DD717F106486EF124C993168C6318C63B56D383FED0E04B69914884222D54C413511644474A16AC70657FD3267FC323F29F948ACC99E08AC631DAAA67F44CC99194994995FE29C2BC26CAE8E2FC59991358E077C8CCCCC8896318C66466670664D706706464329A541F2110C5FA26983E4A319D22318B53CC92332B326224AA85A3F8CCBE3FE1F2D115CBA6CC706706664318C7A643926A32932918E472391C8C7D5443924E3F768F36AE9CA2F4432AB44FEC8A844D3ACD0C9A6608A24CA4C89A9945524D464643B318E4E4E4E48A3FA7C94A9E3D5A654F045431C0F920AAA8A792AAB2976895047C913E49A7F84C2E2D133065A2BBD22AB38E8633F248C7E86151F8EA3F1134531E64888FD148847ECA9A265DEAB5132CCE1934C49312444C910BD06318FD18F8E48F8E0504C9949948EA911C6922E0ABC930531C93133247C7253F1A318BBFA28F8E648F8E23544D260607E33029A49B4C997079938FA07D14FC73FB22222CA0988578B4935998C67C73C93022532A9E083229F064455F474C4C94D111ACF8D918C08444222A9192564C7FC6DE291544532FD95D34D39110BD392799223E9621910A3D4AC8BBFA3A7CEDC8A7FA4FFE8BFD17FA23131319148A452478BCC088D247AB18ECC63E842EA77A6BE85028318318318318313116FC9C9C9CDD0BA17AF15190FBB21DB2328FA642159C915999940FAA7C916A88E3DA5EA7244CC1919199940C77929B4DA3D29F49F72BB1D9991336C863E96318C63B4F4AB4C11E8A10BB15A3EA175D147F4AA888BA66331A448C63FA87B45324524495DE92BF4BC0F4885EF44998C99B443B4CF43BBDDD98ECF55D4BD4C863EB5BF1E82D50BDF7D2851742B210AEBD042EF8816EBD477577789FA381DA7A11E345A48CA745AAF5A748169FFFC4003C10000103020305070303020601050000000100021121311012320320224151303360617181914050A113234292B15262707280A2E18290B0C1E2FFDA0008010000063F02FF00E17EB7FED8214A07098527C67D0F2F3595C4F928E6A4C2CA6CB20F6F19C3A9E78D70B4A0793B1AFD544D7C112838156A7665169BF255C4CCA00594B2AEE8B31BF440ED1A4345958A8CF5F347664CB49E7C93DADDAC70D116BABB46FE516ED1D73425659138C3F9A1472A490BF6F4A0768E01C81999E8A20C755AA1472EAA1825641B207D0EECFF001E4BF533717A2702F26511B52E7745984C79F812A5100A2D26A8D25CB8AE4E3759495A940699E48528AB810E0B89A18EEA165998DD703CD12FA984288106CA4C4F961A8E2C7730A5662B8F621C79153451BF1C9661745D9A6791433182B81E1C8D792E224EE40753A1525C6536B24F2599B6F00719E344A99575050E2E25571859C6D688B9DB554C080AA65173B6BECA039020CCDC2A616AF656C2141BAC8EE5632AA4EFD915A5515A31CCD689EC781C428DAD7CD0E2BAAB8781028CABC95057EBEEAEAD2A393708261428ECE094D22901389105A119B838C332B8F3F241907880B0E7F7681651955B083F682A9F40ECD1561BA0E069D10636D32E384C0E2142A8611D9BDD3491B85CE3002766E102DE6B28A3BA1FB84CA99AAAFD157E9E9B94EDF8139C09693D0A009985544B60C8852D595CA72C9FFE93981DFB7365459A2570DF98DDE2780BBC0A850F5FB45BE9ABF476DC8FA596BA107175621119AAAF80842A60AB9C2F015A4F9E399A535C3AF832BBC7E9A9D84845BB56667722A4C7B6175757A6044D26708CD27C94A86C9F40ACA5CE0079AE0639DF85C5B07479195C2FF62AEAEB505A95D5F0CCE3014B1D2AE3EF5A96A5A955C703F5B6C28AA8F58424EE3B31A2046CCFE9A0C85D1AA00595B577F6599D538F9F559369EC55C2B85A87CAD4151EB81A4BBABCA97992B36CDB97CD71BC3BD903F7C25EFF00451CD1133F573D8157512A3922D6B9D97CF0EF769FD4BBDDA7F59C39FCAB9572B51553BA1514B8D54B0FB27521D6406F4B8C05404A80E83D0E326CA8F0A1CE00AA19FA9E1612B8DC1AB8897AE1D9B570ECDBF0A0EC7667D97103B33FE55DFD3D14E52E599D91BECB26CF663FDD18D7B004094384A3F63A157C2A29BD15F7C72E196EB4C7BEF89FF7154DCCFB374A21D752E270CCA5B996A74A8712B2B4554BC15C248501CE557153C3B9751DB7085C6E856CC7CD434615718DEE27183D150870E8553F69FE565C429C8F65C20AAA3D566C2579EE5119A6EC2F3554333C094726D1B42AED3E9D857114BD909E78C2BE1184F4C070E159C2EAF851491B918C72B944FF00272A15C4556A3AE1455107AAF2C2EAEAEAF85D5D5D5F7614ACA4555948EC4D6142CAA33286C92A7F49F1E8B881077AE8F50AB655128879961FE0A9B377F52EECFCAEE4FCA33B177CAE17382A1951B928390CAAF5504C846040DC95652298DB073CF346918D570B6B2A1D2137A3821BB0541154CA69433034562B841841AD1C4A0DC62E044853FA913FC555C02A57B38E7B83ABAA5796EDF082A5942A1C3B23D71CC0570941CAAA6242902984A876EF0ECDC571BC35711CE7CD70315E3D112570EE3951A51CD62AAE501D0AB98FAAE168F7557B07A05ADC540C239AF3C01C2EAEB50575A8616429452E98E5B94C64289472818426B63862AB28957506ED34DF98C5EE2385A3093A8D54E248D455AA54B9A1CEF352D191DE4A08EC64DCEE5748A95E6EC62E17476F43D4B2A3B1761314C214AC8772B8495412AADCA3CD7EEED67C9ABF6F6227A95D154E17C3540E8AED5C9415A158055DA2D4E2B47CAA0016A57DEF3DFA1575572E22B8556AAE55771C22F8E401594852EED6399BA9595CE904632F30A109C72BBDBB0CC6C3779DEA4A915DDAD42E9BB45D1CAA37E55D6590B24AD6AB8038C11288AB55439FEAA366D6B3D02A9570B52BAA2A05653996B3872542AF555FB89B6122E1660A1952B33CC9C0633CD661BF02C377296D3A852D32AA15A4296155C277E1DC414ECBE157B00AA4E55020AD28FEF003A15727D1706CFF00A96A8F455A954185D6A3BF42AFB95BEE792B4FDACA27D8625A39AB6E0C4B4D8A8DDE2B792963B36E00551483057EE57CC296BA5743D42A891E4A5A5018570A6E48A2ADFAAA8A75C38584AE321AAEAFB94519A479AE26653D42A6E51AA8AAACACACAD8DD5D5D735CD596954C4C2F3FB580B2E2C713454703ED289FD316C06E8EBB90144FB1A852D3FA0FF00FA95FBECA7F8DAA58730C1CEDC9060A8DA09F30A5A5743D5751E4A541184E30A1431B4F35C50A72367B2D32AD95595BE9201850EF9FB850AF3505EE8E92B85B18796152A1BBB9BA6145C2E81D39291FB2EFF00AAFDE67FEB6AE0E29DC8C24182A36827CC296995D0AEAA85415A41F65CC7BA999ED69F5145C5454FB4D366FF00855647AAF3C029DC9C6FBE4F5DD86D3CB9282F3B33D628BF79923FC6D59B66730C279EE4B4C79A8757D1485D0AA8F05D012BBA3EEAA583DD716D47B05573CAD1F25518DF8C6F8B1C6E548C6AA79764D6EE8F24550C7972523F6DDD5B62A5CCA750148AE1404FA2A885134F25400637F03E8F95C7B403D15439DEAA9B3605ABE17FE55D504AB2A9DDE11EEA5E537FDDBB6EC404775CE34F555DA663D182570EC03075DA95C6F2EFC054C38803ECA903C13C2D2571B837F2AC5DEAA995BE8AA4954185D530F2C2F859720B8B69F0BAFAE3EFBC0B707429DD27A05A4AEEDCA5F9583FCC5737FE02E10D67A26B76998E65C2C015154ABAA617F0370B495FB8F8F454649EAE5754EC25A16A572ADBF65920F551B955E5811D710E7B6A5685A026B4014AA975FA2BC0E83101668F05DB28F355198F9A8B7A761A559595B0B61FF9571F2AE15C2D4AF8725B676C87951444AB61656A2AE001C437A61759B990893B8E77B61C27C0F004953B5A0E8A1AD014B7E1444157DFA395CAD456B2B595ACAD45716DE177E5576A5778E520B97F2577AFE4BF97CA7816F34081C2E546AAC2A57727740EA89E8206EEC9BD6A8E067C0B2FE06FE54347BEE7477550FED281710DFB0C483FC8280AAE56959DCA8D818CEE83D2AA377D0781B2B1AA4F13BAEFC39574F5ECE56C9F34704376A159595935E39292D737D16A71F65C0CF95C4564188DD9EBBA11280C2D8498541DAD95BEE399FC2CFEEB2B440EC60D42CCDAB3FB76563F0B2916B28730FC2EE5DF0BB977C2EE4AEECAA6CCAD1F95A47CAAE5455318C40C2146300D86ED94AF35572B9C34AA0FBED02CFB5ABBA76A4E55A5680B405A02D23E15BB53E88EFE73814730910B49FEA5A3F2BBB0A9B30B405DDB5686AD23EE96EDE00AACCEABFF00B7D0DFE80EFC9D2A889C0BFAF82FF51C388FE3EC51BC02C839621A39A0DECB9F8025D61F5770AE16A0B585AC2D616B0B5843299DE6A73B0841C2E10E312AB845CAE262916DCE230A9B4A2D616A0AE3EFA77AC5687AEEF69F0BBADA7C2EE9FF000BBA72EE8AEED680B4B559ABF8FC2B85A96B5DE15DE15ACAD656A2AE55FB7CA3706C80008B9591A25A292B2178A233FD91A55435A8B4361A3A9404CE04EF5CAD456A2B515A95D5F1B0510A5596956562AC573FBB59582D214960F85FC7E17F0F85662FE3F2B97CAFFF004AFF00F65A8FCAD456B72D67E1779F85DE7E17783E16B6AD4D570B92B05C5D900999390A94E60D99CDD5327634E6A088C1EEC8E959B68DBFE14074A8E67C25A169563F2BF97CAD4E5DE3977AE5DF15DF2EF977ABBDFCAD7F95A87CE165A5685DDAE211D912F54A7B2D4B5A8710548216B0B585ADAB89C3E55FF2AFF957FCABFE55FF002AFF0095A8AD456A2B52D4B52D4B52D415C29954570AE1725C9725CBC1A3709DD74895A42D01680B405A1695A7F2AC7E558FCAE7F2B9AE6AE55CAD456A2B595ACAD657785778BBC5DE2EF1778BBC5C4E95C2616B5A96A570AE172F08E52A951B8EF085157ECB7C29FEAD5FC6B6C2FE37B7FA5D75A82D416B5ABC183E8F886F542D2B4AD2B4AD21690AC1586175757F098FA491E38836C20788A79F826BF6D8010647BA0766E2D2A1E3DD1DE2AE8F836BF5F26AEFEC886F2C288EED90AC3977923C5521B455CA14BA0B917B5B23C97EA6C8FAAA18289B928EE9795C215511E29CAFA2E128E62B68D9E7CD67D99806E14CA99477205F0247B2CCDAF8AEEAFD80039A97AA100AE1130B3ECA80DC29F19C8B85552B3047FE0ED07D7C786637E3EAEBFF000C2DFE8C57EDFE7DBD7C443B61F6FF00FFC4002A1000030002020202020104030101000000000111102131415161207181913040A1B1D1C1E1F0F150FFDA0008010000013F21C090B2D7AE0F912A210743C721A330842108242C5065C4C93E2B142613E2B1714A5FE0B30B83392CC5A8FF008012104268838C41E664484B0596B2122610F628C6C6C7914B8BF06AD2284310824472CF0CED7C0A8162222120BE085C738F03104840FE0C9627C5084CCC466F0AFCDE10F81D13DE1E2132994CAE4E87C0E9E0F11B112084B1588BE110D1084C5C18D0D0D7C2130B2DB385D8F034181D0A10660D68B0A5C2947B1621C17E098D51285C702B1628BF9E6094FE0A263F84C7862C4DE1146FE1D62660C50621821097F0A0CE04D0C6C63F8BCACC05F624645891A0A85F8B71C90B05BF80486A89061042305BC209132D0BE17FA083435999791308584C438C52FC4CA5C318EF2A58A5297E22B50F06C63586529733E0966D20E9C86AC4B5F084C520C6B626C813E0F170528D874265F9279652FF004132418998421084210EB106BF8A4CC1FC5946289970363D8831B186FF0081618D9121A07A1B07D3615BC2C41A0C3BC0825306F09090D0D0F0410D8984BE1099D89109FD2DFE4641A1668FF80C652970798258A518F063F921DAF88DA3EB12B120C7228D8A94B88426442618D1079CA058213FFF0015B28BE2D61933728A52E1B18C6F07914A5F80A51E0627C1ACE4361A096110B4263C3B08A313F94F941A2108421064FE65FE9A946C4E1482ACB7C5E58B34583630C314A5294B85F804F0D67457C3379C91109B34C9C8CAB8203082EF14BE298698C17E13FA06D0C51F1982FCA97F81B4509E1723784684F5865294BF09707A1FC18D8C6365CDC5C5C2C220F08585843BE282C9858515468AD338E3578B85197105FD151E03A36FE248ACB59426694618570C4C5288BFC54B837F06C6C6C6319084C2B256090825862597098820A884851656610E595843A2335EF04C314C5B2610B0D94B85CAE0D8FE00C3C1561C3595C10A26309FC9972B0A41E2E6E2970A5CB297E532337F009092332CD45961051E18F04CC6C27284CDC412CD392482509108F828C59C8A52E4BF0688F1464B84F827C1AC08242C27852E1A2629704D08A0F5F014A365C132E171714DB0C868D61CC42105F182104266C5F8DCA4586C42E1B1D8994B92FC05108CB58629714A5F85C52E18D94586946B04850845F204F0F173365297E05BC5132CC6FC54A5F831C0D8A35C6971B67182C144F0C58528C538F828D9B3161C66A312912C528DE28217E74781BB9297E0B17E46344213170A5F80A52E1316150F170A5C1BC8F052E0DFC42FC052E297146C4CB8A20D8C4C513105C0F14A5105A2E5FC24132FB294652946FE01878A5CBCA7CA94A5294451EC6B0A1514A32E4BF00852949C88B51B45294A365294B852E14A5C94A5294B85F03B13C58B9962AB0865C98C278A51E2E5E1C47B6418421DE2A2E16E1BC213C1170D1B45194BF2B9A26528B0D1B297E0186294BF2B9294A52E60F2FF00845F1AD0961227C0DFC6FC84104C4A9A1334BF1D0410451A262E1DE2AB8BC530A5294A3297E14A5CDCDF80A5F84361FC3DB94464C244C5F95F80A32E6B14524848D138EA4CBCDC1E43170FE744CE42E445C365294A5332636614E8559222BDC5184C1AD0D61311B2B1328F34A5297252E14B8A5294A22130FE5A22C124444E345E46213E6842C5C528A711B1B2FC814A5294A52E171AC442440E5918B4632E0C5294B8616C5A1E41725131EFE0BAC20D2F8C41218F14BF0A5294A534C99288C45F80708466CA528994A5F8282A2A20797F1A529458786CA528D94A5F8C788CA28AC5F8208A57F168851A0B09E1618728D30DB611584D8CB8621146A8D71A836210F8EF34A52E082626249A2709E879A263468785C4646565F8851B1FCEFCC662B352FF0002441241ACC8FC471841098A0DE14A5294EB09945C947221C31310A791C8F608367431A0C2785A3C8991335875D8DD312BA36C2D860D8A262944C5F11698D4795C60FE0D97E3ACC1054399A52FC2970A5297F821042C1E4A2785B945848A5C13C529444262E1368E83A289E1CA826670C484890C4C4847118FE05160A213E0D19E01BF00908642304EE294631BCC20B22078EC6E508C78A52FC297E5084F8DCDF902FC5A26508A2B2CF80986C5B106BAC29170A1310C4B80D4ECB826C6199A0B061460DE1C8FE04C809DF83589835233588A427C20D0D88D6362152B34CD7E0E848F51B945979A8A28B2CA2886BE17E7084C217F077F104B3343785F0E510E0B834582D0E59F88C78B91638092C9E161944C425A187FC7A27CA97128C2112CC633E5084213D124E2F5093C1E81A117820D86F8AFC15E0B2FC7C099F42F00B14124E109F084CAC32906966946C3C20B82F9C0A5C27838E3BBC1E145C61D1571A426C27703F9DFE8662FCA7F14CC2116213241036759242585F384F8399A52970F11F82BC09B0D1457C1BCACA213E0A265C213042F952FC04FFA4842108C84C931313E170D7C194A529714B8785865289E694B8788C8CA2668863984CA5294827E5486F1A2A2E18A41ED3E4D8A918F4FA16D074D427D274D8B0906843D14BFD1421309F1A5294A2A4B871948904BC0D3424D8D9669950996B084378BF2A264C2889A264841AF85297E3A1E42647CA6213092492488E9C4ED839B34682797E0EA636B194FD8D683655B1B7890D329A6443FE7D90998423C22C22C38411234342886E66E488995A1E1B1A34317159B10E5C346F3BC421708A368D613C8F21B2E170B928A36563652E17E0F98E0D14DEDFF001BB35F8062BE74CA94446F66BA35175E06ADBFA06F76D7904CA2EDA0A955958E488888BC64D1514D1514AB25C6CB06873E9E835F24F92482DC53675833462370E4852E1517055883E3099B1B1B0D8D9BF89D3786CD9B368ACFF629597360D94AB26C3C68889F0A2DCC6A62316748513157C29468BB2E78855F46D40129A4DA12265422119CD47D8950E3854DFDF4BE0487783718540A841F578684C587E91A35E305102F67242E19C5A1CF21C0EEC5FD85E02D9722D3B495442AD12F7CCE51029DC7061DC69DAD9133B68D1AF8733907F810CA3FCC8685B6FC3C0A4DAF96B422B073E829F7E31B186D07A6AF8641654AA27E038007AEBFF03A1C6FB582D44DE104C422326E72232870A96138B62C151A2E17E0AA1434459B92E46C5F452E378B928B28D9B27C694D14A546B0C124AA2B0B73A63D293362686671B2CD88B8C7A836DC1EC7BF4AB63CFA50DD9BF286647CFB1498E051C142809CA16D343B4560FE48E8C4EE6D3935F01CC7026D9A7C089A9CC4349EF4507A9FB3AEE32EEC497820C57A79E881E5B1C1AE2F053691A60CA84F091CA34574C6DDB1B468B8B4668D9585215327E5C03FAA2DA5D09DD29627B1BE336273789454D988DB511AA5148516AC7F634A7621F0294CAC464F65F7820B78A1B1B28D9BC20486CAF07957D957C719B2B64642628D9B1084C32B08B117F04BF314B0B275392ADD78186D4FEC6B7548A85BD95BF80521602BE487FE1F4C57375E5F63AEADF918CE991A8AE2A1A1925CAA12E6EAE5311EA6316D1BD47B0B5217BF3898D894259D1A9E06E682965A11A57D95E51E19759B4D82C6937C51A361B1747228123DC38BB5B1787F0A4A90459B6465DFB18A1385B0F9342536E5E46E47E057E86875AD15A362A56EB29C47CA1EB763A1A7F3C5A113682F3451A4D3E48466CA6CD919BC365657E4FC93D93D93DE61F83F0528A379D17E228985FC50842111113E308C86DF425723D2D3D613DA20408D4A2ED0CAAB5F2E96D7E84F6176523B0EFD9B7435A2E289555160A209C86ACE0B4D44AEF63D7D95D0EC67908219DB546DA7A1611A16CAF21205754416D422B57C5E8A25AEC06ED5FA1C54E7499656B475214B921EC57EC84EF434366C5D47577810A6DA4EFE46DB7173866C36913E47B7FE80B8D2A2DA56E3F03A8585C1D8D9B366FE3F83F07E314D909F1847C1C921A35FD4FA15E87D655CB1D35075039A5D0764DF236BEC872213D8C28ADA13686A334C944C13DE301C8B109D1386CB62086AC60E0F4727E3C3CDC9B447D15A150BC891727B0FA063707FE834D0F2BA29E0DBE726EC9347D9BAD34306AB1B835378AAA1089C8E5D0F75CA230D33BB8AF0269F74B1A7A4FF0091395BEC98F39343E7E1D8593247E4B0BB5C6C8BF0842133B366CDE697F86E2FF35C5289F76CA57C027EA81FB859AD0AA70FC9D167244F79AC6E4422876E9FA09374A4D111914F2172109ED143D0B12D60B83F2125E0E0D8B190912131F612313843CDA38A57EF10550D0A2D85A17D9161CA4D0DDD10A9ECD9AD8DE4855706E614414B3982558EFA82EA2963B251A682734509971EF88F91F4EE7AE4ABE76074BE5437D38E48BA32D47E0A6A4AB6F5F06D2E5A3F5AD44C04B54D7A6378398FE86FF4AA5B7B147621AF2118510C767B2970B4699E824A6F0EAC6C4A506742756D8A762EA72990968E0A7C0AE0A923710A54745B9AD1084BC1C2082A29C8ABE06CDEC6D1046D0DFA0B68F582A8253BA3B2C3D0509A5D0C4434BA3B20E3829A19F452DB2DECD9EC829B4EBC0C46F99E8A48D948C46D65DDE8E40F2CEF7CA6399968D32BBFD823BB7F939F3C0849B5FC819D7295E188B0795D31BFF5ABC3F8DFE0A5FE92FC672E4AB0E58DA10E0F14B0A56223F0460D9B204F050BB94B9D963DF78537A26C43E0419C14A5172517C6781697489AD90DC8D7236C4CDA21363FC23E67C942F02BE82F4C48B4CE42653ECE5098D31DF47353D31A746C911D950BF660367B34D1CB91B3DB834C4A31E9FD1CF6A68E774B66EFA2F6C3F0890DC67E10D312D13EC71E6D9BFD825DF057219197ECD1773CC387FA0A7D47B66D660ABBAFBC7922CDAF6BC0DFA9E4A1C7C9FD0CF5BF67AF8CF5B1FDA7F8156AC6BEC7C4D5DB1E222D336CDDE2FF00475B1276285453584350D4CB13294B148D4D4D72311DD1F958505116118277450EA7B2E28889A7B2A0EF0E608E04EE5852D0213898CA31FD87E93666C3557386C5B6F1B87E0F41363312887E2C5ECB62A9B947B7044DC9D68DB4550ED68A085B1781B175E86F93B3BBFD0884FD03B6557B843C8D9AD31BA4E3A19DB122EBB12689ECB938BA7D703E5BFA5766B6D0DDC7BFC04767DEF0693E8DC2513809B562FB7F71FF00DE2067FF00667814FD329AF67BFD87D7BC6B46B9295A5538E69FD8BE69A1A6BF9EE1141D9472D9CACA06CBA13D8F6CE42C34245CA199696132E2E260D4570E48D32846173A3817E8E06836E4EE4FBD2295E961AAA5F4F432407A1C3828A18C6E11A39258F7D94C6AE972150FB0D3F247871F45A1C1A5C953DA4547EC132A98B74413B8D6AF43E55E4586E2691CAAE30E7474B94D790B472F861AF374E17F723F5C1FF8EC48B86D7E59AB9FD82D2FF90DBFD983AB47F637362A784BED895F60F426BF43ED86F237FF0044395B52FD988BC42C78069A2CC479F232751F9433543D05BDE12C644ED9A37189CCCE9B12D527A65CB6D228ACF65F1F09BB36702F6483429831D1459FF1943AAAFF00BACAE85CF48473F2D537FF008D209C7EA45B47E055A57E1A32A90F2DEC6A2D434034FAD3C5085B15742D468C6F65C2D7451A82281C3DA25C4D11422ED12AA33C8383283961BD315CD11A1A2D04FD8837B18CB4427ADE0BAA246F0BC09AD336686C3EC6CF470315ABB21257B46BBD99A23B2A4EDAE4ADE1ED0F6127824833A4E2F436993B9CE825AA41A7B0A4687CE49BF4269C7FE40D6F0C6D1A07233F468AC449F27FF47F617209A10B46935B1E194E6768704DA6932A0BECDB352E7A29477B42B587DD929841E2A2BA5B166D135CB629D27A62061570FD8A2C6DE86A651537C374128D5B595B3416E2B9434EEC785CA72AB5E47E9AD3C238E5F2EC34AB4BD283BCB838BD875482E04B70808DEA1D41AB11F2055753DA7FE19B3FF3923BD678620D4498788F1507D8C4F07991165EDB2D687E2560AB386B9E4EC27BE84CE46C6EA9B293884AE4742585E547B15A43F5536907BC6FD131AADB7C0D46BE4A3D713B61BB4C6D2D1626CA684BB130F4BD81B447621AF5EFB0985E1A2EC97B26B70CDECA27391CBE424D2E75E4D0390EB51913547C1CF5A640490DF424AAAB8F224701EB7EC7E543D271B203C84B8ED0E8729212C27022A190A5747E818C019D62634E43DAD0CD2FAE1E47AFB13C9446FEB4724AF9A28D09134370EBA09179109D4C72DB891315C06BD31F72562D1E86F1C32EA7943FA524668C72D350DA0984868B5D154447BC15EC7A807808A2C53B14D9A33847A487FA42088EBB826A09F43C0A9CA66CA8E97A855B71916DF49BC5BC08F27D99F9C5E1BCD65E023FEE9D57FE43991412FF00847B1BB257D0AE59315B7D0DDEC425214812E9ABBA4C09791C955843A816D1B34A5ADF23A10D1C626F86E517BA366E563A215E06BE05A1CDDAE8556A412995B6A0DAC982DAAE14927B58B05E56FC0CDDB62634E7F93A02D0725C1E118D56846C441262F4E86FAA104DFF0071A1ED07B26DB4A8D12E070A1A14E3CB14A8F98A8E8B48E729308AC24F892A7172F812EDEC421C2A6FDFE0174719C38C6BB06BE20946716CBE8EF3BF62FF002793F5F9D154132131084213DE2D4B42C442D237C62E0A0485E98B51C3229343E99BD10D742908F6C8295F47580C2CA5DB3F2588693F2696D8DEB53E5FF07196BF130D19729F2D9BF83C9BA4B649568A7F75346E885CAA923C8837312A72FCB1B9B3FCE836A5F6DC72629FF082173A4F6CB25154D6FC31A4F1D9B0917C7A1FD18BF0235A7EC8F3FBC42A893C8D06754B8D172626F286A84BD18E4E0B9AD3175C9116C37222296FC2D96D88DB90B64155734CB63850B89C0A5A89AA11A68A9BACBCEB92ADB7376329791DDA381B5A1788AAF053C9121F83F06C0B6DBF6FA1D24FA43A068AFB2D32D8CF051E0F51827F66080D0E43FF00B7717F6BE45D4BECAFCE3F044BC0CA44317ADFDD3D0C6B65CB0BF5E04B683632A209A7633C0FE0DFD320E42C7FEA8D1C68B85294A8FA0A12F96CDC4D08DBE81C06CDC3344E48BBE9F0420F696A9249AE99B85CA1163AE06E939BC243C3F69A8DEB5FC072A3EC8C70D27A47283FB65487E30B79628513C8EC6D49084171677C342470CFECDA4FD02BABF07981D256FD8FF8C91CB30D72CFF232E856DAC0DF406CD5B742721C77C116137E47EC4D190D5C86B6CD0E234DFB1DE34BB177186F26FF00266FC6057810C9AACA5F818154B0F97E4B14D2A8CB2954A68478A577847C2EF59EB34706BC157812A749B1F628B53A04FCF42B704AE857F50875475BE84564269557C90C4444BB91721A8FE130826FD27B6369BD109EC84412D2BB0C72D0F087A7BD08B31B351F499B65B1C46D0A04530E7957B3631798E9579E8D67F06BC235E07249B625A7A515BB3D8DE31F928AB6FA1A15CE0F617DA17D9F62469726A07F428AB478F035AC7DA2112216F1CFED8F985C905A545762263D7442D1091D967D1E6BF967861468DF427969FE43206ACA5C9C71FB16BB7B12E76F3097C9E934CDF4C2482314FC2DF51E447A0F49E9443A47A09249211AF234BC9A344F2279131CF3279E091BC2DB1D4F92642CDD362293E7942353FE31C842D06E7E16C7869EB09E8D3FEC5656CA5650D63517D06CD645481ADFB48B28799BCA1BDFE4A5B12C038D2636BC99696EB1F0393A3ABC95A1A55F63AAFB45AF717B123CD2E360D3C0AFB5EC6B57D26C51FF009639C8FA673CCEE345F8037FF75A8EF8AF843867B9E38E084D6E590BAE187B6FF26ADA364CAA2A8564840E498AF0B4C5FF00383520989ED83CC1ECF51E827810E1435E0ECAA94A8A8FA93E0FA0C7D0BC836766CF614509B2E17E40A565D159595E295959663EDC19E4EB5FB04DC474B191F847BB5194840F7A2D88D93C8BBFE803D8D8850D6852A5AE353822097D7235308D7627285D8D5849164BC8853123FF001A20927AE8737603A1EE93FE3062A00E8EA1D7DBC8C5A7B105E42682382FA7D8FF0018C5953AA0EAE8FF000707129E396529D0E7B7EC9EC490D0D8E3387D10ECF0EE5237CEEBF4356CD967799E03624709E34FA423AA36C5C75FA3D92F844CE90BCA8F65FD1FF9824ECAEF63D1FB097FF73638B042DA14192397099AD7AC8A5F65F65294A5C294B8B8A52E2957CE108C8C8CA38FC85362874A643FC7629351C3F2418A3B59C097D0DF74554A0B7A9C96B66B2388F48DE8F1674D94A7D8468DCD8E08ABCAA86C77913BFE822A50AB1FD7C8D35C9F53510FB6C523568A05E4470C1F908A2CDDFE01CBAF7077A687C61EC4CE19B950BE54E046B4E5933C0764FB04855EF2BA1817F004535A38266CF79291C193E45A9A4332E53C217404F113C11AF05C970BFC5A2A1936632208BB672E974292CF86BEFE0294B9A52FC35F36BE559BF8D295971D8FA1D42D8372DE48FF00006C7529E4866A4E81E442FAEC5B6F625DE428D7097D93783B798437AB4A4358CE6633FB1757F5E857BBF3ADB0AB9D7DC8D193C6D4E96868A256CD8995CBC88E383F3108D3D1B29EC423BC3B884DF43E84C7F75538063D321754CB86CEDE58F9CCEDC4AE18DF23DD9DD14BF179B1493F8A9480A28A2CA28A2C17E02D3A832B5BF62C3448BC0C3B7B23364FE96109F0FCBE5CE10E04DC137F472CBF334D6AF9648D355E507D9FDC0F622C09C5B7E468D9684269F417ABFE050B4436566F1EC6E6B29DB427376D6B5BF1D0E34A0E3FC9341FA3887A07D5C896EDA85BDA099151885275CAF4242B7EFCBF421DE3F43FBF9176423436BC8D5D45F470CC4EE31CF86774CED86EC6F1193E27382314111088843F18689F30261F43E87D0412F80D088820C4C42626263F243BFE0BF0E0FEDD8A9C427F43FC8BA1213FE48523A6BFB19D0DF4756945F44539B7F67374513399CE0AD3BD62E5AFA3C9F0252E874EFC86974683398C3EC58C6F14F50ACB6563BC4D8636A7C9D4BEF937E07FB5FEC77D0E16368DA14E2BEB66EF0C9771E82E5C9F22C3E8E87EE93B3B3A2D70358EE8EF8EC07484F83A5294BF1D111A344443DB1F83F04CBF6C24F1223C1087243BF82FA3F18868835EC4BD930CE7E1AC7271F1FC11B126E16DFA23C74BCE87F6ACB59CB1FB447F82CAC5C2717841AB7FB091F421270937E87D44EC1B7F637B1EF17CD39A279703D08B4BA429ABD0D4EE7D952743713D9742AFF90D770EB46D670B66C8590FD0D4DA6CF5AF873C722ECA6BD08CFDFF00107F399FA42D2B9F097F604B4C2FA1F817E4DBFE284B13E8471078D8C7677C367D8FDBE12DC5F9FE0FC0A9B2321F620884D151514A5C7052FC77E31BF87E3E10989F0FCE6AC719E44BD8CD67FB0D2D0CBABBC723BDFEFA14A2A7D05406AB8873B18E58E78E95E5DFB3B97D99EFBF91CB5C8F38CBF0C7FB63123FF8830BCBC84C49268A357FA9456F968E81C361747D41CAA5D9D68DEC616961091E65457076AFE019DA7FE05C6FD04B5F2BFE02BE7EB08751EB59493767FF0062FDB4BA5B653E110D97FB84C12E436D339461AEC63C34514466CDFCEDEB34BB35E4BF0EC4898515D14564843BE4ECFC9AF26BC9AF268AAF06BC16609970ACA294B94DB3613DE363A2A26365656349F528EB9EBB33648FB01251EBE11C4077B3B41E1DBE84EF054A435C89F7FA0F98E17FE9153821D31D46FC9B168A26262D3FDC10D58FD383DB07C88DF83ABB65E46BC1ED1B94CACE1327328916F64A12361439B57D097D05C6FD224092568D307D051DFAC1C5FF0024F7A3663F2C52DF6E888F71C3363DE4AED9B765FE09870876517F30825E8FC62E35DB35D144FD1589BC77C8E79348A8BE86D89E20B1563815B1B7E04DE09C1BA226661B5D63A2108447D22F5CB36CD3CFFEA6CD9F88BF435E2278D10B7077036615B23ED89DF09B3CA1EDE253B7E8AF29FE8FFC509D2E3FBA3D9A1AB08D3B6FF51DA4FD2120C172BF6553A31CABF08369748EC4387A3ECA763D0B5B60F74DB0BF369B38473A12A6A243D88F50FC0917ED4B4885D0D0E425C9273B70708BA83B517E87A6310C2A33FE1BFC1D0B1B58D9D10D1A29661745C5296FC6ECA73D651116210DF2697C54B95C97653F234FC89142BC08E5BF05EC9B212ACA155D91CD1AF26FD95BCFEF17E0C78D2CD2D3CB60CF215FF742EC034F2525CF12E1EFE87B548A2269F91A47FB052F1FB29D13346E13FEE14C1689EC75C073547E11E53E88D4A9AF504A9A08BC68E37B85079684C3821F614D725B9D1C74C56E396A25F497B7630A6BA1A3BA9069E46C682FE1EFF854A6D8517D9A294A5B8EF2CE8455884554A88E8BE4D14797E4BB1ECE14C1636E99058A26329E8AC48B7F7449B5E7B323A19BC2E3E842628FFB31B74270E28CB70B765DF10D3912782A159BD28395A2FB269ED1227C91E887B70381A49FD89F6FD12CD61782E94BD322AAC7C942092F28FF00387026DC5B5D21A2297C21C436CD9F2636C5CADE3850CEAFCC7876266ABCB8712EA45BF22A375CC28AD86F131930822062CDF83F055FC69FAC6DA36744C4D908CD13C0B42469F454862E88134C4846DB8444A702544868D75F14CD7668D11964A7A21F43F3B0FA42B5F72E884366528D9EC4AD7DF687CE4DC14E31371DBC196A32F13B4AF2B68676B617B2A389D7785166CD3E495282DA8B9B62EC2B6D6FC32BFB435185BF5343A95F67992BC742BD6E59A6DB14F820BD12364378691253973758C3C7F9DD1EAF78AE92A7269B21DD91CC3F213147E04C21084213E081968EC5D2CFA315415E19F564612110E31CAD222CC22C453C78168F8E05C6123770FA12F62E31C0AB39EC72C350A51B13C738A6DF07D9AE8EC6FD09FA2657A17611D2474861861329465FC0675D3A6377406EF827B1CF255E885BE9FE8BF8026C0DF674515914938795373D5A1AF1FD9105FF003966487E4ABC6741E9FE443DE93E8653F863553E28BD146B81D35B1B8E8F20D4855FC06E0F6240A316FA1B9C1BC2E6EF5FA1915A380710BD95F84365329F4DFDB3A8847857E068842130A288412DE21324F813C548D862C3E04C6DE096CE8884D08B452611187D28A71542D1CE91CCAC62B62637792F436F81A685888E31A120A698DBE12202BC1E069C60C51B297E1E9C1535E849EE7D9FFAB1185C6FD26EBFD9C4BC49FA22F0BF46CDE1FDE2EB92AF255E46D7921310299BD8FDE3D87A16D073B2AC7581D1A39E04D9AB7027988E8E0F9DFB04271FB95A6EF6C9F07F787FFE61FF00C223C7E924510C63430C30D13F81897F040B826C46C4309313170FEC4B468E8706AF1A474A52FA35E0FC117D8A2108217D6189CE8A559D0F45CDC242FA1379618618A51B294A52949BA8FC0FBAFD13C91B1BAB7A3F2C9ED93DBFD91EC88888BC1AF06BC15781B28F46C87DE3DBD1A8CE444537C16A89BD0DE1D7FB8E4867682DD174E5F5F5FC4C6318C631E19BF9213E331D65B2E2C5A56520BE0ACD90D90976344D1C12BD9A498E7E0AD3917A1B2766CB3B3F27E7144990424BA9780F0314A3204FFC94A52FCA94B8A5F9B6318E31F64D904F4476642FB8704E21281CFF0031C8C3414BB84B37E4DB391D64E178138D618C6878421308420D6119BC7E33A1694B8A2D9CE52F26B3C20F829D1468B2DECB4FA3F24EEE6339357C9F6128DD5C0B7D11D0B5B706887C1589E536C1878AE68F6FE3714A52FC77E191E3F2BF67FF00607FF6C3FF00B91ABFDA3FFB51FF00DB893FF9F0254BA5C9A1A0DD6104B25DD1EBF04976A12910E86304715754685649796C4AD55B47F63287380BCA629BEB77F06A9268E10DB4FC133FCE5F8FDA7FF588F2BF78787F37F1B9A5A76437D1D6CABC89E87E88F34A278DC37487473C1C1C6573A2FA2A45D0F584AB644E050C7E06CE4D70422D68BBBF18D071DFF439EEFC0DA03585AC4D22FF00EE07E9FD8FAFF79E126FE17F8C03FF00A429FF0052BDC66545B7FDE53FDA3FFBD2BFEE1F907DCFBE14A3ECA5EF05944F7BFC1344B4872D31D63A1CF92A69A1B75A565B81510349C9F78E831B4F0E0BCCD4DCE86E2D06E1B112CD4B9B468957C21943433C9C16789B3FFA47FF004096085E663FFE88F62FD1EB5844774DF838A17FFD9FFAA7B3FBF8E067D46766CE887054789B3294BBD9A22CAC4D0B5D953C6DCA1CB0D74410F98370AF18772A727071C157252EAF254417C73F6C29AF0BF446E9FA3FF947FF002C7198B91E365926BC15F81A70C78D87806CB7D1FA0651BF90F0418887FA8637FD8447FEE347FB0E1D28CA2102C4C7314F9A49BDD1134BB240C9F11BFB0A464391CB2FCF8536994C0EB5A1574785E3EC4C6ADE99AD03678D3352947FC0BA637F2891A1B582AC69A2F8C4A4D9C742C79891C722D6CB9A64A25315154C9269EF0C86F033A366C4C2217C8D5C68BADBDE417C3D6992970A7D8D0DAD63F37F6504D80FA7F69E0168B83F104787FEE478FF00327C2FEC970821D443B06BE06AEA34F2BFA11E7FB4ECFF00611B7966122D5F8B6CE0A645AA2EDD7D07FE68F489877B36C0F6C9A8B2586C9814E87D63EA623E01F743FF00B01FFF0038FF00CC8F7C8F3FD62F53F47A594EB823FA12D3259C4F67EC7FE0CFFD19FF00B339176267E04E9B26B64D1DE8EB1CB1A90AE1B7D8B4141044725DE1B6B1B6A1D6C9E49AF4690FD0F9075E85493BC7471CB2D5C9BF2342E05A27C9EF1058915FC380A5F80A5C529DDF8363C380DA3D659F787F17E292762C88BFF927FF003711EB0FFF00AC794E23D45FFAB3DDFBFF000EDADC9C1EC61FB3F0B33560906F48F5CF43349EDC34298D2469D1CE13252464A448D745C5782B38DD3786F22C18CE448DDD0DB1D6702D84EC7A1E46868E85A6588610FD84C3CA7B04388B4139457F2A205297252946C6C5ACF844C4A2DA780DA7E0133AB28A5294A529714A5FE67FC0F7E4884F087C8B0E08946625D8A26374518FEF0B0DD09D132ECBA126D725CBFEE10A2D7B1946ABE4E0A35AE46856ED687081C16CD1E62EC362642F61158560499E3E03EC59657C15294A51B1B18DC766D3EE687A121131B5BF434E984A9ED945214A5294A5F85F95FE2EFF0081B1530D94A128C79D8F3EC3E4D95A70365530F46CEB2D367441217D616B34FB385C89F217C951D0545DC8DC53D8E4269BD15DE0DBD1A7646845EC208A2BF8A82E7FC101B186362E8910BB0B08391A8F0D294A52970A5294A5294A5CD294A528B9CDCD29CAD949A424BC1ABA1B2C2ABC149AE4D8E9609E3436D706CE849BE87A2C0AC23B946C4244132A2A66C84B9137A413BC2C6E223487B64428B16DA701B744FC8D7730A5C3C67CA105ED8A2EC5EC2F72C2D36CE5F1382323C55B604D1A36529CA3B10B562C8A52E294A52E29734A52E6E694A5294A51B463C0DA413173C8CD8F6D223383AF6703626C7591CD1A954E0D4E44DAE8F676361336F82933808445E0AFC17C098FC0A13A890A5D412686BB0EBE0DCD3D9EC1E87B362E094AFC8D26369466AF05F43EBD42EC2813424F3FC7B832F11AC5B7F00B78534645F028FEAC5A0000AFB10F4136474DF0405832A454D8D091ED8F65366D94644B0B46C455DA1C644B44E2E0DF93821B2B50E011B4586C885E1852963422421EC6B3D9A3422F2221899B7628E468D748A2BC60BC12C7DF8248B8A23B6CFB11F9C218AFE425D07C0DAF8B3578669FD68F04F407DFE2CFB931FDD8A9702D46C4E89D253BA8DC16C68727010ED1B086271949CE05B6D8E2EF0DEC8B91F81228D9123815E84EE9C0BD0F443BC26F229EC610EC685C7236E1CBC94725742C13C16F654B45436B0B4E0534592ECA9F4347C0934433621220DA3432BB8567A06A86311098AF86917817E31FB9F727C93E7E5FA2CA28B70995BA67D59E93BB17ADFB3DFF00613B7EE27743ECFD146160A3566D323D1C1D9B223123E917D086C74DB109B3828F6C3F434687C68BD2374DF472B673E58E3826B43A47A6F0CE868DF9447C1C72F0A56B61B4D1358823864F05436C7BEC4B5B382AC46382342BA53B3D61CA27B1F93E9B1282E07D1BE3E049263DAA4F034F838326C241E18CAA30A99D5F90F44E6728D56B6762E4ED22381F449F53C0561454FF0041E27FD862BF73DE2BC8D95E2130C565698F41631546CB672E0452FB18B10DB12F4488EC6C6C36CD9E1A6E891613A6C6A2D1A22ECE027E917C886C778B84BD9F93852C650FC9723578139C8D0AFF00056274A997425ECE06F336A2393412B63DF4518B7925947A1A8906910470582361C2217221C39106A8E03E8832E54E59953FB03ED645B63E45F243CD1FF03651A1A36245D0D210DAC44D70682435B1E843644693D09CDE0A33487E0AFC637D147B3917E70557B1AAA8F6C7A1B73484D393845F08B16DC0CE21C07C908F2C1C144C70532AEC775454A5C1FDC5C1E2FB2242D93B37970A8B8E86C28FEC6FC913210D21A66A88A2FC1C098D215F4712D393B9C0685652E39F81C1287BDA2CBA30842944F1462C7632E2109313104A8E11CB1F80B4C24EF272685C8742AFA18D1BBD9D1763381AC7669B139B1DF02158761F81B2B2D12D6CADB64F66AFA1F80DDF58710A32BC89CE4E4838894FB38E9ECF0426CBE848BA3436D8AE942A68863D8B7A12DF03E783AD0EC3691C93D8B420B66D1DB9103E1AF43C96558B443816BE0D7E258D86A3C55F05984C41220884C6F1D0B63294A26A722B6D15244AC7D898F106C537B3C021B8467A3E917F041086F4339B3704C8348AE417B1AA30B7C1C3684DB1D9C8B93A2ABC0F6BC110F7D0A21185608636285531E71BF050B81B49954D8C54A05C60A2355E8D122D763836557C19C9B406FA45D955CF014B0875C31A141F4C82544822943E6E87B5FEA228729610F089470349E2C462D3324221DE2970B929DE107C92E54CDB1CF468727B07A636270740913B2A907E8A3A2764F222F3B202D8D5A1515169764A3D0D36E46490D2139B457B31E9E90F6781BD857655E06D1C3456D41DE96068D10E0A27D11E145B424E38C5186FBC3951D6F43A22D13C0CECF02C3D06CD083C8BBC0E94DC43D5687086B90685BB722A1AA6E7091BD3C9043D114E92A6CDDDBC885EEB23D167260910685A63161E6B46A108420D1A2621A263B3FC8F9C3CA0B5AC44BECD91E46412F047D912F2298DD3966CC9685BE8FC86D8D7744BB173C9C86212F34D2E8E50EF9341ECD177A1E85BB1780D944D323A426F44FA341BA45E069BC7A9793546F111C9A0E1B1B83870304A765D15A0F6705F42469B2EA1CA43E7D0FD227ED386DC2D190FDD397234CD482E3F016F81C3B47EACC6726096F5C37F09B11E234B58A13A7A10878E84724CEC3A470B2947585C0D0910B1D29F0B1498E4E8483C3E7E1B29A1195B65468D09396847D9210D0A70346AA0DD381CE141EC6E1B21C484DF0E083A396084F646E3917972226364855727B2EF62422E84EF2CBD9B3150DA97C9C36CBAE0AB816D1BE0EB1C07052E86F4553FB07A179138E0E031B50F471D8A91AFD9C431E8DBA5CF0C84E835157E1D8641721E04C92297BF3B2FBA63E7096C4A555B842DAEF96F438409A10D1A93B4BA2E348AF1F58A5F821311121DE78131CF2442E481726BB3C478621EC5294A88E7A63E872F1AC14431B0A0E56C5A3EC5DF02689E6105783E0688C5DCEE21E9A13229515B2361FA137615A39EC811468D118EEC992ECEE8569F036CA4385691D23963DB45AC685EF04BC86A1AA4D704E86D250ABD97A87A2958DF4426C49D8245B4D8E8662321773955BBB117929A1DC723A96C7EF0DB46CCBA6EBEA055A4C967936049FEDC31EB672697054246869746A085F38A705CF585ECE182D06B70771C931A221516E887072CD85490E23CBA1726849534B0DB7C15F7C8DB5C92D62896E9A37746DA137C95B5C6B096FD1A28C27A3644D9AE0A9336232B9095610D96D17C325C607B78AC048D0E7B1BF078A5DE9CFB251034BB470351868E9B3A4D1AAF4467024E02FA1B1265A7A37589F8C0FD06C0D3E45D5EC6D89B3D4727E08225F6327EB09F833F302B26C617934F1C2D0F6B434F06B289917920F8C20D1B3BC68486A7268690DCCEEE0F0C4C6CB08744642F4543ADFC217672B649C3912AEC7A68A2AC1CEC7D427AFF0081EDB17073BC8D1F42757055C8FA0DF62D92B6D615BFA34BA34427B3A29A5BEC62E61D8A8A2DC6CB63F0C5D684AF9435F248D3A4A544EE8DF65AC7311143C057D034F036BA45168490B12243DBD12974449422546E56C6DF089E47A189854D3458C6EC126DC0DA5A1C43D9A45C68CE4520E9D62F81B18B0738D091E54BD60A51C89326C4E0A04CFCE1A2B6CF0193636BA28C27AC2EC6E8D5137CE88E70469CC584DD2395A2A2174552C38703F2472890AFA34C9172726BC15BE7837D152B3455793508BB2299F444DD1763468889B63DC7A0B8D2A4AB78242A6CAB8169ED0AE11EC360F66D9A1A10D83F71C416DD95D16C728F63F0E0A2D0B67E0449EC9D8B9A5D106E17474098CDA638412F24A2D06D5A5F421A0917E11318A17672D0E3226498E457BC1B1D1B82D931B64835AA4519B1386C46D15CA5D88346539B19A20E50B50F586E048D1D46DA1E90B6E316C62E4E043A1B70B1EC7286B67B1742D975B1B38D0AD88BB42D8D16F63E873A249722D0ACB4C6BC8E75A1B726D8BE4542242A24DB393BD0ADD22764341362E06B7835E0E3ECE59B960510D5EB04F3825AD724763C3E8698872FD1E845D9A4C689B39486C6CDF8383B86268DB18E4D05A6374DA7A1B623C862C33A368EB46E9221CE8A11D6F9215846B91C6F48EB6341B86AC71B151CA4F285312EDE0B5F07230F5D0A19FE448D2545C53B3634E0653A62B4F62D3981A6854F911F82286B83842E0D746DB6DBAC8E09796696C2623663DA0E2D08E06FB3934D463E01BC1B109B1B0FA049E87D8A9F7B2BDA3C10914DB97B13557223D9A2B8337E44B62E456E10B4CE0246C84D896B1B439D31698DA7C1F67B174E46CF1C1C113DA1348A2E865A8E846343268DB342950CECFB34F66C98FDC69FE0D0543E06451B9C0DE0D8DB1828952509DF14AFB44F91244196B34389F235437BE44FD8A08A781A6F82C47E09D107F67B1A5EC7EC25284A13D0E8245C92F46BC1AA37AF63226C459B436625DD2C7A37506D929395233F284AB3517D0C7C0DFA19D3395AE7C895D51B654E217687AAFB2B63701726C52E45F46DA1594C52ECE16CE65101B1E8B8A549977C15E0568DE264BB374DA16CA47BC249F268765DE1A14B09DF07A0D1BA2A7C8D1448A46D15B7B43A8B55784D91B513786847E0499A16C354D15A26CB0D98A4ED04C7911CE96960F81341960D0897793B1FA2275B25188884315D0AFC8A58A2D22421A2907BD8D9A629ACA21B27A35889B3914F61A1B4F91F437B343A509D636977B1BD5627DA221B21C892B437938908F63E83DF0CF67B2FE0246FA362F67286484A34849A65944461A8F656CD50B6C4E842651FA3475B231389C1ABA34824E0A6AD45A8DA1ED0B9135444B7051A34BA12A1268FBE44A2391762AD3D1C7B12F648466C6B0B835D61DD90C43550FF00456FC8887B792DA17E44827564ED3D426E8DF80F4C6043E8490D98ED1B227414FB39640B470FB2FC216B10C90241E832DBF446C48B918D06B9A4289EF16915D8D8CE56C4FC0D9EC3EBC0C93A3A156362AD0A31A19A4DF06E87A09F6482CE5511C966C6C721CB34E0E82538C69DE169D72348B426DE99210F91A834F5D9CC34AEC8A3A5EC6F83210D05A5C92BF441AE4E8E79124DF307C8F9361ECFAC2DF64D8F9D1CB45220B61EF6462D15E44D6F9209222371F83616B41ECAF150D3FC15D136DF305F7B26F912E561F912E5DBE8642E2E1808BA3B6110947915A1367905C944F46A6D8B6AD1CED955E30A2746A2D50DE86936CA0AC983651A22435E0F03EA3DEA95C527B4634C3D4D34B634A41A8D7257E5917824C8B7B1AAD7069F6C492D1AF786C82682E3657D0DA93069D0E10DA68BA3CB05BC0BC86A6C238137C9B7CF040E3D90C5EC316D090567D0F71237A4341A21C976744D69960D5BA73C0976278151309B1A14127E4B06CC7044C53578186758F18BC171876703B1CCEF15D88EC7519C67238635C88F38F20B8391CD8B861D1E07F09DC5C62B2EB0B965BBC3887D67A0B81C163B6082E43E302E07711E07FF0027F80E984E731D8FA3A9D1C0F183E161E85C619D9DE27C9C8EB043BF883B3AC7019D0C2C5CC7D61F43EB1FFFDA000C03000001110211000010F808415958225ED2F2884DD992B3AD443345CE6EF516292146695554885B7106DDBA8894D504866F2B22298CB24074654E62229FB6C049D768AF2DE77B9E057295316D2C37D1372C92BEAE42E0EF9909AF2D4C035188A5BC2ED5BC7086AB162383DE0F82BB7C6A5092B6D8732250310584D604F8C7B7B87C593805ECB6A258238AE7732C0B574858A13929203C3E76E5080D63ED208E6334255F8B3236274DA824A51E56421CC2B3D82902453CC704E16F5F99890A325ACAE6164F8025A1BA5574C22954A04B7DB1D34772F33C226873EE478926034956FE1A52B65F8D0247DF6271A18B8AED82673E5A0B3080C31D80C54F67E2AF24EB4E758B282E8681C1589A32E79F2131A9339282D852B6A82C0E128DD1EA2B31A1F8742152A62422A4E43048984041993EE70C8C09DF39BE5D3841D0C0B97867A290C8ACC107CEFF00066516F68CA00C7FEB448CDA84E41C11DAD88033278EDA42E742D01CE63000F4D098A8254B02A03769AD8EF600B122ED50AFC50E62B025D9F20D8BDE14294D12B0E8ECC15CD4344A023A1F22F10C0E921A580EB45EAF4324EF46A623E885704D6FD942F8A97E16080406093F0496E1465AEC67295FE548073138329E3BC95870C5A7AA0253091EC1E6C164E46E30B2E31495FB970C03CC385ED9054A1CC0C27927D4E92EEF9045F314B9C42B7EDF2DF24FB986C92904ACD197609C823EF9194B8C3352C8F2A35DD472A517A663287306F052AEBE92270C0C96B4A7DF0CB285268B695E8A6EC8061251592B385D45FD7948001317402A2336B31090A2E222A80ED49248618D6E31E3AFA76E2B9C0D84107613415A90B7837C72C2DC8C5268560661C3D3E639B5D0A140C160853094BD5E1C45A15A68D84BC464DC0378E43EDA4D63BA4AD888CD16CCB49509380EFA38F48129C29EC08CAE38E74D0B906B8B188250998203D7710DE84C44E11C44129E737F2031D7A4449392C964391C5F7705FF00F0E455049406C939F564BD95D504750866D26406015D4EB581CD480D0967BEA59624651425D86F630660A5D9967D668B88BC230C03353CF2EB5C356D758593A50D90DACCDB1F99ED3B9AD011868331F07082A047CDEF80189A3F830D8010DAA400ECB48405458714612AA819180000C74CD06B4E89A110D15C9D422CA0E6004C916471EEA222E1174A5F083630CDA66F4C39872A2996AB9B410C3E7AF6DE7473ACCE825F2F80788939F009490E822DE8831F680BDEC36E7F1CD51930656042A188642B2C7C63180D28B925C540E5AF61DBE882DA7AE14C040E9EDB6C8A29075C635FC992074F092FC310FB80A6F56A02D65E4E0744D60E0D4B19C3FE27240CA617639D5FEF02E03E9583854F364F5FC45FD3B090F80C0A5F12C641980ED4A2A07E104495BB3AF145BBE90A6729B022D427F71E33CA1B59C91669E48E2990AEEF678D91D205BFD281F6408652570561261597311A3A40089122040BC1D87FC653546635975D030843C50A729F4D4579E311863FF77A0B00AD467A7D92BF060014C47A0F3374C13D6C94267B229D1340271E8C07040E8C0D410F886B9619DBA84B9B45A94ABA1CB8E47F4B2E916B64F9E5337DF0E25286A241BB8A333769176DC866DE547A211F0D7C6D7D91D9D35B462917C4BCC68B0807F95A3A4D58C5A4B545E470A7E06974C6C569446BF38E4F9B4B081FB5540E1920F98AEB60707E59EC64F61B1980902F648800235E1FEE3715634818260EB9AA552170C4784A67CE8AD8CC894401769C88EA7DEA9A15FE2F52D6C53BAAA092B6FE68AC007C9FD01389E80178F99B52BB120B4AE4D856D14CC09C246AB5B7DD08880C17141C89FD27999BAFA40CFB30201CA3189F68623BC49AFD3518A89150F6D1D031A3C1C9283FAEC3121592B180015C09BDCF62D8D69094A8197E7180C2E047389058B3842D4674C3CE70827B6EA31FE06E06874807EFDBF43834CA48125CF2E4D9A4F4CE30D431C8CA7C4ED20007E70740C00932C45E34CBCD60B77D8B001881C7EB3C50A2F071CA41C48641B92DD03FED704602D6AC4D719BBD4B4821BF83283BE49AE894002EFF003CB59E8D83A16034EA09F5E36CDC8405FA181918020296D3ACC5CBC111A521102BA0971F05C1824CA8E28AD2B187C27B5FADBA2832290B52095030015B8C542583538602B86321C5296C4B7712023EBDBE9E3C5BD8D035046328966400D85232445800A386BC6663082D05E7B520977231BCEAD69DDE0B11F8A4F01B6724CF811A5B3148CC21EE4221672C9A00C6D4A20934EBEC2CC2CCF1FBC9239917C26B0CA8BB3615DE7528F7999BB6B0A8C3A28089CDA800D64C38ABDB8B89E6816CF69CD116CCD46C659948584616A9C8DDAA5C4213364FEBB2FAAF9C7ED41CD7E3A148129168EA9005FD4A9B141827BB34BA41F81AD1F831BCECB7E5487E92503D41E29C0CB588A391578423FABF88A1F0EBC1F28975F10623283A6B8CC72D90247C48F703264678268189A2988F50D8511BD9093B840A103EACF84CA046810E2A8BCB4DAC3D602D2DA221ECA4E4B6D61DE9B42DDB699507810131AA681A444B5A0C0BA1F6A9EEAA52F2C20C1CCECD144692F5A31DDC0A048941B052065364CD146736D9310CFCA299D0E49845D4F1D852321A7272A0FAEAAEAD829E10C5C1EA18407F7AEBC672006886E1034FC2206EB734CDBAE1AB19624242DEEA7D4819C676C3D68A08D6C1CA238590B53400F1AA12DD245849D90124706C050370661B7D4B3192A9005A7EE627F7CCE61842AAC27880488A55884A8F188EB30357280BC1B05A16A3AE167151F0014D4C62A14696D7C218DB2BA157B523BBC0E4041D0608A50B9310DD3EA4293BFB144BC2B10C60C15A45656358FA493EC112AEAE0D9DE1672EA90841802014EEEA75524DF105079984DB323116B8D35CBC9AFFC400241100030003010003010002030100000000000111102131412051617191A130E1F0F1FFDA0008010211013F10E88886B42E906882A41AC6B3364A7E0A27C04214AC6D972F14A3B88421098687820909667C19B1DCDC24F4FE65337131C29A1236436265C52E74443447F068824410B0B13107811714A5C3CDC7B884D0932E210685CC21A235F0263365C46464156459D908348508444261A161BF9B7854B063190421AA210B2CB884210842091EFC5613CCC313C525C1B184CD13E74A378513C294E936412341385C2262B13150994B0BB2BCAF8311AF88D142A64214B83C517C5E2E5E6327C566109F084440A0D91932DB222B1D2B2B66C788C8C82D098D9462290A5294A528DFC121109221111841A142E54A529E94A545294A86D14A5294B87884222A294B8659852E0C5294A5294A365166B288453A4F8DA162A28D89FC1BF9513294A52970C4C4CA3C9BC358B8D89328D8B099AC565288265378B9550994847918D1B132E294A5294B0A527A41A44208421450D358A5362424223444B1714B82651B5862422E1B13132E16F1EE18F08626336565C3626516C4841D2E1A2958DE4B46CB8E1704207828DFCC9B0941659082289E131B121531FE1BA219C2953121034630D88C46A5C3831B21296CB241D2B25B8A8A537F0D1A13295886DE6323A6CD8B08242D2E0CA3610D947308EFC2220858B86AE08423CE98D06A4679120E0A2323111979D08422265A652E28D8984C4CB48C5A28416C44208278AB34A5C5C5C6C788C9F199A519082C5132FC366C43424443208F808C83C3AEC70C84F85F86C8C4B048688423F49884C44448488B310D2F8A1A1284468D1514A5F886B34BF06D6F0F6342626C4C82A2E1192947E461485441051362624F725C5294995346B14ACB95294EF48248884D633B3A1A8F0490D0904A0B0921068883D0F61282C53A25469896F6425E8D7C1A98278311891A2A4565145654D9511875BC4F819B2327D9110826DB3FB8D1B3B8482C510B8D31A3AC86912AD160B686C49DD1F6635E07A16D0D7634444A3A113639E1308444232321263444446859A3CD2FC342FD28C4A622642226124F83F98EF48909D7B13313A33C90D98D0F0DE642D8D26251E8D91B4C49AE92A28309DF44A5A79B85FF001ACA38B42A47EE379657884D1FC38F0B47E90E0B102FC1B21CD0D523126B65BB106BD36D091239C1A1513F09F4336E0AF7294443444246463068FA49F278A4131A1629778A8A266F582A5D649D2AF448F82E0FF09A1A43D0AF4FD230DB43DA1B7C383D289741E9A2BF4974C93841C3574645205C5C25F04BF07D1B63446A06AD6CB688A51BC560DCAA5FA5B2C9443718B668AC67751DF71523A6845591C045D29F59742B80C9FACBC68D958F826358C7B1897D8D21244C4421C121529E0A1A63E61531CB477A20D3A5121C26896E0AC6B564352103F48DA12C4622121148B057490BE74FD46EA33728B958D7D2BC29F4686787DE84E9513B42FE88ADE826ADE06C6B134368A8AB1427048CBA28A0B59B5C24090589C6BF62D08B6290F51A0B68AFE84F0EB363D6C504869E1A426C21898DAE9A3F446AE0744D6892E2130E32133BA13A71B1FA6C487C23D28C77D3D095F58E3F85105AEC7E81BFA1BBACD5D3F73F7C310D467F91C7109FD0FC304FD613F44FD11F43FC8A9C83133543663E904C4D95959BA2A3FA66D3D0A0A849AC6CE8A74C4F4345834851325AD2228311229705788A2D0DDE8DBE8E3D2BE8D5882344B434BA43468B1624C7F446B0CD1A21084209212AE131ED42BC624970BC04833A34CA226C63FCC1EF0D888C4C6AFCB62A6D1743A52BB852EEA36E118D106DE908CFEE521E2661542A29A4485257D194D89109EC8BB229109B982440E43852DE9151C624C411488E160D959B4566FCC6DF053DC34C5DD896C8888A5C42148D8D9A288C5953D210DA1F936C5A08FA4861F4444A5A3E8367B1518F6334CE70324416863FA1194489050D108420A9BC698BA69E1426884624E11D1360B04209092F4D234348634D8DA4CAF09F62E62A4441894430BC684C586EE86196219B770488A6CC81245C7714452B24F10D85875E08718426110D62E28DE5B35186CFA36D6136313A54368EF09E892658706D8A888401B041A234311424D9EE241F33462F8773C358BB2BCB11B10F684BE06C8FA6CA9147F0988C484A74D61BF0F44D7A55E09A4CFA46E1B09B36A121C235B12F44E2C27B20D8FE62951A13204D09A189E9A3468485D9B5E8D04C424A50DB4CAE9BF485F11B36384377E71108853171309685D1B11D09D078EDFCD099708D1B13D8C453453C152C1B42690C38263AC856C41FD0A7A249110D3F09B1B490F7BC4C327D1B2FC632BC29E1B787E638E896C6844B6221717D620087515892C1ABC28E674FE5E1163F11B63A4646312FB3424F0D9110D52C36F0F94D9650BD112EFF00B9FF00D63FF467A413EB091FF512FF00EA2FFE63FF00305F7FFAC2A06E0BAA3986F0276C6E21257626F8E1B81B209506881BBC1FD03FAC7F40FEA14543F0CBD3F84D524D9694A6F025B22B4BF43B888B06C512D8D45A16F42084210843AE82FB07ECC2FDBFD23F9FF83F15FE0AF53FC13F5FF07E519354A7C2CD8F7E84BEAC4B51367F4C4DD588FDA7EC27EC259D63BF31BF846EBA5FC1BFA8FDD1E1CE129A46C48258AFD35D1B4F84A8D7070498D0BA3382D04CD0930D2A4C3266689885C410C94B4413B550C987F045C2E085D28CD0845C7317EF1293EC81DA4C1D27A127E9A451B14F46D236426B849F515154D0CD098426C888890843C20B0718F01286B0CF08416C86E8D312C247B8FE08AC8E91FA434543FB3675483886E91B1AFB212D0A3635AD60D7E0B524E8E385A310B4C8D62C206CD3227C1A11308B1A238C7431366C825884213E1E1D22222228A98DB624DF4828868D61A42E9222B5C1266DD247A34861BFB1BD51386D91E19B106A68BA8584E237B1C60B5C2A625BC7D487631AD90843788342AC54851B104B0824248D266B0F09BF06C758F62115223CAC835763ACD98A74C48D5343A2C1DA25869B3D83C7F453A7F0D23B3C1CB4B47F307B19161FC665173AC528DE1B62A22E23B44C784EB8C6B710AA1DF4E04A9049D2145BE097D0FF004DB3EE24FD18438B656CADE849C24E9E6340F1C236620D099BBFF1FB89888641562D0DC3CC2434528AD267DCB4BD11ACB5B347E22262530FF0BA14F4A844432D9C43A9A8A1365341F7191146E1DCB68EF0FC15F478947A79A5364C446870D1455B2108243132E8E8908F46B0DD27DE1A1885121D0B412FB191883634C36D08244EA1B778336CE0CBA83D2151DA4D8DEC7CA27A34D8D0B7C362586D1E14708415E094D9474DD16B118D098DCC26CA74E0CA5D9DC41CC1A38D1D5B1221250850BF46D412A4A2B1A1F05084DFA56750D8F62FD1B7A1B7E62520B306DC3C11048A9BC4128C6A906CE950C68D8623CD971B3C16BA25AC384D1B1B884A88FA4128C7B5A2C45BD1DF0FA15944626E0D6B788DBD0BEC2FD3C133D1D879D3F84D1C1A6429FD20CB9D9CC6864137C1DF06B1742297586E0B2EF8785504D51F30443539865AF446FA53D1B47EA1AAB457C29A154A1131C42663756C4F509F6251E8BBD923141A4D17C42FB10984D7C345D9FDC6C8B2EE745D620D60B0E8DF82699544B44484CB3087F43417E11B154C89A125C268896C6A8898EF0D91CD8CDA45A2E6846C4C4C6D515707A109617D90E3174A528C6A52C29C2612D679858263704F5495E5BD098C5A1BFA13A3625729EC486CB551A8C5B44C25E92320F5C3887AC2BE0F4C836CFFC40020110003000300030101010100000000000000011110213120415161713040FFDA0008010111013F101B13316D07CC16070A52B1B66CB870412342B321711092F0222648F0542D0D8C6C62E8427048919706A317812141421A265C1AA25434C5446888E146C5B1A641B5EC70A843FF000AF1509216B09306C26A367706A2C5C208ED9842084842CDC318AA9A6868B3048C4CA6F42E6C6E604D319DF968D7F856565C52B297098F688243D2C5F02441E1085863131B1FD18E8AAC3C5C1B1D146363744F5E71E6E16662E121A84794F2D110C212B926108705E048419A2A2D221A32C61A12A48D17F930D88C18B235984784AC41D20B84261D12C210BC5B2898F0CA3D06E973616592FA36849874B35F0D109224846848E150DA2A36211893141A12698B620D3208684C41AC270A28A28A2B06131F86130D1099222224AF420841220D10986A676464C8D623184C178047920F7820D0B0D2121AC4C7AC4C6899A2096090C35948870588344C1221042108343C08313291ACD4545454FC11D26C83468344854426528C4295148A107B5948588421099214A2628AC184846086990586C6C57ECACD8910984C2130F42C1F482121E10684986A8B0BA218A0E7A11A2612C2118EA1C1B65B42A78689E01212CCA30D8A136121058786CFD891950F788254AD14784F7887C8D86C2D745260B0911AE170B1323EA252A6360D084D9448B849240A3230488421086B0F161B2334C3424966A2AC54320D86DC2463A34623420B443107F02C31E29594597886C4DAC18B4AB344E20BCBFC0D85441026863686A467AC2B2E109928D1B343843221A98A3D9231C3D628D86AE61084213CB58ABFC666B28788420BC141B427836C54ACA28AC1B5ECA86E91FB25A12345FF002A529442898D9514ACA3132B295E38565F0A3651B366CDE2108C58271BE4F0831015136150C8DF08C9F4D095213128B5AC4961A15841AC478426662ACA655210423C91111C295944AE61305486E5BE8950CA6DF46D686AF4AA333A35BD8DA62A9D260CC9320845EB092ED08F4513698993E887D2111161A58DE22208126123491B28D3B86C45170A105452B2D7C129D11AD1953A570758D6B425A134230DBD8D1BA3FD1409A7B2A442CE9BF42667EB1090843132591066B8224B1B215170DB85652A29478ACACACBFE696129A84E688D11DA6C4D0CB826B8266E8E9D05D83504CBA3438D131A6417E86D301CA844D9B4B43F7426438747AE15BA26494E0B4CE0543442FBF184FF0054329B2FC3A10B2D5C26218CC8770968634B08FD3106A63D3420C742974F721D3D885B42A6CDDC1217747E0EDD09B4264F82595D2B3409E6B29426C289F94CA6624F0544B288B72D47624621D8908B8366E0A3632EC693E1E84D1A2E27A255060DEE857EC4A620E1B16C5017C3F04817B84E1618C6AD899B421448A41584E104109FC828BE6C4DD0C58A755431B8267470F4366265A12313BEB0C87A11758926B58406884CC468888A45A24316C6C6B090D5A8869F47AD8D3721ABA35F47F257C2253C454B062D15365897D9533A1845C650B8604C68EA852E4F87F46128349F0D8491115E14FD0E2642631FA9F24266271C62692D10C49F0824406946A489481A2E0E5FA351A1208C6C4228FA507B9150614872B852899B87D1DB45D0C6638127BC264218C6F184685F362FA05CD0D1EB6421FB88F086374E0DFE8DAF44984AC6B63496D9A705F618D9A3051FA0BED897D8AFA5959594ACA51B29472D1D11940924A234145C069A719B2EA23BB38414097D1A347C44A2CA7E856C861B1BF43B09AD1E822DDD89F83FC9504E5651450AE50AB4C535020119191E21BE11E62CB706CA5CC3D8D43087B12A63240D45959C32C6A025EC3910D9A012BE913E1A227936B3D1A22A22A3F22A2A2A34558AB2FC077D18EE214B89E2C65126F8267108937B7802AB82B98C0FC19EC616DC358B09224CE666D10B33D1104C5451B63A6C9084F1A5D64A28A519AA2C6FC295F824DF067BA13EB12FD115AC12C04A9D620BB84C482A76C490328F6838383229AB624FA296614A3A578B96CB46C6FC10D10688A0D221FC23F6709484268708412F86E1E8E96CB3836CD919435F0A6B6CFECFE85FA2AFA26F42A2174B5050508BF0A4A3D22FF8C216FF008259D9ECA4427F982288B8E91FA18D859A14F7894A28A9FA222862720DE7054A8E4A0ABA85EEC56AC074B6C9193A356258C6D623FF001D6614A7A16262898CB0AA88A6C4991618C46C7D9329C1D3C11595E1A3E8D8285065CD560377D3B1B508D0A938947C37CD621C2E61084CB9888FE1A1C36F131094E88A92910921445C4507B7E0B58BFE043E891095631A2422F85657E3078BE1110F79D23798B0DE1323E94D0D61215A687341525FEB51515157D13D15098C2EC5CC4620D95171EC7E499098D4C2E8D334543BE8D94DCC43D9B21AF47079B94588FE91FD27D0C08D7450B17F4AFA5E0FCCFC8FC859822829DE0FF0AC57D8D10C54274A5CB427AF034EE19464792434B31110BA41C382DC7A189A652F874FC0FC8FC0FCF1228FA7F47F42E6165DF584C0D311FC27E1FC1FC13F084424A9111111068D62544C344357158F0BA3A21D19113E1EC492E0F4C4A24294A565FF000642D095976345A62673FC6F85F1A74D6297637B2BB09886F1378D3641A84F82A4212090E1018241140994A5294A5C6A1EBC726E17317FC3599E12A18B63D3290931035A3F05515D365D1B173625762426CDE3F0D21BA2D910A7A151DC11820449591E84CA4367587D1EC6D099717CE94A52944C6D8B5BC386C50730D7D2A1B13A27849906BE6106E904B1A3F98A224C68EE296CF65145828131542D8B48228AD9084D0995151510411849184F64D0B106908BF4EA1522419049A64BC3D41B5E86DE37E84BEE1F34423B8484A91212B82410844444437E8D8936464F685B12621EC7A8786C86CDB12CB34458AC6D41B504F1049247F07C82D744E1A161BF82EEC496156B10F58DD213242985DD9A66C7CC2D94D8DC22104ED109CD89108432B1516961B1B28F0B0D7C1A7EC6910826379718E1AC2441BC271899FD27B1BBC1250B942F8598254899165820C5AD31D3E0EB14E1095374994F65133655CC58262DE2B29459AC3A3D087BD0C61B85DE1E84EE993638545708273A74490A1509EF1ECD0CE09D64228529EC6C4DD130486E5385C07388D50F4585468A32CC1684D102E1309BCC22A83443151B150E51D121A745B20E13636B1B1520D18941B837A2E85589316B1457305A1B1D137209E3450ACAC374970CF596FE099A1687B28B135BC27C7869105386BD8D4189E22436C8C91093653646B3B211025313E084FD1F822CCB42851BA2FD345422A2A2AF631B13AB0A1A1F70F47E04DD13C548B56C4CD0C48F149C22C2048AC1A9293088839898DD29BA3426B0965B342160F65209524228421B27A121A22F631F06C84A748BA68DD231AA241612771342748687A2E12989A2A3D1B367A1C313BB2A83A24654B157D8E605F7346DC14368AC9F4825F70A909F4D62B1ABB2AA75E2C35D35D2FC3FA54250A5D09E1AD60C74665DE531313F4363A121D4370BB1A74625A3D0DA1B3104DB70E0B07A3D1C1AD8BD879435EC43786E115C5F4731453330C4495458378D8817088A7FFFC400271001000202020202020301010101000000010011213141516171108191A1B1C1D1E1F020F1FFDA0008010000013F10BD05C6C55DC458EA5107C22AD553108186771EB49532B15CCF4803505829672E07C5470F8D7FF85C3F027A9ACB8C0B2AE5E3697E25885FC0C019C91411570BC31B84A7C25C0A9AFC220C444C66E0E61B8BE631959851035B804945BAD4A8CB288CB2DC6BBC92D4DBD4B87A81E21552CCFCC92C0A99A306B9666D4AA351421623126599696E22EABE1A6202017A95F102F330704C549F17389433281947C3F44E9F82F14C794C9A979949BDCAA22A60D00A71119586F98EE5CCA952F9A16A2CC6A6A532DF17E0854A210527158ECCC6BE065133979DCA0472C4CB966E522230A350B712C4268254A750A7CD592BF3964CFC2A291045658CA7984E65C7E0592A99AE25BE0310A547BAF86070CA0433C419952928A8EA588B877423E48DF352E2A894B820388BC1831D41DC1616727C347C085469679A0472C1F069337EA2A2B60CCC6CA2E26732B1330166B92289598BCCBBF8A44295A84B5034C4AD6A6F90E18C5C0E8A894AA770A5435388450CDCD65653885896398DA4144C42F13E0B219E6163F014340B5051C304FC04DDD46AA548AFFF009AF9BF8A952A54A88623E125C4958972C9645C4729445647998B94D4CEF136D42031D420984155130F8031A816E274F8DF512B8844BC4087061A88B5044C60B2782542E2E22D45F33A41194445665023302393BE22A9BA65C411389442A5E2664A83DC5F06E2F28E416FB8459030B89971DCBE4E620430C31B798DDCAEE1CE0BEE5B2A891930B2AE17596EA797C109D08A2C8B7987BB83CB3242EC21C4EFF1CF0A71F14479725CBCCB2394B964595FFCDCB967FF0009708E9F80181646FC4AAE206203C4AF52E189795553494EA0B8B1D40EC9AE2230B3199C788C26E566132D7C0804D405A8E1A98310DC3118F69D6E397C1ACC1966FBF8073571715F01B9822445461CFA874837A626252EE1B8398659762582E64FF91D41941916A75E22ED988C07B9C4CF52C9A8BDC2C944A6653888E134198252E09883984CC46657129507057324B1A9774E628D4C1837F2B16BE0BB9F73020A5B570F72E5FC6A5C254A85C5C4BF8B9771B328C004A554E8831944A9495EA57A9AEBE0C23823F0259184CC2EC9A45B610698BCC59B4A8C240222B32B714B9771199898B9E48CACBCEEC409B2511E3E1C93CD15CCCC18241B944A62B69861C13C51042440C30AAC051518550169743C44AF50A804805D4440109D43B2E65CC276135C45665F846B50E126A1458C231733066066206E149528F8B8CA3B986A73A8DCA5324A32B72A54A8988199552E5FCD7C240C7C1F2C614E20DCAFFEAA2F8998947C94B753483F184CE6D940C7C5C6BE1C93562CEE1566446BCC6D1F514B9CC2DF0608DCF83B619F68F152CCCD4A6038C407B85B04DC14C5A175007328392583041D1D464DC35B81B195350DD4A98B1030CB0D47B5C1C6C4094E488EA56A5E5FA9E0819A25DD449947744DF336F9058C448CB0F8772FE521F1C43FFBA6661770F8CC1F8C7CB2F1F092BB81F0CB944CCC44F3F09311D67E0A2A518742054588A9772A3EE6FF000DA192E714C3E34462E9B6E53BBF8D21C615E665CFC0E328C038603A992328EA1D060EA2D930350F08C07733B1F11843A6560A86D8801994EA14DCA7394BB5863146D54C461B95DCA18098208CC57C22C140EA5218464F86D07152B0289572A544A8C23A87CAD13161720C3E6A57CD44C620C25D45C4B8B0B84A952E266B8974D2DC2D13E65DA7E68C4251D4E271F07DC2AA2A8AF12B116A5D7712295047E1CAFC0BF51DA6A4BCB77F1B4F784186E11692AC964A4A41F4C021E133C731032802C105C5B14470C54B3DA26973C65BBF8846B310152D4E18BCE672A21A6570188D4360C403CC13E2BE0A1342F301CCF340262567E3729F352A54A257CD7C71F07C110B3076627898C2D29DC20097F2C7730993987C05CD7C592C94611B58EED8B88AAD16A8825B58665CBA17887CC284319DBE0B7299589DD00450AFE2F31688B799E68C8B332478CB972D07373A4BCCB4BCC4FE60E73F88F1598DA0A97989E094E7E34950A6A11CC206F70A2012F72F330631B96C097CCB2638961821C4A8A3FF0060267241D3137D41C92FA876968E11F371CB981655C1EC616B0C3E730F3FFCE3FF008597F1612A4AF4C46727C0B1F13CE1E7149E4863881EE0B0655CA4A4E25B08A93A622C5694A6616E50D4B8519D22858A9C47717110AB21A9A8A4BCCB8C13ACCBB9624AB9553809B2793E21B77035152F2FC4B4F141CB5EA16620A2F53C1299506732D65CCD75F1328D173821571282058AEE0A2E087104D4A47512C81650221C237D91BE88A311DC8A9C0910A099E896AAA205831E04C1925C71A949124CB3F1442719595EE651587794AC4E522FC01729DC1EE0C4DC25624C51F094AC4B6E0BCC14B20E25C48D44B8388EBE2898CDB317CCE6260D84E820BA9832CB86572FA833497E6525751A41ACCA144B8A3C2794B843CC58B16C54B75163D53D7E06714EBE2F146DC4BF53C30A107C0133CAB896C2949888704ACC515B00E98860B9967505A920752D76C2326033EE02F72982A94F32BCCD4BDEA5D3245B8F86C41EA26B53B8986A6A09CCA04A5C3326E29B2DC3CE66C6AC3CA105CFFE15D4499927DA2CF30437F1A932113C4CCB399992DD4B73161F1698AAB32E6089D7C6FDC5B897181CD4290AC42CD2621240C610EF2B533311CE638BA825A98218FC3DE2F99B6E3982EE2B4AB953EE0160691A46BAF81065094EE5650F861500657881CC6A4C171BED9BBC41261DE2522E7E1B885DC2194773AE6022CC74B3C196210EC4F243CE0E19FC0CF32811136CC426184198362E5952C65B141AB9515F2DE6E1F03941CC183081EE02184C3186C871CF7299696B82496DE258E2043018C35284A710AF3338C972FCFC185580BE2673C901B8E1167337CCEF71F686C58E5326E181B88BDCCF9976EE5660A1F0DFE391F0BC4252C78AA764A5DDC24AACBB14B158098637371CBF1F8471857B8DD5C5571AF882F13489E2225B1B59944951DF129282CCCA64E221712F111625EE36E65CD4BD95CAE548B508F18F589477894F3059498744B2F504EE55F302996F705DC50A8E5714472847F2F85B2E0C5071F018306E388A12E6483BD40D4AF50A23563158F9CC7986319CE693CE0E7128276411CC68D4DDA9925C77FF00C34571F0AA386E59F0D770AF332DB1A0E7E337F97D67B420F38F29C5CC3E36CB4CD2C6028CC0E6208172E601C93965D11528660A8424890520E0A6454BD5BF98ADC6EA59220298A17DC3110BAC4A5B3896D4BF328135F32FB8AC96B856E3E7325DC28C35CC2C4BF1323133D4593325BDC5A3E2B86652554359964BF319B4290F8B48423862C731C2C0EE21CCC511D4728A98B3996EE5AB714F32C31F2879C04CC06A048481101899620DC5114626DF0B4576CC11F28B8CBE72952BC4268EE394BF6E65C97F811F6808410702C96C44C724A12744311DACAD54B1DCB9CC635DEE705C0BB855048B99A82E1712D839B8C6D3222566E6989EE334946ABB8F55213060970952EE665A4AC0A8D138235C5C4CA8C6E5BCCB25C5198162A373CE3BC448A932331B7F0D25F3047611AA8E3E2BCC69D4BF926388C524532FDC5EE3F2196F2DF82891862E5B2E0FB830BCB0CB5EFE1B4F79EF037C433B943CC59CC485458A7C195988CA6063501BD4B3C4BF505601EE784B84B4CD10330D445940DC31994B971AEA25C4FC47EE2E6384B972EA148653C912A2544A6E5813B5C75173B989B6560E7DC2EBE12C99B314E65A4CC2E346982EA54E6699A55C057113220C5D40A8C5BCC5063439848A7306E7E27281884C4798C30B997C423FA4D37F05967C2E2D3530839BE59494799499A9ACC6512E8891822C7B471DCBF7024CFA95712D038897A96EA38FFE0FC2CC3987467B4BAC6D36E3E17F026882789DE4A19258D7C32E68941A941E23496442EF100B8A89CCCF3368C0F72AFB81ACB0B8652A5C512E5546ABCCF31DCB60A25EDF332CA1B8A07D7C2F39F8DCA34263CC0566008FC10BC4A05C389328A984142C120E99CB162985554054039996840B8E1A81732880C42CEA764D22CD4A798B4E7E4728CDF9F87B47E3D25202B32E99EF0C653B8794218911703535A890D45BDFC3F144A85B880823314259CCA77102C650E49BE48E5A8B71F19638F8B96C5E7E084F85209880E261662542EAB9DF99C0C19CC47CA3E52F1C332DDC547E2BCA5CA4F798CF7A8E132F5049B544355F012D4018DC5CA2398C5B89653023A8C90AB535ABF82D96310712B4B883D4B70621791105A0194C50AC99C4BBCC6C61E49EB28B99C2DC54D544986660659C37012EE57E3FC4C239C5972DB9D3E52D290CA0C33870B834DC0A10292D5996B9840732C8F448D12C4BCBF72F896EF12B3F59EF043B85CAB95976E5C711C98F81151A988925FC54370473097CE0E2B207704E22115BB8DE321186EBF058BE26E52B3D929C7C0AEAE73EE5269FC4A4E196332DA82C0B66425022DD104602D5C42F53D448D772EE8604CCFA8AD97D4BE5DB5030C314665955209412A77F062DC4B8D205188CAC7C915C54C3941CA30F32E364D094658B7E65D63CFDC6FF00ECCCCB1215A6E2E65AB70F8FCE0BB89F182C2188EC23D731639827535CC582BAB996603B8C31125884E68D12865F99E9359595627B81DFC1B7996F72F377373997F0F0D735070929092B2DD3166EE2D8AADC66D788B997E6592E590812F878A62D4AF894228B2AF152CCA427A4557B8610CF2C55A97620100CCD6E79CA45BE235DE603A98E13170AA02A34A4C1B82EE19D00B08C372C20AB71A9E33C12D0A8C04B1874873825AA8153242F728EE54408CBE201115C40EBF1047108E6304313B41D43C2EE56F505A94DC40B9230510BEE6D2D1718891AE24C261855C4C15150788B388A8070A80AAB8CE198956CB5591462AE2D96DEA5333D413927A4A228732A979B8A698A14E18BE7E37F85CB8389A6218CB4FBFC6D7B994B97F1B952A52F104B080998D8F47E45C3CA0D6E538222E5AE5BA94BC7DFC18473DCF689DCB7B8E3F02915BB996E28E25B550BD5CB5C910CB2D2D30404679214C4E6606350D7B841B813175045DFEE52AB96B3204F1898AEE671D611A8CDE3A8B164506196620E61258D437330CD4C5448D6A0D2CCC6563117D3F88AB9718A331E261593C081BDCEC4BF8B16D12C3F029DC60206F0C4AEE3721CD821A9D07D45D478209D444D3295A303D32E588A5968BEE0A4B9B4B4B970B99952D52D0849A989EF5012C9640E718F972263332E65183E60938DCB3BF33495E2113502CBA3D92D52C11C636D41DE6048653245B85C9810F26D97AC3353A945371BB50C964196E254C3728DDEA13C42CCCD30CE131AF705B94307494CB1862F98373A46B9C5AC30C4C3CC1CCE0F80E27B62CAB652E180F3134CAB9A8A96911C31673008A6EE00F30A38F8B265E25A333CC8A1B219E60405D4C3CC04CCB45BB837883773898621D41CB896304070DFC07A23C0443896EA2FA99350E861A2A1D2C13883710089371CA2F8972EDF85CBF30A32DFE615DE250F31131047896D5C1DFF00C823860F75048378FD414B70C43640724AE31130EB28A81312964992A2EA22F5312C27372CA256690AC2B018BB88CCB988D25C6BA0900D34429D4BB00580A980855DFC0159603A60B8039C5C41CC529163A70CB58988A61961C9080656AC633633296A0783E6FF00F8E3E0C4BEE54277282107E2E2C994B2A2760817641370E04C51502E5DB94BB941C4E65CBC4770C911CFC1CF715BA81E91572115E2A165D0CAFC205C450E905A08DB88C8F88B1A2E3C846884311E28872FC46029C42DC040AF49E1088B921A5CD16401B06629533440F5295895E637DC488F826E1295A96554C185B132DCE54A2E584B1C4B4CA5846AB71F043AF10FFC12B0AC5AEA5DDBFB9B53308708672D1CEA394B639F0801DC38C72AB3310CB2D8DDE1B9F847A4163BB3EFE2ACD30C2DCF052F7994150976302DF8A657C5C07714825CB25E273FFCA63531FF00C512A1426BE372FE1F86011C33090F8A952A62E7D453C4AA8E63795E6011AEA5594BA655220E0967116E26D21E89566A00C13B0898A82B6CA26983F1A601A8E53688CD15704372CD4A0837CC709B4319ED372C3DCB75CCB7502BB4DE653A9920CC038963CCA7501E2116ED85DD4B7E612CF305E6A7A4D2EA155A961AC446A64627E099416E9DC3A94E789B7C309866C9A8D26601764328652E20F8DB2F13710B84B972F12E5CB972D970F8F533F15D4B4B4CB8F8092FF13CA7B4AF72B10C2E54DA57C733A9144145BB8839254652538669017B9AE185A5FC292CB98C1CC52E20DCDE322CB8EBE4B6E5BD44CCC7825DB9EA9D5637370AED860602E60C12CEA511CE6D73C48E50AB04F139808FFE12FDE25C3B9638B8BBC1006126B8A83D214404C4D31B94B2BAA5D9FF002648AABC37B9492D363CEAA13929C623165F1BAA829788DBF8852193245B7550031B9D2057896B969789ACCE60E771A944C4D4B97065FCDCE2054B4B4B3F0A0EA6262E54BAE332BD4F19A5CB4B242B64B2F117C32D5492EE55C25CC42D9FA9DDFA8D52316A2E675F8ACD937AB10CC521588711CE25F04711B998A204DA41333CCAEE545C662566A1173A679419B22BA99B9194C8B85E7BCF39549485C54C46F884B7C5CA6CDCBAD317B8D7734DC0257C403CC1F804F10A9CC2AEBB803297A94752AC2729E0FC42ED7EE75DC019FAA3E49CA2600647CC320382E0611DD24E2EF9FAB816A112AB0E71CC582458E2BA8A706366AAAEBDC022F9D411352EB24C83325DC58AF820329AD4F35324BCCBC4CBF14BA1949B2BE2A0531070CE52710313DA2F86579A625C232888BB7E2A3A832C1326A1C1974AA82B63286AFE160D42F8DCA3813F2CA1CC712F888846D33143662A60CC5558A807A80146A2A2CE8CCF2612EDCC7A2211AEC2612ECD4B4704D3242DDDD4CB4411C43854A2ED252509C8974C0412F880404A7702A15884DC0EE256632AF1F079DCC1BCC5F99634453714F312E62F72FDC177155BBF85637B81084D95B06771219683B83664FA98DFC1F24AEE1F0605749A78B655EC5B24472472C0309A627A43519BD1BC371617261607C4AB03A36CC9A82932E0B84698752E19E610DC29AA95F499B51AF531F2803897D25F5FD4E8C47B172FA453612A1F154C446AA1682BA80B72C7501D0FB9CC316273F88334FB85507645B9818560A2C2D4395443997F82CF502393316D1A4C5A99EA386496698E52D7136957AF820C20AEC8949129A884468623108B12DA9B2EA5A115D932E205E7A47BC7E05805CCB4B3CCB42EF738AEE5A0DC4C6DF80653BF8A9C7C55022673FCC0E53A07E15E25661704B31A2102009AB5C44C7621F4C3C04537315065D30E508C8A0F6C03CE1E6E0CDCE75844CA31E8AE4557FB310C8892B0419B798FC8E7AAB83294392D39EFFE4D6880864DED94EEE02E6FA1261B0199585C169A3203EA1DBC51A5FD900754194E879FBDCB535B0BDD642AC2357457BE6E67C777467F48936150724EEA5F994E6244B25738DFF24C4473635006305AB2BFD8640C41B91612BDD8F35E22A2A6D704A6C6B8FE847CBAE970FCC2DB0D3907E332D562DAC8F0F33627C69F6049C22A99E44C74478653CA044209055F66235B0282BC262904603FA7D4A936528ADCE78F1053D458A29ABD405BD4EC624312AEE206E50DA29A965C0EA34DA4B1BBCCB9BB9430C51B202A67C316F291C8CDC120C56B0C6DB626B70031AE89678966599388D8E3E043B988F4A9D5E206F64A55D91A6A5EB53A52FDA288DA3713A4C9B26FA96E082E08F8A7FE2A29B58BD1FB94A2B6CBA967C7328712BD7B97D253529A9C812D75063825516F3176C580B8BEED0055BFD4A868A46304033914C4565F189AABAF5297215BCCAEA2342CF50352A706F3E256E2A40DC1C7583742F9190CC3E2E5B0874AB07860350A9D0BDA4A11A8CAB22D9302A256C31CADC7302430D76C6BB846C6019DBA8B15DD2C5D704B946B52B1296B6BBF13938585DC3EA8292C59D452EF0C5B8B1AB94300BAE3E928EBA34B8D39B296FE20F611D379230D4F46A899324375C4A0BFA22C04B97552B530BD45BAB95B1E52DAEA50B2302F10BF80232FFDE202CC24526B06E0EA3D4A42D34ED1A855DA87684573E2254B2CE1961236466741A00F103262BA25DA544C0332546191D4460C65A8D21A1A4A4D1F10D7211EC22F73CB185B97C622E19D53A10AB9816D8576C1E27B4F7957715699B55B0B7307ACC33DB1388A4EC47B20AF70F29833B94E2526664991B889EF01196E18A3B97E6047710773C5735A25C5EA2B5B8E79881CFEA0CA68CF797E18AEE2573F2BBCFD4E286D28FDB52F604D16D128FE1110C6EC96DDCDAC6167516C2AC37D1DCCF11B2F040945DA387C5457E265479E3EA55E4C4A2E912A4A6C8E4006F5E48B181F1BF7120280839EA6B898357EA79A1260E60380CB572AB9AD41124E69A4637302C44A3735860D771B15575E6086600B3A8D414AF0444E5B2A9940E71B251B8A8BC0F7065F15767EE641EC283EBD4A2DA0D9A104A75134228B9A99E4C72BB95B314E0C24003A676412B2A885FE41D6A253858A55CCA391CF71C2DA534EE1D14EFD7DCDD8BB055E789582B1C93099C195F613BB6F8942696EE5F958C8916C8A66E6076D0387A890929465BEC876FD7A86E02119B6109CD0589C90EACA34C59B5F32D713D0851AA8D75F0B9F4C0A2EF7F01108A8FB9EEE7A332116F697AC1F07860AD5C2F99F772FB4A87C5B107AB8AA4513B1135736FC04F52BBF877F0988E7BF8D653E20B8943897E25C588BC7E25BA9E082827E504B59978052D79855EC96E598A8039940F327328CCAAD47831368222A3AA3FC93D08D246FA8C1BC3BB5CAAEA9EC80C5C9CC48A2663BEA5EE37412A2DDC5B12936987BA9465770079226ADC0E2610DAF94E7FC2E50D571A2ECD733246D8494398A16B12851917CC03B6650B96462A3EB1E1845E15D4B11C0EE1AF8EF64E3C31BA7F91B15533F830F12FF0017ED72C1A19460B95F63C196874B2E703A9A071D4C187A258CFE5380D24B286C83BD229428AB1EA35D8E79CB989B68ED8F50D958C1D20A06251CF065DD7A8DF5050591F15C39FE3CCB8D50BC5AE8B75462638A61E52F5CFD4AEAB11E994EEA52F0CCF24C768043517E352FD4B5C932DBEA34EE57FEBF80CCAF047C659FFEC4C07989EE50EE0144660947304751712F32EA5FC5FCFAF873F1CCB8B2E5DC59718FC6E1A8CB18F242D47DE4AC0AA71CC56D85831A4B1A0DC2B6706094144E28A3329A32851A94731C04229D4CC6C65F6116A3862A91BFB9821B982B68B960F2F1055D5545B1788D6AE664E9EE11B80966A02CE20595838440CDC0AD55DC60B1A455D19635DFD500B86E5CDA53CCAB9733365BEA2B05AA0A9E1295141EE0D81A05CC04B43B704CA548E3A972CD544B4FB5B2830972CE40D44738FA82FEC885610B485E96A154AAEF309C4A456A8B82474C5146E288770EC45AC0EAEBF53C3F1774443F43A2581E7FD825829B7CC0C68E072D34FB592EBE718090E80ADD96F22BB3552B10C373ECFCC1266B72EA1DD132AEB755EFC4199FEA4BDD3A7E1646ABE28CAFE3E4D312BE1F483CD8F94A7B859CCFA4DA2F32C97F1A972E5C62E3172E2E2E5C597F17F172C0972FE1677F0A445808E3C418F06D581F49AA8FE6E53B702F53D260CED99AA8C00B2E005D2368623BA817DDCDA7440E4115B3016E9EB1161AD94B316EC11D13320C4C8063DC6F5B2D54215310E4D65F0A2A5616A8BA9B016DC0C06B36DCC0C4B4A372E986859B94E12FA6C828D11C7FDC0773BD2194C614C78A8577A259D94658B68CD7823705F7E26C3612AFEC26BEDE6354CEBC41AC6FEA28B36622B065181957740840303A88C8BA9716ACEE350560C4F2DC02F98816354712F603594152FF003D933E81A73B14F3E25B0958B56DB1DAD8E2686AA7F2279959606FA8CA351145B68595E2A0DAB2438A79D5D5C579B9416E7977FB975AD2CAE0C32283364BA7B8E16DC4CEFCF3A82CBCCB82607B6A2D4D55DB3FC4A5ACB8CE087DBB4D9714ADC04D98C3FE45B25F12E5CB97F1C626A5CBC4B972E5CBF82E25E65CB8B165CB632E5FC2F997739977F2F5F08BD2B80D4E15AA6234067CCE01AE26A3961349646CC161ACA30AAAB87316C92C320C70514C7F5B3C8BEE030B671737ECA8581D4BA8D98C4B8AF82FA8015BCEA087747695E117C10F3005B51BD3642EA6C80379804B5AF1738AE29995163752C62E2C31FA96D19D44B9599D4BA2698AA99E5DF10D7B3D4F17A6E2686C64E23A15F71466AE6C3494667B40AA0C72C5A9758DC3BA0A7244370EEA1519637108EA1548F70515FA8B8B6E666561E2266CCB8962B2A3336880BABB88C0422655F18866FC21BD08C10DD55731DB15DBE6130D6D69689C7853EE632BC5B0C543268F3F28224BE221291E030881157178C5D42BC03CAFF608D9B8CF3FECCBA9F7D5FB772F2B0DAB57DC7741E228D87AC440486EDFCA40DD69F886C65952C9717E172E5C197183EE537292E591C4597F172E5F1337F172E5CB9799716F32EBE0BF0B9C46DBBD4D035199BAA21B07EA21CC456AA2A62B7511506BE1936CB2EE2C57111B732EE5B81C8CF211774CB0DD995603318D817B8D6151947659C4A6D91328B6B7E18E5B357CC7BB33D9836250F11651C4C7986735A97C22392E5780730A281F2EE0B40BCDCC2CA6030F3177C878943340D46ADFEE1B3594D00046C06BDC52D65475F4F3155208F103BB3FA957A5A25CAB01CB174399A175506B2C2F82738C4C8AD0CCB5411E6675E0E711DA3112D8307921E8CAEA86360C06989656D77098E57A2322B4D8398B3F34BB8B28B1711A32C9158851C1A50BD52EA278900F4095562C65A023358157C85C029D28C9B2F51806C63A02FEC9AB7CAC20A941B169F8832B50DA045C43C233FBD4AF9F645CE9BF2AAFD1B65182D3A4FCE7F5140D9B827D34C030DDE3FE9A9826FF00F7627FE913DFED889A3E8667A10E81CA96172029FC4DB8296BF1CBF886D914693D8EA3F8F0173EE3E2665B715FA9965FC3F172E3B8FC5E25FC5FC3B833257CB05B18A816DB651CEA2C01BA61494E9CC4BC0CCD626634621CC71C12E1731D6C6234AC7A828A2533001B7512EE950B3EA2ABCCB83925DB6E36EEE2F2FCCBB30B69252B45AD18A96C4DA0DCB6234ABD2E058998158A9656FCE62F87EA276E8733BAA9681F6E609AD21F9945D5459AB46A39CEE71E2E2D18D454AE139865631DE04E58272D2E982A5CF98E3B1801E23B4832E730F5E61B8D2EA0E784D40BA0F64D4456B532034E0B96C909CA9384CC05263C42E835092D77B9DB62BDA5D6F88B5B8910002BDC0B1107116CA719B6E501DACC8473916C972813F688DDF64A425304403DABBE72D4B919CE4FC88F4A0595A8F939946107035F4A83915706E1843C657ECEDF12EA13E8F5D1284569B444DC4584667456A9ABF0C51F8348839FC797B20F9102E7F1652F8639896C30CB01EA0C6AC0E5FA389A9210C35E563884EA8C2D9B4B3CA2DFBA97B89692E2C5972E5F8972E5F72E5E3E2FA9E988ADCC5D4B95A886089B12F438808ED5BA984CC032731E087B662C01885401708F1A8856094532BA8596D01F04B62731A6E58F31415B997262646C8274331C6896AA7300C27A09A5ACCA0CE63CCC24370F1960A15D3AC4386D4F32E51698ABE669B0F8948E57C333FAE12D468E19EB11E8188E6F68A80065AA8E929646943DA23614C404625F3985B6C712D0E65DADC5443402F10BA9829595DC69546E314B83B5BBEA1C2C31B43931E59584CC1FC910B5731419FA4B3069E599B1751541B215A9039840888F1292D06DD4CB112E862E72C6C8172B88637B87076EA5FC8D2EE1BA145583A69AC6615850BD5C550072DD4A6DA75DA47E9C0DDAEC312E8841B11A4604B187427E996BB27980C00AF82FEE24D090E3FD260817DE3717719339536073947FA953442CC23B492F0A65A6A52B0516270011D9BBA64C5402E056E914EE46348FCBCAFAE3EA5661103638047F338934171046A0B9C1DD7710A4488A5B57DCBC5DC7C4DB58E014D756FA633BD140FABD7EE545811D531BBA607625B8022391100799A48C0C679E184FF13C8B97C4BE8FD4034EBA88ADDC1B6ACA5CA2CD25AF5B88ACC5B6CD4C246396E1A58A8DAADCDCEDE202535153550D08F4CB2F54C55C45CD840B4B23E173C987E61AE96FE0468B86CFF04B332736253830D287F52D44148E4833756DDFE0FF0051D702FF00A15010DE847B098D138683F863DD3E5F4744DAC773262951E0608267E21944CF536B887810156BA983AA9AF0073054D50817084DE4D4CC0DE7155531D489D97306FD104872EA5A660D3E0B40BB9992095BB9570544016208C4A7AD6E1D8772AD460DF1B862A5CED97B1C24300AA9560C32ED9136040C387C4A4B4BA23B7658F09060215B858BDC4D58CC86167737BE61BB095D416AC3750A7572458257610AAAB60B0260C790FCC12D36C031F139A1FA802A0266E862D51F8A0A1851C4BCF285CB83C7AACA890528B2279EFAA7FD41ACE793CDE89CC2AF89432BA3273D7FD882B873BE256A6DF528883C866578A0FB5287D0BFB97288C619DF29D25081045B7A7D1FF2180E10DD511AA9342C1F52F052F82515A06D1764483529B2045C9D667FA82D49AA303EC20CF0B14E5329AA49F5CCDE19A720DA1AE457F3570CA80859FF0090538141FCE610C5B98BA4C141E25BABBDCDE061E859C9335534C2ACB1A40EA375DC50948A17D4CD1AB873055A62CCE8DCA6B46960817915958450CDE57E25061C05425CFD3DC432B6687EA708616331C44C4E20A34277026CB02E035E89AE10BEC41FA6CDEC9E4E3EA5A18FB5EB650885CA80D90058A4B04B621D44C6635E2644B8861157874DC3754C48C674C63801FDC14C0581D9F512EB3056DD68A6254D02D901B1832C336CE6A6DC70984146AEF330B95408680C076A05CB76976BE23B7A145F2C264F6AC85F51304A771B8301E1F983D82D97F3894711148BDAD6651CEAE62CC7A8C64EA01DC0AA34DE634210F71929A6654CD40C595CAF209984334F0D4BE52A165EE018C9192C3AEE541A0DCA10702880F074E97128335915642751440DA76C091A1AC5C634D451509141420472F985544949CAD6E39B2352A58BCB5A8EDE86EEB177112CAAABDAEE8EE01A477500A2E58672B1C59C8F477F9C1F70480456B8BE3EB51BB836A730562DB030FC4784E25B7EE5AC4708805D0F1118178F3F67333BC8056337007BCC24E4F0452B7288AE83C100B7F14C211F04C73A7893A85196BA9A391B1A89E73E2A37221648D8C22DC707C38112EBF3E622028C623B01DCC0D660ACCE23E458198F59060A4BAA691722AE00031E22B1A17ABB99955750482C40662E3753539BC5032B01A4061B3F443007648FA639B7D4D565B80B8C728F8267D6EBB96C528997C5573430175277515177028D4A4C998E68DD35105BD5EBF313401B126A6185AEA41FD154AB2EF6AC4B320AD500FEE0426E3B7E2621956E21AC3F2833927170F9A221002EEA3228156426173758244CB8E2A0A5B2434CBF580C9956E13BDD0CB501BC4C035495C30470BD86A54ED1BE221CF736DCC8099F76DC6A0F285D16AB5E2E3A070A2DAE88E542EF5AB8F41CC2B30684C4BD4A9A00E2A516449F4DC1856B560A57584731E4F70424AC61E60947765E222132BC4BA5AE535D0E125BA21BE997E9B660A6D07EEE270CC4D3B6E6524C108174EFDCC09208C06D7C4571358EC976D594CCE65523083DE4FEA5C1A2C52FD7F70692D2DA582DF6C331ADE2658086CBA991B09658CF9A96BB53E895FF00C4F6FD4A1CC523B6A1296D19F6C45AC72E262C38371F1A21719E1FF2FE2156F844C86E0D5DC4BA3752C6CA0D97C4328CE2D014E2914A40C1058CB5A132B72F4C6207BB220DB115898094799ED30E63E5307B46C2EE00D0B798A4039858966A0939B08CE28D9492CAC2CAF134F2EAE323A8E4D4AA96D862A2338F62E650E5EE50D7518A86AD4596B8FF00F889735F52D8B8E63B0C6A29A8830AD0EA6FB6501C721A3FA813CC03F480932F3494700F5FEA569A96CCBED95C001C8DC16C8EF3B83682DB9760D292D20BB0ED0A23775529C2A2858F737426EAC21E5C010FDD3745092234A235FD597D683004AFFC5094975CD8C2B8D2FB8B2640B3FA802F8648C662E6FC0D37298198DF06CB9489A6E05D0BDCBE3F6038958E4D6770800B2A90EA7361699E63242E1CC7113CDE6A0B8281ACCB9250C0B8F512A039C3164C86EE603B2EEB30220379D42D46F0332C6F43105C01C00B84CD6D5A0D4CC16F5A63EE1401EF6F8462CCAB0866A31CD6B0444219CF269FAA8BB341919CD18AD4A08692EE32F50BCED00982A2DA054939A995BFB3058714E14D74BFBFA96C07040F02EDA65E05EF0B5F5378C32F9895CA30594F31AF6EF75700AC03B5586586E8B3D4A01AF05F57F847B491C2184EE567999E6EA59FF209D9995399E208BA517BC4B6AD75B834F8B66BFEC36D8FE608D5D770C1C4299CB0FE03CAD110A16B50E3FCE3EA36B2B04ACC28104CA3330C5DC3CB3D805998B309518AB1D406C27866AF76164B71705ECF4CB831CA1BF626D0138489AC13D27AC4EC97E655C5CFAA5AAAD08AB96B15E267009C1821B45AD710E118B30CCCD5ECF7086C6693A60CB61925BFE7A37136DA25909B9422841A52FEA5DD230E7E9B97FA1B713F3292A18B05FBB86D16E24FDFBF1FD449780FC10CF2752D28F41160BB9FFC3CC16A5E02EE5651EE15F896A46935B8CD666B0A3F70F514B468C0AD82FF00B941997A689F916B2245493F89789B18F2A21D44A79954BD3D4C560FE6598515862B6B22B305CCEE5751A328C0A7DCA0CED7CCA4E5AADC4B94BF72AB9D2358C03643E5018C4AC654B594E4D800E65BBC4607152C8FAA10531DB1C2A0D763EA79910AAA0712ED61E21B8122DA05DF98BBFE23B0E14D978B94A3528AD4CA11E497E9F983D303DDC078B943935003266572168CAAC0815D2516A28CDCAFDAA6F85307D150C36CC625F88C46853FC202658CC2A10D9DF76D8BE71843823D2C2256B65FD12A4EE058B3BE6F5028BD442E511FC470DCEF35AE7BE65D296E0BFF00808B2DBD443B627CCE98834A334288215C61E03F6B2B484C6C3EA2680A38A88ADE0DCB20ED963239EFA95CDDBB22AB15E4099EF6889B590F0420109975EA5A8528BE06FD929496D1CAFB882F198539A313D970065C690EAA5D2ACC4201616C2C496AA28B06D5E49521E4A6227928D840A62F6E18355A6CD42CE850E9812B600F2C565E71B1133CDD2FE932F6EF17E025705C140FE7E1086A27D9FD42707D11D5FF00088304E4B601913C9B8B1AD79596C35FA99D81D928B49305D80F752D61C4133E4FA711434EF36CC9CCB9CC125B2E31512A77843730F0852F8572D256B13FF31346B85F6670628FE25EEFFA85F70EC26CE0986997602A60D970233770E3FDC79A55C7EA14D567D43B7F72BE195F0A817198254B100381F50EB3F1033353CE474A9814C0FB9D06620E625770B2D246FD1801C5C4ED3CAFC4EDB7D4AE247B9CD98C35C7E6A1666C60C8128DBD3A967EAB936D402C0D8C53CC4D10E32E22F023ABC11E10C04059512A5808EF232AC2B30CB33891C9D9E8DB923BAE322AF6CC9B9E4A87F2D0EEBF2C460428D43316208DAAC2C45DA103A9A3F6399DB90A727D710EA2EB8982D93AADA1ECDFE23BC6669C9FF253FE946CDB29A28E07C47540F28E674C5D998A29E0208CE2A5ADDCC3BC7048318D7082E3378655B3D7717A41A47152DDDE65E2ED80C731AC2AC27E275318A266C2F582380D5396A5A6E055194AB942816E037ECD118C87A563E2A25579E7BFB657CAF65F5297E9DCC523959997D50774111ABE7055BFC4537B78FF0063AC56EEA5E10795C47CC96106388B616404428F71FF00D3122C3D45EDCF962D8B5F731CC4824014A86BB404379A8278654718982D6695DCAC41D92ECA400D2E00E04B8A169540FDA1E04A12FEA25D590AAB88DD809BD43754D70A7DCC3820DD45DC44351EC805409E58763F32FCDB3B99B1717596784F7A9ED2DDCC62B72D716DCF14B45182A3C53B1B96CD710E8F81D1515BEB2FF3FA89995A0E43999C78CC19AAF32C6A07666569A03870CB8D5B7C32CADDA4CE741CFCEA29EF8B97537392C0803AB43DA04348CA2A36AAF816D6D512CA665703C4CA4A5871F923BA77D31CB8966C8E47163C10837AC041441A5A61008E2AA7EF47EA56F6DDB3EC6C8B3DD94FDF0CAE1E5FE66DF8825215C0185FEBEE1D78AB842D2B48EA1C008D0C331CD4C0DE48A3E9187F111BA3C4E64B4DAC54C0BA1B95DA74346FCF71D593431160A0F98A505D987E66679207FE102341C82D4472CE7944AECF76CBE5E8F32B68BD512D56A2F48345A292A2451BB4B4BDAFF660A8316F30DAD4E9F5BA8F3CBA7CF6C50A030488DCD4AA70EB681955CE43FCC29FF50BD1ED8872707C08A2718B36EFA20763EE60816E7ED4D715ABE26345788B2918C5287B84A065EE3BF55734DB2D2DDFCC61CCA7729DCAF71A42981A8E30E932666E525F92239DCAF895EA59C6252A649799FCCA95E2212AE5EE74CC6CC4F341B898259B5C506C8297E5874970D6E5F84054C840DE624A8281E527142C204360772DABEB5187FD99E61BA03E71A891858BB8788E92271539660CFDCAA0AD619A07EA074BCC2502DA86817E209C479D91F36FB9B66018D10EE0E14C15ED1B3D9F894898E2D4FAFE54C3F8B0C83D367F67DC036CC963F4968087728888B392B39D2925E65672D2D32B7C0AFD8FF21D3BE1C9EE3AD55C697D3C3F7055FD04793FC800A2DD36455495F466705F42C46F2685C442B02BAC371BC1B265852AFA8467098D8DCBD5FA23DAE282D5F980809B56D5F983434C00C12F68237519652B04C9D44564897A0895D661D55AF32F013546E0B5E94CBEE6A1FD97023FA264DDF5306E9F89EB29E2E5798DCC25C057F53C5960ADCED7989DAC2AEE53B8CB37BDCBF73165B8900109B49B6608D697D061813BEE18EE5B570B735317114E6E53CC7A170CE6FA9660A19CC1C30F29696976E6730ABCB039DCC8B2E15B8A4DB350A65DA592CAF33996DC5F13C10F28BDDE20CBF32CF0C5712ECA0BE65A2B788A9DF46FEE618D97A750DED56982534ED62820AB4A794C20DED8E1279452791C4262C66CAAED5DB193ABCCD4AD39BCC42216F82E097E5F9FC6E5406CE5DB11DAAC2BEE09606F702D2B2E0E58D7B76DE620A2BD9B1F669FB95924E5AF1CBE9F884A94D8B7FF0083F4CA28AA5594F7E3EE55017718B26668232B2E06EAAD98DDDC8AF30B1769E98C9C268AFB1FE4AE79BB64F7D46B229D6BECD328212CAB2BEA281435402FEE1C51B3389306CF963F24B94384D9FD244E3F84C7E266C3F305C0FCCB308EF33329FD32DE97E638B7FCCD6A639BF8B68F9C51A2306CBD425A06D9D2667925BDC1B370B7372886E0F880C92D352BEC63BFE16F88FC7E47C155957DC4B58C7C9DC3EC1DDB0CC1B08387982EC7A255A602CE65AB980F4CA76CA835CC37B9F72ED9D65953A3130EDD4FB964B3885F0473885D554A6AB129ACD4A949BE277627534E634541655DBC11AB96C6CEA65F33D12919252B134D36469671EE541E105FF11CA63BA0FCA4C5E6509FCC16B8B45A96AF3321CDA2ABD937D6DBDC29B97EE9819C74CC0C40D4BF241908429072311BC65522A8C797B67D0AC996AA36D5106FC4CF2CAA0A5131C1327B667822F82E591A7F0FB95C1B17ECDFE1508E162B2F1D35CD40C3CDDB5E54D7D93362965F03FB8CC0032540A1959C7113C375C4AB9A23528BB9CF2F5FFD5974EB150FDA1904165A3F998AD4DE37DB87EE091D2873AFA951A070008534FE61A9FB65B1FB62ED7E48C2AE72915E62BDC2CC90B6C8A5D1899BA99929BC4B333179819750BB53C33C5283A8FF00E080B8819443E258DD4BF51C324FB433FF00635E098F04A71535CCAD69CCC3943C905BBFE227516F516E3105CA42C50E63667E26C871A992082554B3BDC2A6E511C2CE6562AA74A2A57D4ACE04F69A505B94EE6B316D8608A0FB9FA9A5546FA9B39979CC68DA81E58CD37E7FE10C2A52EE87F6C5976BBB57E02663D2BBF6B3673FF8E264059CCC5C5DC829391F8804305BB187732E330DCB6BCC79E65A2063A0693F8FDCB725BACC0F7670B970507C264C01E6610E0CE17F88036BCC735B6A6C86A58CE332EE63147510CB9B9580BF7173B8D5BDCA2AB0AFB65CE08A387EA5BC4BB7089D0BFEA276055E13C911B64D87DAE3F55105F5CDE7298FC10AAF6C759DA54AB38EEAC7E0C010886ECA811EDC08C75A3B2E90FF0062203C05FB7703B4B945FE503687DA1EDC3D30442DF72E3FB4BFFED368FF00312DB14C67FF00313DA5E604FA4A06C7D43394D4C79877B235D44C5660270C392006ECA80BC91C15702B54CA8C21C19946A00AB8CCBA83652C9B5B15BCBF72D292E505C7920C724B99A594AEA5AF70C697318986563502988E698C6AAA6FC4AF0B94E581C4720B28D61959984B40DD4B8676C034C01C66364A3C1881CE62F389C789FB866F116BFEA08CE25437751B7F1094DB9427E6562DBBBFE513109CDA7D105FB97F7183DB7A2BFC4A16FF00340CA0F043A401E732F643C28429789B96D5E5A02E04DF9B1C284EF169C3F94CE172B77E5807A205F8AA8745A0C8612E7B794D7EB8A878539E602806F0A208520B715BD10BC82F496B37056632FCCC9A07EE50E54C14A378E0CFF51AB501A260965D1533FF00E429065D09893C0E2AEF3C660B291C313EE149C7315EBFBA0EC1366FC2DFDB0C000E0466A06BECC1FF00660C6704415C57B62D261F115A1FD31972CF98FA2A23FF00514ED8DA61CC3EE9746E5259E27D7C007314BA065635298D1880B89599C7999AD991B85DB87818596BF02CC512AE7EE29077342CB636D620DB93EE15C97C47C0A993808ADD32C38B94BA23615982B5F697E636E65C2AAA59782066D80F04CDE0FD4BE1BBE3104E598B3BCCB392551A80D41786A5350B5B60979660DCB1689664CDE6A38CC5454C39A32D616B029751075C4A0DB51BAF2BFC8712C87F07FE3012C2E68FC0989F381B88AABEF13162F3531B893FB911618E49BA882B4ADDAB604C151B6ABEE22D9AFD20896FB4E48BD30469BE21910C7305ECFED61A0025DB72AF6AC0623CEE6215841F399AC02BC408C8CF715F2CB20D423894CAC4A3C59E98D9300BFB8B21405FC910A36FC0A8D361726E39064973A04ACB6FFF0092D4537BC44B9738C8760BAF02DFE2595BCE977DB997418FFF0045855460DDBDB6E8BFDC6AA10B57B227BF215714500388498351D40BE2A2CB3F311545F99CFBEA3176C6DDB1DF81A223DC4F8EC96CA9ABD40542BBB809ED0794C7530332D92DE04BE100EB339CDC0CE252954FB9928662ADBA89FF0090BCCDA8235149A3CC28BABFB87A113BB94D428E250D18A34CD4A350D63284AFF88B7BCE27297E61D20B2E8B85DD136C1C6C6099F0CF130499DCC719217BB5F114ADC3BCCC05730BC4C42F4A8A945D96CEC63D4568AFB88B4B1460C59047AF70EE6D5780F6EA2C11DCD7F55EA511973FF0D4D413584268F5EF72EF20F73689FB8831CF96E1B0536443BC6AB43678962017818260BBC2E0863EFB4AF62BE2687E862AADA82383F70B08177294B2734CA37A2715914434A672F8A982AD3865B91895190770FF00D511CB6F06088B425F388DCDE0278804EF572788707511DB5E099334B031C3F53367DD0CC0FA318446805BA3F9FD47CCC316D66DA9C231EF72EC57CA57F7165AD39979517E1A32C41561B1BAE0AFA84AB8791DCC287E317FE546D9E656DA37E600D919731778971733897F0FA94621958097A3546756221C4A94D61838CD32BB602B70347B4C1A106DD46DAA3C42D2EEA14085FA216E251CA78A00CDC1F32DA693257295DCB327998E484754F517BA88D61B940CC0519FDCC05E3E2B3B8D709D46A039441AAF70784D818F107918152DC4699FDC42A03646D0FE3CCB65428CF98B79BCCC6C5B00E75EA61B704B2EF11FC5111805AAD8D0195818E9CD97D08E1DA7FC54AE9098203F11F6E5F88E34AA3B97F2C69BCC710258FF0064FD4B1381F820E63F143B300643A8BD543995D32FC098A13CA12DFB573425FCC650523FB9656862C1FECC5FC0C902AA3ED201DA576E734FCAE0248952D103FBB9544602324D97F289E803A8CB688E84FD5C2A98283A22652EC8F700E9E22D12C76685A5EA0BA0483A08D35FBA23BFC913AC5232E32C6E414F102345C4EF2B317D810026AFEE9693408B28390C79627F12E1492A36EA66C5A477C5C6199739E3E1B971DCB973CA732E6998F8A3994F302A123FECBB29DF886882D4A501B5B01CCC1DC4BDDDC05D05CA2D54BF50794CB371A1B122EED884D4A7A10A1D4C7A8054C17CC167A223A5B89D8444312DB73E0836ECFA9738B668288841BFA9976C9E660C972CAC625F32FACC29B2277A95494F7715ADC2167311C312C27532B88019FD235E52A1D7E7076C200E0653DA4AFF00856ADFB77282D4CF2BF50E64B4307F133FE83062651AD2D7DCC79BEDB88BC1733DFE25ADA8A7710CF471322F75CCE0B3B18B71FDC325B7DCB542F98BD7D51532082177FDC1050F8FFF00607B6CF05439EABFF84B61BEF11DB56D4C2A2DC3EE36571A39CB4019A7097D630CADB87CC4BBEB6C78DCB4BEC5CA3B87922FAB5D2C6A871D9103072F9832233A4554225F26E2DCBF99E4441307F343FD97230BDDCC5B52DBB8159074EE25DBDA36660F7083CEAF85CCBB0EB19632ED77082E38B10EB645352F607AB82325C1452A5952A5470EE29751F95BF95E2707C32CD8C01DCC0D7DC65A0C5620AFC4CB4C50A09E13DA146C672B2C5A1B96919352938FA815223B5C15D43424C2F6430142216BF8D218C1A430E998ABC5111D4BAE73D41ED98B800F72CB99605220C545538D4AB4A0ED9AE1C41C0FE6357B2352EA025DCA59750D8ACF131AAEF803733706727E0389666A64CFD92A2B28A58D3E26D4B925180676BEFB8AAAFA6B076301A575308DD910B6EE34CDC5398A77714C4E22C81783D2555061F0565174400CAAEA0E5F0D206EB705FAE4EE09BB8205FEF0515B5E63A00CB533681585C4D9A2B656100157A870A03BE22A1ACB1A7FC17F104205807491A613BC8FEE36360E6C5215235858FF00B352BE0FEE2A9115EA6666555ABE04A6A962A5E0241615F7A3F98A06B6F7305315D554A0435D8858F2C167E202BC40C3B4805C16E2A272841A2073EE609A54DCA93B798166139B96713A1623A9476A5BCC5914EE5DFC096CBC4B972FB9CC59E44F352818C4AA6A16E91AB9B948E216BB2D586E5865A85312EB8625854C76A468F08604BEB32A5ACFB973597D4C016333B9620655F45F98553A8A0E660EE64031154ACC7B15515108DDB32F0D101E0498B96E25A2E2EFC45E9A983198B6DC2B9656B7714B4FA85D7F6834459860B978370FDACF5FB5FEA1825391C7A110E364BCCCE398E32EC32C615798C0638E47630F0DCC461F0F98096CBA2E238961ADCBB84C43B942E06C963998B101A7B3A626041A0039EA0A24AC9773807E62F76474AAD752894AE9068A4175511B7C4716EFC419B401CE214C156CA1112AFEE1AE5E412B1C5E974A4A60E5BFD4B3B238181F52D4836C4CDCCF42CE5D4C01B757086F68283823A265E650C53631005A35D12CAF703A664F12B58941C5167D663E58ABB8C50E17F30549CCD2072C56157010DB8874D86492EAC4A351CCF38D3E0DE3DC8A6C45389870B88434ABDC55DDBE494F08945D3F3322D4E6FD52DC857898393EA175889B59639B858C5472A196BB4942EA7880376DC07658A72D4CB4E239816C360A8D3100BA9646212BFC201D3310AC451EE0A57333611A368CEE30841F10B0D6A2C97CC41D45034A899AF303A952AC837570797514ADC32B388A3DCC1DE20E4E11C969C710B040F0271B941BBC22A256431FE44D084023B3B2636618C7B456665F657F8416386862A5ECA6EAAD1E7C4152BC6214D13270F894A7129568996ABF982942BEE2968CB0D27C766191F717F93275D73182B826B0BEEE7F1D403F014FEA12EC5C28A3F107392B948CBEC4CA82E6682FB9FB960351EFFEE388405E5665A87E2D865C9C629A345A710096951CC34FE541D43432151AB5A380981B176FA9563004608E5CB15973904E7EBDCC1775F714534E3B4CFF00715617CCCD74954C9A8A0D4BF1629676E61D8D4604FB94D4E2166125A530E12207EF82883D5F7D816BED93F864252EA30FC6B96EA366A206A5E3DC94A4250EE52263C9222AAA2BB824AB97654019AB983130A94AD401CD6624486B826E23057263D4C79FC4D8B7D4A1D6AE050815B28187301BACA72623B196ABACC391B8956B420F2CC9C547135E65325858B897A17296F7EA361A9B1C4A180697E20CD5AE2043B9D30D6960236988A175F51E05E7887692C1A82D198995EB216535EC7C9EFDB1A2B08BF81331C28462EE5C5B2A36AC5F222D9B5C40064EE8407D5C0EE8E79B462CF05AB3415EE2BA2DEEB1FF009C94E075E10371F89346BF8888A0C3D046E8606EF0FB8E79FDCA7A3CC415A17E62BC1FB9474CC7B9B276198A6AA1AF110456D6E65932B6B133434BB8175518B622329530E08AB9C47B5E096818344BCD16928AE35E986E5BCFFF00ACAF57B3FF00709A6E59AFF728ED3377FEA0065FCA31B311A9C19BD616295F2181A100D07106370E773C932428107713D448E19862177F1C6A3DC365405A94E598EA3F07A461C23C451940C630CB729DBB67797B4326660B79840451772CD5FE111B0C6A770A781016AE001A8F1C54CDC7E653B59EE34C332E2E50AE10D9250AA07B8DA07B2A70857D452D2CB71A863ACCA9BA7DCA5B70D5D3332B64D0CA7ACA3529E02A250E2EE50E63F07D032B0D21F67A132D4E497457C716B9F874F8A4993AF82B241F333D3F8B2B948FFC695CFF0048EB791ACCE24FFF00AE25FF00647B17DCCFCFE6779A9E17E25175FC470D3F110E1F881D1F888E885C2156A6E156352900AD3886BA8CA34025D1EA6D40C2539C6DFE4A8400504B99A5105CCCACA69CA786A1538945CC5C6622D458E08A516525BE03A8253B8B5167130CC596B01094C4C6E5863B9A54A884A944F488B9096EC542F6054F53A5FC4BD6F10A199972EB30791C4EA97E58315513AB5CF0C0E98F12C66A070C4B6D85544AEA0BEE04F28162CC4F1C329162A5BC91F2208718AEE2DCB57155788D383EEE58D311432AEE0EBC7707C95981D32C918BB2CFC252ED5C5A2AB535AA882D659BAA94A45ABFF0CAF0472966E32B89A5820DD3C0A8F5A98BF1F68B8971689714353E91862E3466D2DF858B98B17CFC0A22A658D4C2FAD7D445B99873A27E54BAA6AD8F03B8542B4E1E587D0AB4101BA22ED02DD9878F3063A1107E18BC662C58AC565A5506E516434258483669E627C040F7062296E3DCD447510C44A9A27A4602A042C523D9FA9434FEA53331C6A7D4BAE580DB12BA188FA864508A5A0AA89E102D909931C4A729CC41C06196008D197114A1D40E0CC5CA61C2DC6F60850C8540301E63C8FDCECCC9CB31B6ED8DB850AACAB946EC8D564B815C5C10C44B573CC15A6DD440E040357F89902DCCC99C206406225AEF1D41957F3645B1CF886BB5DA5E20718E8F819F78B2FE0A98DDCCCBCEA5E7E171B31855CBBCCB8CE34CCF03F895FF0004C1A7F1129A7F6D450DFE344765ECC0EFF1E0F7F8B0CCFE1C2EE735DC492CE2BFF6C1076383A94B7314AC793A8B72B4F118D62607C463D50051DD6E5D559C532A4CADEE2B6B021D0C8CE8DCA1D7C0083D44D2313FB49821D11DE5D7D438A4B032E29C31634605D24C7A3225DF85C2858F6ED35D5E8CB0C27D634CD6767C13E0F7312A9B98F8753486A63B8C5A949642994A80C2561B80AB2E190415C12A6B085836BDCB252A274DD4F6E66C6534BF52B5550238711F3CC3C98E8EA21331EC329C030B09A836BB8A158B8DDDA63A817A08ABA31224C99B80AE3712F07E265DCC10213605B2D2FF0011AF0825E710158183952D9962101C0FE6004AD29251E5997CCEEA5F53C7DD0FF61FF107FD8C2FEB9D97E57F93FDADFE4B67F309FD436BEC514B00F6FF00D946C75DCBC27D3714463CC3C61770B704EB06789F50DAFD21157376BF2CCF2F70EE679C3BC5E71DA4FB7FB117A3DB15595F994629DCCA35DB2C68C556D8ADE91198B3512C48E1A66A92C04039B5351A90BCCD4AD1897171C40AB9B611AF58A3C772D6E95C1AA5F2CD6B46B24B20785AF67B852BE0085B23D1F0821CA5E8C4C0C3BBCD750961A2B93EE244A0B5F1189B30788170CB72DE5A89156CA74BD32A301F6816BF2A195FBE05AFB980E480ED27A207C5FA42ED9EE3565FA95196858E72877A97A65184FD40395072FBE92D725F6439421E07D4D82A61DBCD40B0A26B2A977EE632805EE14ADFC45ADD789736420ECCB062E712D9686188E532C90B34CA3ACC319AAF5129692DD3CCD16CCFE51C2E621D6A22851D41B189410BD77295B7302585110D0352E32A6DB2F12908389B2DB856F3017AA966019E61612F9966CCBB8D195B01BEE5A051CE52DEE7724F24043CA6B3DBEE0F394BAED9E60FB25A329F48ED7E3453793C22C529EE579F352C9178237D894AD32C6412C59F842BF529E04F149B9A754FFD8A8EDE1D7F31B7E9FF00E32ED0FA3FDC4EBF38CFEDB0FF00501A3F63FC94B01E66CFF339431F6AFF0063C83EDFEC1184FCFF00B0374F51D83FF7C4E68F5FF31C015A537711CB3070446180298C5CC45E26896A415C1B2A022A4B99864687F31C9596A00BC4DC9D40276F5FF238E7032B20B2E786F6407F7B8929B72DDFC8337E60FB19798B659A57F0FF00EFE230E19CEA1D13D236E23E32FD4F48BC47D4F51B999CCCCF72E4E018E8E36CDA1AF8E7E2A50D414E4962C267E196D1C7510A658DAA0F2ADB2AA8DC419170782CC16EA00C45A19405245744A2C2056C821BE65064228A08EA252B4C116F2D428CC42C6EE166AA58A34F980A9AF31574CB1BC9DC22DC11B00C7DC6F1A9742E286A082EEA30CE62B6EAA362EA997302513A256981010CC0EE796186FE07945E53ACF620F773BCB43396365139197A496A35717C1DD38E813EF3448FB7FB13A5DFFE11AB07F1FE449CCEDAFF00221ABF4C7F913FEA5EFF00C3F70CCFCECB79685D419433FF00BD4FFDE3F89796DFDCD8AFE20BC2F0FF0063DB68E9FEC198B04687CC15571AA8B3602B1381C4BC4BBDC7B95281733F3806888835F042BFECA15FF532B436A8D9E981551AB1A854187982894765CADB017EA5633AABB138437D3976ADE65D845D47FF007DC5CCBFF9EE2FAF0E328E0ABA315A0FBFF117D7EC478BF091B31F642F82FB3FEC6CC39EDFEC7FE0BFECC1FD13CF4D0E54429407371C9567DCFF00F759EA2306DFFE789EF4D5C4688220C059D8450CAA59C71A8B7BDCA64A200A62680922E83069CB703A468D85C1150CC0B2C447DBD3137B6E54D4462F500F355017C3AE6032311559CC0B549113CCC8A5A9AA003C1009ED01603C8B83AADF4C05586D804E1DC680AAF128C3A8EB6DEE66D1AA897854B3209C582F96CC6685DF5068EE3B59C731E876C407FEA62DC2EE01E60F702B640F702F3015B84D7E1ED3CA67319ED08CB9F8ED2E1F062D4CF2C58B147E07F07A4C7B88598FBF8D2B34544AF8FE66985880287B82EA0D5972DDD917C8A9FC64B0FED8AD9FCA2F4CFB450C7E6C7FE9265C7E44714F1E021F6BE48F0ECF11D7FC112D0FE23D47E22495EB031331F8223ABFD328285EE9FF0065663EC1FF00651FF0CCFF00DAE7B5F51BB375E2174E866E57FEBFF279FF00BFF912E7F17FF226D8D7E4FF0027527E3FC94B95FC430E6983BB2EB5209555B8161DCC58B45B5D11D73042B7299DC7620436D12D55EA778C0C9F736F199C4D4CF2B716686560B2EA2ACE89839F502F0B85681F516F617155711AB86CEA66818398E58AA4E8152C63F116F71D4578C6D3881B98502D5586E738F84A78B86683503428DFEA033D3306398DACB3D0A876EA288282855A66DC375CB7694B6439AA1C47EE0EF6C3DAFD4AF7987913DAFEE075F1CB9F83183E3E1EF1F28671171F2F8B0F94A2E60DC516F3A6A384A9658EE6B19CAA5B03DC33F64D9112A279959DCFC01FDC5F023696F9312317F0708C2CB8B1D4BC3189288CA633C919C086A5EFE1EBE28257A9BA3AF72A05583AA88D152CA0BF981D1FC4C9AB95AEBA99305333CD453D54C05B5F70D04FB8B91286B98A8E1F51814C5631997D31AAB6D9802E1EEE601B89153BEA36C77125E1D5C16D6C1704B6F444050C0EDDCAB5B8334CA9D170CACD133822374E65EF284BEC520576C0C5918358850ADD70440941758810AAAC4C1F7DA6CAE63CC1BCCCBB8794A74C35CFC00F881872301DC29CB2DDCB463E3E2CDAFDC2ED224C9DC61F3958E3F1679A7142CC404143729C16A2EA2DBDC2B9500710622208B162A5FD2F24AAA31EA0532F708328521FFC15CBDC5E6E53B97173165C53E0B67C5E273171F17E22C7DC5B8E2625FC1F159B9B0BF50C813D4282D221CDE670A32998DC5E2FA968400FF8C5B3305A4217C5841DB030C06AC81BB869630301620BDAA0D618805634B6414BA8AA50096082AC6BC431CB0BE9253B3EE0CEE05BCCA26328350A97C9544A0E1E258A46111537C08350CACAA98A6A898804BA5B037423A830C5345A97B141C468CB8800DACCDBBC4D8388331841EE0BCC0D5CF2431DC1770F385F4C3CE7B7C0B4CE02D29DCD77F1F7877623B8F97C38B730CBE25592ECB1F19F8A8204B2E1A6146AE032659747642C625F9AF898C2F3127BFF00F6FBF35C26565CBA97E63F0691CBE4576E25FC692CA967C3699B652E37EA52C5DCBCAC254D84E25959304BACE659D8F337826E65302AE58916AAB7CCA004673E2222B0919A11B6D98EDF8980D0895D1D45A87F12CB33A2928BB941E20B72872DE659819D188299DF32A5CCA821056096C05174F4415D81BC4CF432CAA2D22A73A9B31CCB3CE8E62AE14165CAE128820DE7D422D590684E344268ADEA0DE1136B12626D88296B11F0622BC9D4026CFCCE61F5143364F0A1E7305DCB57706B087DC71D9FB87F5A768F5079588FFF00101CCB8F3F02751C1CC3EE056E6744BC32957F140818B697C406E4085B1A1F00B407CD7B976F32E772D2D5A969662B2DCCBEA5B15ADCC7E3ACDA2E25B157B9997E7E47E1BF33B40E27798E0CC5C329800440B630B30D5C42DBE22500AEE5948D929D66CE7297C984966654BACF513525477151B0296D877A19673373899CAB7146FD655142AE0B93A8225EA175C789C5758CCD82A9BCCBCCAD9A1EE2AC377FA8DD9CBB89179E55061C238DF1DCA1696F72E35308E7CCEEB57B8609788255149C6C0CD17C030016C11C8480618A50E58959D11560D132C5CCB616128298C2752B417A31168160ED070CAA182E35CA015B23C4573E504334C799842A4D00FB62B41FB87943CA5F7703009B80EE56B729DCA0EE3E53CB2CE6154F73EC830CA1542052DDC4243511012C01CC038C7CAA40F7F15667131062394A7729BB89EE36E657B81EE5589E18DB128CA4AD6E39EE76997C2B3DE77F87BC7119333D545546B27E66A0FEE66738362E58A6160D34554A524479353848DC1512D85321F72F747D4C29418163822EE4F5023D4C0C41ACC3A018F4D4A5B26A60A0112B32F10BF9B1B3742F98ADB75CC5E9A798B62EB88709E660350CA39EAE566A7AA8916DD540130A3B8B579617E01854A583529BDB2EEF304AED25046C99062B05324C80803FB8D37B436E0B6C638E79EE29BC269A8037101025C8541542D72BB5AC096A28CD44381896E90F11AB55F70B74B1896CF188EA0611733164676153C999731DB332E66545CA3CC414A52A00BCC010F3F864629598EBCC28AB9EF01DCC798794DB73DE646E7BFC7DA22F729DC5F0CB7736DCF68F947CFE34639D5C7AB3DE69B94EE51E661CC45EFE0ACA5EE53B88BDC5BE907B8D88045E581FB98DB14E1F72A5647BB1C3006184AA2153FB86211EE623B64898A61955F3C45C45573125E2D62C01B8C50E0DCA1851886AF04AB32830AE2677E3974F28539658071E62628B8A958F896A453C1322AD58B5B6E32DF2EE3A887A21500ADEE1BD67D51B562A5340C134EFEA0314D42BB710CB0E3CC5FE888E265A63CD2FA834BAB8AE887A80A5CC34B7F12BEE5D8CC34A51C2D73280A1CCA4846D4C44028B2CBAE1F2E20AB1897170D53296B364CF3598A4E09C4CD6446005404F2200ACDCFB46A202B8956704C92DC18D7707D12AC5A54AF7195F15E17A6654CD2C1967BCA57328E60AD6506768B36D7B634DC569749E0C174227DCB4FCA2EB52D7A8CAD74FE25FA1FC44F630DE57AA21FA56252CC9442E5E4986C63DC1EDB359CCCC08C582C115683E9105C0E39B97CDB1C456E9E38EE0983129C2F31CE55E7EA61B5C4707412CF07B80528DC2F425496E2486CADCD979F1129818D31C046D8231987567C1002933E612B73B82557509A18C711AAB2D9626C4CEAF0137ABB25034260514CD961EE25B0B2B640C1C26521732F9614CD9782119983218D06CB9971F88B5A4728769A8D183D4C58144A18959C442E6286EB6F13582A7914518332D1798F86B995B6ED97B1971CBCC4B9B9C1DF112AB0880B5B3394A8C971A5C419CD420F33AC54B2BD3332E6DBD4A5B5C44A5AEE35DCD8BA96099E09F72F5453B4612DE4B2623D4AD5442A61348BA95D8C4C92BBE66BB52C1723348E86E1BEE0396088BB0200930DFC159997A030CD87AC09810F1CC20808686CE676B66BCC32BD9B94A86D76CA347F31131F9A08C1FDCC17F91104A14F72FAB1FA96EB0416603D0962B1F411EE3EE5BBFCF2D294FB664BF80566D1CF2C07B97E79825B7F98FB488256A07864636731C8E12D58B7995AB4F50C59632FD6594DB896185AB96146E3A261C7EB0DA7704D8C41178EA238FB862894069A226B3FB9B86A5053641A2D67510068DC30E5CC0D0DCA677BE250055D12F3484BDE1966570C561C90A72731A4BEA02EEF110EE51AC2B4469C6D11B28271330B5483662CDFA1068F4CC039592D085C5573518F7752E67A8D68215A100DB094A18C5BA217865994F361068DAC470F882D0843B5B95503036812E2980B6CCC4D17982DB309752815E229E27608358C9313737139254CA25B46BB9B6EAE5255EE0341286571355C339250B353B892C5D91C2D9845BCDC58E750659843731BB61961C827FB020F357016A9B4BBC45A86A737107982B7A84B98A9EA1954DBCC0C6666B10D66732E5F896EEC9F922B6A04170CCFD232A5AF948B292A148665D7AEBE1816200752D2EA5B5D4AF9666E8B38B656DEAE5E7AE588C023C46F2EFB8390D3151BCA0B9B047B40F72CC0835099D0634E027506E0284A398C04A0DE21A0200043773718AE91011B5E626A806E22948F71A576AEA5BA53EA21FE728F2A998DE1E2586816CA1A4A943ADC7A34F102F65AF8940D90450307456D19CCE06E8E58CA85E65E95DB70A115710B772EEE9CB0D8B1E66624A2C172B76D916D831297714673100E598198A27B80161E67433D91057096B4B51F6A5DB167250A372C6911F503A5460AA692F31141CCBF36AE62B66562E9511F0945440DF32EC5E63466054A218CC1AC429962C7F041AA97D4160FF00536CA0EA0751E3532964BC5CC39AB81482B9855620D4B21695F02DF055B888E65093C20E4D75284315341A8D585DC6DA5710B6D21852D30CE5DCADDDCA0D302B714599EAA50ED18711F01EE640004E02B103B180A89999975A85564F511C9BA636517D4236B8D630225222B388A80C359828185F2DC4AB0D7105CCD44DC56704F11B035702265BCC429334AD4434CC47A87385A9B2DA9C459264AD220BC8BE2082E5BB89012E345F06A581854A770F3340BCD4119731B5BA8871B4E4602C3CCEB2BA6A0CB351B71492CBBC442DDEA00C90CAE1812DE104234800655356DBCC3C903B3352B7CC7347220CEE656C9658941E46467988C1DA5FD446144E182E4881473157DCA70C2C5A44277A86849736EA20E262EA5B74EA2E2552738C4A7C0073146A64C5C6CDC7C4D332AB530B93E171C4AAC31ACB730C60CB899398E31C10611FCC711901660CB336BB95184785C0B4D33195C752A15FB9988241C657729751A98C40CD37EE21D2402536C1706A261CB4C56873DCE0B9C8FEE5AC363B19A165A50377D4AA679985DFE63E92D2D29DB8B97251184DBEA6A9C454E88EE1B208D6123B7F0812816770C4C6E1FF00A25C3CCE6E122B6DA500AF2627FDC03C46BB9631D4BA0CE382054040F1295B408C15DCA1663EA3892CC8CCB54020382D4041C2B145B6C4528A6E0116D3308A6198217638585C3528ECC42DB8F855918F72D01A25187E5177E23FB845C7B9C62E2A3551E73B94FD60353B016C34676A601BCC1E2E8DAFD84B41D1473FD435D69AFC4C609A87788B1A891F71462A2AA023072626903DC000A8DCE3189556E2E899A98189830C0130890CAA558A87D53913147889228C05D31A1B94E431B9AD4A7105D1286510B0ED129606EA65A5468DCAAD4724B318211832E06D848E0DDC682B6C15BF3C45A1BF31BB854977CCCE516255DD788BED04C069DC10E789C0DA152D6E31C975C41A96CC2FBC0F31BAD800A5B5E22B577A8458FB9533898A2516FD42D4B85D9650A096430B840A0858AE5656E98229DF713304027694CB7F64A14AD42CCE4F311D0129C198754892E099707EA20391DC4082DEA61D51CCC482764C8E8FDC26C8DB49751AD5EE0F29B9BE081549A9420221AED850A317CC3A0868B5C66710C1B9A46BBD46E3311143867005B1EB44A322AE0C7FB457854173CB2E94816AD01CC609084B01E7CB1070701FEE352E34CCC86139150D0DC57F98122CE088B066042FA85E8EA00B5802762AF0AC6169CABDC2EC2E59FB4B3C44EF13C62E2A5A4AF3752D5881CD4D6635CC70677045A59E62AF69B4A7DC6C660AF04AB35056133C476E20585318A0D31E43899ADE65B16253F00CB880AA2167129ADC71B588A0C915BA317296C605D112482E5E65C14BCC1D592515DB1CC0D47531546A15B081CC511315301BD6FCCCD001B602AD572837E52D3741C4C82C22E9368D2111CD4216C6D2D01FB8CC9352D1A0B2C119EEA58B2DE2366B31085CFA873ADC10C057304CB8F10F4223E046E0891A6F304A3A826263CCB9AC0EE2296C775281B55D4E0A352DB50C9B5C1B055F711C3C4420CA85ACEC98F1CCA53246A1AA65380D4A76CC064A681962A16202D4942FF68662ABCC7097EE3559CEC858AAE79856CFCA1A5ADB29E497DD71760C46D1584D8605CE261B5CC13FE4EF129A83DCB876940D078BD42C534ADF6BE65C0E99767490CD2EE986102E99462304A4B5040730738B943CCE971D4C9AB751633DC1153915BE2F3896701BA29986EE0DE1C39FEE0C6A2140C4A98B9746096F5072750B0392238215332C6073AA8B4475B80D541A57C008E730A6180A19872584CDDA532AE13F52C343B8A98650EE59C3537137A94BF32ADBC4E62E0F55705BCCBCE19D882D25C720372D71B96F90951D133CEAF9A8D946600A2D31326C4B5923EE24D8EF733312CCC7DC7093F5332E0ED824B5844562C2B4CE7B956CB13BBF12A538805DB31F24C4064C457AE189828D98EA56C1960BA1B83AB5798685C2025C1EA51193CAE3A51B94EBFA94522DAE44722AC98C40361A20E21E234746B51EFCDA6E0AD741006DEA51546254C368B5EA5AD73CCB64BC6A28C9CBD40ED8F2C33C988DA1FB8162D26FCC40842B6020B03D8710AAAB0DA2164C94F5A8CDA6615E1E652B0C5E7772ECE0B0C99592A0C77F0985AAD0B824F58C515F500AEAA0EEAFFF00D9645B2E21FF008849EF9D74F12AC1B92B1387D6D68E385DBA80163D41C55447163C37D0470D2150D9794F1C40B1036757CDBFD475C3FF00410B1E5063D4A0AAE65D96E22883945AE260EA60433DC259D98F52E3C432BBAF1051AAB229AB96D1B96F09CE9956EE378101DCC1C2731C467A416B020C91A963C994AA6A2CC01DFE6170C230CE90178FDCF08D2725504A123A09D41765CBBC598EA346D949B798EA4CE398D580898131EE0B053F30D1684743F98B5E152D6A813E4991B311045370280512A8A6A602896F398B19C455B4C94127B8840DB2AA999C9C666E38331563689B08E21570DC2025DA7500CAF1BB2596A965906D66C20707E635D8EC93881940E032ED835079ACC401A57A8ADA01C1144FB214366E2C166A06FA06102AD6E5ED5C100629D4574B138466595BD4A70E663162651682AB458172A14D7100B66200B07B6079225FF1119F7A8A1B7997CE6163DB2E84AE1DC4D895F337459CB708064D66E29C5226A15494F32D3372B0A26087DC089A4077105E01A0C5757B98468A07F913FCACEABBC7A8E2AB27D0F086B54181A76C91C05D5E6371C2A2965475AC40B94EB73012C73E615C45DE2A74A199CFF533D620D39885E37139331500B5831181B96AC6E2165CBC44C528C661AB196ACCC2D83C25D4B4598F511D1B961122100D665703DCAD11586D3732CFCCB590F704586286F50A629CC5E5C4BF2CB095E3AAE5821A61C45B979854363305519F33A553DC56AAFCC4555BE6575BD41682A6D0889028DCBEAE9CCC5C67C4A2DB88CB77AD4B2E0DC0D6B52866D45B52592379E788BC8BBF12F151A74CB296A8A20D3F88E62608BC455D51C240592DB98CE18023406058B2C480B7D46AAD4EBCC12AF3E201B2B352965EE6CE5347130912C4400D464FF1DC241F5A9A0331039299809D446405C402FE608264430348FB4E6106D4FA83E4F7369A3B17280A40814D4A85B2ECEE0A6ACEA0681B945F0409299266296C14EEE096F6C0AEC4CD6B5053BF44682C8C1125AEA2D168ECDC5B8791D30B281FB8B17096B8CC45A063D2EB51AB729808A82304B52539826E0178FD44B556A35F8A95DD598ABBA995226B0CBCABDC32CEA55588BD916853999EEF371F2C1995AB52A633F5115554037706CA802DB95D8EE12F536E710D70911C54F04C192A00D5CB0A23EA7019963483A717E58583AEEA50B65D4C0B8BEA0DC70B8D7E12C70620580A8DB329ACD66F3378C602973402BD546BDE2F51B0984C2F2CEC7A4616D7EA0DA61E623C33D430AD2559EA34DD54E70EE0D942AB8880A3ED367696A7996E5A8AA8A5901ECDC6553305A2FB82D55B0BDA15BAB98B2C40110B182CA4A145EE25396530A3E63ACB79CB820ED5E526EE28C90742B4C196B012FC1B98057EA608412B77C6E72C6BB23B65B141B2FB96476750AB05B0442E1CED5C7A25B17371E10E168237027DCC238CC5C2D19992C8206A996E367F9467A40AE3982736C0B455428D9930CA6231CCAF1825AB194630C3CB2C0900AFB8E8DCB8571317BC44628E177B8308E664C4C96974DD31BD1146E22844D0406528AE638573079BA94C09A1B46685705432AB9D8170156914A254A993884EDCCA17A621C620DB820101228D88916F6A431A621518B02FB659BD2033E752DC5EA0C6154C1C28724BF7708ADA0C8151E2545A08958053DC0644A2C2BB94B652C762D10EDF89682D68209B0C44A7F49545ED89BA9003771D3804517A3410B17A40762E21CC30B947AB31B3695262158DB1AD397C45487B25F5CC45310845CCC528FA820B0F01D4B284B6C2EE0D68FA8F4D9039D65990057F88AF459632839BE605805E8630BD3880D96B33051B20352566197329988E16EE0E1772A6EC9340C350227300CA30B61647A1F7008A71CCB980291B622E79A9764F4886F45E235DAE10B1E236EF431128205088153BE232ACB2B32928D4BDB5A86555C6BCFA98B94A6BB84B5CC600663DA08E1C411AC304C662382A5E2D9B04372C1A96EC6A12B0CC9BA496C52C96E0FB8933572E1BC751D3DE61A41C4760DCA319102E4670227F89597B810E2E164C554B4D406ADDC5266107EA336ABF0C62E83D45B5355F0395545762D4B0C128B23817109777040D26E2956016005B46A50DBF5002EA35E894C4CBE172ADB59ACB4A97C688B25253331950E78023283331C2BB8046288007198CFA8D1097021E2537AFA840BC3C4032E826E2FACCC9B5142CEA272832F0CEB172DC4C90CEADC6FBD04D6BE44B4B30CB9957B9662CE73297EDA8B3A7D44D366A04DA9E60896841C4543A64A81688D730A56B8DCA85E15883258BD4C80416C0F995AFEE0C8551459B46C851C20A8A0F2CD9747EA1C85AB2594BC772C583F9E638E197B9A56FC22294D3E6381D5E6032BA4F32C3DC75459DDCCF14E2352AC3C1306B8EE0E4A1C0333234FB97A1F6873A9F134532100BEAA34BC1E199B28600446C602B6EB896DC22FB1F501954E235C709C6E7A85DEAE36682660159A8AAD513271941CDF70D97AE209BA0798A65470AEE36F3CC00E5F509EA58682686DB941331D422F2152D7045C1D6E02CCB7D4CDCA258D414EB2CA0A64B515CD7E66A688955D0E67604130C2A1B17772F0ECEA270B0989B6230C4058D12AE2B653502A3BC928F0101D0EE25DE03528D02E0669F44CE35EA5038CC050B4BD4BB805780806CE7C4BABDCAA974066F703972CB70C18AB3DC005AB7A8E6AC117648AEB9F3064AA9610019940748D85022D284C67252AF6B3DCC8646EE2CB39EA283D1A941669D4415528663F10D63103B68072B95006A2E2997A268550307EA03BC0C518108BA1BA26F05789E8E7A8F5B2F728D1A573294D9A8071E88D23CEE62B22FA9995268BAEA01B814A6E3422F0D9DC6820CB3BB2475A01BCEE0166F12CCB665C4374357C41A18992C32C04CA4DF7BE26ED3EC9860EE0145985A5FB8A15D4095B188B9D732F6A94EA06945CC5A1F732B3883E515BA1972C0056BCC52CAE7A83F53C916AE63D1883D9118A854B2392A8C29C16E11BE210B8B20BF10461C46CFEA2A6489B53F5317998D159E265A4103086E1311B0CD752C6D25F1139D8EA50988982EE536DCBB5798679FCCB594BCB65EC4ACF6C78A22702C3540AE6642E1E4E2178C40E3B3E08829C40B08258530A3646855BC51116966602BA96456216F6C73A01A59224DB97CCB07F082D9A8134E62A5C1496906DAEE2CB2DCB05BCA59DDC61BCF8F8CCD28DC456BD404DE76AA80043ED1A60DAF10289B3A89733717A828BBC3883C8BA979DF7012DED10C0B6B6C15801E209B665BC635595FF31446980A4C095A527F10BA96B0B34D262017D30006D2FD035BB8A202835DA6ED5B96305B949393982D47D4A3134A9682AC664031CCA724F12C92AA9C43D6DE482B6315B25581B816D293998823985216EECC5231511586610732C6B703735820169188066F34C54B1D55FA8159C87316EAAE518189BD432C544562C60DCB52D61CC6AEE545BB9B541080A2B72F2F51D8699BB2D11C81998712EB9CC026183EADCA2651772AE588DE74C0B70E08C6DEB9C4D18A67728A4799E4062C809E636433C54B06C9D2C4C4A50F2CB79962FAE262B9E9130BBEA611A2605138E101C116AD6A01855F98637DC09B852BBC0F72F76F771CF004A008E65F3A890D83C622132C0043711B698C0034D4514E602F68642AD2C2A02D34CE24208B03328AE2E66BD3C4514E044073865AD91C4131A0DB2A58C4554AA255D388AB5FC4B50B8ACBE58CFD4829DF12895E105A10BE5456A1B3171B2DCFA2142AEFA830609AA8974CC2EA953ABB96738F72E8B26DCA2A395E8CCC1C9EE12B8666A61DB2D0DD7DCBD0352895AA25D01D6A038128B396894650EE26D46F9624550D4CB30009930A4EE05241BB3DF4DC4177CF15282B412608CCC87D5023AA84645B9B882C33CF516C1C4329A38C47029BEE348A950478663F1361AC46AD2C41907A8BDF23DC3424053299643D4C5D454A0A960C9016459B23A8546DA5B3A8B53A8EF5A83AA5DCB8A13A86D1F8853866F7B6210BDF510A1661C6104D59BEE232BA6216A5A7982AA0401EC1C10C9A35CC50AB8D577799540775136E23667961E52AC033CC5AC3C4D80FB8F02B980C87BCCB8E48B19FB104E5753908A69B8DC1D2F0F70E956D712B8ACF553B2AA586EC4840CFD471B496882C7D3DE79C7B9C06E5708BC27F90C34789B003E2356E23542F98D37507554E2597B13EE72B56E0568061F618985C342EFC4A4B75E20FB200D56B52F2103FCC1BB426158EE194E225A90D730AEC604656C1ADAB5C4C8AC305B094297F51097A6550AD441456B2EC513702A2F0D4F5064B80A89713759256A7EE0A43691A44C934CD2115DD2C554B656E5636BCCECE256D28F31414B90A864C07B60DBD880152A3617AEC83B0CC6DA6F311E806E3BC6D83574E23ED9194BB0A4AC58607FD32DA64972CEE54863D4C60C88361697241E1D4D9BC42434399437662FB2EB89C66A1C2FD25015A85805532D8277158E2611F74238958CCE8A2A6866380143CF9836AE1E266EFA945482F99B8787640E4ACEE2BA8F752C5EAFB8D6D297F888B33718B2628D5E3B8288B739A9528C97E12DCFC414BB2E20B9C9097CABA872B3F334AC12EAFF99596F66B88F77E108C5579630C29B94446E3C4E9572C53EA66716BF30B2B5C5A52CE184C9ED1C85A19A4961DEC3CCB28014EA5A2DCC56A2189A6A6FB0D115432E6D3D40C06BD401003FBC9437E02298598D27537497EA0D8822D4A24E8871BF505E2AA21DD32D75F72E20A3B250DE901700C96F7C4C4845F30528D94340EF996088D4DC70AC05F477199000941B06E6A5F2440A70E60E974752E6B5091AA6486CB719B784882117A990AD85410CB43821E32AF6C0286EB3055F2405AD4E369EE003B521BA654B110158275C8EA26D5ABCD4CB053A864597102268880DEE2153F0872350CAD01D43A35FC4BB2A2996B9EF82056DD4A6DAB85C0A4AE771C8CA6E899477162F77115828732C8C51CC694AE203234EA58DD4B2A87C112968F3705C823A97E6E22BE997C8E652969511903F9880118DB846F28B4E6527B8B46F73A16CB1D1894BC40C908BCC256A3B4560B594DA02DC110E10E3EA6CFB21821AF84C55367C751E67F34E7348D234FB80D13080399730C31EA2637631C3DC4ECF83F96268B7713CF89AA738EF39F84D7D44E236CCCD21A60BB8DC4DBEA5B88FF00286B28ABA2FE027B8D96E4EA0BCB9804907330298873EBE09584CD9EE00A0ABEA1A8DE51AEDF85BB3989E4EFE0D3F30946B9986B188052A2E340A872CE7FFD4DA363DCA703F130C3AF85DBD7C044B06A000A27F2C77F07ED448C19CE6AF71D4259827F682F2626D9E669F805B6626DCFC44B97301C0DFC32CFB613CFDCE268F53522B2D5B6260166399FC93169D4E7EA60C69F0FFFD9, 'a860199d-66e8-495b-912a-c5a6bfb81e37');
/*!40000 ALTER TABLE `uploadtemp` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
