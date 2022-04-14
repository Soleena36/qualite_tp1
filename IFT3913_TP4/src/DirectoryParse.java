import java.nio.file.Paths;
import java.util.ArrayList;

public class DirectoryParse {
    FileTreatment pathFileTreatment = new FileTreatment();
    LOCMetricsMeasurer measurer = new LOCMetricsMeasurer("/*", "*/", "//");
    public ArrayList<String> listOfFiles(String directory)
    {

        //String contentPath = pathFileTreatment.readFile();
        ArrayList<String> fileList = new ArrayList<>();
        pathFileTreatment.explore(directory,fileList);
        return fileList;
    }

    public float[] getBC_Classe_Mesures(String dir)
    {
        ArrayList<String> bc_list = new ArrayList<>();
        ArrayList<String> javaFileList = listOfFiles(dir);
        float[] repoMesures = new float[3];
        float sum_bc = 0;
        float sum_wmc = 0;
        float class_number = javaFileList.size();

        for(int i = 0 ; i < javaFileList.size();i++)
        {
            //System.out.println(javaFileList.get(i));
            LOCMetrics lm = measurer.measureClassLOCMetrics(javaFileList.get(i));
            sum_bc += lm.getBc();
            sum_wmc += lm.getWmc();
        }
        float mwmc = sum_wmc/class_number;
        float mbc = sum_bc/class_number;

        repoMesures[0] = class_number;
        repoMesures[1] = mwmc;
        repoMesures[2] = mbc;

        System.out.println(class_number);
        System.out.println(mwmc);
        System.out.println(mbc);

        return  repoMesures;
    }

    
    public static void main(String[] args) {
        DirectoryParse dp =new DirectoryParse();
        System.out.println(dp.getBC_Classe_Mesures(Paths.get("").toAbsolutePath().toString()+"/jfreechart-master"));;;
    }
}
