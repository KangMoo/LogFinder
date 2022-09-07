package com.github.kangmoo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author kangmoo Heo
 */
public class LogParser {
    public static void main(String[] args) {
        if(args.length < 2){
            System.out.println("Need at least 2 args. \n1:Target Directory \n2:Log prefix, \n3 or more: string to be included. (And) (optional)");
            return;
        }
        String logPath = args[0];
        String logPrefixRegex = args[1];
        List<String> filters = new ArrayList<>(Arrays.asList(args).subList(2, args.length));

        try (Stream<Path> files = Files.walk(Path.of(logPath))) {
            files.filter(Files::isRegularFile)
                    .map(o -> {
                        try {
                            return new String(Files.readAllBytes(o));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .map(o -> o.split(logPrefixRegex))
                    .flatMap(Arrays::stream)
                    .filter(o -> filters.isEmpty() || filters.stream().anyMatch(o::contains))
                    .forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
