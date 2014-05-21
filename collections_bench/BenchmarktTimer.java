public class BenchmarktTimer 
{
    private long start_time;

    public void start() 
    {
        start_time = System.nanoTime();
    }
    public double stop() 
    {
        double estimated_time = (System.nanoTime() - start_time) / 1000000000.0;
        return estimated_time;
    }
}
