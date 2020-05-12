pipeline {
  agent {
    label 'maven-node'
  }
  stages {
    stage('Pull Source') {
      // Get some code from a GitHub repository
      steps {
        git url: 'https://github.com/CMYanko/struts2-rce'
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
        nexusPolicyEvaluation failBuildOnNetworkError: false, iqApplication: "${env.JOB_BASE_NAME}", iqStage: 'build', jobCredentialsId: ''
      }
    }
  }
}
