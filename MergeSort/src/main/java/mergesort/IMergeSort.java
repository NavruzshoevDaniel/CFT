package mergesort;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Path;
import java.util.List;

public interface IMergeSort {

    void sortFiles(@NotNull List<InputStream> sortedStreams,
                   String outputFileName) throws IOException;

    Path mergeKFiles(@NotNull List<BufferedReader> bufferedReaders,
                     String outputName) throws IOException;
}
