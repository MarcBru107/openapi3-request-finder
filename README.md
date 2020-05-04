# Openapi3-request-finder

###### Only testet for openapi: 3.0.1

## Why:

#### Helps you to get the request objects from a openapi-swagger-file   


Use de.bruckm.openapirequestfinder.OpenApiRequestFinder:
- public HashMap<String, RequestsFromYaml> getRequestsMap(final String requestType)
     * Parse the open api input file for the requested request-type
     * @param requestType -> json,protobuf,..
     * @return Map with the url as key and the request as value -> including only entries from typ requestType
    
## Dependencies:
    - org.yaml.snakeyaml
    - org.junit.jupiter.api
    - org.slf4j.Logger
    - com.google.protobuf:protobuf-java
    - com.googlecode.protobuf-java-format
