import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class MeasureDoc{

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

    public static ArrayList<LOCMetrics> recursiveListFiles(String root, LOCMetricsMeasurer measurer, CSVWriter writer){
        ArrayList<LOCMetrics> output = new ArrayList<>();
        if (root.endsWith("/")){ //making sure we don't have extra slashes
            root = root.substring(0, root.length()-1);
        }

        HashSet<String> ignore_list = new HashSet<>();
        ignore_list.add(".git");
        ignore_list.add(".idea");
        ignore_list.add(".vscode");
        //ignore_list.add("tests"); //that one is questionable

        Map<String, Boolean> filesInDirectory = listFiles(root); //boolean is true if file is directory

        boolean has_java_files = false;
        for (String key: filesInDirectory.keySet()){
           if (key.endsWith(".java")){
               has_java_files = true;
               break;
           } 
        }
        
        if (!has_java_files){ //dossier n'est pas un paquet
            for (String key: filesInDirectory.keySet()){
                output.addAll(recursiveListFiles(root+"/" + key, measurer, writer));
            }
            return output;
        }


        //on a atteint ce point car le dossier présent est un paquet
        //on doit accumuler les métriques de ses enfants pour obtenir ses métriques
        
        ArrayList<LOCMetrics> childrenMetrics = new ArrayList<>(); //metrics of children to current package

        for (String key : filesInDirectory.keySet()) {
            if (ignore_list.contains(key)){
                continue;
            } else if (filesInDirectory.get(key)){
                //file is a directory
                childrenMetrics.addAll(recursiveListFiles(root+"/"+key, measurer, writer));
            } else{
                LOCMetrics childMetrics = measurer.measureClassLOCMetrics(root+"/"+key);
                childrenMetrics.add(childMetrics); 
                try {
                    writer.writeClass(childMetrics);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        LOCMetrics self_metrics =  LOCMetricsMeasurer.computePackageLOCMetrics(root, childrenMetrics);
        try {
            writer.writePackage(self_metrics);
        } catch (IOException e){
            e.printStackTrace();
        }

        output.add(self_metrics);
        return output;
    }

    public static void main(String[] args){
        String target = args[0];
        LOCMetricsMeasurer measurer = new LOCMetricsMeasurer("/*", "*/", "//");
        try{
            CSVWriter writer = new CSVWriter("classes.csv", "paquets.csv");
            recursiveListFiles(target, measurer, writer);
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}