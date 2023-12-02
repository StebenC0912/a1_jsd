package a1_2001040121;

import common.DateUtils;
import common.PatronType;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LibraryManager {
    private List<Book> books = new ArrayList<>();
    private List<LibraryTransaction> transactions = new ArrayList<>();

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
        // check if the patron has already checked out 5 books
        int count = 0;
        if (transactions != null) {
            for (LibraryTransaction transaction : transactions) {
                if (transaction.getPatron().getPatronID().equals(patron.getPatronID())) {
                    count++;
                }
            }
        }
        if (patron.getType() == PatronType.PREMIUM) {
            if (count >= 5) {
                throw new ParseException("Patron has already checked out 5 books", 0);
            } else {
                transactions.add(new LibraryTransaction(patron, book, checkoutDate, dueDate, null));
                book.setNumCopiesAvailable(book.getNumCopiesAvailable() - 1);
            }
        } else {
            if (count >= 3) {
                throw new ParseException("Patron has already checked out 3 books", 0);
            } else {
                transactions.add(new LibraryTransaction(patron, book, checkoutDate, dueDate, null));
                book.setNumCopiesAvailable(book.getNumCopiesAvailable() - 1);
            }
        }
    }

    public void returnBook(LibraryTransaction transaction, Date returnDate) throws ParseException {
        transaction.setReturnDate(returnDate);
        transaction.setFineAmount(transaction.calculateFine());
        transaction.getBook().setNumCopiesAvailable(transaction.getBook().getNumCopiesAvailable() + 1);
        System.out.println("Book returned successfully");
        System.out.println(transaction.getDescription());
    }

    public List<LibraryTransaction> getOverdueBooks() throws ParseException {
        List<LibraryTransaction> result = new ArrayList<>();
        for (LibraryTransaction transaction : transactions) {
            if (transaction.getReturnDate() == null && transaction.getDueDate().before(new DateUtils().getCurrentDate())) {
                transaction.setReturnDate(new DateUtils().getCurrentDate());
                transaction.setFineAmount(transaction.calculateFine());
                transaction.setReturnDate(null);
                result.add(transaction);
            }
        }
        return result;
    }
    public void sort() {
        transactions.sort((o1, o2) -> o1.getPatron().getPatronID().compareTo(o2.getPatron().getPatronID()));
        System.out.println("Sorted transactions by patron ID");
        for (LibraryTransaction transaction : transactions) {
            System.out.println(transaction.getPatron().getPatronID() + " " + transaction.getBook().getTitle());
        }
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public List<LibraryTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<LibraryTransaction> transactions) {
        this.transactions = transactions;
    }
}
