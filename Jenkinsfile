pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                bat "mvn clean package"
            }
        }
        stage('Test') {
            steps {
                bat "mvn test"
            }
        }
        stage('Sonar Analysis') {
            steps {
              withSonarQubeEnv(installationName:'Sonar-Qube'){
                bat "mvn clean sonar:sonar"
              }
            }
        }
    }
}

