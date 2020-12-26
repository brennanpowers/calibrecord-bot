package com.brennanpowers.discordbots.calibrecord.listener;

import com.brennanpowers.discordbots.calibrecord.handler.BookMessageHandler;
import com.brennanpowers.discordbots.calibrecord.handler.CommandHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DefaultMessageListener extends ListenerAdapter {
    private static final Logger log = LoggerFactory.getLogger(DefaultMessageListener.class);
    private final ListenerTools listenerTools;
    private final BookMessageHandler bookMessageHandler;
    private final CommandHandler commandHandler;

    public DefaultMessageListener(ListenerTools listenerTools, BookMessageHandler bookMessageHandler, CommandHandler commandHandler) {
        this.listenerTools = listenerTools;
        this.bookMessageHandler = bookMessageHandler;
        this.commandHandler = commandHandler;
    }

    @Override
    public void onMessageReceived(final MessageReceivedEvent event) {
        boolean isBotMessage = event.getAuthor().isBot();
        log.debug("Bot message? {}", isBotMessage);
        boolean isFromListenerChannel = listenerTools.isListeningToChannel(event.getChannel());
        log.debug("Is from listener channel? {}", isFromListenerChannel);
        boolean ignoreMessage = isBotMessage || !isFromListenerChannel;
        if (ignoreMessage) {
            log.debug("Ignoring message.");
            return;
        }

        if (commandHandler.shouldHandle(event)) {
            commandHandler.handle(event);
        } else if (bookMessageHandler.shouldHandle(event)) {
            bookMessageHandler.handle(event);
        }

    }
}
