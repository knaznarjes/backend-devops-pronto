pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub')
        IMAGE_NAME = 'narjesknaz/spring-backend'
        IMAGE_TAG = 'latest'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master',
                    url: 'https://github.com/knaznarjes/backend-devops-pronto'
            }
        }

        stage('Build Application') {
            steps {
                // Use Maven wrapper on Windows
                bat "./mvnw clean package -DskipTests"
            }
        }

        stage('Build Server Image') {
            steps {
                script {
                    try {
                        // Clean up before building
                        bat "docker system prune -f"

                        // Build with proper tags
                        dockerImageServer = docker.build("${IMAGE_NAME}:${IMAGE_TAG}", "--no-cache .")
                    } catch (Exception e) {
                        error "Failed to build image: ${e.message}"
                    }
                }
            }
        }

        stage('Push Images to Docker Hub') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        // Use bat for Windows
                        bat """
                            echo %DOCKER_PASSWORD% | docker login -u %DOCKER_USERNAME% --password-stdin
                            docker push ${IMAGE_NAME}:${IMAGE_TAG}
                        """
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                bat """
                    docker logout
                    docker rmi ${IMAGE_NAME}:${IMAGE_TAG} || true
                    docker image prune -f
                    docker builder prune -f
                """
                cleanWs()
            }
        }
        failure {
            script {
                echo 'Pipeline failed! Cleaning up...'
                bat "docker system prune -f"
            }
        }
    }
}