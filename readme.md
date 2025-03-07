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

To use the code or concepts from PAT2Math in academic papers or projects, please cite the project and its related publications.

For more specific information about published articles and students supervised within the scope of the project, please refer to Prof. Dr. Patricia Augustin Jaques Maillard's Lattes Curriculum:
[http://lattes.cnpq.br/5723385125570881](http://lattes.cnpq.br/5723385125570881)

Suggested citationS:

- JAQUES, P. A. et al. FEEPER: A Gamified web environment for programming education with automatic grading and plagiarism detection. Available at: (https://github.com/patriciajaques/feeper). Accessed on: [access date].

- SMIDERLE, R.; RIGO, S.; MARQUES, L.; COELHO, J.; JAQUES, P. et al. The impact of gamification on students' learning, engagement and behavior based on their personality traits. Smart Learning Environments, v. 7, p. 1-11, 2020. Disponível em: https://doi.org/10.1186/s40561-019-0098-x  


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
