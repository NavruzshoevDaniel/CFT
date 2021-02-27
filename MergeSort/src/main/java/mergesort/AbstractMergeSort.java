package mergesort;

import mergesort.parser.StringParser;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractMergeSort<T> implements IMergeSort {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMergeSort.class);
    protected final Comparator<T> comparator;
    protected final StringParser<T> stringParser;
    protected Map<BufferedReader, Path> inputFilesMap;

    protected AbstractMergeSort(@NotNull Comparator<T> comparator,
                                @NotNull StringParser<T> stringParser) {
        Objects.requireNonNull(comparator);
        Objects.requireNonNull(stringParser);
        this.comparator = comparator;
        this.stringParser = stringParser;
    }

    public void sortFiles(@NotNull List<Path> sortedFilePaths,
                          @NotNull Path outputFilePath) throws IOException {
        prepareSorting(sortedFilePaths);
        List<BufferedReader> bufferedReaders = new ArrayList<>(inputFilesMap.keySet());
        mergeKFiles(bufferedReaders, outputFilePath);
    }

    protected abstract Path mergeKFiles(List<BufferedReader> bufferedReaders, Path outputFilePath) throws IOException;

    protected void prepareSorting(List<Path> sortedFilePaths) {
        inputFilesMap = sortedFilePaths.stream()
                .collect(Collectors.toMap(this::newBufferedReaderWrapper, path -> path));
        inputFilesMap.remove(null);
    }

    private BufferedReader newBufferedReaderWrapper(Path path) {
        BufferedReader newReader = null;
        try {
            newReader = Files.newBufferedReader(path);
        } catch (IOException ignoredException) {
            LOGGER.warn("File({}) was skipped", path, ignoredException);
        }
        return newReader;
    }
}
