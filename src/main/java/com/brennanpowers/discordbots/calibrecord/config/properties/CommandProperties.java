package com.brennanpowers.discordbots.calibrecord.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "command")
public class CommandProperties {
    private final static String DEFAULT_PREFIX = "!";

    private String prefix = DEFAULT_PREFIX;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
