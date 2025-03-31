package com.onixbyte.clearledger.service;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * Service for processing Freemarker templates.
 * <p>
 * Provides functionality to render templates with dynamic parameters into strings, typically for
 * use in email content or other text generation tasks.
 *
 * @author zihluwang
 */
@Service
public class TemplateService {

    private static final String SUFFIX = ".ftlh";

    private final Configuration freemarkerConfig;

    /**
     * Constructs a template service with the required Freemarker configuration.
     *
     * @param freemarkerConfig the Freemarker {@link Configuration} for loading and processing templates
     */
    @Autowired
    public TemplateService(Configuration freemarkerConfig) {
        this.freemarkerConfig = freemarkerConfig;
    }

    /**
     * Processes a Freemarker template with the provided parameters.
     * <p>
     * Loads the specified template, applies the given parameters, and returns the rendered result
     * as a string. Automatically appends the ".ftlh" suffix to the template name if not present.
     *
     * @param templateName the name of the template to process (without suffix if not provided)
     * @param params       the map of parameters to substitute into the template
     * @return the rendered template as a string
     * @throws IllegalArgumentException if the template name is null or empty, the parameters map is null,
     *                                  or the template cannot be loaded or processed
     */
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
