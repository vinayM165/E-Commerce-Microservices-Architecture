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
                  parallel(
                      'Build & test product-service': {
                          dir('product-service') {
                              bat "mvn clean package"
                              bat "mvn test"
                          }
                      },
                      'Build & test order-service': {
                          dir('order-service') {
                              bat "mvn clean package"
                              bat "mvn test"
                          }
                      },
                      'Build & test inventory-service': {
                          dir('inventory-service') {
                              bat "mvn clean package"
                              bat "mvn test"
                          }
                      },
                      'Build & test notification-service': {
                          dir('notification-service') {
                              bat "mvn clean package"
                              bat "mvn test"
                          }
                      },
                      'Build & test api-gateway': {
                          dir('api-gateway') {
                              bat "mvn clean package"
                              bat "mvn test"
                          }
                      },
                      'Build & test discovery-server': {
                          dir('discovery-server') {
                              bat "mvn clean package"
                              bat "mvn test"
                          }
                      }
                  )
              }
        }

        stage('Build & Push Docker Images') {
               parallel {
                   stage('Product Service') {
                       steps {
                           dir('product-service') {
                               withCredentials([usernamePassword(credentialsId: 'ad312487-6758-4462-82e1-7a4b948ecd7e', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                                  bat"docker build -t registry.hub.docker.com/vinaym404/product-service:latest ."
                                  bat"docker login -u $USERNAME -p $PASSWORD registry.hub.docker.com/vinaym404"
                                  bat"docker push registry.hub.docker.com/vinaym404/product-service:latest"
                               }
                           }
                       }
                   }
                   stage('Order Service') {
                       steps {
                           dir('order-service') {
                               withCredentials([usernamePassword(credentialsId: 'ad312487-6758-4462-82e1-7a4b948ecd7e', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                                  bat"docker build -t registry.hub.docker.com/vinaym404/order-service:latest ."
                                  bat"docker login -u $USERNAME -p $PASSWORD registry.hub.docker.com/vinaym404"
                                  bat"docker push registry.hub.docker.com/vinaym404/order-service:latest"
                               }
                           }
                       }
                   }
                   stage('Inventory Service') {
                       steps {
                           dir('inventory-service') {
                               withCredentials([usernamePassword(credentialsId: 'ad312487-6758-4462-82e1-7a4b948ecd7e', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                                  bat"docker build -t registry.hub.docker.com/vinaym404/inventory-service:latest ."
                                  bat"docker login -u $USERNAME -p $PASSWORD registry.hub.docker.com/vinaym404"
                                  bat"docker push registry.hub.docker.com/vinaym404/inventory-service:latest"
                               }
                           }
                       }
                   }
                   stage('Notification Service') {
                       steps {
                           dir('notification-service') {
                               withCredentials([usernamePassword(credentialsId: 'ad312487-6758-4462-82e1-7a4b948ecd7e', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                                  bat"docker build -t registry.hub.docker.com/vinaym404/notification-service:latest ."
                                  bat"docker login -u $USERNAME -p $PASSWORD registry.hub.docker.com/vinaym404"
                                  bat"docker push registry.hub.docker.com/vinaym404/notification-service:latest"
                               }
                           }
                       }
                   }
                   stage('API Gateway') {
                       steps {
                           dir('api-gateway') {
                               withCredentials([usernamePassword(credentialsId: 'ad312487-6758-4462-82e1-7a4b948ecd7e', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                                  bat"docker build -t registry.hub.docker.com/vinaym404/api-gateway:latest ."
                                  bat"docker login -u $USERNAME -p $PASSWORD registry.hub.docker.com/vinaym404"
                                  bat"docker push registry.hub.docker.com/vinaym404/api-gateway:latest"
                               }
                           }
                       }
                   }
                   stage('Discovery Server') {
                       steps {
                           dir('discovery-server') {
                               withCredentials([usernamePassword(credentialsId: 'ad312487-6758-4462-82e1-7a4b948ecd7e', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                                  bat"docker build -t registry.hub.docker.com/vinaym404/discovery-server:latest ."
                                  bat"docker login -u $USERNAME -p $PASSWORD registry.hub.docker.com/vinaym404"
                                  bat"docker push registry.hub.docker.com/vinaym404/discovery-server:latest"
                               }
                           }
                       }
                   }
               }
        }

        stage('Sonar Analysis') {
             steps {
                 // Run the 'mvn' command with SonarQube analysis
                 script {
                     def sonarToken = 'sqp_8708205c8eb669892d742fc0a938985965870227'
                     def projectKey = 'ecommerce_springboot_microservices_dev'
                     def projectName = 'ecommerce_springboot_microservices_dev'
                     def sonarHostUrl = 'http://localhost:9000'
                     bat "mvn clean verify sonar:sonar -D sonar.projectKey=${projectKey} -D sonar.projectName='${projectName}' -D sonar.host.url=${sonarHostUrl} -D sonar.login=${sonarToken}"
                 }
             }
        }
    }
}
