pipeline {
  agent {
    label 'gradle-node'
  }
  stages {
    stage('Pull Source') {
      // Get some code from a GitHub repository
      steps {
        git ''https://github.com/adamjwsuch/android.git
      }
    }
    stage('Build & Install') {
      steps {
        echo "Performing build"
        sh 'yes | sdkmanager --sdk_root=${ANDROID_HOME} --licenses && export ANDROID_SDK_ROOT=/home/jenkins/ && bash ./gradlew --no-daemon cyclonedxBom'
      }
    }
    stage('Nexus Lifecycle Evaluation') {
      steps {
        nexusPolicyEvaluation failBuildOnNetworkError: false, iqApplication: "${env.JOB_BASE_NAME}", iqStage: 'build', jobCredentialsId: '', iqScanPatterns: [[scanPattern: 'build/reports/bom.xml']]
      }
    }
  }
}
