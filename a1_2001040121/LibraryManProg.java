package a1_2001040121;
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

    public static void main(String[] args) {
        LibraryManager libraryManager = new LibraryManager();

        // Initialize at least 10 books in the library collection.



        // Initialize at least 3 patrons involving both regular and premium patrons.


        // Initialize and use to create 5 book transactions


        // Print currently checked-out books


        // Patron returns the book


        // Print list of the overdue books that are not returned yet


        // Patron returns the book



        // Sort transactions by patron ID

    }
}
