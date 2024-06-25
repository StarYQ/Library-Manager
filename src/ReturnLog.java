public class ReturnLog {
    private long ISBN;
    private long userID;
    private Date returnDate;
    private ReturnLog nextLog;
    public ReturnLog() {
        this.ISBN=0;
        this.userID=0;
        this.returnDate=new Date();
        this.nextLog=null;
    }
    public ReturnLog(long ISBN, long userID, Date returnDate) {
        this.ISBN=ISBN;
        this.userID=userID;
        this.returnDate=returnDate;
        this.nextLog=null;
    }
    public long getISBN(){
        return ISBN;
    }
    public void setISBN(long ISBN){
        this.ISBN=ISBN;
    }
    public long getUserID(){
        return userID;
    }
    public void setUserID(long userID){
        this.userID=userID;
    }
    public Date getReturnDate(){
        return returnDate;
    }
    public void setReturnDate(Date returnDate){
        this.returnDate=returnDate;
    }
    public ReturnLog getNextLog(){
        return nextLog;
    }
    public void setNextLog(ReturnLog nextLog){
        this.nextLog=nextLog;
    }
    public String toString(){
        return String.format("| %-13d | %-10d | %-12s |", ISBN, userID, returnDate.toString());
    }
}