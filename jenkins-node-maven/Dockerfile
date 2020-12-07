# This Dockerfile is used to build an image containing a jenkins node for maven builds

FROM debian:10
MAINTAINER Adam Such <opensource@adamjwsuch.com>

# Upgrade and Install packages
RUN apt-get update && apt-get -y upgrade && apt-get install -y git openssh-server openjdk-11-jdk maven

# Prepare container for ssh
RUN mkdir /var/run/sshd && adduser --quiet jenkins && echo "jenkins:jenkins" | chpasswd

ENV CI=true
ENV MAVEN_REPO=https://repo1.maven.org/maven2/
EXPOSE 22

CMD ["/usr/sbin/sshd", "-D"]
