pipeline {
  agent {
    label 'gradle-node'
  }
  stages {
    stage('Pull Source') {
      // Get some code from a GitHub repository
      steps {
        git branch: 'dev', url: 'https://github.com/HoraApps/LeafPic'
      }
    }
    stage('Build & Install') {
      steps {
        echo "Performing build"
        sh 'export ANDROID_HOME=/home/jenkins/ && bash ./gradlew clean build'
      }
    }
    stage('Nexus Lifecycle Evaluation') {
      steps {
        nexusPolicyEvaluation failBuildOnNetworkError: false, iqApplication: 'LeafPic', iqStage: 'build', jobCredentialsId: '', iqScanPatterns: [[scanPattern: '**/*.*']]
      }
    }
  }
}
