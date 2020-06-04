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
        sh 'npm -v && npm install'
      }
    }
    stage('AuditJS Scan Dev Scan') {
      steps {
        sh 'npx auditjs@latest iq -a ${JOB_BASE_NAME}-auditjs --server ${IQserver} -u ${IQusername} -p ${IQpassword} -s build --dev'
      }
    }
    stage('AuditJS Scan') {
      steps {
        sh 'npx auditjs@latest iq -a ${JOB_BASE_NAME}-auditjs --server ${IQserver} -u ${IQusername} -p ${IQpassword} -s stage-release'
      }
    }
    stage('AuditJS Scan ') {
      steps {
        sh 'wget https://download.sonatype.com/clm/scanner/latest.jar'
        sh 'npx auditjs@latest sbom > auditjs-bom.xml'
      }
    }
  }
}
