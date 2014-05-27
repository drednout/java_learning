import java.io.*;

public class Jcp {
    private static final int BUF_SIZE = 4096;

    public static void copyFileContent(String input_file, String output_file) 
    throws IOException {
        FileInputStream in = null;
        FileOutputStream out = null;

        try {
            in = new FileInputStream(input_file);
            out = new FileOutputStream(output_file);

            byte buf[] = new byte[BUF_SIZE]; 
            int num_bytes = 0;
            while ( (num_bytes = in.read(buf)) != -1 ) {
                out.write(buf, 0, num_bytes);
            }
        } finally {
            try {
                if (in != null)
                    in.close();
            }
            finally {
                if (out != null)
                    out.close();
            }
        }
    }

    public static void main(String[] args) {
        if(args.length < 2) {
            System.out.println("Usage:");
            System.out.println("    java Jcp input_file output_file\n");
            System.exit(1);
        }
        try {
            copyFileContent(args[0], args[1]);
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }
        System.exit(0);
    }
}
