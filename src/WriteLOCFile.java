import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class WriteLOCFile {

    ArrayList<String> pathList = new ArrayList<>();
    ArrayList<String> csvLign  = new ArrayList<>();
    ArrayList<String> packages = new ArrayList<>();


    public String classCSVLign(String path){
        LOCMetrics lm = new LOCMetricsMeasurer("/*", "*/", "//").measureClassLOCMetrics(path);

        int loc = lm.getLoc();
        int cloc = lm.getCloc();
        float dc = lm.getDc();

        String javaString = path.substring(path.lastIndexOf("/")+1,path.length());

        return path + ","+ javaString +","+ String.valueOf(loc) + "," + String.valueOf(cloc) + "," + String.valueOf(dc);
    }
//inspiré de : https://stackoverflow.com/questions/3332486/program-to-get-all-files-within-a-directory-in-java
//mais inversé la logique du programme et adapté à nos besoins spécifiques
    public void getFiles(File f) {
        File files[];
        if (f.isDirectory()){
            files = f.listFiles();
            for (int i = 0; i < files.length; i++) {
                getFiles(files[i]);
            }
        }
        else if(f.isFile() && f.getAbsolutePath().indexOf(".java") != -1){
            String absPath = f.getAbsolutePath();
            pathList.add(absPath.substring(absPath.indexOf("jfreechart"),absPath.length()));
            packages.add(absPath.substring(absPath.indexOf("jfreechart"),absPath.lastIndexOf("/")));
        }
    }

    public void application(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("classes.csv")));
            for(int i=0 ; i<pathList.size();i++){
                csvLign.add(classCSVLign(pathList.get(i)));
            }
            for(int i=0;i<csvLign.size();i++){
                bw.write(csvLign.get(i)+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> setMap(){
        HashMap<String,LOCMetrics> path2Mesures = new HashMap<>();
        ArrayList<String> lignes = new ArrayList<>();

        for(int i =0;i<packages.size();i++){
            int loc = Integer.parseInt(csvLign.get(i).split(",")[2]);
            int cloc = Integer.parseInt(csvLign.get(i).split(",")[3]);
            if(!path2Mesures.containsKey(packages.get(i))){
                path2Mesures.put(packages.get(i),new LOCMetrics(packages.get(i),true,loc,cloc,0));
                //System.out.println("ha!");
            }
            else{
                int demo = path2Mesures.get(packages.get(i)).getLoc() + loc;
                path2Mesures.get(packages.get(i)).setLoc(demo);

                int demo2 = path2Mesures.get(packages.get(i)).getCloc() + cloc;
                path2Mesures.get(packages.get(i)).setCloc(cloc);
            }
        }
        
        for(int i=0;i<packages.size();i++){
            LOCMetrics cur_element = path2Mesures.get(packages.get(i));
            String oneLine = packages.get(i) + "," + packages.get(i).replace("/",".") + ","+cur_element.getLoc()+","+cur_element.getCloc()+","+((float)cur_element.getCloc()/(float)cur_element.getLoc());
            if(!lignes.contains(oneLine)){

                lignes.add(oneLine);
                System.out.println(oneLine);
            }
        }
        return lignes;
    }

    public void writePackageFile(){
        ArrayList<String> ligns = setMap();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File("paquets.csv")));
            for(int i=0;i<ligns.size();i++){
                bw.write(ligns.get(i)+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        //String a = new WriteLOCFile().classCSVLign("/home/celia/qualite_tp1/jfreechart/src/test/java/org/jfree/data/xy/VectorTest.java");
        //System.out.println(a);
        WriteLOCFile wlf = new WriteLOCFile();
        wlf.getFiles(new File(System.getProperty("user.dir")+"/jfreechart"));
        wlf.application();
        wlf.setMap();
        wlf.writePackageFile();
    }
}
