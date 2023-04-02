pipeline {

    agent any

    stages{
        stage('Build'){
            steps{
                echo '---------- BUILD STAGE STARTED --------------'
                sh './docker/build.sh'
                sh 'kubectl apply -f k8s/userservice/deployment.yml'
                echo '---------- BUILD STAGE FINISHED --------------'
            }
        }
    }

}