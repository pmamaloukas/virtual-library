import java.util.ArrayList;
import java.util.List;

public class SearchCmd extends LibraryCommand {

    private String searchValue; /**will store the user's input search value if it's valid*/
    
    //----------------CONSTRUCTOR :
    public SearchCmd(String argumentInput) {
        super(CommandType.SEARCH, argumentInput);
    }

    /**
     * This helper function checks if a given string consists of only one word.
     * @param argument the string we want to check
     * @return true if the string is one word, false otherwise.
     */
    public static boolean oneWord(String argument){
        boolean flag = true;
        for (int i = 0; i < argument.length(); i++) {
            if ((argument.charAt(i) == ' ') && (i < argument.length() - 1)) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    //----------------parseArguments FUNCTION :
    @Override
    protected boolean parseArguments(String argumentInput) {
        try {
            boolean flag = false;
            if (argumentInput.length() > 0 && oneWord(argumentInput)) {
                flag = true;
                searchValue = argumentInput;
            }
            return flag;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * This helper function creates a list whose elements are the 
     * separate words of a given book title. Its structure is similar 
     * to LibraryFileLoader.separateAuthors().
     * @param entry the given string that needs to be split.
     * @return a list that contains all the separate words as elements.
     */
    public static List<String> separateTitleWords(String entry){
        String word;
        List<String> titleWords = new ArrayList<>();
        int start=0; 
        for (int i = 0; i < entry.length(); i++) {
            if (entry.charAt(i) == ' ') {
                int temp = start; 
                word = entry.substring(start, entry.indexOf(' ', start));
                titleWords.add(word);
                start = entry.indexOf(' ', temp) + 1;
            }
        }
        titleWords.add(entry.substring(start, entry.length()));
        return titleWords;
    }

    //----------------execute FUCTION :
    @Override
    public void execute (LibraryData data) {
        List<BookEntry> books = data.getBookData();
        List<String> printTitles = new ArrayList<>();
        for (int i = 0; i < books.size(); i++) {
           String title = books.get(i).getTitle();
           List<String> titleWords = separateTitleWords(title);
           for (int j = 0; j < titleWords.size(); j++) {
               if ((searchValue.toLowerCase()).equals((titleWords.get(j)).toLowerCase())) { 
                   printTitles.add(title);                                                 
                   break;
               }
           }
        }
        if (printTitles.size() == 0) {
            System.out.println("No hits found for search term: " + searchValue);
        }
        else {
            for (int i = 0; i < printTitles.size(); i++) {
                System.out.println(printTitles.get(i));
            }
        }
    }
}