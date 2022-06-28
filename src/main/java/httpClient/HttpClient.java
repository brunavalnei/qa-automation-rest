package httpClient;

import common.utils.PropertiesUtil;

import stepDefinitions.restApi.Api;

import static java.util.Objects.nonNull;

public class HttpClient {
    private String hostSchema;
    private String hostName;
    private String apiVersion;
    private Integer hostPort;

    public HttpClient(String domain, String apiVersion, String hostName, Integer hostPort) {
        this.hostName = hostName;
        this.hostPort = hostPort;
        this.httpClienteConfigurator(domain, apiVersion);
    }

    public HttpClient(String domain, String apiVersion) {
        hostName = PropertiesUtil.getProperty("host.name");
        String sHostPort = PropertiesUtil.getProperty("host.port");
        if (nonNull(PropertiesUtil.getProperty("host.port"))) hostPort = Integer.valueOf(sHostPort);
        this.httpClienteConfigurator(domain, apiVersion);
    }

    private void httpClienteConfigurator(String domain, String apiVersion) {
        hostSchema = PropertiesUtil.getProperty(domain + ".host.schema");
        this.apiVersion = apiVersion;

        if (apiVersion == null || !apiVersion.isEmpty()) {
            this.apiVersion = PropertiesUtil.getProperty("api.version");

            if (domain != null && !domain.isEmpty()) {

                if (PropertiesUtil.getProperty(domain + ".host.name") != null && !PropertiesUtil.getProperty(domain + ".host.name").isEmpty()) {
                    hostSchema = PropertiesUtil.getProperty(domain + ".host.schema");
                    hostName = PropertiesUtil.getProperty(domain + ".host.name");
                    hostPort = Integer.valueOf(PropertiesUtil.getProperty(domain + ".host.port"));
                    this.apiVersion = PropertiesUtil.getProperty(domain + ".api.version");
                }
            }
            if (!"localhost".equalsIgnoreCase(hostName)){
//                informação do proxy se tiver
            }
        }
    }

    public static String getCompletePath() {
        String completePath = PropertiesUtil.getProperty("host.schema") + "://" + PropertiesUtil.getProperty("host.name") + ":"
                + PropertiesUtil.getProperty("host.port") + PropertiesUtil.getProperty("api.version") + Api.getPath();
        if (Api.getApiVersion() != null && !Api.getApiVersion().isEmpty()) {
            completePath = PropertiesUtil.getProperty("host.schema") + "://" + PropertiesUtil.getProperty("host.name") + ":"
                    + PropertiesUtil.getProperty("host.port") + Api.getApiVersion() + Api.getPath();
        }
        if (Api.getDomain() != null && !Api.getDomain().isEmpty()) {
            if (PropertiesUtil.getProperty(Api.getDomain() + ".host.name") != null && PropertiesUtil.getProperty(Api.getDomain() + ".host.name").isEmpty()) {
                completePath = PropertiesUtil.getProperty(Api.getDomain() + ".host.schema") + "://" + PropertiesUtil.getProperty(Api.getDomain() + ".host.name") + ":"
                        + PropertiesUtil.getProperty(Api.getDomain() + ".host.port") + PropertiesUtil.getProperty(Api.getDomain() + "api.version") + Api.getPath();
            }
        }
        return completePath;
    }
}






