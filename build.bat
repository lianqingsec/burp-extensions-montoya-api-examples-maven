@echo off
set JAVA_HOME=C:\Penetration\ProgrammingEnvironment\Java\jdk-17
set PATH=%JAVA_HOME%\bin;%PATH%
mvn clean package
pause 