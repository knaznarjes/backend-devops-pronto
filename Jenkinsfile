pipeline {
    agent any

    triggers {
        pollSCM('* * * * *')
    }

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub')
        IMAGE_NAME_SERVER = 'narjesknaz/spring-backend-server'
        IMAGE_NAME_CLIENT = 'narjesknaz/spring-backend-client'
    }

    stages {
        stage('Checkout') {
            steps {
                cleanWs()
                git branch: 'master',
                    url: 'https://github.com/knaznarjes/backend-devops-pronto'
            }
        }

        stage('Build Server Image') {
            steps {
                dir('server') {
                    script {
                        dockerImageServer = docker.build("${IMAGE_NAME_SERVER}")
                    }
                }
            }
        }

        stage('Build Client Image') {
            steps {
                dir('client') {
                    script {
                        dockerImageClient = docker.build("${IMAGE_NAME_CLIENT}")
                    }
                }
            }
        }

        stage('Test Docker Hub Login') {
            steps {
                sh '''
                echo "${DOCKERHUB_CREDENTIALS_PSW}" | docker login -u "${DOCKERHUB_CREDENTIALS_USR}" --password-stdin
                '''
            }
        }

        stage('Push Images to Docker Hub') {
            steps {
                script {
                    sh """
                    echo "${DOCKERHUB_CREDENTIALS_PSW}" | docker login -u "${DOCKERHUB_CREDENTIALS_USR}" --password-stdin
                    docker push ${IMAGE_NAME_SERVER}
                    docker push ${IMAGE_NAME_CLIENT}
                    """
                }
            }
        }
    }

    post {
        always {
            script {
                sh '''
                    docker logout
                    docker system prune -f
                '''
                cleanWs()
            }
        }
        failure {
            script {
                echo 'Pipeline failed! Cleaning up...'
                sh 'docker system prune -f'
            }
        }
    }
}
