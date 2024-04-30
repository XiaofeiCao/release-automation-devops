package io.weidongxu.util.releaseautomation;

import com.azure.core.util.CoreUtils;
import com.azure.dev.models.Variable;

import java.util.Map;

/**
 * Metadata for lite release.
 */
public abstract class LiteReleaseMetadata {
    protected final Configure configure;
    /**
     * @return source to generate SDK from
     */
    public abstract String generationSource();

    /**
     * @return sdk name, azure-resourcemanager-${sdkName}
     */
    public abstract String sdkName();

    /**
     * @return variables for codegen pipeline
     */
    public abstract Map<String, Variable> generationPipelineVariables();

    protected LiteReleaseMetadata(Configure configure) {
        this.configure = configure;
    }

    public static LiteReleaseMetadata fromConfigure(Configure configure) throws Exception {
        if (!CoreUtils.isNullOrEmpty(configure.getSwagger())) {
            return new SwaggerLiteReleaseMetadata(configure);
        } else if (!CoreUtils.isNullOrEmpty(configure.getTspConfig())) {
            return new TypeSpecLiteReleaseMetadata(configure);
        } else {
            throw new IllegalArgumentException("Neither swagger nor tspConfig is present!");
        }
    }
}
