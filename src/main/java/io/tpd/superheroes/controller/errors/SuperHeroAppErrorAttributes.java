package io.tpd.superheroes.controller.errors;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

class SuperHeroAppErrorAttributes extends DefaultErrorAttributes {
    private final String currentApiVersion;
    private final String sendReportUri;

    public SuperHeroAppErrorAttributes(final String currentApiVersion, final String sendReportUri) {
        this.currentApiVersion = currentApiVersion;
        this.sendReportUri = sendReportUri;
    }

    @Override
    public Map<String, Object> getErrorAttributes(final WebRequest webRequest, final boolean includeStackTrace) {
        final Map<String, Object> defaultErrorAttributes = super.getErrorAttributes(webRequest, false);
        final Map<String, Object> customErrorAttributes = new HashMap<>();
        customErrorAttributes.put("api_version", currentApiVersion);
        customErrorAttributes.put("error", SuperHeroAppError.fromAttributeMap(defaultErrorAttributes, sendReportUri));
        return customErrorAttributes;
    }
}
