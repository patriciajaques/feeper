# Feeper: Gamified Programming Learning Environment

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

> **Important note**: Feeper was developed at UNISINOS under the guidance of Prof. Dr. Patrícia Augustin Jaques Maillard, who is no longer at this university. For this reason, the system has been taken offline. Currently, Prof. Patrícia is working on a new system that integrates generative AI for teaching programming.

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

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contact

Patrícia Augustin Jaques Maillard - patricia.jaques@gmail.com

Project URL: [https://github.com/patriciajaques/feeper](https://github.com/patriciajaques/feeper)
