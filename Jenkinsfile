pipeline {
  agent {
    label 'auditjs-node'
  }
  stages {
    stage('Pull Source') {
      // Get some code from a GitHub repository
      steps {
        git url: 'https://github.com/bkimminich/juice-shop.git'
      }
    }
    stage('Build & Install') {
      steps {
        echo "Performing build"
        sh 'npm -v && npm install && npm pack'
      }
    }
    stage('Nexus Lifecycle Evaluation') {
      steps {
        sh 'auditjs iq -d --server ${IQserver} -u ${IQusername} -p ${IQpassword} -a ${JOB_BASE_NAME} -s build'
      }
    }
  }
}
