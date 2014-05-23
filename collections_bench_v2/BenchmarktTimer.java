public class BenchmarktTimer {
    private long startTime;
    private final double timerPrecision;

    public final void start() {
        startTime = System.nanoTime();
    }
    public final long stop() {
        return System.nanoTime() - startTime;
    }
    public static double toSeconds(final long timeInNanoseconds) {
        return timeInNanoseconds / timerPrecision;
    }
}
