package com.github.kangmoo;

import org.kohsuke.args4j.CmdLineParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws Exception {
        Config config = new Config();
        CmdLineParser parser = new CmdLineParser(config);
        parser.parseArgument(args);

        List<String> results;

        Pattern findPattern = Pattern.compile(config.getFindPattern());

        if (Files.isDirectory(Path.of(config.getFilePath()))) {
            results = Files.walk(Path.of(config.getFilePath()))
                    .filter(Files::isRegularFile)
                    .sorted()
                    .parallel()
                    .map(o -> findLogs(o, config.getSplitPattern(), findPattern))
                    .flatMap(List::stream)
                    .toList();
        } else {

            results = findLogs(Path.of(config.getFilePath()), config.getSplitPattern(), findPattern);
        }

        for (String result : results) {
            System.out.println(result);
        }
    }

    public static List<String> findLogs(Path filePath, String splitPattern, Pattern pattern) {
        try {
            return Arrays.stream(new String(Files.readAllBytes(filePath)).split(splitPattern))
                    .parallel()
                    .filter(o -> pattern.matcher(o).find())
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
