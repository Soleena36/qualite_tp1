import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class WriteLOCFile {

    LOCMetrics lm;
    LOCMetricsMeasurer lmm;
    MeasureDoc mc;

    public String classCSVLign(String files){
        lm = lmm.measureClassLOCMetrics(files);
        int loc = lm.getLoc();
        int cloc = lm.getCloc();
        float dc = lm.getDc();

        Stack<String> stack = new Stack<>();
        String[] reps = files.split("/");

        for(int i =0; i<reps.length;i++){
            stack.push(reps[i]);
        }
        String javaFile = stack.pop().split(".")[0];

        return javaFile + ","+ String.valueOf(loc) + "," + String.valueOf(cloc) + "," + String.valueOf(dc);
    }
    
    public static void main(String[] args) {

    }
}
