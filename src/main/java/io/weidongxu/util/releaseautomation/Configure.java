package io.weidongxu.util.releaseautomation;

public class Configure {

    // the service name at https://github.com/Azure/azure-rest-api-specs/tree/main/specification
    private String swagger;

    // URL of the tspconfig.yaml file
    // e.g. https://github.com/Azure/azure-rest-api-specs/blob/042e4045dedff4baaf5ae551bf6c8087fbdacd40/specification/deviceregistry/DeviceRegistry.Management/tspconfig.yaml
    private String tspConfig;

    // preview release
    private boolean autoVersioning = true;

    // used only if preview=false
    // if preview=true, uses current next release version in https://github.com/Azure/azure-sdk-for-java/blob/main/eng/versioning/version_client.txt
    private String version;

    // tag in readme.md
    // prompt in stdin, if not provided
    private String tag;

    // the service name at https://github.com/Azure/azure-sdk-for-java/tree/main/sdk
    // same as swagger, if not provided
    private String service;

    // e.g. "containers" for namespace "com.azure.resourcemanager.hdinsight.containers"
    private String suffix;

    // generate tests
    private Boolean tests;

    public String getSwagger() {
        return swagger;
    }

    public void setSwagger(String swagger) {
        this.swagger = swagger;
    }

    public boolean isAutoVersioning() {
        return autoVersioning;
    }

    public void setAutoVersioning(boolean autoVersioning) {
        this.autoVersioning = autoVersioning;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean getTests() {
        return tests;
    }

    public void setTests(Boolean tests) {
        this.tests = tests;
    }

    public String getTspConfig() {
        return tspConfig;
    }

    public void setTspConfig(String tspConfig) {
        this.tspConfig = tspConfig;
    }
}
