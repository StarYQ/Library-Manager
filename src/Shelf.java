class BookAlreadyExistsException extends Exception {
    public BookAlreadyExistsException(String message) {
        super(message);
    }
}
class InvalidISBNException extends Exception {
    public InvalidISBNException(String message) {
        super(message);
    }
}
class BookDoesNotExistException extends Exception {
    public BookDoesNotExistException(String message) {
        super(message);
    }
}
enum SortCriteria {
    ISBN, NAME, AUTHOR, GENRE, YEAR, CONDITION
}
public class Shelf {
    private Book headBook;
    private Book tailBook;
    private int length;
    private SortCriteria shelfSortCriteria;
    public Shelf() {
        this.headBook=null;
        this.tailBook=null;
        this.length=0;
        this.shelfSortCriteria=SortCriteria.ISBN;
    }
    public void addBook(Book addedBook) throws BookAlreadyExistsException {
        if (exists(addedBook.getISBN())) {
            throw new BookAlreadyExistsException("A book with this ISBN already exists.");
        }
        if (headBook==null||compareBooks(addedBook, headBook, shelfSortCriteria) < 0){
            addedBook.setNextBook(headBook);
            headBook=addedBook;
            if (tailBook==null){
                tailBook=headBook;
            }
        }
        else{
            Book current=headBook;
            while (current.getNextBook()!=null && compareBooks(addedBook, current.getNextBook(), shelfSortCriteria) >= 0) {
                current=current.getNextBook();
            }
            addedBook.setNextBook(current.getNextBook());
            current.setNextBook(addedBook);
            if (current==tailBook){
                tailBook=addedBook;
            }
        }
        length++;
    }
//This checks if a book already exists with the given ISBN
    private boolean exists(long ISBN){
        Book current=headBook;
        while (current!=null) {
            if (current.getISBN()==ISBN){
                return true;
            }
            current=current.getNextBook();
        }
        return false;
    }
    public void removeBook(long removedISBN) throws BookDoesNotExistException {
        if (headBook==null) {
            throw new BookDoesNotExistException("Shelf is empty.");
        }
        if (headBook.getISBN()==removedISBN) {
            headBook=headBook.getNextBook();
            if (headBook==null) {
                tailBook=null;
            }
            length--;
            return;
        }
        Book current=headBook;
        while (current.getNextBook()!=null && current.getNextBook().getISBN()!=removedISBN) {
            current=current.getNextBook();
        }
        if (current.getNextBook()==null) {
            throw new BookDoesNotExistException("Book with ISBN "+removedISBN+" does not exist.");
        }
        current.setNextBook(current.getNextBook().getNextBook());
        if (current.getNextBook()==null) {
            tailBook=current;
        }
        length--;
    }
    public void sort(SortCriteria sortCriteria) {
        if (headBook==null || headBook.getNextBook()==null) {
            return; //No sorting needed for empty or single-item shelf
        }
        shelfSortCriteria=sortCriteria;
        //For the actual sorting, using bubble sort on the LinkedList
        boolean wasChanged;
        do{
            Book previous=null;
            Book current=headBook;
            wasChanged=false;
            while (current!=null && current.getNextBook()!=null) {
                if (compareBooks(current, current.getNextBook(), sortCriteria)>0){
                    //To swap the books:
                    wasChanged=true;
                    Book tmp=current.getNextBook();
                    current.setNextBook(tmp.getNextBook());
                    tmp.setNextBook(current);
                    if (previous==null){
                        headBook=tmp;
                    }
                    else{
                        previous.setNextBook(tmp);
                    }
                    previous=tmp;
                }
                else{
                    previous=current;
                    current=current.getNextBook();
                }
            }
        }
        while (wasChanged);

    }
    private int compareBooks(Book book1, Book book2, SortCriteria criteria){
        switch (criteria){
            case ISBN:
                return Long.compare(book1.getISBN(), book2.getISBN());
            case NAME:
                return book1.getName().compareTo(book2.getName());
            case AUTHOR:
                return book1.getAuthor().compareTo(book2.getAuthor());
            case GENRE:
                return book1.getGenre().compareTo(book2.getGenre());
            case YEAR:
                return Integer.compare(book1.getYearPublished(), book2.getYearPublished());
            case CONDITION:
                return book1.getBookCondition().compareTo(book2.getBookCondition());
            default:
                return 0;
        }
    }
    public Book findBookByISBN(long ISBN){
        Book current=headBook;
        while (current!=null) {
            if (current.getISBN()==ISBN){
                return current;
            }
            current=current.getNextBook();
        }
        return null;
    }
    public String getFormattedTable(int shelfNumber){
        StringBuilder sb=new StringBuilder();
        sb.append(String.format("Shelf %d:\n", shelfNumber));
        sb.append(String.format("%-20s%-15s%-20s%-15s\n", "Name", "Checked Out", "Check Out Date", "Checkout UserID"));
        Book current=headBook;
        while (current!=null){
            sb.append(current.getFormattedTable()).append("\n");
            current=current.getNextBook();
        }
        sb.append("Number of books on the shelf: ").append(length);
        return sb.toString();
    }
    public String toString(){
        StringBuilder sb=new StringBuilder();
        Book current=headBook;
        while (current != null) {
            sb.append(current).append("\n");
            current=current.getNextBook();
        }
        sb.append("Number of books on the shelf: ").append(length);
        return sb.toString();
    }
}
