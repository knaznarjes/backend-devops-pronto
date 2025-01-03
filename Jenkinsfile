pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub')
        IMAGE_NAME = 'narjesknaz/spring-backend'
        IMAGE_TAG = 'latest'
        MAVEN_OPTS = '-Dmaven.repo.local=.m2/repository -Dfile.encoding=UTF-8'
        JAVA_HOME = tool 'JDK17'
        PATH = "${JAVA_HOME}/bin:${PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                cleanWs()
                checkout scm

                script {
                    if (isUnix()) {
                        sh 'mkdir -p .m2'
                        sh 'chmod +x mvnw'
                    } else {
                        bat 'mkdir .m2 2>nul || exit 0'
                        bat 'attrib -R mvnw'
                    }
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'java -version'
                        sh './mvnw -v'
                        sh './mvnw clean compile -e'
                    } else {
                        bat 'java -version'
                        bat 'mvnw -v'
                        bat 'mvnw clean compile -e'
                    }
                }
            }
        }

        stage('Package') {
            steps {
                script {
                    if (isUnix()) {
                        sh './mvnw package -DskipTests -e'
                    } else {
                        bat 'mvnw package -DskipTests -e'
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    def dockerCmd = isUnix() ? 'docker' : 'docker'
                    if (isUnix()) {
                        sh "${dockerCmd} system prune -f"
                        sh "${dockerCmd} build -t ${IMAGE_NAME}:${IMAGE_TAG} --no-cache ."
                    } else {
                        bat "${dockerCmd} system prune -f"
                        bat "${dockerCmd} build -t ${IMAGE_NAME}:${IMAGE_TAG} --no-cache ."
                    }
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'dockerhub',
                                                    usernameVariable: 'DOCKER_USERNAME',
                                                    passwordVariable: 'DOCKER_PASSWORD')]) {
                        if (isUnix()) {
                            sh "echo \$DOCKER_PASSWORD | docker login -u \$DOCKER_USERNAME --password-stdin"
                            sh "docker push ${IMAGE_NAME}:${IMAGE_TAG}"
                        } else {
                            bat "echo %DOCKER_PASSWORD% | docker login -u %DOCKER_USERNAME% --password-stdin"
                            bat "docker push ${IMAGE_NAME}:${IMAGE_TAG}"
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                if (isUnix()) {
                    sh """
                        docker logout
                        docker rmi ${IMAGE_NAME}:${IMAGE_TAG} || true
                        docker image prune -f
                        docker builder prune -f
                    """
                } else {
                    bat """
                        docker logout
                        docker rmi %IMAGE_NAME%:%IMAGE_TAG% || exit 0
                        docker image prune -f
                        docker builder prune -f
                    """
                }
                cleanWs()
            }
        }
        failure {
            script {
                echo 'Pipeline failed! Cleaning up...'
                if (isUnix()) {
                    sh 'docker system prune -f'
                } else {
                    bat 'docker system prune -f'
                }
            }
        }
    }
}