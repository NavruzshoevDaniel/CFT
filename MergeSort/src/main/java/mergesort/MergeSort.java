package mergesort;

import mergesort.exception.UnsortedFileException;
import mergesort.parser.StringParser;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class MergeSort<T> extends AbstractMergeSort<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MergeSort.class.getName());
    private boolean resultFile = true;

    public MergeSort(Comparator<T> comparator, StringParser<T> stringParser) {
        super(comparator, stringParser);
    }

    @Override
    public Path mergeKFiles(@NotNull List<BufferedReader> readerList,
                            @NotNull String outputName) throws IOException {
        Objects.requireNonNull(readerList);
        Objects.requireNonNull(outputName);

        Path newPath = createFile(outputName);

        try (BufferedWriter outputWriter = Files.newBufferedWriter(newPath)) {
            int readersSize = readerList.size();
            if (readersSize == 1) {
                BufferedReader bufferedReader = readerList.get(0);
                String line = bufferedReader.readLine();
                writeRemainElements(line, bufferedReader, outputWriter);
                return newPath;
            }

            if (readersSize == 2) {
                mergeFiles(readerList.get(0), readerList.get(1), newPath);
                return newPath;
            }

            List<BufferedReader> leftBufferedReaderList = readerList.stream().limit(readersSize / 2)
                    .collect(Collectors.toList());

            List<BufferedReader> rightBufferedReaderList = readerList.stream().skip(readersSize / 2)
                    .collect(Collectors.toList());

            Path mergedLeftPath = mergeKFiles(leftBufferedReaderList, outputName);
            Path mergedRightPath = mergeKFiles(rightBufferedReaderList, outputName);

            mergeFiles(Files.newBufferedReader(mergedLeftPath),
                    Files.newBufferedReader(mergedRightPath), newPath);
        }

        return newPath;
    }


    private void mergeFiles(BufferedReader leftReader,
                            BufferedReader rightReader,
                            final Path outputFilePath) throws IOException {
        Objects.requireNonNull(leftReader);
        Objects.requireNonNull(rightReader);
        Objects.requireNonNull(outputFilePath);

        try (BufferedWriter outputWriter = Files.newBufferedWriter(outputFilePath)) {
            String leftLine = leftReader.readLine();
            String rightLine = rightReader.readLine();

            var leftSortedChecker = new SortedChecker<>(comparator);
            var rightSortedChecker = new SortedChecker<>(comparator);

            while (!isEOF(leftLine) && !isEOF(rightLine)) {
                T leftInteger = stringParser.parse(leftLine);
                T rightInteger = stringParser.parse(rightLine);
                if (!leftSortedChecker.isSorted(leftInteger)) {
                    leftLine = null;
                    continue;
                }
                if (!rightSortedChecker.isSorted(rightInteger)) {
                    rightLine = null;
                    continue;
                }
                if (comparator.compare(leftInteger, rightInteger) <= 0) {
                    outputWriter.write(leftLine);
                    leftLine = leftReader.readLine();
                } else {
                    outputWriter.write(rightLine);
                    rightLine = rightReader.readLine();
                }
                outputWriter.newLine();
            }
            writeRemainElements(leftLine, leftReader, outputWriter);
            writeRemainElements(rightLine, rightReader, outputWriter);
        }
    }

    private void writeRemainElements(String nextLine, BufferedReader reader, BufferedWriter writer)
            throws IOException {
        var sortedChecker = new SortedChecker<>(comparator);
        while (!isEOF(nextLine)) {
            if (!sortedChecker.isSorted(stringParser.parse(nextLine))) {
                break;
            }
            writer.write(nextLine);
            writer.newLine();
            nextLine = reader.readLine();
        }
    }

    private Path createFile(String outputName) throws IOException {
        Path newPath;
        if (resultFile) {
            newPath = Files.createFile(Path.of(OUTPUT_DIR, outputName));
            resultFile = false;
        } else {
            newPath = Files.createTempFile(Path.of(TMP_DIR), outputName + "TMP", ".txt");
        }
        return newPath;
    }

    private boolean isEOF(String line) {
        return line == null;
    }
}
