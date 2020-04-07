# nexus-ci-examples
CI example builds for different languages

Each of the examples are split into branches so they can easily automatically be pulled into a Jenkins multibranch build job.

To make the builds repeatable and simple this project include Jenkins docker build nodes, these are Docker containers with a preconfigured build environment for each ecosystem, for example for Maven build the container preconfigures Maven and Java. The dockerfile and supporting content are provided with each build example.

