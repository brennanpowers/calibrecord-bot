package com.brennanpowers.discordbots.calibrecord.handler;

import com.brennanpowers.discordbots.calibrecord.config.properties.CommandProperties;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommandHandler implements Handler {
    private static final Logger log = LoggerFactory.getLogger(CommandHandler.class);

    private final CommandProperties commandProperties;
    private final CommandTools commandTools;

    public CommandHandler(CommandProperties commandProperties, CommandTools commandTools) {
        this.commandProperties = commandProperties;
        this.commandTools = commandTools;
    }

    @Override
    public void handle(MessageReceivedEvent event) {
        log.debug("Handling command message.");
        if (!shouldHandle(event)) {
            throw new IllegalArgumentException("CommandHandler is attempting to handle an event that it should not.");
        }
        String commandMessage = event.getMessage().getContentStripped();
        log.debug("Command message contents: {}", commandMessage);
        String[] commandSegments = commandMessage.split("\\s+");
        String commandString = commandSegments[0];
        log.debug("Command string: {}", commandString);
        commandString = commandString.replace(commandProperties.getPrefix(), "");
        Command command = Command.getCommand(commandString);
        log.debug("Command: {}", command);
        if (command == null) {
            log.debug("Command could not be resolved, setting to HELP");
            command = Command.HELP;
        }

        String outboundMessage;
        switch (command) {
            case HELLO:
                outboundMessage = commandTools.getHelloMessage();
                break;
            case CALIBRE_URL:
                outboundMessage = commandTools.getCalibreSiteMessage();
                break;
            case ALTERNATE_DROP_URL:
                outboundMessage = commandTools.getAlternateDropSiteMessage();
                break;
            case HELP:
            default:
                outboundMessage = commandTools.getHelpMessage();
        }
        log.debug("Outbound message: {}", outboundMessage);
        event.getChannel().sendMessage(outboundMessage).queue();
    }

    @Override
    public boolean shouldHandle(MessageReceivedEvent event) {
        Message message = event.getMessage();
        boolean shouldHandle = message.getContentStripped().startsWith(commandProperties.getPrefix());
        return shouldHandle;
    }

    private enum Command {
        HELLO("hello"),
        HELP("help"),
        CALIBRE_URL("link"),
        ALTERNATE_DROP_URL("drop");

        private final String value;

        Command(String value) {
            this.value = value;
        }

        public static Command getCommand(String value) {
            for (Command command : Command.values()) {
                if (command.getValue().equals(value)) {
                    return command;
                }
            }
            return null;
        }

        public String getValue() {
            return value;
        }
    }
}
