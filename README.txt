Feed Import application

Based on: Spring Boot data JPA, Hibernate, Gradle, PostgreSQL 9.4

Step-by-step for installation:

1. Clone repository
2. Install PostgreSQL 9.4 on your local machine
3. Add configuration your local DB to application.properties (src/main/resources)
    - username
    - password
    - change database url in "spring.datasource.url" (port, database name)
4. Run your PostgreSQL client (pgAdmin) and run script "create_table.sql" for creating table
5. Logging: by default all logs will be put to c:/logs. You can change it
    in "src/main/resources/logback.xml" (property name="DEV_HOME" value="your_path")
    - Info logs will be in "feed.log"
    - Error logs in "error.log"
6. There are exist scripts for building application (build.bat and build.sh in root of application)
7. After building, will be created executable jar artifact which will be put to "/build/libs"
8. There are exist scripts for running jar file (run.bat and run.sh in root of application)
9. Application isn't running if you enter bad credentials of DB or all bad start up configurations
10. After running application wait around 15 sec for initializing Spring Boot, you'll see "hello ...",
    after that you need to enter directory paths:
    - for incoming files
    - for processing files (directory where files will be processing)
    - for error files (bad format, invalid files and etc.)
    - for processed files
    and enter time period of execution in seconds (for examples: 1, 10)
11. You cannot enter bad path or the same directory for different process
    (you'll see validation message) and you'll need to enter another directory
12. Finally, you'll see message that "Application is started...", you can put file to your "in"
    directory.
13. Good luck
