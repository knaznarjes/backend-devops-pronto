pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub')
        IMAGE_NAME = 'narjesknaz/spring-backend'
        IMAGE_TAG = 'latest'
        MAVEN_OPTS = '-Dmaven.repo.local=.m2/repository'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master',
                    url: 'https://github.com/knaznarjes/backend-devops-pronto'

                // Ensure Maven wrapper has correct permissions
                bat 'attrib -R mvnw'
                bat 'attrib -R .mvn\\wrapper\\maven-wrapper.jar'
            }
        }

        stage('Build & Test') {
            steps {
                script {
                    try {
                        // Clean local Maven repo
                        bat 'if exist .m2 rmdir /s /q .m2'

                        // Set JAVA_HOME explicitly if needed
                        bat '''
                            set JAVA_HOME=%JAVA_HOME_17%
                            ./mvnw --version
                            ./mvnw clean compile -e
                        '''

                        // Run tests with detailed output
                        bat '''
                            set JAVA_HOME=%JAVA_HOME_17%
                            ./mvnw test -e -Dtest.verbose=true
                        '''
                    } catch (Exception e) {
                        junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'

                        if (fileExists('target/surefire-reports')) {
                            bat 'dir target\\surefire-reports'
                            bat 'type target\\surefire-reports\\*.txt'
                        }

                        error "Build or tests failed: ${e.message}"
                    }
                }
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                bat './mvnw package -DskipTests -e'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    bat 'docker system prune -f'
                    bat "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} --no-cache ."
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'dockerhub',
                                                    usernameVariable: 'DOCKER_USERNAME',
                                                    passwordVariable: 'DOCKER_PASSWORD')]) {
                        bat '''
                            docker login -u %DOCKER_USERNAME% -p %DOCKER_PASSWORD%
                            docker push %IMAGE_NAME%:%IMAGE_TAG%
                        '''
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                bat '''
                    docker logout
                    docker rmi %IMAGE_NAME%:%IMAGE_TAG% || exit 0
                    docker image prune -f
                    docker builder prune -f
                '''
                cleanWs()
            }
        }
        failure {
            script {
                echo 'Pipeline failed! Cleaning up...'
                bat 'docker system prune -f'
            }
        }
    }
}