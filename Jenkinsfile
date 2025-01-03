pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub')
        DOCKER_USERNAME = 'narjesknaz'
        IMAGE_NAME = "${DOCKER_USERNAME}/spring-backend"
        IMAGE_TAG = "${BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat './mvnw package -DskipTests'
            }
        }

       stage('Docker Build & Push') {
           steps {
               script {
                   try {
                       withCredentials([usernamePassword(
                           credentialsId: 'dockerhub',
                           usernameVariable: 'DOCKER_USER',
                           passwordVariable: 'DOCKER_PASS'
                       )]) {
                           bat """
                               docker build --pull --cache-from %IMAGE_NAME%:latest -t %IMAGE_NAME%:%IMAGE_TAG% .
                               docker tag %IMAGE_NAME%:%IMAGE_TAG% %IMAGE_NAME%:latest
                               docker push %IMAGE_NAME%:%IMAGE_TAG%
                               docker push %IMAGE_NAME%:latest
                           """
                       }
                   } catch (Exception e) {
                       error "Docker build/push failed: ${e.message}"
                   }
               }
           }
       }
    }

    post {
        always {
            bat """
                docker rmi %IMAGE_NAME%:%IMAGE_TAG% || true
                docker rmi %IMAGE_NAME%:latest || true
                docker system prune -f
            """
            cleanWs()
        }
        success {
            echo "Pipeline completed successfully!"
        }
        failure {
            echo "Pipeline failed! Check the logs for details."
        }
    }
}