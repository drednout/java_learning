import java.util.LinkedList;

public class LinkedListBenchmark implements Benchmark 
{
    private void do_single_bench(int iteration_count, int n)
    {
        LinkedList<Integer> linked_list = new LinkedList<Integer>();
        BenchmarktTimer timer = new BenchmarktTimer();

        timer.start();
        for( int i = 0; i < iteration_count; ++i ) 
        {
            linked_list.add(i);
        }
        System.out.println(BenchmarkHelper.FormatBenchResult("LinkedListBenchmark:add", 
                           iteration_count, n, timer.stop()));
                                            
        timer.start();
        for( int i = 0; i < iteration_count; ++i ) 
        {
            linked_list.remove();
        }
        System.out.println(BenchmarkHelper.FormatBenchResult("LinkedListBenchmark:remove", 
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

