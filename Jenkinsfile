pipeline {
  agent {
    label 'npm-node'
  }
  stages {
    stage('Pull Source') {
      // Get some code from a GitHub repository
      steps {
        git url: 'https://github.com/OWASP/NodeGoat.git'
      }
    }
    stage('Build & Install') {
      steps {
        echo "Performing build"
        sh 'npm -v'
        sh 'npm install'
        sh 'npm pack'
      }
    }
    stage('Nexus Lifecycle Evaluation') {
      steps {
        nexusPolicyEvaluation failBuildOnNetworkError: false, iqApplication: 'Juice-shop', iqStage: 'build', jobCredentialsId: '', iqScanPatterns: [[scanPattern: '**/*.*']]
      }
    }
  }
}
