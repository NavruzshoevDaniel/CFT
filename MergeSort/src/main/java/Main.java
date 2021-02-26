import mergesort.MergeSort;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Main {


    public static void main(String[] args) throws IOException {
        List<BufferedReader> bufferedReaders = new LinkedList<>();


        bufferedReaders.add(Files.newBufferedReader(
                Paths.get("/Users/daniel/Projects/CFT/MergeSort/src/main/resources/in1.txt")));

        bufferedReaders.add(Files.newBufferedReader(
                Paths.get("/Users/daniel/Projects/CFT/MergeSort/src/main/resources/in2.txt")));

        bufferedReaders.add(Files.newBufferedReader(
                Paths.get("/Users/daniel/Projects/CFT/MergeSort/src/main/resources/in3.txt")));

        var integerMergeSort = new MergeSort<Integer>(Comparator.naturalOrder(), Integer::parseInt);

        try {
            integerMergeSort.mergeKFiles(bufferedReaders, "salam");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
