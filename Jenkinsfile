pipeline {
  agent {
    label 'gradle-node'
  }
  stages {
    stage('Pull Source') {
      // Get some code from a GitHub repository
      steps {
        git 'https://github.com/nextcloud/android.git'
      }
    }
    stage('Build & Install') {
      steps {
        echo "Performing build"
        sh 'yes | sdkmanager --sdk_root=${ANDROID_HOME} --licenses && export ANDROID_SDK_ROOT=/home/jenkins/ && bash ./gradlew --no-daemon build'
      }
    }
    stage('Nexus Lifecycle Evaluation') {
      steps {
        nexusPolicyEvaluation failBuildOnNetworkError: false, iqApplication: 'Nextcloud', iqStage: 'build', jobCredentialsId: '', iqScanPatterns: [[scanPattern: '**/*.*']]
      }
    }
  }
}
