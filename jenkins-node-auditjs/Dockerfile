# This Dockerfile is used to build an image containing an node jenkins slave

FROM node
MAINTAINER Adam Such <opensource@adamjwsuch.com>

# Upgrade and Install packages
RUN apt-get update && apt-get -y upgrade && apt-get install -y git openssh-server && apt-get install -y openjdk-8-jdk

# Prepare container for ssh
RUN mkdir /var/run/sshd && adduser --quiet jenkins && echo "jenkins:jenkins" | chpasswd
RUN npm install -g auditjs

ENV CI=true
EXPOSE 22

CMD ["/usr/sbin/sshd", "-D"]
