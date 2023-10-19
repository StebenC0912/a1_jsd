import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class LibraryTransaction {
    private Patron patron;
    private Book book;
    private Date checkOutDate;
    private Date dueDate;
    private Date returnDate;
    private double fineAmount;

    public LibraryTransaction(Patron patron, Book book, Date checkOutDate, Date dueDate, Date returnDate) throws ParseException {
        this.patron = patron;
        this.book = book;
        this.checkOutDate = checkOutDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.fineAmount = calculateFine();
    }

    public LibraryTransaction(Patron patron, Book book, Date checkOutDate, Date dueDate) {
        this.patron = patron;
        this.book = book;
        this.checkOutDate = checkOutDate;
        this.dueDate = dueDate;
    }

    public double calculateFine() throws ParseException {
        if (returnDate.before(dueDate)) return 0;
        long diff = TimeUnit.DAYS.convert(Math.abs(returnDate.getTime() - dueDate.getTime()), TimeUnit.MILLISECONDS);
        double price = 0;
        if (diff <= 7) price = 1;
        else if (diff <= 14) price = 2;
        else price = 3;
        return price * diff;
    }

    public Patron getPatron() {
        return patron;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    public Book getBook() {
        return book;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public double getFineAmount() {
        return fineAmount;
    }

    public String getDescription() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM dd YYYY", Locale.ENGLISH);
        return String.format("Transaction Details:\n\tPatron ID: %s\n\tBook ISBN: %s\n\tCheckout Date: %s\n\tDue Date: %s\n\tReturn Date: %s\n\tFine Amount: %,.2f", patron.getPatronID(), book.getISBN(), sdf.format(checkOutDate), sdf.format(dueDate), sdf.format(returnDate), fineAmount);
    }
}
