
import mergesort.PriorityMergeSort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        List<Path> paths = new LinkedList<>();


        paths.add(Paths.get("/Users/daniel/Projects/CFT/MergeSort/src/main/resources/in1.txt"));

        paths.add(Paths.get("/Users/daniel/Projects/CFT/MergeSort/src/main/resources/in2.txt"));

        paths.add(Paths.get("/Users/daniel/Projects/CFT/MergeSort/src/main/resources/in3.txt"));
        paths.add(Paths.get("/Users/daniel/Projects/CFT/MergeSort/src/main/resources/in4.txt"));

        var integerMergeSort = new PriorityMergeSort<Integer>(Comparator.reverseOrder(), Integer::parseInt);

        try {
            integerMergeSort.sortFiles(paths, Path.of("result", "new"));
        } catch (IOException e) {
            LOGGER.error("Error:", e);
        }
    }
}
