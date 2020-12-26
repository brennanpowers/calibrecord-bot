package com.brennanpowerse.discordbots.calibrecord.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "calibre")
public class CalibreProperties {
    private String importPathAbsolute;
    private String validExtensions;
    private String calibreUrl;
    private String alternateDropUrl;
    private String alternateDropPassword;

    public String getImportPathAbsolute() {
        return importPathAbsolute;
    }

    public void setImportPathAbsolute(String importPathAbsolute) {
        this.importPathAbsolute = importPathAbsolute;
    }

    public String getValidExtensions() {
        return validExtensions;
    }

    public void setValidExtensions(String validExtensions) {
        this.validExtensions = validExtensions;
    }

    public List<String> getValidExtensionsAsList() {
        return ConfigurationPropertiesTools.getListFromCommaSeparatedString(validExtensions);
    }

    public String getValidExtensionsFormatted() {
        // Format each channel to an bold version, i.e. **.epub**
        String[] array = getValidExtensionsAsList().stream().map(channel -> String.format("**%s**", channel)).toArray(String[]::new);
        return Arrays.toString(array);
    }

    public String getCalibreUrl() {
        return calibreUrl;
    }

    public void setCalibreUrl(String calibreUrl) {
        this.calibreUrl = calibreUrl;
    }

    public String getCalibreUrlAsLink() {
        return ConfigurationPropertiesTools.formatStringAsLink(getCalibreUrl());
    }

    public String getAlternateDropUrl() {
        return alternateDropUrl;
    }

    public void setAlternateDropUrl(String alternateDropUrl) {
        this.alternateDropUrl = alternateDropUrl;
    }

    public String getAlternateDropUrlAsLink() {
        return ConfigurationPropertiesTools.formatStringAsLink(getAlternateDropUrl());
    }

    public String getAlternateDropPassword() {
        return alternateDropPassword;
    }

    public void setAlternateDropPassword(String alternateDropPassword) {
        this.alternateDropPassword = alternateDropPassword;
    }
}
