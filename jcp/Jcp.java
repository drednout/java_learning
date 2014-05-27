import java.io.*;

public class Jcp {
    private static final int BUF_SIZE = 4096;

    public static void copyFileContent(String input_file, String output_file) 
    throws IOException {
        FileInputStream in = new FileInputStream(input_file);
        FileOutputStream out = new FileOutputStream(output_file);

        try {
            byte buf[] = new byte[BUF_SIZE]; 
            int num_bytes = 0;
            while ( (num_bytes = in.read(buf)) != -1 ) {
                out.write(buf, 0, num_bytes);
            }
        } finally {
            in.close();
            out.close();
        }
    }

    public static void main(String[] args) 
    throws IOException {
        if(args.length < 2) {
            System.out.println("Usage:");
            System.out.println("    java Jcp input_file output_file\n");
            System.exit(1);
        }
        copyFileContent(args[0], args[1]);
    }
}
