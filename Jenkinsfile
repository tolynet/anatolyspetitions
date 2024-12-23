pipeline {
    agent any

    tools{
        git 'Default'
        maven 'Default'
        dockerTool 'Default'
    }
    stages {
        stage('GetProject') {
            steps {
                git branch: 'main', url: 'https://github.com/tolynet/anatolyspetitions.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }
        stage('Archive') {
            steps {
                archiveArtifacts allowEmptyArchive: true,
                                artifacts: '**/target/*.war'
            }
        }
        stage('Approve Deployment') {
            steps {
                input message: 'Deploy to production?', ok: 'Deploy'
            }
        }
        stage('Deploy') {
            steps {
                sh 'docker build -f Dockerfile -t webapp .'
                sh 'docker rm -f "webappcontainer" || true'
                sh 'docker run --name "webappcontainer" -p 9090:8080 --detach webapp:latest'
            }
        }
    }
}