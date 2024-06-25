import java.util.EmptyStackException;
class InvalidReturnDateException extends Exception {
    public InvalidReturnDateException(String message) {
        super(message);
    }
}
class BookNotCheckedOutException extends Exception {
    public BookNotCheckedOutException(String message) {
        super(message);
    }
}
class BookCheckedOutBySomeoneElseException extends Exception {
    public BookCheckedOutBySomeoneElseException(String message) {
        super(message);
    }
}
class StackIsEmptyException extends Exception {
    public StackIsEmptyException(String message) {
        super(message);
    }
}
public class ReturnStack{
    private ReturnLog topLog;
    public ReturnStack(){
        this.topLog=null;
    }
    public boolean pushLog(long returnISBN, long returnUserID, Date returnDate, BookRepository bookRepoRef) throws InvalidISBNException, InvalidReturnDateException, BookNotCheckedOutException,
            BookCheckedOutBySomeoneElseException, InvalidUserIDException{

        //To validate userID and ISBN
        if (String.valueOf(returnISBN).length()!=13){
            throw new InvalidISBNException("Invalid ISBN.");
        }
        if (String.valueOf(returnUserID).length()>10){
            throw new InvalidUserIDException("Invalid User ID.");
        }
        //To find the book in the repository and check its checkout status
        Book returnedBook=bookRepoRef.findBookByISBN(returnISBN);
        if (returnedBook==null || !returnedBook.isCheckedOut()){
            throw new BookNotCheckedOutException("Book is not checked out.");
        }
        if (returnedBook.getCheckOutUserID()!=returnUserID) {
            throw new BookCheckedOutBySomeoneElseException("Book checked out by another user.");
        }
        //To determine if the book is returned late
        boolean isLate=Date.compare(returnDate, returnedBook.getCheckOutDate())>0;
        //Creatine a new ReturnLog and pushing it into stack
        ReturnLog newLog=new ReturnLog(returnISBN, returnUserID, returnDate);
        newLog.setNextLog(topLog);
        topLog=newLog;
        return isLate;
    }
    public ReturnLog popLog() throws StackIsEmptyException{
        if (topLog==null){
            throw new StackIsEmptyException("ReturnStack is empty.");
        }
        ReturnLog log=topLog;
        topLog=topLog.getNextLog();
        return log;
    }
    public ReturnLog peekLog() throws StackIsEmptyException{
        if (topLog==null){
            throw new StackIsEmptyException("ReturnStack is empty.");
        }
        return topLog;
    }
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("---------------------------------------------\n");
        sb.append("| ISBN         | UserID     | Return Date   |\n");
        sb.append("---------------------------------------------\n");
        ReturnLog current=topLog;
        while (current!=null) {
            sb.append(current.toString()).append("\n");
            current=current.getNextLog();
        }
        sb.append("---------------------------------------------");
        return sb.toString();
    }
}
