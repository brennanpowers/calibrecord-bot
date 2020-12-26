package com.brennanpowerse.discordbots.calibrecord.handler;

import com.brennanpowerse.discordbots.calibrecord.config.properties.CalibreProperties;
import com.brennanpowerse.discordbots.calibrecord.config.properties.CommandProperties;
import com.brennanpowerse.discordbots.calibrecord.config.properties.ListenerProperties;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class CommandTools {
    private final MessageSource messageSource;
    private final CommandProperties commandProperties;
    private final ListenerProperties listenerProperties;
    private final CalibreProperties calibreProperties;

    public CommandTools(MessageSource messageSource, CommandProperties commandProperties, ListenerProperties listenerProperties, CalibreProperties calibreProperties) {
        this.messageSource = messageSource;
        this.commandProperties = commandProperties;
        this.listenerProperties = listenerProperties;
        this.calibreProperties = calibreProperties;
    }

    public String getHelloMessage() {
        String partialHelloMessage = getMessage("hello", listenerProperties.getChannelsFormatted(), calibreProperties.getValidExtensionsFormatted());

        String calibreSiteMessage = getCalibreSiteMessage();
        String alternateDropMessage = getAlternateDropSiteMessage();
        String helpMessage = getHelpMessage();

        String fullHelloMessage = String.format("%s\n%s\n%s\n%s", partialHelloMessage, calibreSiteMessage, alternateDropMessage, helpMessage);
        return fullHelloMessage;
    }

    public String getCalibreSiteMessage() {
        return getMessage("calibre-library-site", calibreProperties.getCalibreUrlAsLink());
    }

    public String getHelpMessage() {
        return getMessage("help", commandProperties.getPrefix());
    }

    public String getAlternateDropSiteMessage() {
        String alternateDropUrl = calibreProperties.getAlternateDropUrlAsLink();
        String alternateDropMessage =
                (alternateDropUrl != null) ?
                        getMessage("alternate-drop-site", alternateDropUrl) : getMessage("no alternate-drop-site");
        return alternateDropMessage;
    }
    private String getMessage(String key, String... parameters) {
        return messageSource.getMessage(key, parameters, Locale.getDefault());
    }
}
