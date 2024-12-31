pipeline {
    agent any
    triggers {
        pollSCM('H/5 * * * *')
    }
    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub')
        IMAGE_NAME = 'narjesknaz/spring-backend'
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'master',
                    url: 'https://github.com/knaznarjes/backend-devops-pronto'
            }
        }
        stage('Build Server Image') {
            steps {
                script {
                    dockerImageServer = docker.build("${IMAGE_NAME}")
                }
            }
        }
        stage('Scan Server Image') {
            steps {
                script {
                    catchError(buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {
                        sh """
                        docker run --rm -v /var/run/docker.sock:/var/run/docker.sock \
                        aquasec/trivy:latest image --exit-code 0 --severity LOW,MEDIUM,HIGH,CRITICAL --timeout 5m \
                        ${IMAGE_NAME}
                        """
                    }
                }
            }
        }
        stage('Push Images to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('', "${DOCKERHUB_CREDENTIALS}") {
                        dockerImageServer.push()
                    }
                }
            }
        }
    }
    post {
        always {
            echo 'Cleaning up...'
        }
    }
}