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
            final int benchNumber,
            final ArrayList<BenchmarkResult> benchResults) {
        String benchName = benchInfo.getKey().toString();
        Collection<Integer> collection = benchInfo.getValue();

        BenchmarkTimer timer = new BenchmarkTimer();

        timer.start();
        for (int i = 0; i < ITERATION_COUNT; ++i) {
            collection.add(i);
        }
        long timeEstimate = timer.stop();

        BenchmarkResult benchResult;
        String subBenchName = String.format("%s:add", benchName);
        benchResult = new BenchmarkResult(
                          subBenchName,
                          ITERATION_COUNT,
                          benchNumber,
                          timeEstimate
                      );
        benchResults.add(benchResult);

        timer.start();
        for (int i = 0; i < ITERATION_COUNT; ++i) {
            collection.remove(i);
        }
        timeEstimate = timer.stop();

        subBenchName = String.format("%s:remove", benchName);
        benchResult = new BenchmarkResult(
                          subBenchName,
                          ITERATION_COUNT,
                          benchNumber,
                          timeEstimate
                      );
        benchResults.add(benchResult);
    }

    public static void main(final String [] args) {
        System.out.println("Running Java collections benchmarks...");

        LinkedHashMap<String, Collection<Integer>> benchMap;
        benchMap = new LinkedHashMap<String, Collection<Integer>>();

        ArrayList<BenchmarkResult> benchResults;
        benchResults = new ArrayList<BenchmarkResult>();

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
                doSingleBench(entry, i, benchResults);
            }
        }
        for (BenchmarkResult benchResult : benchResults) {
            System.out.println(benchResult.toString());
        }
        System.out.println("Done.");
    }
}
