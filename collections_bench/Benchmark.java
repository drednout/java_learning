public interface Benchmark
{
    // *iteration_count* - a number of times which some
    // code will be executed
    // *repeat_count* - a number of benchmark runs 
    public void run(int iteration_count, int repeat_count);
}
