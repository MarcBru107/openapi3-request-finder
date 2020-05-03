package de.bruckm.openapirequestfinder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

/**
 * Test of AnalyseOpenApiFiletReadIn.java
 */
public class TestAnalyseOpenApiFiletReadIn {

    private static final String APPLICATION_X_PROTOBUF = "application/x-protobuf";

    @Test
    public void testWithInputStream() {
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("ExampleSwagger.yaml");
        final AnalyseOpenApiFile reader = new AnalyseOpenApiFile(inputStream);
        final HashMap<String, RequestsFromYaml> file = reader.getRequestsMap(APPLICATION_X_PROTOBUF);
        assertNotNull(file);

        assertEquals(4, file.size());
    }

    @Test
    public void testWithInputString() {
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("ExampleSwagger.yaml");
        final Reader targetReader = new InputStreamReader(inputStream);
        final AnalyseOpenApiFile reader = new AnalyseOpenApiFile(targetReader);
        final HashMap<String, RequestsFromYaml> file = reader.getRequestsMap(APPLICATION_X_PROTOBUF);
        assertNotNull(file);

        assertEquals(4, file.size());
    }

    @Test
    public void testWithInputStringGetProtobufRequest() {
        final InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("ExampleSwagger.yaml");
        final Reader targetReader = new InputStreamReader(inputStream);
        final AnalyseOpenApiFile reader = new AnalyseOpenApiFile(targetReader);
        final HashMap<String, RequestsFromYaml> requestMap = reader.getRequestsMap(APPLICATION_X_PROTOBUF);

        // Test
        assertNotNull(requestMap);
        assertEquals(4, requestMap.size());

        final RequestsFromYaml entry = requestMap.get("/v1/deadpool");
        final String prefix = "de.bruckm.openapirequestfinder.proto.";
        final String mainProtobufClass = (String) entry.getTags().get(0);

        final Class<?> request = reader.getInnerRequestClass(entry, prefix, mainProtobufClass);
        assertNotNull(request);
    }

}
