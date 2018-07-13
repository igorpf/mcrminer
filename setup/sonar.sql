create database if not exists sonarqube;
grant all privileges on sonarqube.* to sonar@localhost identified by 'sonar';