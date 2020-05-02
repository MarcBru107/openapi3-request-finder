package de.bruckm.openapirequestfinder;

import java.util.ArrayList;

/**
 * RequestsFromYaml
 * 
 * httpMethod -> POST,DELETE,...
 * 
 * nameResponseObject -> yaml schema name of the reauest object
 * 
 * requestLanguage -> protobuf, json
 * 
 * tags -> tag from the yaml
 */
public class RequestsFromYaml {

    private String httpMethod;
    private String nameResponseObject;
    private String requestLanguage;
    private ArrayList tags;

    public String getHttpMethod() {
        return this.httpMethod;
    }

    public void setHttpMethod(final String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getNameResponseObject() {
        return this.nameResponseObject;
    }

    public void setNameResponseObject(final String nameResponseObject) {
        this.nameResponseObject = nameResponseObject;
    }

    public String getRequestLanguage() {
        return this.requestLanguage;
    }

    public void setRequestLanguage(final String requestLanguage) {
        this.requestLanguage = requestLanguage;
    }

    public ArrayList getTags() {
        return this.tags;
    }

    public void setTags(final ArrayList tags) {
        this.tags = tags;
    }
}
