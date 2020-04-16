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
        stage("Build Docker"){
            steps{
                sh ('''#!/bin/bash -ex
echo "** Building docker image started" && \\
docker build -t playground:latest . && \\
echo "** Building docker image finished"
''')
            }
        }
    }
}
