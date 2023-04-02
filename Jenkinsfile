pipeline {

    agent any
    tools {
            maven 'maven'
            dockerTool 'docker'
        }

    stages{
        stage('Build'){
            steps{
                echo '---------- BUILD STAGE STARTED --------------'
                sh './docker/build.sh'
                sh '${docker}/kubectl apply -f k8s/userservice/deployment.yml'
                echo '---------- BUILD STAGE FINISHED --------------'
            }
        }
    }

}