enum Condition {
    NEW, GOOD, BAD, REPLACE
}
public class Book {
    private String name;
    private String author;
    private String genre;
    private Condition bookCondition;
    private long ISBN;
    private long checkOutUserID;
    private int yearPublished;
    private Date checkOutDate;
    private Book nextBook;
    private boolean checkedOut;
    public Book(){
        this.checkedOut=false;
        this.nextBook=null;
    }
    public Book(String name, String author, String genre, int yearPublished, Condition condition, long ISBN){
        this.name=name;
        this.author=author;
        this.genre=genre;
        this.yearPublished=yearPublished;
        this.bookCondition=condition;
        this.ISBN=ISBN;
        this.checkedOut=false;
        this.nextBook=null;
    }
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }
    public String getAuthor(){
        return author;
    }
    public void setAuthor(String author){
        this.author=author;
    }
    public String getGenre(){
        return genre;
    }
    public void setGenre(String genre){
        this.genre=genre;
    }
    public int getYearPublished(){
        return yearPublished;
    }
    public void setYearPublished(int yearPublished){
        this.yearPublished=yearPublished;
    }
    public Condition getBookCondition(){
        return bookCondition;
    }
    public void setBookCondition(Condition bookCondition){
        this.bookCondition=bookCondition;
    }
    public long getISBN(){
        return ISBN;
    }
    public void setISBN(long ISBN){
        this.ISBN=ISBN;
    }
    public boolean isCheckedOut(){
        return checkedOut;
    }
    public void setCheckedOut(boolean checkedOut){
        this.checkedOut=checkedOut;
    }
    public long getCheckOutUserID(){
        return checkOutUserID;
    }
    public void setCheckOutUserID(long checkOutUserID){
        this.checkOutUserID=checkOutUserID;
    }
    public Date getCheckOutDate(){
        return checkOutDate;
    }
    public void setCheckOutDate(Date checkOutDate){
        this.checkOutDate=checkOutDate;
    }
    public Book getNextBook(){
        return nextBook;
    }
    public void setNextBook(Book nextBook){
        this.nextBook=nextBook;
    }
    public String getFormattedTable(){
        String checkOutDate=(getCheckOutDate()!=null) ? getCheckOutDate().toString():"N/A";
        return String.format("%-20s%-15s%-20s%-15s", getName(), isCheckedOut(), checkOutDate, getCheckOutUserID());
    }

    @Override
    public String toString(){
        return "Book{"+
                "name='"+name+'\''+
                ", author='"+author+'\''+
                ", genre='"+genre+'\''+
                ", yearPublished="+yearPublished+
                ", condition="+bookCondition+
                ", ISBN="+ISBN+
                ", checkedOut="+checkedOut+
                ", checkOutUserID="+checkOutUserID+
                ", checkOutDate="+(checkOutDate!=null ? checkOutDate.toString():"N/A") +
                '}';
    }
}
