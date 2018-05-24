import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InputHandler {

    public Grammar initFile(String path) {
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String readLine;
            List<Role> roles = new ArrayList<>();
            readLine = bufferedReader.readLine();
            while (readLine != null) {
                int flagType = 0;
                if (CF_Handler.isVariable(readLine.charAt(0))) {
                    Role temp = new Role(readLine.charAt(0), readLine.substring(2));
                    for (int i = 0 ; i < temp.getRightSide().length() ; i++) {
                        if (CF_Handler.isVariable(temp.getRightSide().charAt(i))) {
                            flagType ++;
                        }
                        else
                            flagType --;
                    }
                    if (flagType == temp.getRightSide().length())
                        temp.setRoleType(RoleType.VARIABLE);
                    else if (flagType == - temp.getRightSide().length())
                        temp.setRoleType(RoleType.TERMINAL);
                    else
                        temp.setRoleType(RoleType.VARIABLE_TERMINAL);
                    roles.add(temp);
                }
                else {
                    System.err.println("Input Error: Variable is not Correct!");
                    System.exit(1);
                }
                readLine = bufferedReader.readLine();
            }
            Grammar g = new Grammar(roles);
            g.setVariable();
            return g;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        InputHandler a = new InputHandler();
        Grammar grammar = a.initFile("test.txt");
        System.out.println(CF_Handler.unitReduction(grammar));
    }

}
