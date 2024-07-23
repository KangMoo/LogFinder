package com.github.kangmoo;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.kohsuke.args4j.OptionHandlerFilter.ALL;

public class Main {
    public static void main(String[] args) throws Exception {
        Config config = new Config();
        CmdLineParser parser = new CmdLineParser(config);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("LogFinder [options...] arguments...");
            parser.printUsage(System.err);
            System.err.println();
            System.err.println("  Example: LogFinder" + parser.printExample(ALL));
            return;
        }
        List<String> results;

        Pattern findPattern = Pattern.compile(config.getFindPattern());

        if (Files.isDirectory(Path.of(config.getFilePath()))) {
            try (Stream<Path> walk = Files.walk(Path.of(config.getFilePath()))) {
                results = walk.filter(Files::isRegularFile)
                        .sorted()
                        .parallel()
                        .map(o -> findLogs(o, config.getSplitPattern(), findPattern))
                        .flatMap(List::stream)
                        .toList();
            }
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
