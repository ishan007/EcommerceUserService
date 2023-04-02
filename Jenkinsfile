pipeline {

    agent any

    stages{
        stage('Build'){
            steps{
                echo '---------- BUILD STAGE STARTED --------------'
                sh 'mvn install'
                sh '${PATH}docker build -t i-userservice:latest -f docker/Dockerfile .'
                sh '${PATH}docker tag i-userservice:latest ishangaurav/i-userservice:latest'
                sh '${PATH}docker push ishangaurav/i-userservice:latest'
                sh '${PATH}kubectl apply -f k8s/userservice/deployment.yml'
                echo '---------- BUILD STAGE FINISHED --------------'
            }
        }
    }

}