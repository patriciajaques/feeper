# Feeper: Ambiente de Ensino de Programação Gamificado

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

> **Nota importante**: O Feeper foi desenvolvido na UNISINOS sob orientação da Profa. Dra. Patrícia Augustin Jaques Maillard, que não se encontra mais nessa universidade. Por esta razão, o sistema foi tirado do ar. Atualmente, a Profa. Patrícia está trabalhando em um novo sistema que integra IA generativa para o ensino de programação.

## Sobre o Projeto

Feeper é um ambiente web para apoio ao ensino de programação, desenvolvido originalmente pelo grupo de pesquisa orientado pela Profa. Dra. Patrícia Augustin Jaques Maillard, na Universidade do Vale do Rio dos Sinos (UNISINOS). Esta versão estende o ambiente original incorporando elementos de gamificação (pontos, medalhas e ranking) com o objetivo de estudar como diferentes perfis de usuários interagem com esses elementos.

## Histórico de Desenvolvimento

O Feeper foi desenvolvido e evoluído ao longo do tempo em vários trabalhos acadêmicos, todos orientados pela Profa. Dra. Patrícia Augustin Jaques Maillard:

1. **Versão Original**: Desenvolvida como Trabalho de Conclusão de Curso por Fábio Pacheco Alves, intitulado "Um ambiente virtual de aprendizagem com feedback personalizado para apoio a disciplinas de programação" (2014).

2. **Extensão para Detecção de Plágio**: Adicionada por Gilvani Schneider em seu TCC "Combinando técnicas de análise estática e avaliação dinâmica para avaliação de código em ambientes virtuais de aprendizagem" (2014).

3. **Incorporação de Gamificação**: Implementada por Rodrigo Smiderle em sua dissertação de mestrado "O EFEITO DA GAMIFICAÇÃO NO ENGAJAMENTO E APRENDIZAGEM DE PROGRAMAÇÃO: UM ESTUDO CONSIDERANDO A PERSONALIDADE E A ORIENTAÇÃO MOTIVACIONAL DOS ESTUDANTES" (2017).

O Feeper permite que professores disponibilizem exercícios de programação que são corrigidos automaticamente por um Juiz Online, fornecendo feedback instantâneo aos alunos. Isso permite que os professores concentrem seus esforços no ensino e na resolução de dúvidas, em vez de na correção manual dos exercícios.

## Principais Características

- **Correção Automática**: Juiz Online que verifica soluções automaticamente
- **Sistema de Gamificação**: Inclui medalhas, pontos e ranking para aumentar o engajamento
- **Personalização de Gamificação**: Elementos de gamificação podem ser habilitados seletivamente para diferentes grupos de usuários
- **Editor de Código Online**: Permite escrever e testar código diretamente no navegador
- **Acompanhamento do Progresso**: Permite que professores monitorem o progresso dos alunos

## Elementos de Gamificação

O Feeper oferece os seguintes elementos de gamificação:

- **Pontos**: Alunos recebem pontos por completar exercícios
- **Medalhas**: Sistema com 9 tipos de medalhas diferentes, cada uma com 3 níveis (Bronze, Prata e Ouro)
- **Ranking**: Classificação dos alunos com base em pontuação, com rankings global e por turma

## Estrutura do Projeto

A aplicação é dividida em três módulos principais:

- **feeper.CorretorJava**: Serviço responsável pela correção dos exercícios
- **feeper.Data**: Camada de acesso a dados e modelo de domínio
- **feeper.Web**: Interface web e controladores da aplicação

## Tecnologias Utilizadas

- **Backend**: Java EE, Spring MVC
- **Frontend**: HTML, CSS, JavaScript, jQuery, Bootstrap
- **Banco de Dados**: MySQL
- **ORM**: Hibernate
- **Compilação**: Maven
- **Servidor**: Apache Tomcat

## Requisitos de Instalação

- JDK 8 ou superior
- Apache Tomcat 8 ou superior
- MySQL 5.6 ou superior
- Maven 3.x

## Configuração e Instalação

1. Clone este repositório:
```
git clone https://github.com/patriciajaques/feeper-public.git
```

2. Configure o banco de dados:
```sql
mysql -u root -p < estrutura_banco.sql
```

3. Configure as propriedades de conexão com o banco de dados em `hibernate.cfg.xml`

4. Compile os módulos com Maven:
```
cd feeper.CorretorJava
mvn clean install
cd ../feeper.Data
mvn clean install
cd ../feeper.Web
mvn clean install
```

5. Implante os arquivos WAR gerados no servidor Tomcat

## Contribuindo

Contribuições são bem-vindas! Para contribuir:

1. Faça um fork do projeto
2. Crie uma branch para sua funcionalidade (`git checkout -b feature/nova-funcionalidade`)
3. Faça commit das mudanças (`git commit -m 'Adiciona nova funcionalidade'`)
4. Envie para o repositório remoto (`git push origin feature/nova-funcionalidade`)
5. Abra um Pull Request

## Pesquisa e Publicações

Este ambiente foi utilizado como estudo de caso em uma pesquisa sobre o efeito da gamificação no engajamento e aprendizagem de programação, considerando a personalidade e orientação motivacional dos estudantes. Os resultados mostraram que:

- A gamificação teve impacto positivo na qualidade das soluções enviadas por estudantes com motivação extrínseca
- Houve um efeito positivo na qualidade das soluções enviadas por estudantes introvertidos, com baixa amabilidade e baixa abertura à mudança
- O elemento ranking foi mais benéfico para estudantes introvertidos do que para extrovertidos

## Citação

Se você utilizar o código deste projeto ou se basear nele para seu trabalho, por favor cite as publicações originais abaixo, além do link do código fonte desse projeto:

```
@mastersthesis{smiderle2017efeito,
  title={O efeito da gamificação no engajamento e aprendizagem de programação: um estudo considerando a personalidade e a orientação motivacional dos estudantes},
  author={Smiderle, Rodrigo},
  year={2017},
  school={Universidade do Vale do Rio dos Sinos}
}

@misc{schneider2014combinando,
  title={Combinando técnicas de análise estática e avaliação dinâmica para avaliação de código em ambientes virtuais de aprendizagem},
  author={Schneider, Gilvani},
  year={2014},
  howpublished={Trabalho de Conclusão de Curso. (Graduação em Análise e Desenvolvimento de Sistemas) - Universidade do Vale do Rio dos Sinos}
}

@misc{alves2014ambiente,
  title={Um ambiente virtual de aprendizagem com feedback personalizado para apoio a disciplinas de programação},
  author={Alves, Fábio Pacheco},
  year={2014},
  howpublished={Trabalho de Conclusão de Curso. (Graduação em Tecnólogo em Análise e Desenvolvimento de Sistemas) - Universidade do Vale do Rio dos Sinos}
}
```

## Licença

Este projeto está licenciado sob a licença MIT - veja o arquivo LICENSE para detalhes.

## Contato

Patrícia Augustin Jaques Maillard - patricia.jaques@gmail.com

URL do Projeto: [https://github.com/patriciajaques/feeper](https://github.com/patriciajaques/feeper)
