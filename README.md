# Sonatype Lifecycle CI Examples
CI example builds and Sonatype Lifecycle analysis for different languages.

Each of the examples are split into separate Git branches, so they can easily automatically be pulled into a CI multibranch build job, as explained below for Jenkins CI.

|Language  |Build System         |Lifecycle Integration             |Git Branch|
|----------|---------------------|----------------------------------|:-----|
|C++       |cmake                |[Sonatype CLI][CLI]               |[c++-cmake-opencv](../../tree/cpp-cmake-opencv)|
|ios      |ios                 |[Sonatype Jenkins Plugin][Jenkins]|[ios-weather-app](../../tree/ios-weather-app)|
|Java      |Java                 |[Github Action][Actions]|[BenchmarkJava](../../tree/BenchmarkJava)|
|Java      |Gradle/Android Studio|[Sonatype CLI][CLI]               |[java-android-gradle-LeafPic](../../tree/java-android-gradle-LeafPic)|
|Java      |Gradle/Android Studio|[Sonatype CLI][CLI]               |[java-android-gradle-nextcloud](../../tree/java-android-gradle-nextcloud)|
|Java      |Maven                |[Sonatype Jenkins Plugin][Jenkins]|[java-maven-struts2-rce](../../tree/java-maven-struts2-rce)|
|Java      |Maven                |[Sonatype Jenkins Plugin][Jenkins]|[java-maven-webgoat](../../tree/java-maven-webgoat)|
|Java      |Java                 |[Sonatype Jenkins Plugin][Jenkins]|[java-maven-clm-plugin-innersource](../../tree/java-maven-clm-plugin-innersource)|
|Java      |Java                 |[Sonatype Jenkins Plugin][Jenkins]|[java-maven-clm-plugin-innersource](../../tree/java-maven-sbom-struts2-rce)|
|JS      |Node                 |[Sonatype Jenkins Plugin][Jenkins]|[JuiceShopGHA](../../tree/JuiceShopGHA)|
|JS      |Node                 |[Sonatype Jenkins Plugin][Jenkins]|[NodeGoatGHA](../../tree/NodeGoatGHA)|
|JS|NPM                  |AuditJS                           |[javascript-auditjs-juiceshop](../../tree/javascript-auditjs-juiceshop)|
|JS|NPM                  |[Sonatype Jenkins Plugin][Jenkins]|[javascript-npm-juiceshop](../../tree/javascript-npm-juiceshop)|
|JS|NPM                  |[Github Actions][Actions]|[javascript-npm-nodegoat](../../tree/javascript-npm-nodegoat)|
|Nuget      |Nuget                 |[Sonatype Jenkins Plugin][Jenkins]|[C#-NuGet-MvcWebApp](../../tree/C#-NuGet-MvcWebApp)|
|PHP       |Composer             |[Sonatype CLI][CLI]               |[php-composer-symfony](../../tree/php-composer-symfony)|
|Python    |PIP                  |Jake                              |[python-jake-homeassistant](../../tree/python-jake-homeassistant)|
|Python    |PIP                  |[Sonatype Jenkins Plugin][Jenkins]|[python-pip-homeassistant](../../tree/python-pip-homeassistant)|
|Python    |Python                 |[Sonatype Jenkins Plugin][Jenkins]|[python-jake-sbom-ado](../../tree/python-jake-sbom-ado)|
|Rust      |Rust                 |[Sonatype Jenkins Plugin][Jenkins]|[rust-rust-story](../../tree/rust-rust-story)|
|Rust      |Rust                 |[Sonatype Jenkins Plugin][Jenkins]|[vuln-rust-project](../../tree/vuln-rust-project)|




To make the builds repeatable and simple, this project include Jenkins Docker build nodes provided as Docker containers with a preconfigured build environment for each ecosystem; for example for Maven build example, the container [preconfigures Maven and Java](https://github.com/sonatype-nexus-community/nexus-ci-examples/blob/java-maven-struts2-rce/jenkins-node-maven/Dockerfile). The Dockerfile and supporting content are provided with each build example branch.

## Usage instructions for Jenkins
### Jenkins setup

You will need the Jenkins "Docker" and “Nexus Platform” plugins 

Jenkins -> Manage Jenkins -> Manage Plugins -> Available
            Type: “Nexus Platform” in the search box
            Select the Nexus Platform plugin, then “Install without restart”

### Configure the Nexus Plugin
Jenkins -> Manage Jenkins -> Configure System

Scroll down to the Sonatype Nexus section

“Add IQ Server”

and add the IQ server configuration and cridentials, you can check the connection on this page.

### Install the Docker Plugin
Jenkins -> Manage Jenkins -> Manage Plugins -> Available
            Type: “Docker” in the search box
            Select the “Docker” plugin (not the Docker API plugin)

Select “Download now and install after restart”
             Select the “Restart after download when no jobs are running”

### Configure the Docker Cloud
The groovy scipt on the project can automate the setup of the Docker plugin.
                        Click on the set-up-cloud.groovy file
                        Click the “raw” button near the top
                        Copy the entire file

Login to Jenkins:
                        Jenkins -> Manage Jenkins -> Script Console (it’s under Tools and Actions)

Paste the text/copy buffer into the window

Search for 'IQserver' and change the IQ URL to match you system configuration (bear in mind that this is from within a docker build node, not from the Jenkins machine itself)
For example if you use Docker Desktop and your IQ server is running on the same host as docker you would use: http://host.docker.internal:8070
You will also need to configure the location the system can use to reach your docker host. For a local Jenkins and Docker install the unix socket in the default config will work, for other systems, such as Jenkins within docker you will need to mount the socket as a volume or expose the API to http.


### Jenkins Job Configuration
Before configuring this, you need to create a GitHub token for Jenkins to access the GitHub repository with.  Failure to do this will result in errors caused by hitting the GitHub API limits.

### Create a GitHub “Token”
Open http://www.github.com  and “Signin”.    You may need to provide your 2FA token using Google Authenticator.

Click on your user icon in the upper right corner -> Settings

At the bottom left -> Developer settings -> Personal access tokens

[Generate new token]


### Create the Jenkins job

Jenkins -> Create a job

              Enter an item name:  Nexus Example Builds

              Scroll to the bottom and select: Multibranch Pipeline.  -> OK



              Display Name:  Nexus Example Builds

              Branch Sources -> Add source -> GitHub

                        Credentials:  Add -> Jenkins

                                    Username:      <username>@sonatype.com

                                    Password:       Paste your token password

                                    ID:                   NexusExampleBuilds-GitHub

                                    Description:   NexusExampleBuilds-GitHub

                                    [Add]

                        Credentials:  Select NexusExampleBuilds-GitHub

                        Repository HTTPS URL:  https://github.com/sonatype-nexus-community/nexus-ci-examples.git

                                    [Validate]

                        Scroll to the bottom and select “Save”



At this point – Builds for all the platforms/eco-systems will automatically kick off.

[CLI]: https://help.sonatype.com/en/sonatype-iq-cli.html
[Jenkins]: https://help.sonatype.com/en/sonatype-platform-plugin-for-jenkins.html
[Actions]: https://help.sonatype.com/en/sonatype-github-actions.html
