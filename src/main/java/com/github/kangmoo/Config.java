package com.github.kangmoo;

import lombok.Data;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kangmoo Heo
 */
@Data
public class Config {
    @Option(name = "-f", usage = "Log file path", required = true)
    private String filePath;

    @Option(name = "-sp", usage = "Pattern to Split")
    private String splitPattern = "(?=\\[\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3}\\])";

    @Option(name = "-p", usage = "Pattern to find")
    private String findPattern = ".*";
}
