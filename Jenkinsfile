pipeline {
  agent {
    label 'maven-node-java-11'
  }
  stages {
    stage('Pull Source') {
      // Get some code from a GitHub repository
      steps {
        git url: 'https://github.com/sonatype/se-scripts/tree/master/herve/inner-source'
      }
    }
    stage('Build & Install Producer') {
      steps {
        echo "Performing maven build for producer"
        sh 'cd producer && mvn com.sonatype.clm:clm-maven-plugin:evaluate -Dclm.stage=build -Dclm.applicationId="${env.JOB_BASE_NAME}-consumer" -Dclm.serverUrl=${IQserver} -Dclm.username=${IQusername} -Dclm.password=${IQpassword}'
      }
    }
    stage('Build & Install Consumer') {
      steps {
        echo "Performing maven build for consumer"
        sh 'cd ../consumer && mvn com.sonatype.clm:clm-maven-plugin:evaluate -Dclm.stage=build -Dclm.applicationId="${env.JOB_BASE_NAME}-consumer" -Dclm.serverUrl=${IQserver} -Dclm.username=${IQusername} -Dclm.password=${IQpassword}'
      }
    }
  }
}
