public class BenchmarkResult {
    private String benchmarkName;
    private int iterationCount;
    private int benchmarkNumber;
    private long time;

    public BenchmarkResult(final String benchmarkName, final int iterationCount,
            final int benchmarkNumber, final long time) {
        this.benchmarkName = benchmarkName;
        this.iterationCount = iterationCount;
        this.benchmarkNumber = benchmarkNumber;
        this.time = time;
    }
    public final String toString() {
        String benchAsStr = BenchmarkHelper.formatBenchResult(
                                benchmarkName,
                                iterationCount,
                                benchmarkNumber,
                                BenchmarkTimer.toSeconds(time)
                            );
        return benchAsStr;

    }
}
