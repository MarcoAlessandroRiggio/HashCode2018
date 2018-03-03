import entity.Configuration;
import entity.TaxiSolver;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.io.File.*;

public class HashCode2018 {


	private final static String[] fileNames = new String[] {
			separator + "a_example",
			separator + "b_should_be_easy",
//			separator + "c_no_hurry",
//			separator + "d_metropolis",
//			separator + "e_high_bonus"
		};

	public static final String Version = "_v6.5";
    public static final String rootPath = System.getProperty("user.dir");

    public static void main(String[] args) {
        System.out.println("Start");
        copySourceDir();
		for(String inputFileName : fileNames){
			String inputFile = rootPath +  separator + "resources" + separator + "input" + inputFileName + ".in";
			String outputFile = rootPath + separator + "output" + inputFileName + Version + ".out";

			System.out.println("Doing file " + inputFile);

			Configuration.readConfiguration(inputFile);
			TaxiSolver solver = new TaxiSolver();
			String result = solver.theOranAlgorithm();
			try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFile))) {
				writer.write(result);
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println("Done with File " + inputFile);
		}
		System.out.println("Finish");
	}

	public static void copySourceDir(){
	    String sourceDir = rootPath+"\\src\\entity";
	    String destDir = rootPath+"\\output\\source"+Version;
        new File(destDir).mkdirs();
	    copyFolder(new File(sourceDir), new File(destDir));
    }

    public static void copyFolder(File source, File destination)
    {
        if (source.isDirectory())
        {
            if (!destination.exists())
            {
                destination.mkdirs();
            }

            String files[] = source.list();

            for (String file : files)
            {
                File srcFile = new File(source, file);
                File destFile = new File(destination, file);

                copyFolder(srcFile, destFile);
            }
        }
        else
        {
            InputStream in = null;
            OutputStream out = null;

            try
            {
                in = new FileInputStream(source);
                out = new FileOutputStream(destination);

                byte[] buffer = new byte[1024];

                int length;
                while ((length = in.read(buffer)) > 0)
                {
                    out.write(buffer, 0, length);
                }
            }
            catch (Exception e)
            {
                try
                {
                    in.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }

                try
                {
                    out.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
    }
}
