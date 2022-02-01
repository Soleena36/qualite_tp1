class LOCMetrics{
    int loc; //nb of lines of code
    int cloc; //nb of comment lines of code
    float dc; //comment density cloc/loc

    public LOCMetrics(){
        //TODO: remove when done implementing
    }

    public LOCMetrics(int loc, int cloc){
        this.loc = loc;
        this.cloc = cloc;
        this.dc = (float)(cloc)/loc;
    }
}