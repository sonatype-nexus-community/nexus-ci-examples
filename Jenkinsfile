pipeline {
  agent {
    label 'maven-node'
  }
  stages {
    stage('Pull Source') {
      // Get some code from a GitHub repository
      steps {
        git url: 'https://github.com/CMYanko/struts2-rce'
      }
    }
    stage('Build & Install') {
      steps {
        echo "Performing maven build"
        sh 'mvn clean install'
      }
    }
    stage('Nexus Lifecycle Evaluation') {
      steps {
        // see documentation https://help.sonatype.com/en/sonatype-platform-plugin-for-jenkins.html#adding-an-evaluation-to-a-build
        nexusPolicyEvaluation(
                        //advancedProperties: 'test=value',
                        //enableDebugLogging: true,
                        //failBuildOnNetworkError: true,
                        //failBuildOnScanningErrors: true, 
                        iqApplication: "${env.JOB_BASE_NAME}",
                        //iqInstanceId: 'MyIQServer1',
                        //iqModuleExcludes: [[moduleExclude: '**/module-2-exclude/module.xml'], [moduleExclude: '**/module-1-exclude/module.xml']],
                        //iqScanPatterns: [[scanPattern: '**/*.jar'], [scanPattern: '**/*.war'], [scanPattern: '**/*.ear'], [scanPattern: '**/*.zip'], [scanPattern: '**/*.tar.gz']],
                        iqStage: 'build',
                        jobCredentialsId: ''
        )
      }
    }
  }
}
