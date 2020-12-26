package com.brennanpowerse.discordbots.calibrecord.handler;

import com.brennanpowerse.discordbots.calibrecord.config.properties.CalibreProperties;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class BookMessageHandler implements Handler {
    private static final Logger log = LoggerFactory.getLogger(BookMessageHandler.class);

    private final CalibreProperties calibreProperties;

    public BookMessageHandler(CalibreProperties calibreProperties) {
        this.calibreProperties = calibreProperties;
    }

    @Override
    public void handle(MessageReceivedEvent event) {
        log.debug("Handling book message");
        if (!shouldHandle(event)) {
            throw new IllegalArgumentException("BookMessageHandler is attempting to handle an event that it should not.");
        }

        MessageChannel channel = event.getChannel();

        List<Message.Attachment> validAttachments = determineValidAttachments(event);
        log.debug("This message has {} valid attachments", validAttachments.size());
        List<CompletableFuture<File>> filesToDownload = new ArrayList<>();
        for (Message.Attachment attachment : validAttachments) {
            String absoluteDownloadPath = String.format("%s%s%s", calibreProperties.getImportPathAbsolute(), File.separator, attachment.getFileName());
            log.debug("Downloading file to {}", absoluteDownloadPath);
            CompletableFuture<File> fileToDownload = attachment.downloadToFile(absoluteDownloadPath);
            filesToDownload.add(fileToDownload);
        }

        CompletableFuture.allOf(filesToDownload.toArray(new CompletableFuture[0])).thenAccept(nothing -> {
            final List<File> downloadedFiles = filesToDownload.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());
            downloadedFiles.forEach(file -> log.debug("Downloaded file {}", file.getAbsolutePath()));
            channel.sendMessage(String.format("Moved %s file(s) to Calibre's import directory", validAttachments.size())).queue();
        });
    }

    private List<Message.Attachment> determineValidAttachments(MessageReceivedEvent event) {
        Message message = event.getMessage();
        for (String extension : calibreProperties.getValidExtensionsAsList()) {
            if (extension.startsWith(".")) {
                log.warn("Extension {} starts with a period.  Please remove the period.", extension);
            }
        }
        List<Message.Attachment> validAttachments = message.getAttachments()
                .stream()
                .filter(attachment -> calibreProperties.getValidExtensionsAsList().contains(attachment.getFileExtension()))
                .collect(Collectors.toList());
        return validAttachments;
    }

    public boolean shouldHandle(MessageReceivedEvent event) {
        return determineValidAttachments(event).size() > 0;
    }
}
