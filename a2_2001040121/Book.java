package a2_2001040121;

import common.Genre;

public class Book {
    private String ISBN;
    private String title;
    private String author;
    private Genre genre;
    private String publicationYear;
    private int NumCopiesAvailable;

    public void generateISBN() {
        String ISBN = "";
        for (char character: author.toCharArray()) {
            if (character >= 65 && character <= 90) {
                ISBN+= Character.toString(character);
            }
        }
        int i = 0;
        for (Genre genre : Genre.values()) {
            if (genre == this.genre) {
                if (i < 10) ISBN += "-0"+ Integer.toString(i + 1);
                else ISBN += "-" + Integer.toString(i + 1);
            }
            i++;
        }
        ISBN += "-" + this.publicationYear;
        this.ISBN = ISBN;
    }

    public Book(String ISBN, String title, String author, Genre genre, String publicationYear, int numCopiesAvailable) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
        NumCopiesAvailable = numCopiesAvailable;
    }

    public Book(String title, String author, Genre genre, String publicationYear, int available) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.NumCopiesAvailable = available;
        generateISBN();
    }

    public int getAvailable() {
        return NumCopiesAvailable;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Genre getGenre() {
        return genre;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public int getNumCopiesAvailable() {
        return NumCopiesAvailable;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setNumCopiesAvailable(int numCopiesAvailable) {
        NumCopiesAvailable = numCopiesAvailable;
    }

    @Override
    public String toString() {
        return "LibraryBooks{" +
                "ISBN='" + ISBN + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", publicationYear=" + publicationYear +
                ", available=" + NumCopiesAvailable +
                '}';
    }
}
