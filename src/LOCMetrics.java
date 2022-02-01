class LOCMetrics{
    private int loc; //nb of lines of code
    private int cloc; //nb of comment lines of code
    private float dc; //comment density cloc/loc

    public LOCMetrics(){
        //TODO: remove when done implementing
    }

    public LOCMetrics(int loc, int cloc){
        this.loc = loc;
        this.cloc = cloc;
        this.dc = (float)(cloc)/loc;
    }

    @Override
    public String toString(){
        String res = "";
        res += "Lines of code: " + loc + '\n'
            + "Lines of comments: " + cloc + '\n'
            + "Comment density: " + dc;

        return res;
    }
}