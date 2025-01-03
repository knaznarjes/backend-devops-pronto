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

        stage('Docker Build') {
            steps {
                script {
                    try {
                        // Nettoyage préalable
                        bat 'docker system prune -f'

                        // Vérification de Docker
                        bat 'docker info'

                        // Build avec logs détaillés
                        bat 'docker build --no-cache -t %IMAGE_NAME%:%IMAGE_TAG% .'

                        // Tag latest
                        bat 'docker tag %IMAGE_NAME%:%IMAGE_TAG% %IMAGE_NAME%:latest'
                    } catch (Exception e) {
                        error "Docker build failed: ${e.message}"
                    }
                }
            }
        }

        stage('Docker Push') {
            steps {
                script {
                    try {
                        withCredentials([usernamePassword(
                            credentialsId: 'dockerhub',
                            usernameVariable: 'DOCKER_USER',
                            passwordVariable: 'DOCKER_PASS'
                        )]) {
                            // Login Docker
                            bat 'docker login -u %DOCKER_USER% -p %DOCKER_PASS%'

                            // Push des images
                            bat '''
                                docker push %IMAGE_NAME%:%IMAGE_TAG%
                                docker push %IMAGE_NAME%:latest
                            '''
                        }
                    } catch (Exception e) {
                        error "Docker push failed: ${e.message}"
                    } finally {
                        bat 'docker logout'
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                try {
                    bat """
                        docker rmi %IMAGE_NAME%:%IMAGE_TAG% || exit 0
                        docker rmi %IMAGE_NAME%:latest || exit 0
                        docker system prune -f || exit 0
                    """
                } catch (Exception e) {
                    echo "Cleanup warning: ${e.message}"
                }
                cleanWs()
            }
        }
        failure {
            echo "Pipeline failed! Checking Docker status..."
            bat 'docker info'
            bat 'docker ps -a'
        }
    }
