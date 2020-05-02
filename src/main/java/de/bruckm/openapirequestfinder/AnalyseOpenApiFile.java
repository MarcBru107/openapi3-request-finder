package de.bruckm.openapirequestfinder;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.yaml.snakeyaml.Yaml;

/**
 * AnalyseOpenApiFile.java
 */
public class AnalyseOpenApiFile {

    private static final String YAML_PATH_TO_SCHEMAS = "#/components/schemas/";
    private static final String JAVA_SUBCLASS_PREFIX = "$";
    private static final String YAML_REQUEST_BODY_TAG = "requestBody";
    private static final String CONTENT = "content";
    private static final String SCHEMA = "schema";
    private static final String TAGS = "tags";
    final Yaml yaml = new Yaml();
    private final Map<String, Object> logConfigValueMap;

    /**
     * Constructor
     *
     * @param yamlInputStream
     */
    public AnalyseOpenApiFile(final InputStream yamlInputStream) {
        this.logConfigValueMap = (Map<String, Object>) this.yaml.load(yamlInputStream);
    }

    /**
     * Constructor
     *
     * @param targetReader
     */
    public AnalyseOpenApiFile(final Reader targetReader) {
        this.logConfigValueMap = (Map<String, Object>) this.yaml.load(targetReader);
    }

    /**
     * Parse the open api input file for the requested request-type
     *
     * @param requestType -> json,protobuf,..
     * @return Map with the url as key and the request as value -> including only entries from typ requestType
     */
    public HashMap<String, RequestsFromYaml> getRequestsMap(final String requestType) {
        final HashMap<String, RequestsFromYaml> responseMap = new LinkedHashMap<String, RequestsFromYaml>();
        final LinkedHashMap<String, Object> pathMap = (LinkedHashMap<String, Object>) this.logConfigValueMap.get("paths");

        final Set<String> keys = pathMap.keySet();
        for (final String k : keys) {
            final LinkedHashMap<String, Object> pathMapEntry = (LinkedHashMap) pathMap.get(k);
            final Set<String> keysREST = pathMapEntry.keySet();
            for (final String httpMethod : keysREST) {
                System.out.println(k + " " + httpMethod);
                final LinkedHashMap<String, Object> restEntry = (LinkedHashMap) pathMapEntry.get(httpMethod);
                if (restEntry.containsKey(YAML_REQUEST_BODY_TAG)) {
                    final ArrayList tags = (ArrayList) restEntry.get(TAGS);
                    final LinkedHashMap requestBody = (LinkedHashMap) restEntry.get(YAML_REQUEST_BODY_TAG);
                    final LinkedHashMap content = (LinkedHashMap) requestBody.get(CONTENT);
                    if (content.containsKey(requestType)) {
                        final LinkedHashMap typ = (LinkedHashMap) content.get(requestType);
                        final LinkedHashMap schema = (LinkedHashMap) typ.get(SCHEMA);
                        final Map.Entry<String, String> entry = (Map.Entry<String, String>) schema.entrySet().iterator().next();
                        System.out.println(k + " " + httpMethod + " " + entry.getValue());
                        responseMap.put(k, this.createYamlFilterResponse(httpMethod, entry.getValue(), requestType, tags));
                    }
                }
            }
        }
        return responseMap;
    }

    /**
     * Build the RequestsFromYaml
     *
     * @return RequestsFromYaml
     */
    private RequestsFromYaml createYamlFilterResponse(final String httpMethod, final String nameResponseObject, final String requestLanguage, final ArrayList tags) {
        final RequestsFromYaml response = new RequestsFromYaml();
        response.setHttpMethod(httpMethod);
        response.setRequestLanguage(requestLanguage);
        response.setNameResponseObject(nameResponseObject);
        response.setTags(tags);
        return response;
    }

    /**
     * In protobuf the request objects are always in the main classes in which they were defined.
     * <p>
     * This method simplifies the access to these request objects
     *
     * @param entry from the HashMap<String, RequestsFromYaml>
     * @param prefix package where all the requests are
     * @param mainProtobufClass as described above, the protobuf request are implemented as inner classes
     * @return the inner-class of the request
     */
    public Class<?> getSubRequestClass(final RequestsFromYaml entry, final String prefix, final String mainProtobufClass) {
        try {
            final String[] parts = entry.getNameResponseObject().split(YAML_PATH_TO_SCHEMAS);
            final String output = parts[1].substring(0, 1).toUpperCase() + parts[1].substring(1);
            final String pathToClass = prefix + mainProtobufClass + JAVA_SUBCLASS_PREFIX + output;
            return Class.forName(pathToClass);
        } catch (final ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
