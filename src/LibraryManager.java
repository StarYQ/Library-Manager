import java.util.Scanner;
public class LibraryManager {
    private static BookRepository bookRepository;
    private static ReturnStack returnStack;
    public static void main(String[] args){
        bookRepository=new BookRepository(); //Initializing new empty bookRepository
        returnStack=new ReturnStack(); //Initializing new empty returnStack
        Scanner sc=new Scanner(System.in);
        System.out.println("Starting...\n");
        while (true){
            printMenu();
            char option = sc.next().charAt(0); //To only take the next single letter input as an option
            sc.nextLine();
            switch (Character.toUpperCase(option)){
                case 'B': //Because there are so many sub-options for options B and R,I broke it up so that
                    //I have one method to manage the options that involve the bookRepository LinkedList and another to
                    //manage those involving the returnStack stack
                    manageBookRepository(sc);
                    break;
                case 'R':
                    manageReturnStack(sc);
                    break;
                case 'Q':
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);
                    break;
                default: //Printed if B, R, or Q aren't entered, which should mean it is an invalid option
                    System.out.println("Invalid option. Please enter a valid option.");
                    break;
            }
        }
    }
    private static void printMenu(){ //Following the sample I/O format to reprint the menu for every input
        System.out.println("Menu:");
        System.out.println("\n(B) - Manage Book Repository");
        System.out.println("    (C) - Checkout Book");
        System.out.println("    (N) - Add New Book");
        System.out.println("    (R) - Remove Book");
        System.out.println("    (P) - Print Repository");
        System.out.println("    (S) - Sort Shelf");
        System.out.println("        (I) - ISBN Number");
        System.out.println("        (N) - Name");
        System.out.println("        (A) - Author");
        System.out.println("        (G) - Genre");
        System.out.println("        (Y) - Year");
        System.out.println("        (C) - Condition");
        System.out.println("(R) - Manage Return Stack");
        System.out.println("    (R) - Return Book");
        System.out.println("    (L) - See Last Return");
        System.out.println("    (C) - Check In Last Return");
        System.out.println("    (P) - Print Return Stack");
        System.out.println("(Q) - Quit");
        System.out.println(
                "-----------------------------------------------------------------------------------");
        System.out.print("Please select what to manage:");
    }
    //Made following methods due to how many possible inputs there are to manage, as mentioned before
    //manageBookRepository below manages sup-options C, N, R, P, and S of option B, as well as the sub-options of
    //option S
    //Each sub-option has a separate method farther down this class, each having additional scanner input(s)
    private static void manageBookRepository(Scanner sc){
        System.out.print("Please select an option:");
        char option=sc.next().charAt(0);
        sc.nextLine();
        switch (Character.toUpperCase(option)){
            case 'C':
                checkoutBook(sc);
                break;
            case 'N':
                addNewBook(sc);
                break;
            case 'R':
                removeBook(sc);
                break;
            case 'P':
                printRepository();
                break;
            case 'S':
                sortShelf(sc);
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }
    //manageReturnStack below manages sub-options R, L , C, and P
    private static void manageReturnStack(Scanner sc){
        System.out.print("Please select an option:");
        char option=sc.next().charAt(0);
        sc.nextLine();
        switch (Character.toUpperCase(option)){
            case 'R':
                returnBook(sc);
                break;
            case 'L':
                seeLastReturn();
                break;
            case 'C':
                checkInLastReturn();
                break;
            case 'P':
                printReturnStack();
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }
    //addNewBook prompts the user inputs for isbn, book name, author, genre, and year of publication
    private static void addNewBook(Scanner sc){
        try {
            System.out.print("Please provide an ISBN number:");
            long isbn=sc.nextLong();
            sc.nextLine();
            System.out.print("Please provide a name:");
            String name=sc.nextLine();
            System.out.print("Please provide an author:");
            String author=sc.nextLine();
            System.out.print("Please provide a genre:");
            String genre=sc.nextLine();
            System.out.print("Please provide a condition:");
            String condition=sc.nextLine();
            System.out.print("Please provide the year published:");
            int yearPublished=sc.nextInt();
            bookRepository.addBook(name, author, genre, yearPublished, Condition.valueOf(condition.toUpperCase()), isbn);
            System.out.println("\nLoading...\n");
            System.out.println(name+" has been successfully added to the book repository!\n");
        }
        catch (Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }
    private static void removeBook(Scanner sc){
        try {
            System.out.print("Please provide an ISBN Number:");
            long isbn=sc.nextLong();
            sc.nextLine();
            bookRepository.removeBook(isbn);
            System.out.println("\nLoading...\n");
            System.out.println("Book with ISBN "+isbn+" has been successfully removed from the book repository!\n");
        } catch (Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    private static void checkoutBook(Scanner sc){
        try{
            System.out.print("Please provide a library user ID:");
            long userID=sc.nextLong();
            sc.nextLine();
            System.out.print("Please provide an ISBN Number:");
            long isbn=sc.nextLong();
            sc.nextLine();
            System.out.print("Please provide a due date (MM/dd/yyyy):");
            String dueDateStr=sc.nextLine();
            String[] dateParts=dueDateStr.split("/"); //To get the month, day, and year from the entered date
            int month=Integer.parseInt(dateParts[0]);
            int day=Integer.parseInt(dateParts[1]);
            int year=Integer.parseInt(dateParts[2]);
            Date dueDate = new Date(month, day, year);
            bookRepository.checkOutBook(isbn, userID, dueDate);
            System.out.println("\nLoading...\n");
            System.out.println("Book with ISBN "+isbn+" has been checked out by "+userID+" and must be returned by "+dueDateStr+".\n");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private static void printRepository(){
        System.out.println("\nLoading...\n");
        System.out.println(bookRepository.toString());
        System.out.println();
    }
    private static void sortShelf(Scanner sc){
        System.out.print("Please select a shelf: ");
        int shelfIndex=sc.nextInt();
        sc.nextLine();
        System.out.print("Please select a sorting criteria (I - ISBN, N - Name, A - Author, G - Genre, Y - Year, C - Condition):");
        String sortCriteriaStr=sc.next().toUpperCase();
        sc.nextLine();
        String criteria = "";
        //Accepts a criteria entered by case and tries to sort it - if a valid criteria to sort by isn't entered,
        //should mean it's an invalid input
        switch (sortCriteriaStr){
            case "I":
                criteria="ISBN";
                break;
            case "N":
                criteria="NAME";
                break;
            case "A":
                criteria="AUTHOR";
                break;
            case "G":
                criteria="GENRE";
                break;
            case "Y":
                criteria="YEAR";
                break;
            case "C":
                criteria="CONDITION";
                break;
            default:
                System.out.println("Invalid sorting criteria.");
                return;
        }
        try {
            bookRepository.sortShelf(shelfIndex, criteria);
            System.out.println("\nLoading...\n");
            System.out.println("Shelf "+shelfIndex+" has been sorted by "+criteria+"!");
        }
        catch (InvalidSortCriteriaException e){
            System.out.println("Error: "+e.getMessage());
        }
    }
    private static void returnBook(Scanner sc){
        try {
            System.out.print("Please provide the ISBN of the book you're returning:");
            long isbn=sc.nextLong();
            sc.nextLine();
            System.out.print("Please provide your Library UserID:");
            long userId=sc.nextLong();
            sc.nextLine();
            System.out.print("Please provide your current date (MM/dd/yyyy):");
            String returnDateStr=sc.nextLine();
            String[] dateParts=returnDateStr.split("/");
            int month=Integer.parseInt(dateParts[0]);
            int day=Integer.parseInt(dateParts[1]);
            int year=Integer.parseInt(dateParts[2]);
            Date returnDate=new Date(month, day, year);
            // Call the ReturnStack's pushLog method to handle returning the book
            boolean isLate=returnStack.pushLog(isbn, userId, returnDate, bookRepository);
            String bookname=bookRepository.findBookByISBN(isbn).getName();
            System.out.println("\nLoading...\n");
            if (isLate){
                System.out.println(bookname+" has been returned LATE! Checking everything in...");
            }
            else {
                System.out.println(bookname+" has been returned on time!");
            }
        }
        catch (Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }
    private static void seeLastReturn(){
        ReturnLog lastReturn=null;
        try {
            lastReturn=returnStack.peekLog();
        }
        catch (StackIsEmptyException e){
            System.out.println("Error: "+e.getMessage());
        }

        if (lastReturn!=null){
            long isbn=lastReturn.getISBN();
            System.out.println("\nLoading...\n");
            System.out.println(bookRepository.findBookByISBN(isbn).getName()+" is the next book to be checked in.");
        }
        else{
            System.out.println("No books have been returned yet.");
        }
    }
    private static void checkInLastReturn(){
        try{
            ReturnLog lastReturn = returnStack.peekLog();
            System.out.println("\nLoading...\n");
            long returnISBN=lastReturn.getISBN();
            try {
                bookRepository.checkInBook(returnISBN);
            }
            catch (BookNotFoundException e){
                System.out.println("Error: " + e.getMessage());
            }
        }
        catch (StackIsEmptyException e){
            System.out.println(e.getMessage());
        }
    }
    private static void printReturnStack(){
        System.out.println("\nLoading...\n");
        //Simply using returnStack's toString method to print the stack here in tabular form
        String returnStackContent=returnStack.toString();
        System.out.println(returnStackContent);
    }

}