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
        // Need to create application in IQ Server 1st for auditjs scans.  javascript-auditjs-juiceshop-auditjs
        sh 'npx auditjs@latest iq -a ${JOB_BASE_NAME}-auditjs --server ${IQserver} -u ${IQusername} -p ${IQpassword} -s build --dev'
      }
    }
    stage('AuditJS Scan') {
      steps {
        // Need to create application in IQ Server 1st for auditjs scans.  javascript-auditjs-juiceshop-auditjs
        sh 'npx auditjs@latest iq -a ${JOB_BASE_NAME}-auditjs --server ${IQserver} -u ${IQusername} -p ${IQpassword} -s stage-release'
      }
    }
    stage('CLI Scan Full Directory') {
      steps {
        sh 'wget https://download.sonatype.com/clm/scanner/latest.jar' // should be in docker file
        sh 'java -jar nexus-cli*.jar -s ${IQserver} -a ${IQusername}:${IQpassword} -i ${JOB_BASE_NAME} -s build .'
      }
    }
    stage('Jenkins Plugin Scan Full Directory') {
      steps {
        nexusPolicyEvaluation failBuildOnNetworkError: false, iqApplication: "${JOB_BASE_NAME}", iqStage: 'stage-release', jobCredentialsId: '', iqScanPatterns: [[scanPattern: '**/*.*']]
      }
    }
    stage('CLI Scan Full node_modules') {
      steps {
        sh 'java -jar nexus-cli*.jar -s ${IQserver} -a ${IQusername}:${IQpassword} -i ${JOB_BASE_NAME}-node_modules -s build node_modules'
      }
    }
    stage('Jenkins Plugin Scan node_modules') {
      steps {
        nexusPolicyEvaluation failBuildOnNetworkError: false, iqApplication: "${JOB_BASE_NAME}-node_modules", iqStage: 'stage-release', jobCredentialsId: '', iqScanPatterns: [[scanPattern: 'node_modules/**/*.*']]
      }
    }
    stage('AuditJS SBOM - CLI Scan') {
      steps {
        sh 'npx auditjs@latest sbom > auditjs-bom.xml'
        sh 'java -jar nexus-cli*.jar -s ${IQserver} -a ${IQusername}:${IQpassword} -i ${JOB_BASE_NAME}-auditjs-bom -s build auditjs-bom.xml'
      }
    }
    stage('AuditJS SBOM - Jenkins Plugin Scan') {
      steps {
        nexusPolicyEvaluation failBuildOnNetworkError: false, iqApplication: "${JOB_BASE_NAME}-auditjs-bom", iqStage: 'stage-release', jobCredentialsId: '', iqScanPatterns: [[scanPattern: 'auditjs-bom.xml']]
      }
    }
    stage('AuditJS Dev SBOM - CLI Scan') {
      steps {
        sh 'npx auditjs@latest sbom --dev > auditjs-dev-bom.xml'
        sh 'java -jar nexus-cli*.jar -s ${IQserver} -a ${IQusername}:${IQpassword} -i ${JOB_BASE_NAME}-auditjs-dev-bom -s build auditjs-dev-bom.xml'
      }
    }
    stage('AuditJS Dev SBOM - Jenkins Plugin Scan') {
      steps {
        nexusPolicyEvaluation failBuildOnNetworkError: false, iqApplication: "${JOB_BASE_NAME}-auditjs-dev-bom", iqStage: 'stage-release', jobCredentialsId: '', iqScanPatterns: [[scanPattern: 'auditjs-dev-bom.xml']]
      }
    }
  }
}
