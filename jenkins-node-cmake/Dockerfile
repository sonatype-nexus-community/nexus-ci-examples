FROM openjdk:8-jdk-slim
MAINTAINER Adam Such <opensource@adamjwsuch.com>

# make Apt non-interactive
RUN echo 'APT::Get::Assume-Yes "true";' > /etc/apt/apt.conf.d/90jenkins \
  && echo 'DPkg::Options "--force-confnew";' >> /etc/apt/apt.conf.d/90jenkins

ENV DEBIAN_FRONTEND=noninteractive

# Make sure PATH includes ~/.local/bin
# https://bugs.debian.org/cgi-bin/bugreport.cgi?bug=839155
# This only works for root. The jenkins user is done near the end of this Dockerfile
RUN echo 'PATH="$HOME/.local/bin:$PATH"' >> /etc/profile.d/user-local-path.sh

RUN apt-get update \
  && mkdir -p /usr/share/man/man1 \
  && apt-get install -y \
    git xvfb apt \
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

RUN apt-get update && \
    apt-get install --yes make cmake autoconf build-essential libssl-dev groff-base autogen libtool pkg-config && \
    rm -rf /var/lib/apt/lists/*

RUN curl https://download.sonatype.com/clm/scanner/nexus-iq-cli-1.90.0-01.jar -L -o /usr/bin/nexus-iq-cli.jar && \
chmod a+rx /usr/bin/nexus-iq-cli.jar

USER jenkins
ENV PATH /home/jenkins/.local/bin:/home/jenkins/bin:${PATH}
ENV HOME /home/jenkins

CMD ["/bin/sh"]
