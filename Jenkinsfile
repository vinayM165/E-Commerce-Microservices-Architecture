pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build & test All') {
            steps {
                bat "mvn clean package"
                 bat "mvn test"
            }
        }
        stage('build & test product-service'){
            steps{
                dir('product-service'){
                    bat "mvn clean package"
                    bat "mvn test"
                }
        }
        }
        stage('Build & Push Docker Image') {
            steps {
                dir('product-service') {
                    // Authenticate with Docker Hub or registry using Jenkins credentials
                    withCredentials([usernamePassword(credentialsId: 'ad312487-6758-4462-82e1-7a4b948ecd7e')]) {
                        // Build the Docker image
                        bat "docker build -t registry.hub.docker.com/vinaym404/product-service:latest ."

                        // Log in to Docker Hub or your registry
                        bat "docker login registry.hub.docker.com/vinaym404"

                        // Push the Docker image to the registry
                        bat "docker push registry.hub.docker.com/vinaym404/product-service:latest"
                    }
                }
            }
        }
        }
        stage('build & test order-service'){
            steps{
                dir('order-service'){
                    bat "mvn clean package"
                    bat "mvn test"
            }
        }
        }
         stage('build & test inventory-service'){
            steps{
                dir('inventory-service'){
                    bat "mvn clean package"
                    bat "mvn test"
                }
            }
            }
          stage('build & test notification-service'){
             steps{
                 dir('notification-service'){
                     bat "mvn clean package"
                     bat "mvn test"
                 }
             }
             }
          stage('build & test api-gateway'){
                 steps{
                     dir('api-gateway'){
                         bat "mvn clean package"
                         bat "mvn test"
                     }
                 }
                 }
            stage('build & test discovery-server'){
                   steps{
                       dir('discovery-server'){
                           bat "mvn clean package"
                           bat "mvn test"
                       }
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

