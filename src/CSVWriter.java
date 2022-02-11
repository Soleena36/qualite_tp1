/**
 * Classe qui s'occupe de l'écriture des fichiers CSV contenant les métriques.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {
    //BufferedWriter bw = new BufferedWriter(new FileWriter(new File("classes.csv")));
    private String classCSVName;
    private String packageCSVName;
    private BufferedWriter classWriter;
    private BufferedWriter packageWriter;

    /**
     * Unique constructeur de cSVWriter
     * @param classCSVName le nom du CSV qui va contenir les metriques de classes
     * @param packageCSVName le nom du CSV qui va contenir les metriques de paquets
     * @throws IOException
     */
    public CSVWriter(String classCSVName, String packageCSVName) throws IOException{
        this.classCSVName = classCSVName;
        this.packageCSVName = packageCSVName;
        writeHeaders(); 
    }

    /**
     * Ferme les BufferedWriter utilisées par la classe.
     * @throws IOException
     */
    public void close() throws IOException{
        classWriter.close();
        packageWriter.close();
    }

    private void writeHeaders() throws IOException{
        classWriter = new BufferedWriter(new FileWriter(new File(classCSVName)));
        classWriter.write("chemin, class, classe_LOC, classe_CLOC, classe_DC, WMC, classe_BC\n");
        packageWriter = new BufferedWriter(new FileWriter(new File(packageCSVName)));
        packageWriter.write("chemin, paquet, paquet_LOC, paquet_CLOC, paquet_DC, WMP, paquet_BC\n");
    }

    /**
     * Écrit une ligne dans le fichier CSV des classes.
     * Représente les métriques d'une classe.
     * @param classMetrics data class contenant nom+métriques de classe
     * @throws IOException
     */
    public void writeClass(LOCMetrics classMetrics) throws IOException{
        classWriter.write(classMetrics.toString());
    }

    /**
     * Écrit une ligne dans le fichier CSV des paquets.
     * Représente les métriques d'un paquet.
     * @param packageMetrics data class contenant nom+métriques de paquet
     * @throws IOException
     */
    public void writePackage(LOCMetrics packageMetrics) throws IOException{
        packageWriter.write(packageMetrics.toString());
    }

}
