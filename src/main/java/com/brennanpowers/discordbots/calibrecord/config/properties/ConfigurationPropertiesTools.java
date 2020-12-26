package com.brennanpowers.discordbots.calibrecord.config.properties;

import java.util.ArrayList;
import java.util.List;

class ConfigurationPropertiesTools {
    protected static List<String> getListFromCommaSeparatedString(String commaSeparatedPropertyString) {
        List<String> extensionList = new ArrayList<>();
        if (commaSeparatedPropertyString != null) {
            String[] extensionArray = commaSeparatedPropertyString.split(",");
            for (String extension : extensionArray) {
                extension = extension.trim();
                extensionList.add(extension);
            }
        }
        return extensionList;
    }

    public static String formatStringAsLink(String link) {
        if (link == null) {
            return null;
        }
        return String.format("<%s>", link);
    }
}
