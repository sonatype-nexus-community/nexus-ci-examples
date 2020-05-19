# nexus-ci-examples
CI example builds for different languages:
- [c++-cmake-opencv](../../tree/c++-cmake-opencv)
- [java-android-gradle-LeafPic](../../tree/java-android-gradle-LeafPic)
- [java-android-gradle-nextcloud](../../tree/java-android-gradle-nextcloud)
- [java-maven-struts2-rce](../../tree/java-maven-struts2-rce)
- [java-maven-webgoat](../../tree/java-maven-webgoat)
- [javascript-auditjs-juiceshop](../../tree/javascript-auditjs-juiceshop)
- [javascript-npm-juiceshop](../../tree/javascript-npm-juiceshop)
- [javascript-npm-nodegoat](../../tree/javascript-npm-nodegoat)
- [php-composer-symfony](../../tree/php-composer-symfony)
- [python-jake-homeassistant](../../tree/python-jake-homeassistant)
- [python-pip-homeassistant](../../tree/python-pip-homeassistant)
- [rust-rust-story](../../tree/rust-rust-story)

Each of the examples are split into separate Git branches, so they can easily automatically be pulled into a Jenkins multibranch build job.

To make the builds repeatable and simple, this project include Jenkins Docker build nodes provided as Docker containers with a preconfigured build environment for each ecosystem; for example for Maven build example, the container preconfigures Maven and Java. The Dockerfile and supporting content are provided with each build example branch.
