# Feeper: Gamified Programming Learning Environment

> **Important note**: Feeper was developed at UNISINOS under the supervision of Prof. Dr. Patrícia Augustin Jaques Maillard, while she was part of the PPGC faculty at that university. The system is no longer available due to this institutional change. Currently, Prof. Patrícia is leading innovative research at PPGInf/UFPR, developing a new system that integrates generative AI for programming education. The professor welcomes Master's and PhD students interested in contributing to this promising line of research. Those interested can contact her via email at patricia@inf.ufpr.br to discuss supervision and collaboration opportunities in this expanding field.

## About the Project

Feeper is a web environment to support programming education, originally developed by the research group led by Prof. Dr. Patrícia Augustin Jaques Maillard at the University of Vale do Rio dos Sinos (UNISINOS). This version extends the original environment by incorporating gamification elements (points, badges, and ranking) with the aim of studying how different user profiles interact with these elements.

## Development History

Feeper was developed and evolved over time through several academic works, all supervised by Prof. Dr. Patrícia Augustin Jaques Maillard:

1. **Original Version**: Developed as a Final Course Project by Fábio Pacheco Alves, titled "A virtual learning environment with personalized feedback to support programming courses" (2014).

2. **Extension for Plagiarism Detection**: Added by Gilvani Schneider in his final project "Combining static analysis techniques and dynamic evaluation for code assessment in virtual learning environments" (2014).

3. **Gamification Integration**: Implemented by Rodrigo Smiderle in his master's thesis "THE EFFECT OF GAMIFICATION ON PROGRAMMING ENGAGEMENT AND LEARNING: A STUDY CONSIDERING THE PERSONALITY AND MOTIVATIONAL ORIENTATION OF STUDENTS" (2017).

Feeper allows teachers to provide programming exercises that are automatically graded by an Online Judge, providing instant feedback to students. This enables teachers to focus their efforts on teaching and resolving doubts, rather than manually grading exercises.

## Key Features

- **Automatic Grading**: Online Judge that automatically verifies solutions
- **Gamification System**: Includes badges, points, and ranking to increase engagement
- **Customizable Gamification**: Gamification elements can be selectively enabled for different user groups
- **Online Code Editor**: Allows writing and testing code directly in the browser
- **Progress Tracking**: Enables teachers to monitor student progress

## Gamification Elements

Feeper offers the following gamification elements:

- **Points**: Students receive points for completing exercises
- **Badges**: System with 9 different types of badges, each with 3 levels (Bronze, Silver, and Gold)
- **Ranking**: Classification of students based on score, with global and class-specific rankings

## Project Structure

The application is divided into three main modules:

- **feeper.CorretorJava**: Service responsible for grading exercises
- **feeper.Data**: Data access layer and domain model
- **feeper.Web**: Web interface and application controllers

## Technologies Used

- **Backend**: Java EE, Spring MVC
- **Frontend**: HTML, CSS, JavaScript, jQuery, Bootstrap
- **Database**: MySQL
- **ORM**: Hibernate
- **Build**: Maven
- **Server**: Apache Tomcat

## Installation Requirements

- JDK 8 or higher
- Apache Tomcat 8 or higher
- MySQL 5.6 or higher
- Maven 3.x

## Setup and Installation

1. Clone this repository:
```
git clone https://github.com/patriciajaques/feeper-public.git
```

2. Configure the database:
```sql
mysql -u root -p < database_structure.sql
```

3. Configure the database connection properties in `hibernate.cfg.xml`

4. Compile the modules with Maven:
```
cd feeper.CorretorJava
mvn clean install
cd ../feeper.Data
mvn clean install
cd ../feeper.Web
mvn clean install
```

5. Deploy the generated WAR files to the Tomcat server

## Contributing

Contributions are welcome! To contribute:

1. Fork the project
2. Create a branch for your feature (`git checkout -b feature/new-feature`)
3. Commit your changes (`git commit -m 'Add new feature'`)
4. Push to the remote repository (`git push origin feature/new-feature`)
5. Open a Pull Request

## Research and Publications

This environment was used as a case study in research on the effect of gamification on programming engagement and learning, considering the personality and motivational orientation of students. The results showed that:

- Gamification had a positive impact on the quality of solutions submitted by students with extrinsic motivation
- There was a positive effect on the quality of solutions submitted by introverted students with low agreeableness and low openness to change
- The ranking element was more beneficial for introverted students than for extroverted ones

## Citation

If you use the code from this project or base your work on it, please cite the original publications below, in addition to the source code link of this project:

### Citations in bibtex:

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

@article{smiderle2020impact,
  title={The impact of gamification on students' learning, engagement and behavior based on their personality traits},
  author={Smiderle, Rodrigo and Rigo, Sandro and Marques, Leonardo B. and Coelho, Jorge A. P. M. and Jaques, Patricia A.},
  journal={Smart Learning Environments},
  volume={7},
  pages={1--11},
  year={2020},
  publisher={Springer},
  url={https://doi.org/10.1186/s40561-020-00127-6}
}

@inproceedings{smiderle2019studying,
  title={Studying the Impact of Gamification on Learning and Engagement of Introverted and Extroverted Students},
  author={Smiderle, Rodrigo and Marques, Leonardo and de M. Coelho, Jorge Artur P. and Rigo, Sandro J. and Jaques, Patricia A.},
  booktitle={2019 IEEE 19th International Conference on Advanced Learning Technologies (ICALT)},
  pages={71--75},
  year={2019},
  organization={IEEE},
  url={https://doi.org/10.1109/ICALT.2019.00024}
}

@inproceedings{smiderle2019estudando,
  title={Estudando o impacto da gamificação na aprendizagem e engajamento de alunos de acordo com os traços de personalidade e a orientação motivacional},
  author={Smiderle, Rodrigo and Rigo, Sandro and Jaques, Patricia},
  booktitle={Anais do XXX Simpósio Brasileiro de Informática na Educação (SBIE 2019)},
  pages={793--802},
  year={2019},
  organization={Brazilian Computer Society (Sociedade Brasileira de Computação - SBC)},
  url={https://doi.org/10.5753/cbie.sbie.2019.793}
}


@article{schneider2016combinando,
  title={Combinando técnicas de análise estática e avaliação dinâmica para avaliação de código em ambientes de aprendizagem de programação},
  author={Schneider, Gilvani and Jaques, Patricia Augustin},
  journal={Revista Brasileira de Computação Aplicada},
  volume={8},
  number={2},
  pages={114--129},
  year={2016},
  url={https://doi.org/10.5335/rbca.v8i2.5903}
}


@inproceedings{alves2014ambiente,
  title={Um Ambiente Virtual com Feedback Personalizado para Apoio a Disciplinas de Programação},
  author={Alves, Fábio P. and Jaques, Patricia},
  booktitle={XXV Simpósio Brasileiro de Informática na Educação},
  pages={1078--1082},
  year={2014},
  organization={SBC},
  url={https://doi.org/10.5753/cbie.sbie.2014.1078}
}

@inproceedings{alves2018melhorando,
  title={Melhorando a atenção dos estudantes através da tutoria de mindfulness em sistemas tutores inteligentes},
  author={Alves, Antônio and Morais, Felipe and Schaab, Bruno and Jaques, Patricia},
  booktitle={XXIX Simpósio Brasileiro de Informática na Educação},
  pages={973--982},
  year={2018},
  organization={SBC},
  url={https://doi.org/10.5753/cbie.sbie.2018.973}
}
```

### Citations in ABNT: 

- SMIDERLE, Rodrigo. O efeito da gamificação no engajamento e aprendizagem de programação: um estudo considerando a personalidade e a orientação motivacional dos estudantes. 2017. Dissertação (Mestrado) - Universidade do Vale do Rio dos Sinos, São Leopoldo, 2017.

- SCHNEIDER, Gilvani. Combinando técnicas de análise estática e avaliação dinâmica para avaliação de código em ambientes virtuais de aprendizagem. 2014. Trabalho de Conclusão de Curso (Graduação em Análise e Desenvolvimento de Sistemas) - Universidade do Vale do Rio dos Sinos, São Leopoldo, 2014.

- ALVES, Fábio Pacheco. Um ambiente virtual de aprendizagem com feedback personalizado para apoio a disciplinas de programação. 2014. Trabalho de Conclusão de Curso (Graduação em Tecnólogo em Análise e Desenvolvimento de Sistemas) - Universidade do Vale do Rio dos Sinos, São Leopoldo, 2014.

- SMIDERLE, Rodrigo et al. The impact of gamification on students' learning, engagement and behavior based on their personality traits. Smart Learning Environments, v. 7, p. 1-11, 2020. Disponível em: https://doi.org/10.1186/s40561-020-00127-6. Acesso em: 7 mar. 2025.

- SMIDERLE, Rodrigo et al. Studying the Impact of Gamification on Learning and Engagement of Introverted and Extroverted Students. In: IEEE INTERNATIONAL CONFERENCE ON ADVANCED LEARNING TECHNOLOGIES, 19., 2019. Proceedings [...]. IEEE, 2019. p. 71-75. Disponível em: https://doi.org/10.1109/ICALT.2019.00024. Acesso em: 7 mar. 2025.

- SMIDERLE, Rodrigo; RIGO, Sandro; JAQUES, Patricia. Estudando o impacto da gamificação na aprendizagem e engajamento de alunos de acordo com os traços de personalidade e a orientação motivacional. In: SIMPÓSIO BRASILEIRO DE INFORMÁTICA NA EDUCAÇÃO, 30., 2019. Anais [...]. Porto Alegre: Sociedade Brasileira de Computação, 2019. p. 793-802. Disponível em: https://doi.org/10.5753/cbie.sbie.2019.793. Acesso em: 7 mar. 2025.

- SCHNEIDER, Gilvani; JAQUES, Patricia Augustin. Combinando técnicas de análise estática e avaliação dinâmica para avaliação de código em ambientes de aprendizagem de programação. Revista Brasileira de Computação Aplicada, v. 8, n. 2, p. 114-129, 2016. Disponível em: https://doi.org/10.5335/rbca.v8i2.5903. Acesso em: 7 mar. 2025.

- ALVES, Fábio P.; JAQUES, Patricia. Um Ambiente Virtual com Feedback Personalizado para Apoio a Disciplinas de Programação. In: SIMPÓSIO BRASILEIRO DE INFORMÁTICA NA EDUCAÇÃO, 25., 2014. Anais [...]. Porto Alegre: Sociedade Brasileira de Computação, 2014. p. 1078-1082. Disponível em: https://doi.org/10.5753/cbie.sbie.2014.1078. Acesso em: 7 mar. 2025.


## License

This project is licensed under the Creative Commons Attribution-NonCommercial 4.0 International License (CC BY-NC 4.0). This license allows non-commercial use, including academic research, with appropriate attribution. For commercial use, please contact the authors.

For more details, see [Creative Commons BY-NC 4.0](https://creativecommons.org/licenses/by-nc/4.0/).

## Permitted Use

This software is freely available for:
- Academic research
- Teaching and education
- Personal non-commercial use

For any commercial or production use, please contact the authors to obtain a commercial license.

## Contact

Patrícia Augustin Jaques Maillard - patricia.jaques@gmail.com

Project URL: [https://github.com/patriciajaques/feeper](https://github.com/patriciajaques/feeper)
