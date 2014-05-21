import java.util.TreeSet;

public class TreeSetBenchmark implements Benchmark 
{
    private void do_single_bench(int iteration_count, int n)
    {
        TreeSet<Integer> sorted_set = new TreeSet<Integer>();
        BenchmarktTimer timer = new BenchmarktTimer();

        timer.start();
        for( int i = 0; i < iteration_count; ++i ) 
        {
            sorted_set.add(i);
        }
        System.out.println(BenchmarkHelper.FormatBenchResult("TreeSetBenchmark:add", 
                           iteration_count, n, timer.stop()));
                                            
        timer.start();
        for( int i = 0; i < iteration_count; ++i ) 
        {
            sorted_set.remove(i);
        }
        System.out.println(BenchmarkHelper.FormatBenchResult("TreeSetBenchmark:remove", 
                           iteration_count, n, timer.stop()));
    }
    public void run(int iteration_count, int repeat_count) 
    {
        for( int i = 0; i < repeat_count; ++i) 
        {
            do_single_bench(iteration_count, i + 1);
        }
    }
}

