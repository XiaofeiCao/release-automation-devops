package io.weidongxu.util.releaseautomation;

import com.azure.core.http.HttpPipeline;
import com.azure.core.http.HttpPipelineBuilder;
import com.azure.core.util.CoreUtils;
import com.azure.dev.models.Variable;
import com.google.common.collect.Maps;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

/**
 */
public class SwaggerLiteReleaseMetadata extends LiteReleaseMetadata {
    private static final HttpPipeline HTTP_PIPELINE = new HttpPipelineBuilder().build();
    private static final boolean PREFER_STABLE_TAG = false;
    private static final InputStream IN = System.in;
    private static final PrintStream OUT = System.out;
    private final String sdk;
    private final String swagger;
    private final Map<String, Variable> generationPipelineVariables;

    protected SwaggerLiteReleaseMetadata(Configure configure) throws Exception {
        super(configure);

        swagger = configure.getSwagger();
        String sdk = Utils.getSdkName(swagger);
        if (!CoreUtils.isNullOrEmpty(configure.getService())) {
            sdk = configure.getService();
        }
        this.sdk = sdk;

        OUT.println("swagger: " + swagger);
        OUT.println("sdk: " + sdk);

        String tag = configure.getTag();
        if (CoreUtils.isNullOrEmpty(tag)) {
            ReadmeConfigure readmeConfigure = Utils.getReadmeConfigure(HTTP_PIPELINE, swagger);
            readmeConfigure.print(OUT, 3);

            tag = readmeConfigure.getDefaultTag();
            if (tag == null) {
                tag = readmeConfigure.getTagConfigures().iterator().next().getTagName();
            }
            if (PREFER_STABLE_TAG) {
                if (tag.endsWith("-preview")) {
                    Optional<String> stableTag = readmeConfigure.getTagConfigures().stream()
                            .map(ReadmeConfigure.TagConfigure::getTagName)
                            .filter(name -> !name.endsWith("-preview"))
                            .findFirst();
                    if (stableTag.isPresent()) {
                        tag = stableTag.get();
                    }
                }
            }
            OUT.println("choose tag: " + tag + ". Override?");
            Scanner s = new Scanner(IN);
            String input = s.nextLine();
            if (!input.trim().isEmpty()) {
                tag = input.trim();
            }
        }
        OUT.println("tag: " + tag);

        if (configure.isAutoVersioning() && !tag.contains("-preview")) {
            ReadmeConfigure readmeConfigure = Utils.getReadmeConfigure(HTTP_PIPELINE, swagger);
            final String tagToRelease = tag;
            Optional<ReadmeConfigure.TagConfigure> tagConfigure = readmeConfigure.getTagConfigures().stream()
                    .filter(t -> Objects.equals(tagToRelease, t.getTagName()))
                    .findFirst();
            boolean previewInputFileInTag = tagConfigure.isPresent()
                    && tagConfigure.get().getInputFiles().stream().anyMatch(f -> f.contains("/preview/"));

            if (!previewInputFileInTag) {
                // if stable is released, and current tag is also stable
                VersionConfigure.parseVersion(HTTP_PIPELINE, sdk).ifPresent(sdkVersion -> {
                    if (sdkVersion.isStableReleased()) {
                        configure.setAutoVersioning(false);
                        configure.setVersion(sdkVersion.getCurrentVersionAsStable());

                        OUT.println("release for stable: " + configure.getVersion());
                    }
                });
            }
        }

        generationPipelineVariables = new HashMap<>();
        generationPipelineVariables.put("README", new Variable().withValue(swagger));
        generationPipelineVariables.put("TAG", new Variable().withValue(tag));
//        variables.put("DRAFT_PULL_REQUEST", new Variable().withValue("false"));
        if (!configure.isAutoVersioning()) {
            generationPipelineVariables.put("VERSION", new Variable().withValue(configure.getVersion()));
        }
        if (!CoreUtils.isNullOrEmpty(configure.getService())) {
            generationPipelineVariables.put("SERVICE", new Variable().withValue(configure.getService()));
        }
        if (!CoreUtils.isNullOrEmpty(configure.getSuffix())) {
            generationPipelineVariables.put("SUFFIX", new Variable().withValue(configure.getSuffix()));
        }
        if (configure.getTests() == Boolean.TRUE) {
            generationPipelineVariables.put("AUTOREST_OPTIONS", new Variable().withValue("--generate-tests"));
        }
    }

    @Override
    public Map<String, Variable> generationPipelineVariables() {
        return Maps.newHashMap(this.generationPipelineVariables);
    }

    @Override
    public String generationSource() {
        return this.swagger;
    }

    @Override
    public String sdkName() {
        return this.sdk;
    }
}
