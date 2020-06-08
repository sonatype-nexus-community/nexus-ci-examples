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
    stage('Download Nexus-CLI') {
      steps {
        sh 'wget -nv -O nexus-iq-cli.jar https://download.sonatype.com/clm/scanner/latest.jar' // should be in docker file
      }
    }
    // ----------------------------- ci-jshop-auditjs - build - dev flag -----------------------------
    stage('AuditJS Scan Dev Scan') {
      steps {
        // Need to create application in IQ Server 1st for auditjs scans.  javascript-auditjs-juiceshop-auditjs
        sh 'npx auditjs@latest iq -a ci-jshop-auditjs --server ${IQserver} -u ${IQusername} -p ${IQpassword} -s build --dev'
      }
    }
    // ----------------------------- ci-jshop-auditjs - stage-release -----------------------------
    stage('AuditJS Scan') {
      steps {
        // Need to create application in IQ Server 1st for auditjs scans.  javascript-auditjs-juiceshop-auditjs
        sh 'npx auditjs@latest iq -a ci-jshop-auditjs --server ${IQserver} -u ${IQusername} -p ${IQpassword} -s stage-release'
      }
    }
    // ----------------------------- ci-jshop-full - build -----------------------------
    stage('CLI Scan Full Directory') {
      steps {
        sh 'java -jar nexus-iq-cli.jar -s ${IQserver} -a ${IQusername}:${IQpassword} -i ci-jshop-full -t build .'
      }
    }
    // ----------------------------- ci-jshop-full - stage-release -----------------------------
    stage('Jenkins Plugin Scan Full Directory') {
      steps {
        nexusPolicyEvaluation failBuildOnNetworkError: false, iqApplication: "ci-jshop-full", iqStage: 'stage-release', jobCredentialsId: '', iqScanPatterns: [[scanPattern: '**/*']]
      }
    }
    // ----------------------------- ci-jshop-node_modules - build -----------------------------
    stage('CLI Scan Full node_modules') {
      steps {
        sh 'java -jar nexus-iq-cli.jar -s ${IQserver} -a ${IQusername}:${IQpassword} -i ci-jshop-node_modules -t build node_modules'
      }
    }
    // ----------------------------- ci-jshop-node_modules - stage-release -----------------------------
    stage('Jenkins Plugin Scan node_modules') {
      steps {
        nexusPolicyEvaluation failBuildOnNetworkError: false, iqApplication: "ci-jshop-node_modules", iqStage: 'stage-release', jobCredentialsId: '', iqScanPatterns: [[scanPattern: 'node_modules/**/*']]
      }
    }
    // ----------------------------- ci-jshop-auditjs-bom - build -----------------------------
    stage('AuditJS SBOM - CLI Scan') {
      steps {
        sh 'npx auditjs@latest sbom > auditjs-bom.xml'
        sh 'java -jar nexus-iq-cli.jar -s ${IQserver} -a ${IQusername}:${IQpassword} -i ci-jshop-auditjs-bom -t build auditjs-bom.xml'
      }
    }
    // ----------------------------- ci-jshop-auditjs-bom - stage-release -----------------------------
    stage('AuditJS SBOM - Jenkins Plugin Scan') {
      steps {
        nexusPolicyEvaluation failBuildOnNetworkError: false, iqApplication: "ci-jshop-auditjs-bom", iqStage: 'stage-release', jobCredentialsId: '', iqScanPatterns: [[scanPattern: 'auditjs-bom.xml']]
      }
    }
    // ----------------------------- ci-jshop-auditjs-dev-bom - build -----------------------------
    stage('AuditJS Dev SBOM - CLI Scan') {
      steps {
        sh 'npx auditjs@latest sbom --dev > auditjs-dev-bom.xml'
        sh 'java -jar nexus-iq-cli.jar -s ${IQserver} -a ${IQusername}:${IQpassword} -i ci-jshop-auditjs-dev-bom -t build auditjs-dev-bom.xml'
      }
    }
    // ----------------------------- ci-jshop-auditjs-dev-bom - stage-release -----------------------------
    stage('AuditJS Dev SBOM - Jenkins Plugin Scan') {
      steps {
        nexusPolicyEvaluation failBuildOnNetworkError: false, iqApplication: "ci-jshop-auditjs-dev-bom", iqStage: 'stage-release', jobCredentialsId: '', iqScanPatterns: [[scanPattern: 'auditjs-dev-bom.xml']]
      }
    }
  }
}
