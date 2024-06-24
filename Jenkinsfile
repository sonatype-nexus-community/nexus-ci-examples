pipeline {
  agent any

  stages {
    stage('Pull Source') {
      // Get some code from a GitHub repository
      steps {
        git branch: 'dev', url: 'https://github.com/ctownshend/iosWeatherApp'
      }
    }
    stage('Build & Install') {
      steps {
        echo "Performing build - iOS stuff here"
      }
    }
    stage('Nexus Lifecycle Evaluation') {
      steps {
        nexusPolicyEvaluation failBuildOnNetworkError: false, iqApplication: "${env.JOB_BASE_NAME}", iqStage: 'build', jobCredentialsId: '', iqScanPatterns: [[scanPattern: 'Podilfe.lock']]
      }
    }
  }
}
