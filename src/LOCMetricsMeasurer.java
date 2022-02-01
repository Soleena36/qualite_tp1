import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class LOCMetricsMeasurer{
    
    private String blockCommentStart;
    private String blockCommentEnd;;
    private String inlineCommentStart;
    //TODO: should we handle documentation comment blocks seperately?
    
    public LOCMetricsMeasurer(){
        //TODO: write constructor if necessary
    }

    public LOCMetricsMeasurer(String blockCommentStart, String blockCommentEnd, String inlineCommentStart){
        this.blockCommentStart = blockCommentStart;
        this.blockCommentEnd = blockCommentEnd;
        this.inlineCommentStart = inlineCommentStart;
    }

    public LOCMetrics measureClassLOCMetrics(String classFileName){
        //TODO: change so this throws an exception and caller has to handle file error
        int loc = 0;
        int cloc = 0;
        try{
            File classFile = new File(classFileName);
            Scanner scanner = new Scanner(classFile);
            Boolean insideBlockComment = false;
            while (scanner.hasNextLine()){
                String line = scanner.nextLine().strip();
                //System.out.println(line);
                if (line.isEmpty()){
                    continue;
                } else if (insideBlockComment){
                    loc++;
                    cloc++;
                    if (line.endsWith(blockCommentEnd)) insideBlockComment = false;
                } else if (line.contains(inlineCommentStart)){
                    loc++;
                    cloc++;
                } else if (line.startsWith(blockCommentStart)){
                    insideBlockComment = true;
                    loc++;
                    cloc++;
                } else {
                    loc++;
                }
            }
            scanner.close();
        } catch(FileNotFoundException e){ //TODO: should have other file IO errors in there too
            System.out.println("Error reading class file.");
            e.printStackTrace();
        }

        return new LOCMetrics(loc, cloc);
    }

    public LOCMetrics computePackageLOCMetrics(ArrayList<LOCMetrics> childrenMetrics){
       //TODO: implement
        return new LOCMetrics();
    }
}