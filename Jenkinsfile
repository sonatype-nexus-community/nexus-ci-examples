pipeline {
    agent {
        label 'pip-node'
    }
    stages {
       stage('Preparation') { // for display purposes
          steps {
              // Get some code from a GitHub repository
              sh 'python3 -c \'import locale\''
              git 'https://github.com/home-assistant/home-assistant.git'
          }
       }
       stage('Build') {
            steps {
                sh 'python3 -m pip install homeassistant --user'
            }
       }
       stage('IQ Policy Check') {
            steps {
		//print Python version
                sh 'python3 -m pip -V'
		\\An example of how to use a local repo
                //sh 'python3 -m pip download -r requirements_all.txt --index-url=http://10.0.0.1:8081/repository/pypi-proxy/ --trusted-host 10.0.0.1'

                sh 'cp requirements_all.txt requirements.txt'
                nexusPolicyEvaluation failBuildOnNetworkError: false, iqApplication: 'home-assistant', iqStage: 'build', jobCredentialsId: '', iqScanPatterns: [[scanPattern: '**/*.*']]
            }
        }
    }
}
