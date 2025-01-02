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

        stage('Push Images to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]) {
                    bat "echo %DOCKER_PASSWORD% | docker login --username %DOCKER_USERNAME% --password-stdin"
                    bat "docker push ${IMAGE_NAME}"
                }
            }
        }
    }

    post {
        always {
            script {
                bat """
                    docker rmi ${IMAGE_NAME} || true
                    docker image prune -f
                """
                cleanWs()
            }
        }
    }
}