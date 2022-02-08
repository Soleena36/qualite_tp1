import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class MeasureDoc{

<<<<<<< HEAD
    //test test test
=======
    //Adapted from https://stackoverflow.com/questions/5694385/getting-the-filenames-of-all-files-in-a-folder
    public static Map<String, Boolean> listFiles(String root){
        File folder = new File(root);
        File[] listOfFiles = folder.listFiles();
        HashMap<String,Boolean> output = new HashMap<>(); //Boolean will be true if file is directory

        for (int i = 0; i < listOfFiles.length; i++) {
            String filename = listOfFiles[i].getName();
            if (listOfFiles[i].isFile() && filename.contains(".java")) {
                //System.out.println("File " + filename);
                output.put(filename, false); 
            } else if (listOfFiles[i].isDirectory()) {
                //System.out.println("Directory " + filename);
                output.put(filename, true); 
            }
        }

        return output;
    }

    public static void recursivePrintFiles(String root, String margin){
        System.out.println(margin + "Directory: " + root);
        if (root.endsWith("/")){ //making sure we don't have extra slashes
            root = root.substring(0, root.length()-1);
        }
        margin += "\t";

        HashSet<String> ignore_list = new HashSet<>();
        ignore_list.add(".git");
        ignore_list.add(".idea");
        ignore_list.add(".vscode");
        //ignore_list.add("tests"); //that one is questionable

        Map<String, Boolean> filesMap = listFiles(root);

        for (String key : filesMap.keySet()) {
            if (ignore_list.contains(key)){
                continue;
            } else if (filesMap.get(key)){
                //in a directory
                recursivePrintFiles(root+"/"+key, margin);
            } else{
                System.out.println(margin + "Source file: " + key); 
            }

        }

    }

    public static LOCMetrics recursiveListFiles(String root, LOCMetricsMeasurer measurer){
        if (root.endsWith("/")){ //making sure we don't have extra slashes
            root = root.substring(0, root.length()-1);
        }

        HashSet<String> ignore_list = new HashSet<>();
        ignore_list.add(".git");
        ignore_list.add(".idea");
        ignore_list.add(".vscode");
        //ignore_list.add("tests"); //that one is questionable

        Map<String, Boolean> filesMap = listFiles(root);
        
        //first pass through the file map to see if this contains source files
        // boolean is_package = false;
        // for (String key : filesMap.keySet()) {
        // }

        ArrayList<LOCMetrics> childrenMetrics = new ArrayList<>();

        for (String key : filesMap.keySet()) {
            if (ignore_list.contains(key)){
                continue;
            } else if (filesMap.get(key)){
                //in a directory
                childrenMetrics.add(recursiveListFiles(root+"/"+key, measurer));
            } else{
                //ideally this should be in a try catch and measureClassLOCMetrics should just throw
                LOCMetrics childMetrics = measurer.measureClassLOCMetrics(root+"/"+key);
               //childrenMetrics.add(measurer.measureClassLOCMetrics(root+"/"+key)); 
               childrenMetrics.add(childMetrics); 
               System.out.println(childMetrics+"\n");
            }
        }

        LOCMetrics self_metrics =  LOCMetricsMeasurer.computePackageLOCMetrics(root, childrenMetrics);
        System.out.println(self_metrics+"\n");
        return self_metrics;

    }
>>>>>>> 1b357ee7950d3d438546e35d369383adfc996b84

    public static void main(String[] args){

        //first unit test
        LOCMetricsMeasurer metricsMeasurer = new LOCMetricsMeasurer("/*", "*/", "//");
<<<<<<< HEAD
       // LOCMetrics metrics = metricsMeasurer.measureClassLOCMetrics("tests/test1.java");
        //System.out.println(metrics); //23
=======
        recursivePrintFiles("tests", "\t");
        recursiveListFiles("tests", metricsMeasurer);

        // LOCMetrics metrics = metricsMeasurer.measureClassLOCMetrics("tests/test1.java");
        // System.out.println(metrics); //23
>>>>>>> 1b357ee7950d3d438546e35d369383adfc996b84
    }
}