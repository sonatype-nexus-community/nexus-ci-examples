pipeline {
  agent {
    label 'rust-node'
  }
  stages {
    stage('Pull Source') {
      // Get some code from a GitHub repository
      steps {
        git url: 'https://github.com/drbawb/rust-story'
      }
    }
    stage('Build & Install') {
      steps {
        echo "Performing build"
      }
    }
    stage('Nexus Lifecycle Evaluation') {
      steps {
        sh 'java -jar /usr/bin/nexus-iq-cli.jar -a admin:admin123 -i ${env.JOB_BASE_NAME} -s ${IQserver} . --stage Build'
      }
    }
  }
}
