import java.util.List;

public class ListCmd extends LibraryCommand {

    private String command; /**will store parameter for LIST command if it's valid*/

    //----------------CONSTRUCTOR :
    public ListCmd(String argumentInput) {
        super(CommandType.LIST, argumentInput);
    }

    //----------------parseArguments FUNCTION :
    @Override
    protected boolean parseArguments(String argumentInput) {
        boolean flag;
        if (argumentInput.equals("long") || argumentInput.equals("short") || argumentInput.equals("")) {
            command = argumentInput;
            flag = true;
        }
        else {
            flag = false;
        }
        return flag;
    }

    //----------------execute FUNCTION :
    @Override
    public void execute(LibraryData data) throws NullPointerException {
        if (data.equals(null)) {
            throw new NullPointerException ("argument can't be null");
        }
        List<BookEntry> books = data.getBookData();
        System.out.println(Integer.toString(books.size()) + " books in library:");
        for (int i = 0; i < books.size(); i++ ) {
            if (command.equals("short") || command.equals("")) {
                System.out.println(books.get(i).getTitle());
            }
            else if (command.equals("long")) {
                System.out.println(books.get(i).toString() + "\n");
            }
        }
    }
}