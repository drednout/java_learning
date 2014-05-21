public class BenchmarkHelper
{
    public static String FormatBenchResult(
        String bench_name, 
        int iteration_count, 
        int n,
        double bench_time) 
    {
        return bench_name + " | repeat: " + n + " | iteration_count: " +
                            iteration_count + " | time: " + bench_time;

    }
}
