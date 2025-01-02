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

        stage('Build & Test') {
            steps {
                script {
                    // Use Maven wrapper for building
                    bat "./mvnw clean package"
                }
            }
            post {
                // Archive test results even if the build fails
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Code Quality') {
            steps {
                script {
                    // Run SonarQube analysis
                    bat "./mvnw sonar:sonar"
                }
            }
        }

        stage('Build Server Image') {
            steps {
                script {
                    try {
                        // Clean up before building
                        bat "docker system prune -f"

                        // Build Docker image
                        bat "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} --no-cache ."
                    } catch (Exception e) {
                        error "Failed to build image: ${e.message}"
                        currentBuild.result = 'FAILURE'
                        throw e
                    }
                }
            }
        }

        stage('Push Images to Docker Hub') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
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
                // Cleanup
                bat """
                    docker logout
                    docker rmi ${IMAGE_NAME}:${IMAGE_TAG} || true
                    docker image prune -f
                    docker builder prune -f
                """
                cleanWs()
            }
        }
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            script {
                echo 'Pipeline failed! Cleaning up...'
                bat "docker system prune -f"
                // You could add notification steps here (email, Slack, etc.)
            }
        }
    }
}