import common.PatronType;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LibraryManager {
    private List<Book> books;
    private List<LibraryTransaction> transactions;

    public void addBook(Book book) {
        books.add(book);
    }
    public List<LibraryTransaction> getCheckedOutBooks(Patron patron) {
        List<LibraryTransaction> result = new ArrayList<>();
        for (LibraryTransaction transaction : transactions) {
            if (transaction.getPatron().getPatronID().equals(patron.getPatronID())) {
                result.add(transaction);
            }
        }
        return result;
    }
    public void checkOutBook(Patron patron, Book book, Date checkoutDate, Date dueDate) throws ParseException {
        if (patron.getType() == PatronType.PREMIUM) {
            transactions.add(new LibraryTransaction(patron, book, checkoutDate, dueDate, null));
        } else {
            transactions.add(new LibraryTransaction(patron, book, checkoutDate, dueDate, null));
        }
    }
    public void returnBook(LibraryTransaction transaction, Date returnDate) throws ParseException {
        transaction.setReturnDate(returnDate);
        
    }
    public List<LibraryTransaction> getOverdueBooks() throws ParseException {
        List<LibraryTransaction> result = new ArrayList<>();
        for (LibraryTransaction transaction : transactions) {
            if (transaction.getReturnDate() == null && transaction.calculateFine() > 0) {
                result.add(transaction);
            }
        }
        return result;
    }
}
