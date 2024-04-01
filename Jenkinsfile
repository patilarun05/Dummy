pipeline{
    agent any
    tools{
        maven 'maven_3.9.6'
    }
    stages{
        stage('Code compilation'){
            steps{
                echo 'Code compilation is in progress...'
                sh 'mvn clean compile'
                echo 'Code compilation completed'
            }
        }
        stage('Code QA execution'){
            steps{
                echo 'Junit test case check in progress...'
                sh 'mvn clean test'
                echo 'Junit test case check completed'
            }
        }
        stage('Code package'){
            steps{
                echo 'Creating war artifact'
                sh 'mvn clean package'
                echo 'Artifact created successfully'
            }
        }
    }
}