public class BenchmarkTimer {
    private long startTime;
    private static final double TIMER_PRECISION = 1000000000.0;

    public final void start() {
        startTime = System.nanoTime();
    }
    public final long stop() {
        return System.nanoTime() - startTime;
    }
    public static double toSeconds(final long timeInNanoseconds) {
        return timeInNanoseconds / TIMER_PRECISION;
    }
}
