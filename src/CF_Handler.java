import java.util.ArrayList;
import java.util.List;

public class CF_Handler {

    public static boolean isVariable(char a) {
        if ((int)a >= 65 && (int)a <= 90)
            return true;
        else return false;
    }

    public static boolean isTerminal(char a) {
        if ((int)a >= 97 && (int)a <= 122)
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

        //Create new Role
        for (Role role : grammar.getGrammar()) {
            if (!CF_Handler.isLambdaTransition(role)){
                List<Integer> nullVariableIndex = new ArrayList<>();
                for (int i = 0 ; i < role.getRightSide().length() ; i++){
                    if (nullableVar.contains(role.getRightSide().charAt(i)))
                        nullVariableIndex.add(i);
                }
                for (int i = 0 ; i < Math.pow(2, nullVariableIndex.size()) ; i++) {
                    String binaryPattern = Integer.toBinaryString(i);
                    while (binaryPattern.length() < nullVariableIndex.size())
                        binaryPattern = "0" + binaryPattern;
                    StringBuilder rightSideRole = new StringBuilder(role.getRightSide());
                    for (int j = nullVariableIndex.size() - 1 ; j >= 0  ; j--) {
                        if (binaryPattern.charAt(j) == '0') {
                            rightSideRole.deleteCharAt(nullVariableIndex.get(j));
                        }
                    }
                    if (rightSideRole.length() != 0)
                        newRoles.add(new Role(role.getLeftSide(), rightSideRole.toString()));
                }

            }
        }
        return new Grammar(newRoles).resetGrammarType();
    }

    public static Grammar uselessSymbolReduction(Grammar grammar) {
        List<Role> newRole = new ArrayList<>();
        List<Character> reachableVariable = new ArrayList<>();
        checkReachable(grammar, grammar.getStartVar(), reachableVariable);
        for (Character c : grammar.getVariableList()){
            if (!reachableVariable.contains(c))
                grammar.deleteRole(c);
        }

        System.out.println(grammar);
        return new Grammar(newRole);
    }

    private static void checkReachable(Grammar grammar, Character V, List<Character> reachableVariable) {
        if (!grammar.getVariableList().contains(V))
            return;
        if (!reachableVariable.contains(V)) {
            List<Character> child = new ArrayList<>();
            reachableVariable.add(V);
            for (Role role : grammar.getGrammar()) {
                if (role.getLeftSide() == V) {
                    for (int i = 0; i < role.getRightSide().length(); i++)
                        if (grammar.isExistVar(role.getRightSide().charAt(i)))
                            child.add(role.getRightSide().charAt(i));
                }
            }
            for (Character c : child) {
                if (!reachableVariable.contains(c)) {
                    reachableVariable.add(c);
                    checkReachable(grammar, c, reachableVariable);
                }
            }
        }
    }

    public static Grammar unitReduction(Grammar grammar) {
        List<Role> newRole = new ArrayList<>(grammar.getGrammar());
        List<Role> deleteRole = new ArrayList<>();
        for (Role role : newRole) {
            if (role.getRoleType() == RoleType.VARIABLE && role.getRightSide().length() == 1)
                deleteRole.add(role);
        }
        newRole.removeAll(deleteRole);
        System.out.println(newRole.toString());
        for (Character c : grammar.getVariableList()) {
            List<Character> endChar = new ArrayList<>();
            List<Character> check = new ArrayList<>();
            findDerivation(grammar, c, endChar, check);
            for (Character endC : endChar) {
                List<Role> addRole = new ArrayList<>();
                for (Role role : newRole) {
                    if (role.getLeftSide() == endC) {
                        Role tempRole = new Role(c, role.getRightSide());
                        addRole.add(tempRole);
                    }
                }
                newRole.addAll(addRole);
            }
        }
        deleteRole = new ArrayList<>(newRole);
        for (int i = 0 ; i < deleteRole.size() - 1 ; i++)
            for (int j = i + 1 ; j < deleteRole.size() ; j++) {
                    if (deleteRole.get(i).getLeftSide() == deleteRole.get(j).getLeftSide()) {
                        if (deleteRole.get(i).getRightSide() == deleteRole.get(j).getRightSide())
                            newRole.remove(deleteRole.get(i));
                    }
                }
        Grammar g = new Grammar(newRole);
        g.resetGrammarType();
        return g;
    }

    private static void findDerivation(Grammar grammar, char V, List<Character> endChar, List<Character> checked) {
        checked.add(V);
        for (Role role : grammar.getGrammar()) {
            if (role.getLeftSide() == V && role.getRoleType() == RoleType.VARIABLE && role.getRightSide().length() == 1) {
                if (!checked.contains(role.getRightSide().charAt(0))) {
                    endChar.add(role.getRightSide().charAt(0));
                    findDerivation(grammar, role.getRightSide().charAt(0), endChar, checked);
                }
            }
        }

    }
}


