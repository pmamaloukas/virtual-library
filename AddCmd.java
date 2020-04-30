import java.nio.file.Path;
import java.nio.file.Paths;

public class AddCmd extends LibraryCommand {

    private Path path;

    //----------------CONSTRUCTOR :

    public AddCmd (String argumentInput) {
        super(CommandType.ADD, argumentInput);
    }
    
    //----------------parseArguments FUNCTION :
    @Override
    protected boolean parseArguments (String argumentInput) {
        String validFileType = ".csv";
        String fileType;
        boolean flag;
        if (argumentInput.length() > validFileType.length()) {
            fileType = argumentInput.substring(argumentInput.length() - validFileType.length());
            if (fileType.equals(validFileType)) {
                path = Paths.get(argumentInput);
                System.out.println(path);
                flag = true;
            }
            else {
                flag = false;
            }
        }
        else {
            flag = false;
        }
        return flag;
    }

    //----------------EXECUTE METHOD : 
    @Override 
    public void execute(LibraryData data) throws NullPointerException {
        if (data.equals(null)) {
            throw new NullPointerException ("argument can't be null");
        }
        data.loadData(path);
    }
}