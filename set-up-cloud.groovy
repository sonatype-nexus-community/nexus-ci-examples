import com.nirima.jenkins.plugins.docker.DockerCloud
import com.nirima.jenkins.plugins.docker.DockerTemplate
import com.nirima.jenkins.plugins.docker.DockerTemplateBase
import com.nirima.jenkins.plugins.docker.launcher.AttachedDockerComputerLauncher
import io.jenkins.docker.connector.DockerComputerAttachConnector
import io.jenkins.docker.connector.DockerComputerJNLPConnector;
import hudson.EnvVars;
import hudson.slaves.EnvironmentVariablesNodeProperty;
import hudson.slaves.NodeProperty;
import hudson.slaves.NodePropertyDescriptor;
import hudson.util.DescribableList;
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

def dockerTemplateBaseParametersMaven11 = [
  image:              'adamjwsuch/jenkins-node-maven:latest-java-11'
]

def DockerTemplateParametersMaven11 = [
  instanceCapStr: '4',
  labelString:    'maven-node-java-11',
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

def dockerTemplateBaseParametersCompose = [
  image:              'adamjwsuch/jenkins-node-compose:latest'
]

def DockerTemplateParametersCompose = [
  instanceCapStr: '4',
  labelString:    'compose-node',
  remoteFs:       ''
]

def dockerTemplateBaseParametersPip = [
  image:              'adamjwsuch/jenkins-node-pip:latest'
]

def DockerTemplateParametersPip = [
  instanceCapStr: '4',
  labelString:    'pip-node',
  remoteFs:       ''
]

def dockerTemplateBaseParametersJake = [
  image:              'adamjwsuch/jenkins-node-jake:latest'
]

def DockerTemplateParametersJake = [
  instanceCapStr: '4',
  labelString:    'jake-node',
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

DockerTemplateBase dockerTemplateBaseMaven11 = new DockerTemplateBase(
  dockerTemplateBaseParametersMaven11.image
)

DockerTemplate dockerTemplateMaven11 = new DockerTemplate(
  dockerTemplateBaseMaven11,
  new DockerComputerAttachConnector(),
  DockerTemplateParametersMaven11.labelString,
  DockerTemplateParametersMaven11.remoteFs,
  DockerTemplateParametersMaven11.instanceCapStr
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

DockerTemplateBase dockerTemplateBaseCompose = new DockerTemplateBase(
  dockerTemplateBaseParametersCompose.image
)

DockerTemplate dockerTemplateCompose = new DockerTemplate(
  dockerTemplateBaseCompose,
  new DockerComputerAttachConnector(),
  DockerTemplateParametersCompose.labelString,
  DockerTemplateParametersCompose.remoteFs,
  DockerTemplateParametersCompose.instanceCapStr
)

DockerTemplateBase dockerTemplateBasePip = new DockerTemplateBase(
  dockerTemplateBaseParametersPip.image
)

DockerTemplate dockerTemplatePip = new DockerTemplate(
  dockerTemplateBasePip,
  new DockerComputerAttachConnector(),
  DockerTemplateParametersPip.labelString,
  DockerTemplateParametersPip.remoteFs,
  DockerTemplateParametersPip.instanceCapStr
)

DockerTemplateBase dockerTemplateBaseJake = new DockerTemplateBase(
  dockerTemplateBaseParametersJake.image
)

DockerTemplate dockerTemplateJake = new DockerTemplate(
  dockerTemplateBaseJake,
  new DockerComputerAttachConnector(),
  DockerTemplateParametersJake.labelString,
  DockerTemplateParametersJake.remoteFs,
  DockerTemplateParametersJake.instanceCapStr
)

DockerCloud dockerCloud = new DockerCloud(
  dockerCloudParameters.name,
  [
    dockerTemplateMaven,
    dockerTemplateMaven11,
    dockerTemplateGradle,
    dockerTemplateNPM,
    dockerTemplateCompose,
    dockerTemplatePip,
    dockerTemplateJake
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
dockerTemplateMaven11.setMode(Node.Mode.EXCLUSIVE)
dockerTemplateMaven11.setRemoteFs("/home/jenkins")
dockerTemplateMaven11.connector.setUser("jenkins")
dockerTemplateGradle.setMode(Node.Mode.EXCLUSIVE)
dockerTemplateGradle.setRemoteFs("/home/jenkins")
dockerTemplateGradle.connector.setUser("jenkins")
dockerTemplateNPM.setMode(Node.Mode.EXCLUSIVE)
dockerTemplateNPM.setRemoteFs("/home/jenkins")
dockerTemplateNPM.connector.setUser("jenkins")
dockerTemplateCompose.setMode(Node.Mode.EXCLUSIVE)
dockerTemplateCompose.setRemoteFs("/home/jenkins")
dockerTemplateCompose.connector.setUser("jenkins")
dockerTemplatePip.setMode(Node.Mode.EXCLUSIVE)
dockerTemplatePip.setRemoteFs("/home/jenkins")
dockerTemplatePip.connector.setUser("jenkins")
dockerTemplateJake.setMode(Node.Mode.EXCLUSIVE)
dockerTemplateJake.setRemoteFs("/home/jenkins")
dockerTemplateJake.connector.setUser("jenkins")

println "Configured docker cloud"

//Create global environmental variable for IQ URL
public createGlobalEnvironmentVariables(String key, String value){

        Jenkins instance = Jenkins.getInstance();

        DescribableList<NodeProperty<?>, NodePropertyDescriptor> globalNodeProperties = instance.getGlobalNodeProperties();
        List<EnvironmentVariablesNodeProperty> envVarsNodePropertyList = globalNodeProperties.getAll(EnvironmentVariablesNodeProperty.class);

        EnvironmentVariablesNodeProperty newEnvVarsNodeProperty = null;
        EnvVars envVars = null;

        if ( envVarsNodePropertyList == null || envVarsNodePropertyList.size() == 0 ) {
            newEnvVarsNodeProperty = new hudson.slaves.EnvironmentVariablesNodeProperty();
            globalNodeProperties.add(newEnvVarsNodeProperty);
            envVars = newEnvVarsNodeProperty.getEnvVars();
        } else {
            envVars = envVarsNodePropertyList.get(0).getEnvVars();
        }
        envVars.put(key, value)
        instance.save()
}
// *** Please edit the IQ URL to match you environment ***
createGlobalEnvironmentVariables('IQserver','http://iq:8080')
println "Configured Environmental Variable for IQ server URL, please chack it matches your configuration"

// save current Jenkins state to disk
jenkins.save()
