package com.brennanpowers.discordbots.calibrecord.handler;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface Handler {
    void handle(MessageReceivedEvent event);

    boolean shouldHandle(MessageReceivedEvent event);
}
