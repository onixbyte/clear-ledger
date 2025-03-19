package com.onixbyte.clearledger.service;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

@Service
public class TemplateService {

    private static final String SUFFIX = ".ftlh";

    private final Configuration freemarkerConfig;

    public TemplateService(Configuration freemarkerConfig) {
        this.freemarkerConfig = freemarkerConfig;
    }

    public String process(String templateName, Map<String, Object> params) {
        // validate inputs
        if (templateName == null || templateName.trim().isEmpty()) {
            throw new IllegalArgumentException("Template name cannot be null or empty");
        }
        if (params == null) {
            throw new IllegalArgumentException("Parameters map cannot be null");
        }

        if (!templateName.endsWith(SUFFIX)) {
            templateName += SUFFIX;
        }

        try {
            // load the template
            var template = freemarkerConfig.getTemplate(templateName);

            // process the template with parameters
            var output = new StringWriter();
            template.process(params, output);

            return output.toString();
        } catch (IOException | TemplateException e) {
            throw new IllegalArgumentException("Failed to load template: " + templateName, e);
        }
    }

}
