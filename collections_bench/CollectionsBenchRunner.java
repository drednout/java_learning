public class CollectionsBenchRunner
{
    private static int iteration_count = 5000000;
    private static int repeat_count = 5;

    public static void main(String [] args)
    {
        System.out.println("Running Java collections benchmarks...");
        LinkedListBenchmark bench1 = new LinkedListBenchmark();
        bench1.run(iteration_count, repeat_count);

        ArrayListBenchmark bench2 = new ArrayListBenchmark();
        bench2.run(iteration_count, repeat_count);

        HashSetBenchmark bench3 = new HashSetBenchmark();
        bench3.run(iteration_count, repeat_count);

        TreeSetBenchmark bench4 = new TreeSetBenchmark();
        bench4.run(iteration_count, repeat_count);

        LinkedHashSetBenchmark bench5 = new LinkedHashSetBenchmark();
        bench5.run(iteration_count, repeat_count);
        System.out.println("Done.");
    }
}
