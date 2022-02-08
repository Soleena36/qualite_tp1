class LOCMetrics{
    private String name; //name of thing being measured
    private boolean is_package; //true if it's a package
    private int loc; //nb of lines of code
    private int cloc; //nb of comment lines of code
    private float dc; //comment density cloc/loc
    private float wmc; //weighted methods per class
    private float bc; // high if well commented : dc/wmc

    public LOCMetrics(){
        //TODO: remove when done implementing
    }

    public LOCMetrics(String name, boolean is_package, int loc, int cloc, float wmc){
        this.name = name;
        this.is_package = is_package;
        this.loc = loc;
        this.cloc = cloc;
        this.dc = (float)(cloc)/loc;
        this.wmc = wmc;
        this.bc = dc/wmc;
    }

    // public static LOCMetrics concat_metrics(String name, LOCMetrics m1, LOCMetrics m2){
    //     int tot_loc = m1.getLoc() + m2.getCloc();
    //     int tot_cloc = m1.getCloc() + m2.getCloc();
    //     float tot_wmc = m1.getWmc() + m2.getWmc();

    //     //true because we only concat for packages
    //     return new LOCMetrics(name, true, tot_loc, tot_cloc, tot_wmc);
    // }

    @Override
    public String toString(){
        String res = "";
        res += "Name: " + name + '\n'
            + "Is a package: " + is_package  + '\n'
            + "Lines of comments: " + cloc + '\n'
            + "Comment density: " + dc + '\n'
            + "Weighted Methods per Class: " + wmc + '\n'
            + "Comment score: " + bc;

        return res;
    }

    public float getDc() {
        return dc;
    }

    public int getCloc() {
        return cloc;
    }

    public int getLoc() {
        return loc;
    }

    public float getWmc(){
        return wmc;
    }

    public boolean getIsPackage(){
        return is_package;
    }
}