@echo off
set path_backend=src/app
set name_backend=app-0.0.1-SNAPSHOT.war
cd %path_backend%
mvn clean package
echo "build backend done"
cd %path_backend%/target
copy %name_backend% ../../production/app.war
echo "copy backend done"
cd ../../..

