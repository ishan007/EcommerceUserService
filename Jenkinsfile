pipeline {

    agent any
    tools {
            maven 'maven'
        }

    stages{
        stage('Build'){
            steps{
                echo '---------- BUILD STAGE STARTED --------------'
                sh 'mvn install'
                sh '${docker}/docker build -t i-userservice:latest -f docker/Dockerfile .'
                sh '${docker}/docker tag i-userservice:latest ishangaurav/i-userservice:latest'
                sh '${docker}/docker push ishangaurav/i-userservice:latest'
                sh '${docker}/kubectl apply -f k8s/userservice/deployment.yml'
                echo '---------- BUILD STAGE FINISHED --------------'
            }
        }
    }

}