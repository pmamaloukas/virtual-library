import java.util.ArrayList;
import java.util.List;

public class RemoveCmd extends LibraryCommand {
    
    private String argumentToRemove; /**Will hold the title to be removed if it's valid*/
    private String parameter;        /**Will hold the parameter for the REMOVE command*/

    //----------------CONSTRUCTOR :
    public RemoveCmd(String argumentInput) {
        super(CommandType.REMOVE, argumentInput);
    }

    //-----------------parseArguments FUNCTION :
    protected boolean parseArguments(String argumentInput) {
        boolean flag = false;
        if (argumentInput.length() > 0){
            String firstWord = argumentInput.substring(0, 6);
            if ((firstWord.equals("TITLE ") || firstWord.equals("AUTHOR")) 
               && (argumentInput.length() > 7)) {
                  flag = true;
                  argumentToRemove = argumentInput;
                  parameter = firstWord;
            }
        }
        return flag;
    }

    //----------------execute FUNCTION : 
    @Override
    public void execute(LibraryData data) {
        List<BookEntry> books = data.getBookData();
        //CASE THAT WE WANT TO REMOVE A SPECIFIC TITLE:
        if (parameter.equals("TITLE ")) {
            String name = argumentToRemove.substring(6);
            int index = -1;
            for (int i = 0; i < books.size(); i++) {
                if (name.equals(books.get(i).getTitle())){
                    index = i;
                    break;
                }
            }
            if (index >= 0) {
                books.remove(index);
                System.out.println(name + ": removed successfully.");
            }
            else {
                System.out.println(name + ": not found.");
            }
        }
        //CASE THAT WE WANT TO REMOVE AN AUTHOR:
        else if (parameter.equals("AUTHOR")) {
            String name = argumentToRemove.substring(7);
            List<Integer> indicesToRemove = new ArrayList<>();
            for (int i = 0; i < books.size(); i++) {
                String[] authors = books.get(i).getAuthors();
                for (int j = 0; j < authors.length; j++) {
                    if (name.equals(authors[j])) {
                        indicesToRemove.add(i);
                    }
                }
            }
            System.out.println(Integer.toString(indicesToRemove.size()) + " books removed for author: " + name);
            if (indicesToRemove.size() > 0) {
                int plus = 0;
                for (int i = 0; i < indicesToRemove.size(); i++) {
                    int index = indicesToRemove.get(i);
                    books.remove(index - plus);
                    plus++;
                }
            }
        }
    }
}
