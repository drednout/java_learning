public final class BenchmarkHelper {
    private BenchmarkHelper() {
    }

    public static String formatBenchResult(
            final String benchName,
            final int iterationCount,
            final int benchNumber,
            final double benchTime) {
        return benchName + " | repeat: " + benchNumber + " | iterationCount: "
                         + iterationCount + " | time: " + benchTime;
    }
}
