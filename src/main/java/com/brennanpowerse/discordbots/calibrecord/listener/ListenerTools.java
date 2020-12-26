package com.brennanpowerse.discordbots.calibrecord.listener;

import com.brennanpowerse.discordbots.calibrecord.config.properties.ListenerProperties;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ListenerTools {
    private static final Logger log = LoggerFactory.getLogger(ListenerTools.class);

    private final ListenerProperties listenerProperties;

    public ListenerTools(ListenerProperties listenerProperties) {
        this.listenerProperties = listenerProperties;
    }

    public boolean isListeningToChannel(MessageChannel channel) {
        String channelName = channel.getName();
        log.debug("Channel name: {}", channelName);
        log.debug("Channel list: {}", listenerProperties.getChannels());
        boolean validChannel = listenerProperties.getChannelsAsList()
                .stream()
                .anyMatch(name -> name.equalsIgnoreCase(channelName));
        log.debug("Valid channel? {}", validChannel);
        return validChannel;
    }
}
