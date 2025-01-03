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
                git branch: 'master',
                    url: 'https://github.com/knaznarjes/backend-devops-pronto'

                script {
                    bat 'mkdir -p .m2'
                    bat 'chmod +x mvnw || true'
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    bat 'java -version'
                    bat './mvnw -v'

                    // Clean and compile
                    bat './mvnw clean compile -e'
                }
            }
        }

        stage('Package') {
            when {
                expression { currentBuild.resultIsBetterOrEqualTo('SUCCESS') }
            }
            steps {
                bat './mvnw package -DskipTests -e'
            }
        }

        stage('Build Docker Image') {
            when {
                expression { currentBuild.resultIsBetterOrEqualTo('SUCCESS') }
            }
            steps {
                script {
                    bat 'docker system prune -f'
                    bat "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} --no-cache ."
                }
            }
        }

        stage('Push to Docker Hub') {
            when {
                expression { currentBuild.resultIsBetterOrEqualTo('SUCCESS') }
            }
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'dockerhub',
                                                    usernameVariable: 'DOCKER_USERNAME',
                                                    passwordVariable: 'DOCKER_PASSWORD')]) {
                        bat "echo %DOCKER_PASSWORD% | docker login -u %DOCKER_USERNAME% --password-stdin"
                        bat "docker push ${IMAGE_NAME}:${IMAGE_TAG}"
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
