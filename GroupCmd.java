import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.HashMap;

public class GroupCmd extends LibraryCommand {
    
    private String parameter; /**Will hold the parameter of the command GROUP*/
    
    //----------------CONSTRUCTOR :
    public GroupCmd(String argumentInput) {
        super(CommandType.GROUP, argumentInput);
    }

    //----------------parseArguments FUNCTION :
    @Override
    protected boolean parseArguments(String argumentInput) {
        boolean flag = false;
        if (!argumentInput.equals(null)){
            if ((argumentInput.equals("TITLE") || argumentInput.equals("AUTHOR")) 
               && (argumentInput.length() <= 6)) {
                  flag = true;
                  parameter = argumentInput;
            }
        }
        return flag;
    }

    /**
     * This helper function creates a string array from the titles of the 
     * books in the library and sorts it alphabetically.
     * @param books the list containing all the book entries in the library
     * @return a string array with the titles of the books in alphabetical order
     */
    public static String[] alphabeticalOrderTitle (List<BookEntry> books) {
        String[] titles = new String[books.size()];
        String temp;
        for (int i = 0; i < books.size(); i++) {
            titles[i] = books.get(i).getTitle();
        }
        //using hashmap so that first letters of titles can be compared based on integer value assigned to them
        //this is helpful mostly because we want digits to be after letters and that doesn't happen using ascii values
        HashMap<Character, Integer> values = new HashMap<Character, Integer>();
        for (int i = 0; i < 26; i++) {
            char firstLetter = (char)('A' + i);
            values.put(firstLetter, i);
        }
        for (int i = 0; i < 9; i++) {
            char firstDigit = (char)('1' + i);
            values.put(firstDigit, 26 + i);
        }
        //bubble sort
        for (int i = 0; i < titles.length; i++){ 
            for (int j = i + 1; j < titles.length; j++){
                if (values.get(Character.toUpperCase(titles[i].charAt(0))) > 
                   values.get(Character.toUpperCase(titles[j].charAt(0)))){
                      temp = titles[i];
                      titles[i] = titles[j];
                      titles[j] = temp;
                }
            }
        }
        return titles;
    }

    /**
     * This helper function creates an array whose elements are all the 
     * author names in the library sorted in alphabetical order.
     * @param books the list containing all the book entries in the library
     * @return a list with the author names in alphabetical order.
     */
    public static List<String> getAllAuthors(List<BookEntry> books) {
        List<String> allAuthors = new ArrayList<>();
        for (int i = 0; i < books.size(); i++) {
            String[] authors = books.get(i).getAuthors();
            for (int j = 0; j < authors.length; j++) {
                allAuthors.add(authors[j]);
            }
        }
        allAuthors = allAuthors.stream().distinct().collect(Collectors.toList());
        Collections.sort(allAuthors);
        return allAuthors;
    }

    /**
     * This helper function checks if an element exists in a string array.
     * @param array the string array
     * @param element the string we're looking for
     * @return true if element exists, false otherwise
     */
    public static boolean elementExists(String[] array, String element) {
        boolean flag = false;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(element)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    //----------------execute FUNCTION :
    @Override
    public void execute(LibraryData data){
        List<BookEntry> books = data.getBookData();
        //CASE THAT LIBRARY IS EMPTY:
        if (data.getBookData().size() == 0) {
            System.out.println("The library has no book entries.");
        }
        //CASE THAT WE WANT TO GROUP BY TITLE:
        else if (parameter.equals("TITLE")) {
            System.out.println("Grouped data by " + parameter);
            List<String> titles = Arrays.asList(alphabeticalOrderTitle(books));
            char firstLetter = titles.get(0).charAt(0);
            //printing the header for the first letter in the library
            if (Character.isDigit(firstLetter)) {
                System.out.println("## [0-9]");
            }
            else {
                System.out.println("## " + Character.toString(Character.toUpperCase(firstLetter)));
            }
            for (int i = 0; i < titles.size(); i++) {
                //always checking if we've moved to next letter in library, thus if we need to print new header
                if (titles.get(i).charAt(0) != firstLetter) {
                    firstLetter = titles.get(i).charAt(0);
                    if (Character.isDigit(firstLetter)){
                        System.out.println("## [0-9]");
                    }
                    else {
                        System.out.println("## " + Character.toString(Character.toUpperCase(firstLetter)));
                    }
                }
                System.out.println("    " + titles.get(i));
            }
        }
        //CASE THAT WE WANT TO GROUP BY AUTHOR:
        else if (parameter.equals("AUTHOR")) {
            System.out.println("Grouped data by " + parameter);
            List<String> allAuthors = getAllAuthors(books);
            for (int i = 0; i < allAuthors.size(); i++) {
                String author = allAuthors.get(i);
                System.out.println("## " + author);
                //checking if "author" exists in the specific book's list of authors. If yes print it.
                for (int j=0; j<books.size(); j++) {
                    String[] specBookAuthors = books.get(j).getAuthors();
                    if(elementExists(specBookAuthors,author)){
                        System.out.println("    " + books.get(j).getTitle());
                    }
                }
            }
        }
    }
}  