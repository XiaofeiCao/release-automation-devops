package io.weidongxu.util.releaseautomation;

import com.azure.dev.models.Variable;

import java.util.Map;

/**
 */
public class TypeSpecLiteReleaseMetadata extends LiteReleaseMetadata {
    protected TypeSpecLiteReleaseMetadata(Configure configure) {
        super(configure);
    }

    @Override
    public Map<String, Variable> generationPipelineVariables() {
        // TODO (xiaofeicao, 2024-04-29 17:38)
        throw new UnsupportedOperationException("method [generationPipelineVariables] not implemented in class [io.weidongxu.util.releaseautomation.TypeSpecLiteReleaseMetadata]");
    }

    @Override
    public String generationSource() {
        // TODO (xiaofeicao, 2024-04-29 17:38)
        throw new UnsupportedOperationException("method [generationSource] not implemented in class [io.weidongxu.util.releaseautomation.TypeSpecLiteReleaseMetadata]");
    }

    @Override
    public String sdkName() {
        // TODO (xiaofeicao, 2024-04-29 17:38)
        throw new UnsupportedOperationException("method [sdkName] not implemented in class [io.weidongxu.util.releaseautomation.TypeSpecLiteReleaseMetadata]");
    }

    @Override
    public int generationPipelineId() {
        // TODO (xiaofeicao, 2024-04-29 17:52)
        throw new UnsupportedOperationException("method [generationPipelineId] not implemented in class [io.weidongxu.util.releaseautomation.TypeSpecLiteReleaseMetadata]");
    }
}
