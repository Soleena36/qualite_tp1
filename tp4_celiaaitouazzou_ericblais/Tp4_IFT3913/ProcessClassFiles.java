import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class ProcessClassFiles {

    public String parseCommit(String path)
    {
        String content = "";
        float mBC = 0;
        float  mWMC = 0;
        int nc = 0;
        int i=0;

        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (i > 0) {
                    String[] dataRow = data.split(",");
                    mBC += Float.parseFloat(dataRow[6]);
                    mWMC += Float.parseFloat(dataRow[5]);
                    nc++;
                }
                i++;
            }

            myReader.close();
            return (mBC/nc) + "," + (mWMC/nc) + "," + nc;

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return content;
    }

    public ArrayList<String> commitIDListGen(String commitPath)
    {
        ArrayList<String> commitIDList = new ArrayList<>();

        try {
            File myObj = new File(commitPath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] fields = data.split(" ");
                commitIDList.add(fields[0]);
            }

            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return commitIDList;
    }

    public static void main(String[] args){
        File path1 = new File("commits_measures");
        ProcessClassFiles processClassFiles = new ProcessClassFiles();

        ArrayList<String> commitIds = processClassFiles.commitIDListGen(Paths.get("").toAbsolutePath().toString() +"/logs.txt");
        int i=0;



        try {
            FileWriter myWriter = new FileWriter("jfreecharthistory.csv");


                for (String pathname : path1.list()) {
                    // Print the names of files and directories

                    String oneFile =processClassFiles.parseCommit(path1.getAbsolutePath() + "/" + pathname);
                    if(i < commitIds.size()){
                        myWriter.write(commitIds.get(i) + "," +oneFile);;
                    }

                    //System.out.println(oneFile);
                    i++;

                }
                myWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }



    }
}
