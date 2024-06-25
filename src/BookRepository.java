class InvalidUserIDException extends Exception {
    public InvalidUserIDException(String message) {
        super(message);
    }
}
class InvalidSortCriteriaException extends Exception {
    public InvalidSortCriteriaException(String message) {
        super(message);
    }
}
class BookAlreadyCheckedOutException extends Exception {
    public BookAlreadyCheckedOutException(String message) {
        super(message);
    }
}
class BookNotFoundException extends Exception {
    public BookNotFoundException(String message) {
        super(message);
    }
}
public class BookRepository {
    private Shelf[] shelves;
    public BookRepository() {
        shelves=new Shelf[10];
        for (int i=0; i<shelves.length; i++){
            shelves[i]=new Shelf();
        }
    }
    public void checkInBook(long checkedInISBN) throws BookNotFoundException {
        Book book=findBookByISBN(checkedInISBN);
        if (book==null) {
            throw new BookNotFoundException("Book with ISBN " + checkedInISBN + " not found.");
        }
        book.setCheckedOut(false);
        book.setCheckOutUserID(0);
        book.setCheckOutDate(null);
    }
    public void checkOutBook(long checkedOutISBN, long checkOutUserID, Date dueDate)
            throws BookNotFoundException, BookAlreadyCheckedOutException, InvalidISBNException, InvalidUserIDException {
        Book book = findBookByISBN(checkedOutISBN);
        if (book==null) {
            throw new BookNotFoundException("Book with ISBN "+checkedOutISBN+" not found.");
        }
        if (!isValidUserID(checkOutUserID)){
            throw new InvalidUserIDException("Invalid User ID.");
        }
        if (!isValidISBN(checkedOutISBN)){
            throw new InvalidISBNException("Invalid User ISBN.");
        }
        if (book.isCheckedOut()){
            throw new BookAlreadyCheckedOutException("Book is already checked out.");
        }
        book.setCheckedOut(true);
        book.setCheckOutUserID(checkOutUserID);
        book.setCheckOutDate(dueDate);
    }

    //Helper method to check if user ID is less than 10 digits
    private boolean isValidUserID(long userID) {
        return Long.toString(userID).length()<10;
    }
    //Helper method to check if user ID is less than 13 digits
    private boolean isValidISBN(long ISBN) {
        return Long.toString(ISBN).length()<=13;
    }
    public void addBook(String name, String author, String genre, int yearPublished, Condition condition, long addISBN)
            throws InvalidISBNException, BookAlreadyExistsException {
        if (!isValidISBN(addISBN)){
            throw new InvalidISBNException("Invalid User ISBN.");
        }
        if (findBookByISBN(addISBN)!=null){
            throw new BookAlreadyExistsException("A book with this ISBN already exists.");
        }
        Book newBook=new Book(name, author, genre, yearPublished, condition, addISBN);
        int shelfIndex=Character.toUpperCase(name.charAt(0))-'A';
        Shelf shelf=shelves[shelfIndex%shelves.length];
        shelf.addBook(newBook);
    }
    public void removeBook(long removeISBN) throws BookNotFoundException {
        Book book=findBookByISBN(removeISBN);
        if (book==null) {
            throw new BookNotFoundException("Book with ISBN "+removeISBN+" not found.");
        }
        String bookName=book.getName();
        int shelfIndex=Character.toUpperCase(bookName.charAt(0))-'A';
        Shelf shelf=shelves[shelfIndex%shelves.length];
        try{
            shelf.removeBook(removeISBN);
        }
        catch (BookDoesNotExistException e){
            e.printStackTrace();
        }
    }
    public void sortShelf(int shelfIndex, String sortCriteria) throws InvalidSortCriteriaException {
        // Checking if the shelf index is valid
        if (shelfIndex<0 || shelfIndex>=shelves.length) {
            throw new IllegalArgumentException("Invalid shelf index.");
        }
        //Converting the string sortCriteria to SortCriteria enum value
        SortCriteria criteria;
        try{
            criteria=SortCriteria.valueOf(sortCriteria.toUpperCase());
        }
        catch (IllegalArgumentException e){
            throw new InvalidSortCriteriaException("Invalid sort criteria: "+sortCriteria);
        }
        shelves[shelfIndex].sort(criteria);
    }
    public Book findBookByISBN(long ISBN){
        for (Shelf shelf:shelves){
            Book foundBook=shelf.findBookByISBN(ISBN);
            if (foundBook!=null){
                return foundBook;
            }
        }
        return null;
    }
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append(String.format("%-20s%-15s%-20s%-15s%s\n", "Name", "Checked Out", "Check Out Date", "Checkout UserID", "Shelf"));
        for (int i=0; i<shelves.length; i++) {
            Shelf shelf=shelves[i];
            sb.append(shelf.getFormattedTable(i)).append("\n");
        }
        return sb.toString();
    }
}
