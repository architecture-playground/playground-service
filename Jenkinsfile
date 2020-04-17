#!groovy

properties([disableConcurrentBuilds()])

pipeline{
    agent{
        label 'master'
    }
    triggers { pollSCM('* * * * *') }
    options {
        buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '10'))
        timestamps()
    }
    stages{
//        stage("Login to Docker"){
//            steps{
//                echo "** Docker login "
//                withCredentials([usernamePassword(credentialsId: 'dockerhub_architectureplayground', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
//                    sh '''
//                        docker login -u $USERNAME -p $PASSWORD
//                    '''
//                }
//            }
//        }
        stage("Run Tests and Build Docker"){
            steps{
                sh ('''#!/bin/bash -ex
echo "** Building docker image started" && \\
docker build -t architectureplayground/playground:latest . && \\
echo "** Building docker image finished" && \\

docker run -i --rm -v /var/run/docker.sock:/var/run/docker.sock architectureplayground/playground:latest

#echo "** Start pushing docker image in docker hub repository" && \\
#docker push architectureplayground/playground:latest && \\
#echo "** End pushing docker image in docker hub repository"
''')
            }
        }
    }
}
