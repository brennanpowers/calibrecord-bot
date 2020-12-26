package com.brennanpowerse.discordbots.calibrecord.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "listener")
public class ListenerProperties {
    private String channels;
    private String commandPrefix;

    public String getChannels() {
        return channels;
    }

    public void setChannels(String channels) {
        this.channels = channels;
    }

    public List<String> getChannelsAsList() {
        return ConfigurationPropertiesTools.getListFromCommaSeparatedString(channels);
    }

    public String getChannelsFormatted() {
        // Format each channel to an italicized vesrion with a preceding #, i.e. *#books*
        String[] array = getChannelsAsList().stream().map(channel -> String.format("*#%s*", channel)).toArray(String[]::new);
        return Arrays.toString(array);
    }

    public String getCommandPrefix() {
        return commandPrefix;
    }

    public void setCommandPrefix(String commandPrefix) {
        this.commandPrefix = commandPrefix;
    }
}
