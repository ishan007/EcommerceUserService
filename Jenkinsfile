pipeline {

    agent any
    tools {
            maven 'maven'
        }

    stages{
        stage('Build'){
            steps{
                echo '---------- BUILD STAGE STARTED --------------'
                sh '${bin}/kubectl apply -f k8s/db/deployment.yml'
                sh 'mvn install'
                sh '${bin}/docker build -t i-userservice:latest -f docker/Dockerfile .'
                sh '${bin}/docker tag i-userservice:latest ishangaurav/i-userservice:latest'
                sh '${bin}/docker push ishangaurav/i-userservice:latest'
                sh '${bin}/kubectl apply -f k8s/userservice/deployment.yml'
                echo '---------- BUILD STAGE FINISHED --------------'
            }
        }
    }

}