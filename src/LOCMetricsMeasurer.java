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
            System.out.println(content);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public LOCMetrics measureClassLOCMetrics(String classFileName){
        //TODO: change so this throws an exception and caller has to handle file error
        int loc = 0;
        int cloc = 0;
        float wmc = 1;



        try{
            File classFile = new File(classFileName);
            Scanner scanner = new Scanner(classFile);
            Boolean insideBlockComment = false;
            //Boolean insideMethod = false;
            int nb_methods=0;
            int nb_predicates = 0;
            //regex for matching function definition adapted from:
            // https://stackoverflow.com/questions/47387307/regular-expression-that-matches-java-method-definition
            //Basically we want a word that's not a keyword followed by some parentheses,
            //possibly preceded by public/private/protected,
            //followed by opening brackets.
            Pattern method_def = Pattern.compile(
                "(?!if|while|for|catch|do|new|return)^(public\\s+|private\\s+|protected\\s+).+(.)\\s?\\{$", 
                Pattern.CASE_INSENSITIVE);
            Matcher matcher;

            while (scanner.hasNextLine()){ 
                String line = scanner.nextLine().trim(); // please remember TODO: remember what?
                matcher = method_def.matcher(line);
                //if (line.isEmpty() || line == null){
                if (line.isEmpty()){
                    continue;
                } if (matcher.find()){
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

                if (line.contains("if") ||
                        line.contains("else") ||
                        line.contains("while") ||
                        line.contains("for")){
                    nb_predicates++;
                }
            }
            wmc = (nb_methods + nb_predicates)/ (float)nb_methods;
            scanner.close();
        } catch(FileNotFoundException e){ //TODO: should have other file IO errors in there too
            System.out.println("Error reading class file.");
            e.printStackTrace();
        }




        return new LOCMetrics(classFileName, false, loc, cloc, wmc);
    }

    public static LOCMetrics computePackageLOCMetrics(String dirName, ArrayList<LOCMetrics> childrenMetrics){
        int tot_loc = 0;
        int tot_cloc = 0;
        float tot_wmc = 0;

        for (LOCMetrics childMetric : childrenMetrics){
            if (!childMetric.getIsPackage()) //we don't sum package metrics together
                tot_loc += childMetric.getLoc();
                tot_cloc += childMetric.getCloc();
                tot_wmc += childMetric.getWmc();
        }


        return new LOCMetrics(dirName, true, tot_loc, tot_cloc, tot_wmc);
    }
}