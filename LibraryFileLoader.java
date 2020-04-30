import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** 
 * Class responsible for loading
 * book data from file.
 */
public class LibraryFileLoader {

    /**
     * Contains all lines read from a book data file using
     * the loadFileContent method.
     * 
     * This field can be null if loadFileContent was not called
     * for a valid Path yet.
     * 
     * NOTE: Individual line entries do not include line breaks at the 
     * end of each line.
     */
    private List<String> fileContent;

    /** Create a new loader. No file content has been loaded yet. */
    public LibraryFileLoader() { 
        fileContent = null;
    }

    /**
     * Load all lines from the specified book data file and
     * save them for later parsing with the parseFileContent method.
     * 
     * This method has to be called before the parseFileContent method
     * can be executed successfully.
     * 
     * @param fileName file path with book data
     * @return true if book data could be loaded successfully, false otherwise
     * @throws NullPointerException if the given file name is null
     */
    public boolean loadFileContent(Path fileName) {
        Objects.requireNonNull(fileName, "Given filename must not be null.");
        boolean success = false;

        try {
            fileContent = Files.readAllLines(fileName);
            success = true;
        } catch (IOException | SecurityException e) {
            System.err.println("ERROR: Reading file content failed: " + e);
        }

        return success;
    }

    /**
     * Has file content been loaded already?
     * @return true if file content has been loaded already.
     */
    public boolean contentLoaded() {
        return fileContent != null;
    }

    /**
     * This helper function creates a string array whose elements are the 
     * dash separated names of the authors.
     * @param entry the string with all the dash separated names
     * @return a string array with the names of the authors as separate elements
     */
    public static String[] separateAuthors(String entry) {
        String element;
        List<String> authors = new ArrayList<>();
        int start = 0; 
        for (int i = 0; i < entry.length(); i++) {
            if (entry.charAt(i) == '-') {
                int temp = start; //saving initial value of start
                element = entry.substring(start, entry.indexOf('-', start));
                authors.add(element);
                start = entry.indexOf('-', temp) + 1;//now we only look at the remaining names in the string, i.e. only after 
                                                     //the dash we just checked
            }
        }
        authors.add(entry.substring(start, entry.length()));//no more dashes, so just add the final name
        String[] rAuthors = new String[authors.size()];
        authors.toArray(rAuthors);
        return rAuthors;
    }

    /**
     * This helper function creates a list whose elements are the comma 
     * separated values from the elements of fileContent. It is similar
     * in structure to separateAuthors.
     */
    public static List<String> separateInstances(String entry){
        String element;
        List<String> elements = new ArrayList<>();
        int start = 0; 
        for (int i = 0; i < entry.length(); i++) {
            if (entry.charAt(i) == ',') {
                int temp = start; 
                element = entry.substring(start, entry.indexOf(',', start));
                elements.add(element);
                start = entry.indexOf(',', temp) + 1;
            }
        }
        elements.add(entry.substring(start,entry.length()));
        return elements;
    }

    /**
     * Parse file content loaded previously with the loadFileContent method.
     * 
     * @return books parsed from the previously loaded book data or an empty list
     * if no book data has been loaded yet.
     * @throws UnsupportedOperationException Not implemented yet!
     */
    public List<BookEntry> parseFileContent() {
        List<BookEntry> returnList = new ArrayList<>();
        String title;
        String[] authors;
        float rating;
        String ISBN;
        int pages;
        if (!contentLoaded()) {
            System.err.println("ERROR: No content loaded before parsing.");
        }
        else {
            for (int i = 1; i < fileContent.size(); i++) {
                List<String> newEntry = separateInstances(fileContent.get(i));
                title = newEntry.get(0);
                authors = separateAuthors(newEntry.get(1));
                rating = Float.parseFloat(newEntry.get(2));
                ISBN = newEntry.get(3);
                pages = Integer.parseInt(newEntry.get(4));
                BookEntry newBook = new BookEntry(title, authors, rating, ISBN, pages);
                returnList.add(newBook);
            }
        } 
        return returnList;
    }
}
