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
                    try {
                        // Clean and compile first
                        bat "./mvnw clean compile"

                        // Run tests with detailed output
                        bat "./mvnw test -X"
                    } catch (Exception e) {
                        // Archive test reports even if tests fail
                        junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'

                        // Print test logs if available
                        if (fileExists('target/surefire-reports')) {
                            bat "type target\\surefire-reports\\*.txt"
                        }

                        currentBuild.result = 'FAILURE'
                        error "Build or tests failed: ${e.message}"
                    }
                }
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
                    script {
                        if (fileExists('target/surefire-reports')) {
                            bat "type target\\surefire-reports\\*.txt"
                        }
                    }
                }
            }
        }

        stage('Package') {
            when {
                expression { currentBuild.resultIsBetterOrEqualTo('SUCCESS') }
            }
            steps {
                script {
                    try {
                        bat "./mvnw package -DskipTests"
                    } catch (Exception e) {
                        currentBuild.result = 'FAILURE'
                        error "Packaging failed: ${e.message}"
                    }
                }
            }
        }

        stage('Build Server Image') {
            when {
                expression { currentBuild.resultIsBetterOrEqualTo('SUCCESS') }
            }
            steps {
                script {
                    try {
                        bat "docker system prune -f"
                        bat "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} --no-cache ."
                    } catch (Exception e) {
                        currentBuild.result = 'FAILURE'
                        error "Failed to build image: ${e.message}"
                    }
                }
            }
        }

        stage('Push Images to Docker Hub') {
            when {
                expression { currentBuild.resultIsBetterOrEqualTo('SUCCESS') }
            }
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
                bat """
                    docker logout
                    docker rmi ${IMAGE_NAME}:${IMAGE_TAG} || exit 0
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