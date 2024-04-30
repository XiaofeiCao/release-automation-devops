package io.weidongxu.util.releaseautomation;

import com.azure.core.util.CoreUtils;
import com.azure.dev.models.Variable;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class TypeSpecLiteReleaseMetadata extends LiteReleaseMetadata {
    private final String tspConfig;
    protected TypeSpecLiteReleaseMetadata(Configure configure) {
        super(configure);
        tspConfig = configure.getTspConfig();
    }

    @Override
    public Map<String, Variable> generationPipelineVariables() {
        Map<String, Variable> variableMap = new HashMap<>();
        variableMap.put("TSP_CONFIG", new Variable().withValue(tspConfig));
        variableMap.put("VERSION", new Variable().withValue(configure.getVersion()));
        variableMap.put("README", new Variable().withValue(sdkName()));
        return variableMap;
    }

    @Override
    public String generationSource() {
        return configure.getSwagger();
    }

    @Override
    public String sdkName() {
        if (!CoreUtils.isNullOrEmpty(configure.getService())) {
            return configure.getService();
        } else {
            return Utils.getSdkName(configure.getSwagger());
        }
    }
}
