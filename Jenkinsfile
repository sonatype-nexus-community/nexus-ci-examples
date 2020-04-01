pipeline {
  agent {
    label 'maven-node-java-11'
  }
  stages {
    stage('Pull Source') {
      // Get some code from a GitHub repository
      steps {
        git url: 'https://github.com/WebGoat/WebGoat.git'
      }
    }
    stage('Build & Install') {
      steps {
        echo "Performing maven build"
        sh 'mvn clean install'
      }
    }
    stage('Nexus Lifecycle Evaluation') {
      steps {
        nexusPolicyEvaluation failBuildOnNetworkError: false, iqApplication: 'WebGoat', iqStage: 'build', jobCredentialsId: ''
      }
    }
  }
}
