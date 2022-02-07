class LOCMetrics{
    private int loc; //nb of lines of code
    private int cloc; //nb of comment lines of code
    private float dc; //comment density cloc/loc
    private float wmc; //weighted methods per class
    private float bc; // high if well commented : dc/wmc

    public LOCMetrics(){
        //TODO: remove when done implementing
    }

    public LOCMetrics(int loc, int cloc, float wmc){
        this.loc = loc;
        this.cloc = cloc;
        this.dc = (float)(cloc)/loc;
        this.wmc = wmc;
        this.bc = dc/wmc;
    }

    //public static LOCMetrics concat_metrics(LOCMetrics m1, LOCMetrics m2){
    //}

    @Override
    public String toString(){
        String res = "";
        res += "Lines of code: " + loc + '\n'
            + "Lines of comments: " + cloc + '\n'
            + "Comment density: " + dc + '\n'
            + "Weighted Methods per Class: " + wmc + '\n'
            + "Comment score: " + bc;

        return res;
    }
}