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
                sh 'docker build -t patilarun05/docker_demo:v.1.${BUILD_NUMBER} .'
                echo 'Completed  Building Docker Image'
            }
        }
		stage('Docker Image Push to Nexus') {
            steps {
                script {
                        withCredentials ([usernamePassword(credentialsId: 'nexuscred', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                            sh """
                            docker login http://13.234.111.14:8085/repository/docker-demo-new -u admin -p ${PASSWORD}
                            echo "Push Docker Image to Nexus : In Progress"
							docker tag docker_demo:v.1.${BUILD_NUMBER} 13.234.111.14:8085/docker-demo-new:v.1.${BUILD_NUMBER}
                            docker push 13.234.111.14:8085/docker-demo-new:v.1.${BUILD_NUMBER}
                            echo "Push Docker Image to Nexus : Completed"
                            whoami
                            """
                        }
                }
            }
        }
        stage('Docker Image Push to DockerHub') {
            steps {
                script {
                        withDockerRegistry ([credentialsId: 'dockerhubCred', variable: 'dockerhubCred']) {
                            echo "Tagging the Docker Image: In Progress"
                            sh 'docker tag docker_demo:v.1.${BUILD_NUMBER} patilarun05/docker_demo:v.1.${BUILD_NUMBER}'
                            echo "Tagging the Docker Image: Completed"
                            echo "Push Docker Image to DockerHub : In Progress"
                            sh 'docker push patilarun05/docker_demo:v.1.${BUILD_NUMBER}'
                            echo "Push Docker Image to DockerHub : Completed"
							sh 'whoami'
                        }
                }
            }
        }
        stage('Docker Image Push to Amazon ECR') {
            steps {
                script {
                        withDockerRegistry ([credentialsId: 'ecr:ap-south-1:ecr-credentials', url: 'https://944751024976.dkr.ecr.ap-south-1.amazonaws.com/docker_demo']) {
                            sh """
                            echo "List the docker images present in local"
                            docker images
                            echo "Tagging the Docker Image: In Progress"
                            docker tag docker_demo:v.1.${BUILD_NUMBER} 944751024976.dkr.ecr.ap-south-1.amazonaws.com/docker_demo:v.1.${BUILD_NUMBER}
                            echo "Tagging the Docker Image: Completed"
                            echo "Push Docker Image to ECR : In Progress"
                            docker push 944751024976.dkr.ecr.ap-south-1.amazonaws.com/docker_demo:v.1.${BUILD_NUMBER}
                            echo "Push Docker Image to ECR : Completed"
                            whoami
                            """
                        }

                }
            }
        }
    }
}