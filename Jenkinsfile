pipeline {

    agent any
    tools {
            maven 'maven'
        }

    stages{
        stage('Build'){
            steps{
                echo '---------- BUILD STAGE STARTED --------------'
                sh './build.sh'
                sh '${docker}/kubectl apply -f k8s/userservice/deployment.yml'
                echo '---------- BUILD STAGE FINISHED --------------'
            }
        }
    }

}