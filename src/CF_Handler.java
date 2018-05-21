import java.util.ArrayList;
import java.util.List;

public class CF_Handler {

    public static boolean isVariable(char a) {
        if ( (int)a >= 65 && (int)a <= 90)
            return true;
        else return false;
    }

    public static boolean isTerminal(char a) {
        if ( (int)a >= 97 && (int)a <= 122)
            return true;
        else return false;
    }

    public static boolean isLambdaTransition(Role role) {
        if (role.getRightSide().equals("%"))
            return true;
        else
            return false;
    }

    public static Grammar lambdaReduction(Grammar grammar) {
        List<Character> nullableVar = new ArrayList<>();
        List<Role> newRoles = new ArrayList<>();
        //Add pure nullable Variable in list: A => %
        for (Role role : grammar.getGrammar()) {
            if (isLambdaTransition(role))
                nullableVar.add(role.getLeftSide());
        }
        //Add None_pure nullable Variable in list A => BCD
        for (Role role : grammar.getGrammar()) {
            if (!nullableVar.contains(role.getLeftSide()) && role.getRoleType() == RoleType.VARIABLE) {
                boolean isLambda = true;
                for (int i = 0 ; i < role.getRightSide().length() ; i++){
                    if (!nullableVar.contains(role.getRightSide().charAt(i))) {
                        isLambda = false;
                        break;
                    }
                }
                if (isLambda)
                    nullableVar.add(role.getLeftSide());
            }
        }

        /*for (Character c : nullableVar)
            System.out.println(c);*/

        for (Role role : grammar.getGrammar()) {
            if (!CF_Handler.isLambdaTransition(role)){
                List<Integer> nullVariableIndex = new ArrayList<>();
                for (int i = 0 ; i < role.getRightSide().length() ; i++){
                    if (nullableVar.contains(role.getRightSide().charAt(i)))
                        nullVariableIndex.add(i);
                }
                for (int i = 0 ; i <= Math.pow(2, nullVariableIndex.size()) ; i++) {
                    String binaryPattern = Integer.toBinaryString(i);
                    while (binaryPattern.length() != nullVariableIndex.size())
                        binaryPattern = "0" + binaryPattern;
                    StringBuilder rightSideRole = new StringBuilder(role.getRightSide());
                    for (int j = 0 ; j < nullVariableIndex.size() ; j++) {
                        if (binaryPattern.charAt(j) == '0') {
                            System.out.println("Deleted: " + rightSideRole.charAt(nullVariableIndex.get(j)));
                            rightSideRole.deleteCharAt(nullVariableIndex.get(j));
                        }
                    }
                    System.out.println(rightSideRole.toString());
                }
            }
        }

        return new Grammar(newRoles);
    }
}


