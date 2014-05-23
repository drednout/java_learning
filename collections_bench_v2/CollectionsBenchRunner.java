import java.util.LinkedHashMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.LinkedHashSet;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collection;


public class CollectionsBenchRunner {
    private static final int ITERATION_COUNT = 5000000;
    private static final int REPEAT_COUNT = 5;

    public static void doSingleBench(
            final Map.Entry<String, Collection<Integer>> benchInfo,
            final int benchNumber) {
        String benchName = benchInfo.getKey().toString();
        Collection<Integer> collection = benchInfo.getValue();

        BenchmarktTimer timer = new BenchmarktTimer();

        timer.start();
        for (int i = 0; i < ITERATION_COUNT; ++i) {
            collection.add(i);
        }
        long timeEstimate = timer.stop();
        System.out.println(BenchmarkHelper.formatBenchResult(String.format("%s:add", benchName),
                           ITERATION_COUNT, benchNumber, timer.toSeconds(timeEstimate)));

        timer.start();
        for (int i = 0; i < ITERATION_COUNT; ++i) {
            collection.remove(i);
        }
        timeEstimate = timer.stop();
        System.out.println(BenchmarkHelper.formatBenchResult(String.format("%s:remove", benchName),
                           ITERATION_COUNT, benchNumber, timer.toSeconds(timeEstimate)));

    }

    public static void main(final String [] args) {
        System.out.println("Running Java collections benchmarks...");

        LinkedHashMap<String, Collection<Integer>> benchMap;
        benchMap = new LinkedHashMap<String, Collection<Integer>>();

        benchMap.put("LinkedList", new LinkedList<Integer>());

        /*ArrayList.remove(Object o) has O(N) complexity and can not
        be directly compared using Collection interface with
        other collections*/
        //benchMap.put("ArrayList", new ArrayList<Integer>());

        benchMap.put("LinkedHashSet", new LinkedHashSet<Integer>());
        benchMap.put("TreeSet", new TreeSet<Integer>());
        benchMap.put("HashSet", new HashSet<Integer>());

        for (Map.Entry<String, Collection<Integer>> entry : benchMap.entrySet()) {
            String benchName = entry.getKey();
            System.out.println(String.format("Running benchmark %s ...", benchName));
            for (int i = 1; i <= REPEAT_COUNT; ++i) {
                doSingleBench(entry, i);
            }
        }
        System.out.println("Done.");
    }
}
