import com.nirima.jenkins.plugins.docker.DockerCloud
import com.nirima.jenkins.plugins.docker.DockerTemplate
import com.nirima.jenkins.plugins.docker.DockerTemplateBase
import com.nirima.jenkins.plugins.docker.launcher.AttachedDockerComputerLauncher
import io.jenkins.docker.connector.DockerComputerAttachConnector
import io.jenkins.docker.connector.DockerComputerJNLPConnector;
import jenkins.model.Jenkins

def dockerCloudParameters = [
  connectTimeout:   3,
  containerCapStr:  '4',
  credentialsId:    '',
  dockerHostname:   '',
  name:             'Demo system auto added',
  readTimeout:      60,
  serverUrl:        'unix:///var/run/docker.sock',
  version:          ''
]

def dockerTemplateBaseParametersMaven = [
  image:              'adamjwsuch/jenkins-node-maven:latest'
]

def DockerTemplateParametersMaven = [
  instanceCapStr: '4',
  labelString:    'maven-node',
  remoteFs:       ''
]

def dockerTemplateBaseParametersGradle = [
  image:              'adamjwsuch/jenkins-node-gradle:latest'
]

def DockerTemplateParametersGradle = [
  instanceCapStr: '4',
  labelString:    'gradle-node',
  remoteFs:       ''
]

def dockerTemplateBaseParametersNPM = [
  image:              'adamjwsuch/jenkins-node-npm:latest'
]

def DockerTemplateParametersNPM = [
  instanceCapStr: '4',
  labelString:    'npm-node',
  remoteFs:       ''
]

DockerTemplateBase dockerTemplateBaseMaven = new DockerTemplateBase(
  dockerTemplateBaseParametersMaven.image
)

DockerTemplate dockerTemplateMaven = new DockerTemplate(
  dockerTemplateBaseMaven,
  new DockerComputerAttachConnector(),
  DockerTemplateParametersMaven.labelString,
  DockerTemplateParametersMaven.remoteFs,
  DockerTemplateParametersMaven.instanceCapStr
)

DockerTemplateBase dockerTemplateBaseGradle = new DockerTemplateBase(
  dockerTemplateBaseParametersGradle.image
)

DockerTemplate dockerTemplateGradle = new DockerTemplate(
  dockerTemplateBaseGradle,
  new DockerComputerAttachConnector(),
  DockerTemplateParametersGradle.labelString,
  DockerTemplateParametersGradle.remoteFs,
  DockerTemplateParametersGradle.instanceCapStr
)

DockerTemplateBase dockerTemplateBaseNPM = new DockerTemplateBase(
  dockerTemplateBaseParametersNPM.image
)

DockerTemplate dockerTemplateNPM = new DockerTemplate(
  dockerTemplateBaseNPM,
  new DockerComputerAttachConnector(),
  DockerTemplateParametersNPM.labelString,
  DockerTemplateParametersNPM.remoteFs,
  DockerTemplateParametersNPM.instanceCapStr
)

DockerCloud dockerCloud = new DockerCloud(
  dockerCloudParameters.name,
  [
    dockerTemplateMaven,
    dockerTemplateGradle,
    dockerTemplateNPM 
  ],
  dockerCloudParameters.serverUrl,
  dockerCloudParameters.containerCapStr,
  dockerCloudParameters.connectTimeout,
  dockerCloudParameters.readTimeout,
  dockerCloudParameters.credentialsId,
  dockerCloudParameters.version,
  dockerCloudParameters.dockerHostname
)

// get Jenkins instance
Jenkins jenkins = Jenkins.getInstance()

// add cloud configuration to Jenkins
jenkins.clouds.add(dockerCloud)
dockerTemplateMaven.setMode(Node.Mode.EXCLUSIVE)
dockerTemplateMaven.setRemoteFs("/home/jenkins")
dockerTemplateMaven.connector.setUser("jenkins")
dockerTemplateGradle.setMode(Node.Mode.EXCLUSIVE)
dockerTemplateGradle.setRemoteFs("/home/jenkins")
dockerTemplateGradle.connector.setUser("jenkins")
dockerTemplateNPM.setMode(Node.Mode.EXCLUSIVE)
dockerTemplateNPM.setRemoteFs("/home/jenkins")
dockerTemplateNPM.connector.setUser("jenkins")

clouds*.name.each { cloudName ->
     println "Configured docker cloud ${cloudName}"
}
// save current Jenkins state to disk
jenkins.save()
