package com.gmail.dennavruzshoev.parametrparser;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Parser {
    private final List<Path> inputFiles = new ArrayList<>();
    private String typeElements;
    private SortingMode sortingMode = SortingMode.NATURAL_ORDER;
    private Path outputFile;

    public Parser(String... args) {
        parse(args);
    }

    private void parse(String... args) {
        for (String arg : args) {
            if (arg.equals("-s") || arg.equals("-i")) {
                typeElements = arg;
            } else if (arg.equals("-a")) {
                sortingMode = SortingMode.NATURAL_ORDER;
            } else if (arg.equals("-d")) {
                sortingMode = SortingMode.REVERSE_ORDER;
            } else if (getOutputPath().isEmpty()) {
                outputFile = Paths.get(arg);
            } else {
                inputFiles.add(Paths.get(arg));
            }
        }
        if (safetyGetTypeElements().isEmpty()) {
            throw new IllegalArgumentException("You must write type of element: -s or -i");
        }
        if (getOutputPath().isEmpty()) {
            throw new IllegalArgumentException("You must write output file");
        }
        if (inputFiles.isEmpty()) {
            throw new IllegalArgumentException("You must write at least one input file");
        }
    }

    private Optional<Path> getOutputPath() {
        return Optional.ofNullable(outputFile);
    }

    private Optional<String> safetyGetTypeElements() {
        return Optional.ofNullable(typeElements);
    }

    public String getTypeElements() {
        return typeElements;
    }

    public SortingMode getSortingMode() {
        return sortingMode;
    }

    public List<Path> getInputFilePaths() {
        return inputFiles;
    }

    public Path getOutputFile() {
        return outputFile;
    }
}
