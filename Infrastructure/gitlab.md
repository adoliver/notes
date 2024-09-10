# Gitlab Cloud SaaS
This document provides tips and tricks working with gitlab cloud service

## Custom security scans
The basic documentation lives here: https://docs.gitlab.com/ee/development/integrations/secure.html#artifacts

Concepts missing from the documentation:
* Docker filesystem linking
  * The Docker image provided is launched with options to link the pipeline runtime filesystem working directory with the Docker working directory by default.
* secret_detection scanning types can not currently be used with custom docker image

### Report information
Key Requirements:
* When dismissing a security vulnerability the "id" field is used. If the same "id" is generated again in the future, the vulnerability will be treated as dismissed.
* The vultnerability objects are collapsed when the location.file values are identical (?)
