pipeline {
    agent any

    tools {
        jdk 'JDK17'
    }

    environment {
        IMAGE_NAME = 'narjesknaz/spring-backend'
        IMAGE_TAG = 'latest'
    }

    stages {
        stage('Checkout') {
            steps {
                cleanWs()
                git branch: 'master',
                    url: 'https://github.com/knaznarjes/backend-devops-pronto'
            }
        }

        stage('Build Maven') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker') {
            steps {
                bat "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
            }
        }

        stage('Push Docker') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub',
                                                usernameVariable: 'DOCKER_USERNAME',
                                                passwordVariable: 'DOCKER_PASSWORD')]) {
                    bat "docker login -u %DOCKER_USERNAME% -p %DOCKER_PASSWORD%"
                    bat "docker push ${IMAGE_NAME}:${IMAGE_TAG}"
                    bat "docker logout"
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
        failure {
            bat 'docker system prune -f'
        }
    }
}