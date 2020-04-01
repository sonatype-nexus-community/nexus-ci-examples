FROM ubuntu:16.04
MAINTAINER Adam Such <opensource@adamjwsuch.com>

# Make sure the package repository is up to date.
RUN apt-get update && apt-get -y upgrade && apt-get install -y git openssh-server openjdk-8-jdk
RUN sed -i 's|session    required     pam_loginuid.so|session    optional     pam_loginuid.so|g' /etc/pam.d/sshd
RUN mkdir -p /var/run/sshd

# Add user jenkins to the image
RUN adduser --quiet jenkins

ADD licenses/* /home/jenkins/licenses/

#Add Android SDK
RUN cd /home/jenkins && wget http://dl.google.com/android/android-sdk_r22.3-linux.tgz && tar xfvz android-sdk_r22.3-linux.tgz && rm android-sdk_r22.3-linux.tgz && chmod -R 755 ./android-sdk-linux && ( while [ 1 ]; do sleep 5; echo y; done ) | ./android-sdk-linux/tools/android update sdk --no-ui

#Something in Jenkins had a hard coded path for java so linking to its actual location
RUN ln -s /usr/bin/java /bin/java
# Standard SSH port
EXPOSE 22

CMD ["/usr/sbin/sshd", "-D"]
