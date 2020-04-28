pipeline {
  agent {
    label 'rust-node'
  }
  stages {
    stage('Pull Source') {
      // Get some code from a GitHub repository
      steps {
        git url: 'https://github.com/drbawb/rust-story.git'
      }
    }
    stage('Build & Install') {
      steps {
        echo "Performing build"
	sh 'yes | sdkmanager --sdk_root=${ANDROID_HOME} --licenses && export ANDROID_SDK_ROOT=/home/jenkins/ && bash ./gradlew clean build'
      }
    }
    stage('Nexus Lifecycle Evaluation') {
      steps {
        //nexusPolicyEvaluation failBuildOnNetworkError: false, iqApplication: 'LeafPic', iqStage: 'build', jobCredentialsId: '', iqScanPatterns: [[scanPattern: '**/*.*']]
      }
    }
  }
}
