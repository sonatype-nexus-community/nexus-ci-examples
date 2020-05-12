# nexus-ci-examples
CI example build for OpenCV demonstrating a Cmake build

Uses IQ CLI so requires Jenkins Global Environmental Variable to tell it where to reach the IQ server, note this must be reachable from a Docker build node, if you run Jenkins in Docker you may need to add the node to a Docker network.
