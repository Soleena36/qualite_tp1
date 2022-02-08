import java.io.File;
import java.util.Stack;

public class WriteLOCFile {


    LOCMetricsMeasurer lmm = new LOCMetricsMeasurer();
    MeasureDoc mc = new MeasureDoc();
    LOCMetrics lm = new LOCMetrics();

    public String classCSVLign(String files){
        String content = lmm.readFile(files);
        int loc = lm.getLoc();
        int cloc = lm.getCloc();
        float dc = lm.getDc();

        Stack<String> stack = new Stack<>();
        String[] reps = files.split("/");

        for(int i =0; i<reps.length;i++){
            stack.push(reps[i]);
        }
        String javaFile = stack.pop();

        return javaFile + ","+ String.valueOf(loc) + "," + String.valueOf(cloc) + "," + String.valueOf(dc);
    }
//inspiré de : https://stackoverflow.com/questions/3332486/program-to-get-all-files-within-a-directory-in-java
//mais inversé la logique du programme
    public void getFiles(File f) {
        File files[];
        if (f.isDirectory()){
            files = f.listFiles();
            for (int i = 0; i < files.length; i++) {
                getFiles(files[i]);
            }
        }
        else if(f.isFile() && f.getAbsolutePath().indexOf(".java") != -1){
            System.out.println(f.getAbsolutePath());
        }
    }
    public static void main(String[] args) {
        String a = new WriteLOCFile().classCSVLign("/home/celia/qualite_tp1/jfreechart/src/test/java/org/jfree/data/xy/VectorTest.java");
        //new WriteLOCFile().getFiles(new File("/home/celia/qualite_tp1/jfreechart"));

    }
}
