import java.lang.String;

public class BookEntry {

    private final String title;
    private final String[] authors;
    private final float rating;
    private final String ISBN;
    private final int pages;

    /** ----------------CONSTRUCTOR :
     * @throws NullPointerException, 
     * @throws IllegalArgumentException
     */
    public BookEntry(String title, String[] authors, float rating, String ISBN, int pages) {
        this.title = title;
        this.authors = authors;
        this.rating = rating;
        this.ISBN = ISBN;
        this.pages = pages;

        if (title.equals(null) || authors.equals(null) || ISBN.equals(null)) {
            throw new NullPointerException("instance fields can't be null");
        }
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("rating must be between 0 and 5");
        }
        if (pages < 0) {
            throw new IllegalArgumentException("page number can't be negative");
        }
    }

    // --------------------GETTERS:
    public String getTitle() {
        return title;
    }

    public String[] getAuthors() {
        return authors;
    }

    public float getRating() {
        return rating;
    }

    public String getISBN() {
        return ISBN;
    }

    public int getPages() {
        return pages;
    }


    /**
     * This helper function returns a string that contains all the elements of the array 'authors' for some BookEntry object.
     * For example, {"Author A", "Author B"} -> "by Author A, Author B".
     * @param authors the string array containing the authors for a specific book entry
     * @return a string with the author names separated by commas
     */
    public String toStringAuthors(String[] authors) {

        String output = "by ";
        for (int i = 0; i < authors.length - 1; i++) {
            output += (authors[i] + ", ");
        }
        output += authors[authors.length - 1]; // adding the last one separately so we don't get the extra comma at the end 
        return output;
    }

    // ---------------------toString FUNCTION :
    @Override
    public String toString() {

        String objTitle = getTitle();
        String[] objAuthors = getAuthors();
        String objRating = String.valueOf(String.format("%.2f",getRating())); 
        String objISBN = getISBN();
        String objPages = String.valueOf(getPages());

        String output = objTitle + "\n" + toStringAuthors(objAuthors) + "\nRating: " + objRating + "\nISBN: " + objISBN
                + "\n" + objPages + " pages";

        return output;
    }

    // ---------------------equals FUNCTION:
    /** 
     * first checking if they are the same object, then if they are the same type
     * and then checking every individual instance
     */
    @Override
    public boolean equals(Object obj) {
        boolean flag = false;

        try {
            if (this == obj) {
                flag = true;
            } 
            else if (this.getClass() == obj.getClass()) {
                BookEntry bk = (BookEntry) obj; 
                if (bk.getTitle() == this.getTitle() 
                   && bk.getAuthors() == this.getAuthors() 
                   && bk.getRating() == this.getRating() 
                   && bk.getISBN() == this.getISBN() 
                   && bk.getPages() == this.getPages()) {
                        
                    flag = true;
                }
            }
            return flag;
        }
        catch (NullPointerException n) {
            return false;
        }
    }

    // --------------------hashCode FUNCTION:
    @Override
    public int hashCode() {
        //sum of ascii values of characters in title and name of authors
        int sumTitle = 0;
        for (int i=0; i<this.getTitle().length(); i++){
            sumTitle += this.getTitle().charAt(i);
        }
        int sumAuthors = 0;
        String[] authors = this.getAuthors();
        for (int i=0; i<authors.length; i++) {
            for (int j=0; j<authors[i].length(); j++) {
                sumAuthors += authors[i].charAt(j);
            }
        }

        int thisISBN = this.getISBN().length();
        int intRating = (int) this.getRating();
        return (sumTitle * sumAuthors * intRating * thisISBN * this.getPages());
    }
}
