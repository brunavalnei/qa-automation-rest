package stepDefinitions.restApi;

import java.util.LinkedHashMap;

public class Api {

    private static String apiVersion = "";
    private static String host;
    private static String path = "";
    private static String domain = "";

    public static LinkedHashMap<String, String> userParameters = new LinkedHashMap<>();


    public static LinkedHashMap<String, String> getUserParameters() {
        return userParameters;
    }

    public static void setUserParameters(LinkedHashMap<String, String> userParameters) {
        Api.userParameters = userParameters;
    }

    public static String getApiVersion() {
        return apiVersion;
    }

    public static void setApiVersion(String apiVersion) {
        Api.apiVersion = apiVersion;
    }

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        Api.host = host;
    }

    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        Api.path = path;
    }

    public static String getDomain() {
        return domain;
    }

    public static void setDomain(String domain) {
        Api.domain = domain;
    }

    public static String loadVariable(String userKey){
        return Api.getUserParameters().get("${" + userKey + "}");
    }

    public static void saveVariable(String userKey, String value){
        Api.getUserParameters().put("${" + userKey + "}", value);
    }

}
