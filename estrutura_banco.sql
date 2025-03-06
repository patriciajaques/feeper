SET time_zone='America/Sao_Paulo';
SET @@global.time_zone = '+3:00';
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";
CREATE DEFINER=`root`@`localhost` FUNCTION `GET_TIMEDURATION` (`d1` DATETIME) RETURNS VARCHAR(100) CHARSET latin1 BEGIN
CREATE DEFINER=`root`@`localhost` FUNCTION `REMOVE_HTML_TAGS` (`content` TEXT) RETURNS TEXT CHARSET utf8 BEGIN
CREATE DEFINER=`root`@`localhost` FUNCTION `TRUNCATE_TEXT` (`content` TEXT, `len` INT) RETURNS TEXT CHARSET utf8 BEGIN
CREATE TABLE `configuracaosistema` (
CREATE TABLE `conquista` (
CREATE TABLE `contador_solucao_errada` (
CREATE TABLE `exercicio` (
CREATE TABLE `exerciciocasoteste` (
CREATE TABLE `exercicioclasse` (
CREATE TABLE `exercicioclasseauxiliar` (
CREATE TABLE `exercicioclassemarcacao` (
CREATE TABLE `exerciciopontos` (
CREATE TABLE `exerciciosolucao` (
CREATE TABLE `exerciciosolucaoclasse` (
CREATE TABLE `exerciciosolucaoclassemarcacao` (
CREATE TABLE `exerciciosolucaoerro` (
CREATE TABLE `filanovasenha` (
CREATE TABLE `grade` (
CREATE TABLE `log` (
CREATE TABLE `log_date` (
CREATE TABLE `medalha` (
CREATE TABLE `medalhaniveldescricao` (
CREATE TABLE `medalhapessoas` (
CREATE TABLE `mensagem` (
CREATE TABLE `mensagemcabecalho` (
CREATE TABLE `mensagemcomentario` (
CREATE TABLE `mensagemcurtir` (
CREATE TABLE `mensagemleitor` (
CREATE TABLE `mensagempredefinida` (
CREATE TABLE `novidade` (
CREATE TABLE `perfil` (
CREATE TABLE `pessoa` (
CREATE TABLE `pessoaconquista` (
CREATE TABLE `ranking_global` (
CREATE TABLE `ranking_global_leaderboard` (
CREATE TABLE `ranking_local` (
CREATE TABLE `ranking_local_leaderboard` (
CREATE TABLE `ranking_turma` (
CREATE TABLE `statussolucao` (
CREATE TABLE `tela` (
CREATE TABLE `telaperfil` (
CREATE TABLE `tipolog` (
CREATE TABLE `tipomarcacao` (
CREATE TABLE `tiponovidade` (
CREATE TABLE `turma` (
CREATE TABLE `turmaexercicio` (
CREATE TABLE `turmapessoa` (
CREATE TABLE `uploadtemp` (
DROP TABLE IF EXISTS `contador_solucao_errada`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `contador_solucao_errada`  AS  select `ee`.`ID` AS `ID`,`ee`.`IdExercicio` AS `IdExercicio`,`ee`.`IdAluno` AS `IdAluno`,`ee`.`IdStatus` AS `IdStatus`,`ee`.`DataCadastro` AS `DataCadastro`,`ee`.`ErrosCount` AS `ErrosCount` from `exerciciosolucao` `ee` having (`ID` > coalesce((select `eaux`.`ID` from `exerciciosolucao` `eaux` where ((`eaux`.`IdAluno` = `ee`.`IdAluno`) and (`ee`.`IdExercicio` = `eaux`.`IdExercicio`) and (`eaux`.`IdStatus` = 4)) order by `eaux`.`ID` desc limit 1),0)) ;
DROP TABLE IF EXISTS `log_date`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `log_date`  AS  select `log`.`IdPessoa` AS `idPessoa`,cast(`log`.`DataCadastro` as date) AS `datalogin` from `log` group by dayofmonth(`log`.`DataCadastro`),`log`.`IdPessoa` order by `log`.`IdPessoa`,`log`.`DataCadastro` ;
DROP TABLE IF EXISTS `ranking_global`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ranking_global`  AS  select `exerciciopontos`.`idAluno` AS `idAluno`,sum(`exerciciopontos`.`pontos`) AS `sum_pontos` from `exerciciopontos` group by `exerciciopontos`.`idAluno` order by sum(`exerciciopontos`.`pontos`) desc ;
DROP TABLE IF EXISTS `ranking_global_leaderboard`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ranking_global_leaderboard`  AS  select `rg`.`idAluno` AS `idAluno`,`rg`.`sum_pontos` AS `sum_pontos`,(select count(0) from `ranking_global` `ee` where ((`ee`.`sum_pontos` >= `rg`.`sum_pontos`) and (`ee`.`idAluno` <> `rg`.`idAluno`))) AS `count` from `ranking_global` `rg` ;
DROP TABLE IF EXISTS `ranking_local`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ranking_local`  AS  select `pp`.`IdTurma` AS `idTurma`,`p`.`ID` AS `idAluno`,coalesce(sum(`ep`.`pontos`),0) AS `pontos` from ((`turmapessoa` `pp` join `pessoa` `p` on((`pp`.`IdPessoa` = `p`.`ID`))) left join `exerciciopontos` `ep` on((`ep`.`idAluno` = `p`.`ID`))) group by `p`.`ID`,`pp`.`IdTurma` order by `pp`.`IdTurma`,`p`.`ID`,coalesce(sum(`ep`.`pontos`),0) desc ;
DROP TABLE IF EXISTS `ranking_local_leaderboard`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ranking_local_leaderboard`  AS  select `rl`.`idTurma` AS `idTurma`,`rl`.`idAluno` AS `idAluno`,`rl`.`pontos` AS `pontos`,(select count(0) from `ranking_local` `ee` where ((`ee`.`idTurma` = `rl`.`idTurma`) and (`ee`.`pontos` >= `rl`.`pontos`) and (`ee`.`idAluno` <> `rl`.`idAluno`))) AS `ranking` from `ranking_local` `rl` ;
DROP TABLE IF EXISTS `ranking_turma`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ranking_turma`  AS  select `p`.`ID` AS `idAluno`,`pp`.`IdTurma` AS `idTurma`,coalesce(sum(`ep`.`pontos`),0) AS `sum_pontos` from ((`turmapessoa` `pp` join `pessoa` `p` on((`pp`.`IdPessoa` = `p`.`ID`))) left join `exerciciopontos` `ep` on((`ep`.`idAluno` = `p`.`ID`))) group by `p`.`ID`,`pp`.`IdTurma` order by coalesce(sum(`ep`.`pontos`),0) desc ;
ALTER TABLE `configuracaosistema`
ALTER TABLE `conquista`
ALTER TABLE `exercicio`
ALTER TABLE `exerciciocasoteste`
ALTER TABLE `exercicioclasse`
ALTER TABLE `exercicioclasseauxiliar`
ALTER TABLE `exercicioclassemarcacao`
ALTER TABLE `exerciciopontos`
ALTER TABLE `exerciciosolucao`
ALTER TABLE `exerciciosolucaoclasse`
ALTER TABLE `exerciciosolucaoclassemarcacao`
ALTER TABLE `exerciciosolucaoerro`
ALTER TABLE `filanovasenha`
ALTER TABLE `grade`
ALTER TABLE `log`
ALTER TABLE `medalha`
ALTER TABLE `medalhaniveldescricao`
ALTER TABLE `medalhapessoas`
ALTER TABLE `mensagem`
ALTER TABLE `mensagemcabecalho`
ALTER TABLE `mensagemcomentario`
ALTER TABLE `mensagemcurtir`
ALTER TABLE `mensagemleitor`
ALTER TABLE `mensagempredefinida`
ALTER TABLE `novidade`
ALTER TABLE `perfil`
ALTER TABLE `pessoa`
ALTER TABLE `pessoaconquista`
ALTER TABLE `statussolucao`
ALTER TABLE `tela`
ALTER TABLE `telaperfil`
ALTER TABLE `tipolog`
ALTER TABLE `tipomarcacao`
ALTER TABLE `tiponovidade`
ALTER TABLE `turma`
ALTER TABLE `turmaexercicio`
ALTER TABLE `turmapessoa`
ALTER TABLE `uploadtemp`
ALTER TABLE `configuracaosistema`
ALTER TABLE `conquista`
ALTER TABLE `exercicio`
ALTER TABLE `exerciciocasoteste`
ALTER TABLE `exercicioclasse`
ALTER TABLE `exercicioclasseauxiliar`
ALTER TABLE `exercicioclassemarcacao`
ALTER TABLE `exerciciopontos`
ALTER TABLE `exerciciosolucao`
ALTER TABLE `exerciciosolucaoclasse`
ALTER TABLE `exerciciosolucaoclassemarcacao`
ALTER TABLE `exerciciosolucaoerro`
ALTER TABLE `filanovasenha`
ALTER TABLE `grade`
ALTER TABLE `log`
ALTER TABLE `medalhapessoas`
ALTER TABLE `mensagem`
ALTER TABLE `mensagemcabecalho`
ALTER TABLE `mensagemcomentario`
ALTER TABLE `mensagemcurtir`
ALTER TABLE `mensagemleitor`
ALTER TABLE `mensagempredefinida`
ALTER TABLE `novidade`
ALTER TABLE `perfil`
ALTER TABLE `pessoa`
ALTER TABLE `pessoaconquista`
ALTER TABLE `statussolucao`
ALTER TABLE `tela`
ALTER TABLE `telaperfil`
ALTER TABLE `tipolog`
ALTER TABLE `tipomarcacao`
ALTER TABLE `tiponovidade`
ALTER TABLE `turma`
ALTER TABLE `uploadtemp`
ALTER TABLE `exercicio`
ALTER TABLE `exerciciocasoteste`
ALTER TABLE `exercicioclasse`
ALTER TABLE `exercicioclasseauxiliar`
ALTER TABLE `exercicioclassemarcacao`
ALTER TABLE `exerciciopontos`
ALTER TABLE `exerciciosolucao`
ALTER TABLE `exerciciosolucaoclasse`
ALTER TABLE `exerciciosolucaoclassemarcacao`
ALTER TABLE `exerciciosolucaoerro`
ALTER TABLE `filanovasenha`
ALTER TABLE `grade`
ALTER TABLE `log`
ALTER TABLE `mensagem`
ALTER TABLE `mensagemcabecalho`
ALTER TABLE `mensagemcomentario`
ALTER TABLE `mensagemcurtir`
ALTER TABLE `mensagemleitor`
ALTER TABLE `mensagempredefinida`
ALTER TABLE `novidade`
ALTER TABLE `pessoa`
ALTER TABLE `pessoaconquista`
ALTER TABLE `telaperfil`
ALTER TABLE `turma`
ALTER TABLE `turmaexercicio`
ALTER TABLE `turmapessoa`
