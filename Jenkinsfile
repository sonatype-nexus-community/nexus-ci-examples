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
	//Need to accept license in here, seems new hostname within the docker nodes makes Google want to make you agree again
	sh 'yes | sdkmanager --sdk_root=${ANDROID_HOME} --licenses && export ANDROID_SDK_ROOT=/home/jenkins/ && bash ./gradlew clean build'
      }
    }
    stage('Nexus Lifecycle Evaluation') {
      steps {
        nexusPolicyEvaluation failBuildOnNetworkError: false, iqApplication: "${env.JOB_BASE_NAME}", iqStage: 'build', jobCredentialsId: '', iqScanPatterns: [[scanPattern: '**/*.*']]
      }
    }
  }
}
