package com.gmail.dennavruzshoev.mergesort;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Path;
import java.util.List;

public interface IMergeSort {

    void sortFiles(@NotNull List<Path> sortedFilePaths,
                   @NotNull Path outputFilePath) throws IOException;
}
