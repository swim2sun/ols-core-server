pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/tw-nho/user-service.git', branch: 'master'
            }

        }
        stage('Test') {
            steps {
                sh 'scripts/test.sh'
            }
        }
        stage('Build') {
            steps {
                sh 'scripts/build.sh'
            }
        }
        stage('Deploy') {
            steps {
                sh 'scripts/deploy.sh'
            }
        }
    }
}