/**
 * Classe principale qui mesure les métriques des classes (ou paquets) contenus dans un dossier.
 * Mesure LOC, CLOC, DC = CLOC/LOC, WMC (WMP), DC/WMC (DC/WMP)
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class MeasureDoc{

    /**
     * Liste tous les fichiers/dossiers dans un répertoire
     * adapté de https://stackoverflow.com/questions/5694385/getting-the-filenames-of-all-files-in-a-folder
     * @param root chemin relatif vers répertoire
     * @return une map<string, boolean> des fichiers, boolean est true si un dossier
     */
    public static Map<String, Boolean> listFiles(String root){
        File folder = new File(root);
        File[] listOfFiles = folder.listFiles();
        HashMap<String,Boolean> output = new HashMap<>();

        for (int i = 0; i < listOfFiles.length; i++) {
            String filename = listOfFiles[i].getName();
            if (listOfFiles[i].isFile() && filename.contains(".java")) {
                //fichier source
                output.put(filename, false); 
            } else if (listOfFiles[i].isDirectory()) {
                output.put(filename, true); 
            }
        }

        return output;
    }

    /**
     * Parcours le dossier root pour imprimer toutes les métriques des paquets/classes contenues.
     * @param root chemin relatif vers le dossier
     * @param measurer classe qui s'occupe du parsing des .java pour obtenir les métriques
     * @param writer classe qui écrit les métriques dans un CSV
     * @return une liste de LOCMetrics.  De taille 1 si dossier est un paquet: renvoie ses propres métriques
     * Si pas un paquet, renvoie les une liste contenant les métriques des sous-paquets.
     */
    private static ArrayList<LOCMetrics> recursiveListFiles(String root, LOCMetricsMeasurer measurer, CSVWriter writer){
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
                //On doit utiliser une liste, sinon on aura des problèmes à traiter les
                //descendants d'un dossier qui ne contient que des dossiers
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
        if (args.length != 1){
            System.out.println("Usage:\njavar -jar MeasureDoc.jar <relative-path-to-folder-to-analyse>");
            return;
        }

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