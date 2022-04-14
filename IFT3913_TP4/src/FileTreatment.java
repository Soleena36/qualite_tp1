import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileTreatment {
    String pathOut;



    public String readFile(String path2Read){
        String content = "";
        try {
            File myObj = new File(path2Read);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                content += (data + "\n");
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return content;
    }

    public void explore(String pathIn, ArrayList<String> pathList){

        File root = new File( pathIn );
        File[] list = root.listFiles();

        if (list == null) return;

        for ( File f : list )
        {
            if ( f.isDirectory() )
            {
                explore(f.getAbsolutePath(),pathList);
                //System.out.println( "Dir:" + f.getAbsoluteFile() );
            }
            else if(f.isFile() && f.getAbsolutePath().indexOf(".java") != -1){
                pathList.add(f.getAbsolutePath());
            }
        }
    }

    public void writeCSV(String content , String outPath)
    {
        try {
            FileWriter myWriter = new FileWriter(outPath);
            myWriter.write(content);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
