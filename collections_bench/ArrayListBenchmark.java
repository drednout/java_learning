import java.util.ArrayList;
import java.util.List;

public class ArrayListBenchmark implements Benchmark 
{
    private void do_single_bench(int iteration_count, int n)
    {
        ArrayList<Integer> array_list = new ArrayList<Integer>();
        BenchmarktTimer timer = new BenchmarktTimer();

        timer.start();
        for( int i = 0; i < iteration_count; ++i ) 
        {
            array_list.add(i);
        }
        System.out.println(BenchmarkHelper.FormatBenchResult("ArrayListBenchmark:add", 
                           iteration_count, n, timer.stop()));
                                            
        timer.start();
        for( int i = 0; i < iteration_count; ++i ) 
        {
            array_list.remove(array_list.size() - 1);
        }
        System.out.println(BenchmarkHelper.FormatBenchResult("ArrayListBenchmark:remove", 
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

