pipeline {
  agent {
    label 'cmake-node'
  }
  stages {
    stage('Pull Source') {
      // Get some code from a GitHub repository
      steps {
        git 'https://github.com/opencv/opencv'
      }
    }
    stage('Build & Install') {
      steps {
        echo "Performing build"
        sh 'mkdir build && cd build && cmake -D CMAKE_BUILD_TYPE=Release .. && ls -al'
      }
    }
    stage('Nexus Lifecycle Evaluation') {
      steps {
        sh 'java -jar /usr/bin/nexus-iq-cli.jar -a ${IQusername}:${IQpassword} -i cpp-opencv -s ${IQserver} . --stage Build'
      }
    }
  }
}
