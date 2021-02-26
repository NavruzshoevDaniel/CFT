package mergesort;

import mergesort.parser.StringParser;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public abstract class AbstractMergeSort<T> implements IMergeSort {

    protected final Comparator<T> comparator;
    protected final StringParser<T> stringParser;
    protected final String OUTPUT_DIR = "result";
    protected final String TMP_DIR = "tmp";

    protected AbstractMergeSort(@NotNull Comparator<T> comparator,
                                @NotNull StringParser<T> stringParser) {
        Objects.requireNonNull(comparator);
        Objects.requireNonNull(stringParser);
        this.stringParser = stringParser;
        this.comparator = comparator;
    }

    public void sortFiles(@NotNull List<InputStream> sortedStreams, String outputFileName)
            throws IOException {
        List<BufferedReader> bufferedReaders = sortedStreams.stream()
                .map(InputStreamReader::new)
                .map(BufferedReader::new)
                .collect(Collectors.toList());
        mergeKFiles(bufferedReaders, outputFileName);
    }
}
