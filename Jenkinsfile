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
               withCredentials([usernamePassword(credentialsId: 'sonar-token')]) {
                         bat "mvn clean install sonar:sonar -D sonar.login=$credentialsId"
            }
        }
    }
}
}
