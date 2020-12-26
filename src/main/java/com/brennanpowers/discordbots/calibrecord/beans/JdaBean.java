package com.brennanpowers.discordbots.calibrecord.beans;

import com.brennanpowers.discordbots.calibrecord.config.properties.DiscordProperties;
import com.brennanpowers.discordbots.calibrecord.listener.DefaultMessageListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.security.auth.login.LoginException;

@Configuration
public class JdaBean {
    private final DiscordProperties discordProperties;
    private final DefaultMessageListener defaultMessageListener;

    public JdaBean(DiscordProperties discordProperties, DefaultMessageListener defaultMessageListener) {
        this.discordProperties = discordProperties;
        this.defaultMessageListener = defaultMessageListener;
    }

    @Bean
    public JDA getJdaApi() throws LoginException {
        JDA jda = JDABuilder.createDefault(discordProperties.getApiToken()).build();
        jda.addEventListener(defaultMessageListener);
        return jda;
    }
}
