# Openapi3-request-finder

### Only testet for openapi: 3.0.1

## Why:

Helps you to get the request objects from a openapi-swagger-file     
Use de.bruckm.openapirequestfinder.AnalyseOpenApiFile:
- public HashMap<String, RequestsFromYaml> getRequestsMap(final String requestType)
     * Parse the open api input file for the requested request-type
     * @param requestType -> json,protobuf,..
     * @return Map with the url as key and the request as value -> including only entries from typ requestType
    
## Dependencies:
    - org.yaml.snakeyaml
    - junit-jupiter-api
