pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub')
        IMAGE_NAME = 'narjesknaz/spring-backend'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/knaznarjes/backend-devops-pronto',
                    credentialsId: 'Gitlab_ssh'
            }
        }

        stage('Build Image') {
            steps {
                script {
                    dockerImage = docker.build("${back_end-backend}")
                }
            }
        }

        stage('Scan Image') {
            steps {
                script {
                    sh """
                        docker run --rm -v /var/run/docker.sock:/var/run/docker.sock \
                        aquasec/trivy:latest image --exit-code 0 --severity HIGH,CRITICAL \
                        ${back_end-backend}
                    """
                }
            }
        }

        stage('Push to DockerHub') {
            steps {
                script {
                    docker.withRegistry('', "${DOCKERHUB_CREDENTIALS}") {
                        dockerImage.push()
                    }
                }
            }
        }
    }

    post {
        always {
            sh 'docker system prune -f'
        }
    }
}