FROM openjdk:8-jdk-slim
MAINTAINER Adam Such <opensource@adamjwsuch.com>

# make Apt non-interactive
RUN echo 'APT::Get::Assume-Yes "true";' > /etc/apt/apt.conf.d/90jenkins \
  && echo 'DPkg::Options "--force-confnew";' >> /etc/apt/apt.conf.d/90jenkins

ENV DEBIAN_FRONTEND=noninteractive

# Debian Jessie is EOL'd and original repos don't work.
# Switch to the archive mirror until we can get people to
# switch to Stretch.
RUN if grep -q Debian /etc/os-release && grep -q jessie /etc/os-release; then \
	rm /etc/apt/sources.list \
    && echo "deb http://archive.debian.org/debian/ jessie main" >> /etc/apt/sources.list \
    && echo "deb http://security.debian.org/debian-security jessie/updates main" >> /etc/apt/sources.list \
	; fi

# Make sure PATH includes ~/.local/bin
# https://bugs.debian.org/cgi-bin/bugreport.cgi?bug=839155
# This only works for root. The circleci user is done near the end of this Dockerfile
RUN echo 'PATH="$HOME/.local/bin:$PATH"' >> /etc/profile.d/user-local-path.sh

# man directory is missing in some base images
# https://bugs.debian.org/cgi-bin/bugreport.cgi?bug=863199
RUN apt-get update \
  && mkdir -p /usr/share/man/man1 \
  && apt-get install -y \
    git mercurial xvfb apt \
    locales sudo openssh-client ca-certificates tar gzip parallel \
    net-tools netcat unzip zip bzip2 gnupg curl wget make


# Set timezone to UTC by default
RUN ln -sf /usr/share/zoneinfo/Etc/UTC /etc/localtime

# Use unicode
RUN locale-gen C.UTF-8 || true
ENV LANG=C.UTF-8

RUN groupadd --gid 3434 jenkins \
  && useradd --uid 3434 --gid jenkins --shell /bin/bash --create-home jenkins \
  && echo 'jenkins ALL=NOPASSWD: ALL' >> /etc/sudoers.d/50-jenkins \
  && echo 'Defaults    env_keep += "DEBIAN_FRONTEND"' >> /etc/sudoers.d/env_keep

# BEGIN IMAGE CUSTOMIZATIONS
# END IMAGE CUSTOMIZATIONS

USER jenkins
ENV PATH /home/jenkins/.local/bin:/home/jenkins/bin:${PATH}

CMD ["/bin/sh"]

ENV HOME /home/jenkins

RUN sudo apt-get update && \
    sudo apt-get install --yes \
        xvfb lib32z1 lib32stdc++6 build-essential \
        libcurl4-openssl-dev libglu1-mesa libxi-dev libxmu-dev \
        libglu1-mesa-dev git openssh-server && \
    sudo rm -rf /var/lib/apt/lists/*

# Install Ruby
RUN sudo apt-get update && \
    cd /tmp && wget -O ruby-install-0.6.1.tar.gz https://github.com/postmodern/ruby-install/archive/v0.6.1.tar.gz && \
    tar -xzvf ruby-install-0.6.1.tar.gz && \
    cd ruby-install-0.6.1 && \
    sudo make install && \
    ruby-install --cleanup ruby 2.6.1 && \
    rm -r /tmp/ruby-install-* && \
    sudo rm -rf /var/lib/apt/lists/*

ENV PATH ${HOME}/.rubies/ruby-2.6.1/bin:${PATH}
RUN echo 'gem: --env-shebang --no-rdoc --no-ri' >> ~/.gemrc && gem install bundler

# Download and install Android SDK
RUN sudo mkdir -p ${android_home} && \
    sudo chown -R circleci:circleci ${android_home} && \
    curl --silent --show-error --location --fail --retry 3 --output /tmp/${sdk_version} https://dl.google.com/android/repository/${sdk_version} && \
    unzip -q /tmp/${sdk_version} -d ${android_home} && \
    rm /tmp/${sdk_version}

# Set environmental variables
ENV ANDROID_HOME ${android_home}
ENV ADB_INSTALL_TIMEOUT 120
ENV PATH=${ANDROID_HOME}/emulator:${ANDROID_HOME}/tools:${ANDROID_HOME}/tools/bin:${ANDROID_HOME}/platform-tools:${PATH}

RUN mkdir ~/.android && echo '### User Sources for Android SDK Manager' > ~/.android/repositories.cfg

RUN yes | sdkmanager --licenses && yes | sdkmanager --update

# Update SDK manager and install system image, platform and build tools
RUN sdkmanager \
  "tools" \
  "platform-tools" \
  "emulator"

RUN sdkmanager \
  "build-tools;25.0.0" \
  "build-tools;25.0.1" \
  "build-tools;25.0.2" \
  "build-tools;25.0.3" \
  "build-tools;26.0.1" \
  "build-tools;26.0.2" \
  "build-tools;27.0.0" \
  "build-tools;27.0.1" \
  "build-tools;27.0.2" \
  "build-tools;27.0.3" \
  "build-tools;28.0.0" \
  "build-tools;28.0.1" \
  "build-tools;28.0.2" \
  "build-tools;28.0.3" \
  "build-tools;29.0.0" \
  "build-tools;29.0.1" \
  "build-tools;29.0.2"

RUN sed -i 's|session    required     pam_loginuid.so|session    optional     pam_loginuid.so|g' /etc/pam.d/sshd
RUN mkdir -p /var/run/sshd

# Add user jenkins to the image
RUN adduser --quiet jenkins

#Something in Jenkins had a hard coded path for java so linking to its actual location
RUN ln -s /usr/bin/java /bin/java
# Standard SSH port
EXPOSE 22

CMD ["/usr/sbin/sshd", "-D"]
