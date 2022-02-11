import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Classe responsable de la mesure de la taille d'une entité
 * (classe/paquet).  Store les informations mesurées dans
 * un objet de type LOCMetrics.
 */
class LOCMetricsMeasurer{

    private String blockCommentStart;
    private String blockCommentEnd;;
    private String inlineCommentStart;
    //TODO: should we handle documentation comment blocks seperately?

    /**
     * Seul constructeur de la classe.
     * @param blockCommentStart //chaîne qui débute un commentaire multiligne
     * @param blockCommentEnd //chaîne qui met fin à un commentaire multiligne
     * @param inlineCommentStart //chaîne qui début un commentaire inline
     */
    public LOCMetricsMeasurer(String blockCommentStart, String blockCommentEnd, String inlineCommentStart){
        this.blockCommentStart = blockCommentStart;
        this.blockCommentEnd = blockCommentEnd;
        this.inlineCommentStart = inlineCommentStart;
    }

    /**
     * Opère sur un fichier source de classe java pour mesurer ses métriques de taille
     * @param classFileName nom de la classe (pas le path)
     * @return un objet LOCMetrics contenant les métriques de taille
     */
    public LOCMetrics measureClassLOCMetrics(String classFileName){
        //TODO: change so this throws an exception and caller has to handle file error
        int loc = 0;
        int cloc = 0;
        int wmc = 1;

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
                String line = scanner.nextLine().trim();
                matcher = method_def.matcher(line);
                //if (line.isEmpty() || line == null){
                if (line.isEmpty()){
                    continue;
                } if (matcher.find() && !line.contains("class") && !line.contains("inteface")){
                    nb_methods++;
                    //System.out.println("method:" + line);
                } else if (insideBlockComment){
                    loc++;
                    cloc++;
                    if (line.endsWith(blockCommentEnd)) insideBlockComment = false;
                } else if (line.startsWith(blockCommentStart)){
                    insideBlockComment = true;
                    loc++;
                    cloc++;
                } else {
                    //this line is not part of a block comment
                    if (line.contains(inlineCommentStart)){
                        cloc++;
                    } else if ((line.startsWith("if") || line.startsWith("while") || line.startsWith("for") || line.startsWith("switch"))
                         || line.contains("esle if")
                        && !insideBlockComment){
                            nb_predicates++;
                            //System.out.println("predicate:" + line);
                    }
                    loc++;
                }
            }

            /*On peut obtenir WMC en obtenant la somme des complexités de chaque méthode.
            * Ce qui est just nb predicates + 1 pour chacune.
            * Mais on n'a pas à fournir la complexité de chaque méthode, juste le WMC
            * Donc dans les faits, on peut juste faire sum(nb_predicates) + nb_methods
            * et on obtient le WMC.  Donc on compte les predicats sans se soucier
            * dans quelle méthode ils sont.
            */
            wmc = nb_methods + nb_predicates;
            scanner.close();
        } catch(IOException e){ 
            e.printStackTrace();
        }

        return new LOCMetrics(classFileName, false, loc, cloc, wmc);
    }

    /**
     * Collige les métriques des classes/paquets enfants d'un paquets pour obtenir ses
     * métriques. 
     * @param dirName //nom du paquet
     * @param childrenMetrics //liste d'objets LOCMetrics associés aux enfants du paquet
     * @return un objet LOCMetrics contenant les métriques du paquet
     */
    public static LOCMetrics computePackageLOCMetrics(String dirName, ArrayList<LOCMetrics> childrenMetrics){
        int tot_loc = 0;
        int tot_cloc = 0;
        int tot_wmc = 0;

        for (LOCMetrics childMetric : childrenMetrics){
            if (!childMetric.getIsPackage()){//on n'additionne pas les LOC, CLOC des paquets enfants
                tot_loc += childMetric.getLoc();
                tot_cloc += childMetric.getCloc();
            } 
            tot_wmc += childMetric.getWmc();
        }

        return new LOCMetrics(dirName, true, tot_loc, tot_cloc, tot_wmc);
    }
public static void main(String args[]){ //TODO: enlever
        LOCMetricsMeasurer measurer = new LOCMetricsMeasurer("/*", "*/", "//");
        System.out.println(measurer.measureClassLOCMetrics("DomainOrderTest.java"));
    }
}