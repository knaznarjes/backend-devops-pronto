pipeline {
    agent any

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
                script {
                    withCredentials([usernamePassword(credentialsId: 'dockerhub',
                                                    usernameVariable: 'DOCKER_USERNAME',
                                                    passwordVariable: 'DOCKER_PASSWORD')]) {
                        retry(3) {
                            timeout(time: 10, unit: 'MINUTES') {
                                bat "docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}"
                                bat """
                                    docker push ${IMAGE_NAME} || (
                                        docker system prune -f
                                        docker push ${IMAGE_NAME}
                                    )
                                """
                            }
                        }
                    }
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