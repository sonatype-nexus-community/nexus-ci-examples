pipeline {
  agent {
    label 'maven-node-java-15'
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
        sh 'mvn clean install -DskipTests'
      }
    }
    stage('Nexus Lifecycle Evaluation') {
      steps {
        nexusPolicyEvaluation failBuildOnNetworkError: false, iqApplication: "${env.JOB_BASE_NAME}", iqStage: 'build', jobCredentialsId: ''
      }
    }
  }
}
