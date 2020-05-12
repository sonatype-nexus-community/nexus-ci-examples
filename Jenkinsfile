pipeline {
  agent {
    label 'compose-node'
  }
  stages {
    stage('Pull Source') {
      // Get some code from a GitHub repository
      steps {
        git url: 'https://github.com/symfony/symfony.git'
      }
    }
    stage('Build & Install') {
      steps {
        echo "Performing build"
        sh 'composer -V'
        sh 'composer install'
      }
    }
    stage('Nexus Lifecycle Evaluation') {
      steps {
        sh 'java -jar /usr/bin/nexus-iq-cli.jar -a admin:admin123 -i ${env.JOB_BASE_NAME} -s ${IQserver} . --stage Build'
      }
    }
  }
}
