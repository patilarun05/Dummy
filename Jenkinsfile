pipeline{
    options {
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
    }
    agent any
    tools{
        maven 'maven-3.9.6'
    }
    stages{
        stage('Code compilation'){
            steps{
                echo 'Code compilation is in progress...'
                sh 'mvn clean compile'
                echo 'Code compilation completed.'
            }
        }
        stage('Code QA execution'){
            steps{
                echo 'Junit test case check in progress...'
                sh 'mvn clean test'
                echo 'Junit test case check completed.'
            }
        }
        stage('Code package'){
            steps{
                echo 'Creating war artifact'
                sh 'mvn clean package'
                echo 'Artifact created successfully.'
            }
        }
        stage('Building & Tag Docker Image') {
            steps {
                echo 'Starting Building Docker Image'
                sh 'docker build -t docker_demo:v.1.${BUILD_NUMBER} .'
                echo 'Completed  Building Docker Image'
            }
        }
        stage('Docker Image Push to Amazon ECR') {
            steps {
                script {
				    withDockerRegistry([credentialsId:'ecr:ap-south-1:ecr-credentials', url:"https://944751024976.dkr.ecr.ap-south-1.amazonaws.com/docker_demo"]){
                        sh """
                        echo "List the docker images present in local"
                        docker images
                        echo "Tagging the Docker Image: In Progress"
                        docker tag docker_demo:v.1.${BUILD_NUMBER} 944751024976.dkr.ecr.ap-south-1.amazonaws.com/docker_demo:v.1.${BUILD_NUMBER}
                        echo "Tagging the Docker Image: Completed"
                        echo "Push Docker Image to ECR : In Progress"
                        docker push 944751024976.dkr.ecr.ap-south-1.amazonaws.com/docker_demo:v.1.${BUILD_NUMBER}
                        echo "Push Docker Image to ECR : Completed"
                        sh """
                    }


                }
            }
        }
    }
}