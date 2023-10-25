package a1_2001040121;
import common.Genre;
import common.PatronType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LibraryManProg {
    private static Date[] checkoutDate = new Date[]{
            new Date(2023 - 1900, Calendar.MARCH, 25),
            new Date(2023 - 1900, Calendar.MAY, 8),
            new Date(2023 - 1900, Calendar.JUNE, 1),
            new Date(2023 - 1900, Calendar.JUNE, 25),
            new Date(2023 - 1900, Calendar.AUGUST, 10)
    };

    private static Date[] dueDate = new Date[]{
            new Date(2023 - 1900, Calendar.APRIL, 25),
            new Date(2023 - 1900, Calendar.MAY, 10),
            new Date(2023 - 1900, Calendar.JUNE, 25),
            new Date(2023 - 1900, Calendar.JULY, 25),
            new Date(2023 - 1900, Calendar.SEPTEMBER, 20)
    };

    public static void main(String[] args) throws ParseException {
        LibraryManager libraryManager = new LibraryManager();

        // Initialize at least 10 books in the library collection.
        Book book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", Genre.FICTION, "1925", 5);
        Book book2 = new Book("The Catcher in the Rye", "J.D. Salinger", Genre.NON_FICTION, "1951", 5);
        Book book3 = new Book("The Fault in Our Stars", "John Green", Genre.ROMANCE, "2012", 5);
        Book book4 = new Book("Pride and Prejudice", "Jane Austen", Genre.SCIENCE_FICTION, "1813", 5);
        Book book5 = new Book("To Kill a Mockingbird", "Harper Lee", Genre.FANTASY, "1960", 5);
        Book book6 = new Book("The Book Thief", "Markus Zusak", Genre.THRILLER, "2005", 5);
        Book book7 = new Book("The Chronicles of Narnia", "C.S. Lewis", Genre.BIOGRAPHY, "1956", 5);
        Book book8 = new Book("Animal Farm", "George Orwell", Genre.HISTORY, "1945", 5);
        Book book9 = new Book("Gone with the Wind", "Margaret Mitchell", Genre.SELF_HELP, "1936", 5);
        Book book10 = new Book("The Fault in Our Stars", "John Green", Genre.HORROR, "2012", 5);
        // create 3 premium patrons
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Patron patron1 = new Patron("John Doe", formatter.parse("01/01/2000"), "hello1@gmail.com", "1234567890", PatronType.PREMIUM);
        Patron patron2 = new Patron("Jane Doe", formatter.parse("02/01/2000"), "hello2@gmail.com", "1234567890", PatronType.PREMIUM);
        Patron patron3 = new Patron("John Smith", formatter.parse("03/01/2000"), "hello3@gmail.com", "1234567890", PatronType.PREMIUM);
        // create 3 regular patrons
        Patron patron4 = new Patron("Jane Smith", formatter.parse("04/01/2000"), "hello4@gmail.com", "1234567890", PatronType.REGULAR);
        Patron patron5 = new Patron("John Doe", formatter.parse("05/01/2000"), "hello5@gmail.com", "1234567890", PatronType.REGULAR);
        Patron patron6 = new Patron("Jane Doe", formatter.parse("06/01/2000"), "hello5@gmail.com", "1234567890", PatronType.REGULAR);

        // Initialize and use to create 5 book transactions
        libraryManager.addBook(book1);
        libraryManager.addBook(book2);
        libraryManager.addBook(book3);
        libraryManager.addBook(book4);
        libraryManager.addBook(book5);
        libraryManager.addBook(book6);
        libraryManager.addBook(book7);
        libraryManager.addBook(book8);
        libraryManager.addBook(book9);
        libraryManager.addBook(book10);

        libraryManager.checkOutBook(patron1, book1, checkoutDate[0], dueDate[0]);
        libraryManager.checkOutBook(patron2, book2, checkoutDate[1], dueDate[1]);
        libraryManager.checkOutBook(patron3, book3, checkoutDate[2], dueDate[2]);
        libraryManager.checkOutBook(patron4, book4, checkoutDate[3], dueDate[3]);
        libraryManager.checkOutBook(patron5, book5, checkoutDate[4], dueDate[4]);
        // Print currently checked-out books
        System.out.println("Currently checked-out books:");
        for (LibraryTransaction transaction : libraryManager.getTransactions()) {
            System.out.println(transaction.getBook().getTitle() + " " + transaction.getBook().getISBN() + " " + transaction.getPatron().getName());
        }
        // Patron returns the book
        libraryManager.returnBook(libraryManager.getTransactions().get(0), new Date());


        // Print list of the overdue books that are not returned yet
        System.out.println("Overdue books:");
        for (LibraryTransaction transaction : libraryManager.getOverdueBooks()) {
            System.out.println(transaction.getBook().getTitle() + " " + transaction.getBook().getISBN() + " " + transaction.getPatron().getName());
        }

        // Patron returns the book
        libraryManager.returnBook(libraryManager.getTransactions().get(1), new Date());
        libraryManager.returnBook(libraryManager.getTransactions().get(2), new Date());
        libraryManager.returnBook(libraryManager.getTransactions().get(3), new Date());


        // Sort transactions by patron ID
        libraryManager.sort();
    }
}
