import com.gmail.dennavruzshoev.Parser;
import com.gmail.dennavruzshoev.SortingMode;
import com.gmail.dennavruzshoev.mergesort.IMergeSort;
import com.gmail.dennavruzshoev.mergesort.PriorityMergeSort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            var parser = new Parser(args);
            startSort(parser);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Input parameters are invalid", e);
        } catch (FileNotFoundException e) {
            LOGGER.error("Real files doesn't exist", e);
        } catch (IOException e) {
            LOGGER.error("Error:", e);
        }
    }

    private static void startSort(Parser parser) throws IOException {
        String typeElements = parser.getTypeElements();
        SortingMode mode = parser.getSortingMode();
        IMergeSort mergeSort = null;
        if (typeElements.equals("-s") && mode == SortingMode.NATURAL_ORDER) {
            mergeSort = new PriorityMergeSort<String>(Comparator.naturalOrder(),
                    line -> line);
        } else if (typeElements.equals("-s") && mode == SortingMode.REVERSE_ORDER) {
            mergeSort = new PriorityMergeSort<String>(Comparator.reverseOrder(),
                    line -> line);
        } else if (typeElements.equals("-i") && mode == SortingMode.NATURAL_ORDER) {
            mergeSort = new PriorityMergeSort<Integer>(Comparator.naturalOrder(),
                    Integer::parseInt);
        } else if (typeElements.equals("-i") && mode == SortingMode.REVERSE_ORDER) {
            mergeSort = new PriorityMergeSort<Integer>(Comparator.reverseOrder(),
                    Integer::parseInt);
        }
        assert mergeSort != null;
        mergeSort.sortFiles(parser.getInputFilePaths(), parser.getOutputFile());
    }
}
