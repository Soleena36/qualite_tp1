import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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

    public String readFile(String path){
        String content = "";
        try {
            BufferedReader filein = new BufferedReader(new FileReader(new File(path)));
            FileReader fileIn = new FileReader(path);

            while ((content = filein.readLine()) != null){
                content += filein.readLine();
            }

            /*
            int i;
            while((i = fileIn.read())!=-1){
                System.out.println((char)i);
            } */
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public LOCMetrics measureClassLOCMetrics(String classFileName){
        //TODO: change so this throws an exception and caller has to handle file error
        String line = readFile(classFileName);
        int loc = 0;
        int cloc = 0;
        Boolean insideBlockComment = false;
        try{


            //Boolean insideMethod = false;
            int nb_methods=0;
            //(?!if|while|for|catch|do|new|return)^(public\s+|private\s+|protected\s+).+(.)\s{$
            //above is regex for matching function definition from:
            // https://stackoverflow.com/questions/47387307/regular-expression-that-matches-java-method-definition
            Pattern method_def = Pattern.compile("(?!if|while|for|catch|do|new|return)^(public\\s+|private\\s+|protected\\s+).+(.)\\s?\\{$", Pattern.CASE_INSENSITIVE);
            Matcher matcher;

            
                matcher = method_def.matcher(line);
                //System.out.println(line);
                if (line.isEmpty()){
                    continue;
                // } else if ((line.startsWith("public") ||
                //             line.startsWith("private") ||
                //             line.startsWith("protected") ) &&
                //             !line.contains("class")){
                //                 insideMethod = true;
                } if (matcher.find()){
                    //insideMethod = true;
                    nb_methods++;
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

            System.out.println(nb_methods);

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