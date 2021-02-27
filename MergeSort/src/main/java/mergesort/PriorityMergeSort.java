package mergesort;

import mergesort.exception.UnsortedFileException;
import mergesort.parser.StringParser;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import queue.PriorityHeap;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class PriorityMergeSort<T> extends AbstractMergeSort<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PriorityMergeSort.class);
    private final PriorityHeap<SortedContainer<T>> priorityHeap;

    public PriorityMergeSort(@NotNull Comparator<T> comparator,
                             @NotNull StringParser<T> stringParser) {
        super(comparator, stringParser);
        this.priorityHeap = new PriorityHeap<>((o1, o2) -> comparator.compare(o1.element, o2.element));
    }

    @Override
    protected Path mergeKFiles(List<BufferedReader> bufferedReaders, Path outputFilePath) throws IOException {
        Path newPath = createFile(outputFilePath);

        try (BufferedWriter writer = Files.newBufferedWriter(outputFilePath)) {
            fillPriorityQueue(bufferedReaders);
            merge(bufferedReaders, writer);
        } catch (IOException e) {
            LOGGER.warn("Output file({}) problems", e);
            throw e;
        }
        return newPath;
    }

    private Path createFile(Path outputFilePath) throws IOException {
        if (Files.deleteIfExists(outputFilePath)) {
            LOGGER.warn("File({}) was deleted because it had already existed", outputFilePath);
        }
        return Files.createFile(outputFilePath);
    }

    private void fillPriorityQueue(List<BufferedReader> bufferedReaders) {
        Iterator<BufferedReader> readerIterator = bufferedReaders.iterator();
        while (readerIterator.hasNext()) {
            BufferedReader reader = readerIterator.next();
            try {
                if (reader.ready()) {
                    T firstElement = stringParser.parse(reader.readLine());
                    priorityHeap.insert(new SortedContainer<>(firstElement,
                            reader, stringParser, comparator));
                }
            } catch (IOException exception) {
                LOGGER.warn("File({}) was skipped", inputFilesMap.get(reader), exception);
                readerIterator.remove();
            }
        }
    }

    private void merge(List<BufferedReader> bufferedReaders, BufferedWriter writer) throws IOException {
        while (!bufferedReaders.isEmpty()) {
            SortedContainer<T> sortedContainer = priorityHeap.poll();

            writer.write(sortedContainer.element.toString());
            writer.newLine();
            try {
                if (!sortedContainer.ready()) {
                    sortedContainer.reader.close();
                    bufferedReaders.remove(sortedContainer.reader);
                    continue;
                }
                sortedContainer.readLine();
            } catch (IOException | UnsortedFileException e) {
                LOGGER.warn("File({}) was skipped", inputFilesMap.get(sortedContainer.reader), e);
                sortedContainer.reader.close();
                bufferedReaders.remove(sortedContainer.reader);
                continue;
            }

            priorityHeap.insert(sortedContainer);
        }
    }


    private class SortedContainer<T> {
        private final BufferedReader reader;
        private final StringParser<T> parser;
        private final SortedChecker<T> sortedChecker;
        private int indexElement = 1;
        private T element;

        private SortedContainer(T element,
                                BufferedReader reader,
                                StringParser<T> parser,
                                Comparator<T> comparator) {
            this.element = element;
            this.reader = reader;
            this.parser = parser;
            this.sortedChecker = new SortedChecker<>(comparator, element);
        }

        public void readLine() throws IOException {
            element = parser.parse(reader.readLine());
            indexElement++;
            if (!sortedChecker.isSorted(element)) {
                throw new UnsortedFileException("Unsorted file from the " + indexElement
                        + " element");
            }
        }

        public boolean ready() throws IOException {
            return reader.ready();
        }
    }
}
