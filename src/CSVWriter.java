import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {
    //BufferedWriter bw = new BufferedWriter(new FileWriter(new File("classes.csv")));
    private String classes;
    private String packages;
    private BufferedWriter classWriter;
    private BufferedWriter packageWriter;

    public CSVWriter(String classes, String packages) throws IOException{
        this.classes = classes;
        this.packages = packages;
        writeHeaders(); 
    }

    public void close() throws IOException{
        classWriter.close();
        packageWriter.close();
    }

    private void writeHeaders() throws IOException{
        classWriter = new BufferedWriter(new FileWriter(new File(classes)));
        classWriter.write("chemin, class, classe_LOC, classe_CLOC, classe_DC, WMC, classe_BC\n");
        packageWriter = new BufferedWriter(new FileWriter(new File(packages)));
        packageWriter.write("chemin, paquet, paquet_LOC, paquet_CLOC, paquet_DC, WMP, paquet_BC\n");
    }

    public void writeClass(LOCMetrics classMetrics) throws IOException{
        classWriter.write(classMetrics.toString());
    }

    public void writePackage(LOCMetrics packageMetrics) throws IOException{
        packageWriter.write(packageMetrics.toString());
    }

}
