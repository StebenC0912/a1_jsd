import common.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class LibraryManProg {
    public static void main(String[] args) throws ParseException {
        // Create 10 books
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


        
    }
}
