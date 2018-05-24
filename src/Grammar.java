import java.util.ArrayList;
import java.util.List;

public class Grammar {
    private List<Role> grammar;
    private List<Character> variableList;
    private char startVar;

    public Grammar(List<Role> grammar) {
        this.grammar = grammar;
    }


    public List<Role> getGrammar() {
        return grammar;
    }

    public void setVariable() {
        variableList = new ArrayList<>();
        for (Role role : grammar){
            if (!variableList.contains(role.getLeftSide()))
                variableList.add(role.getLeftSide());
        }
        startVar = variableList.get(0);
    }

    public void deleteRole(char c) {
        List<Role> deleteList = new ArrayList<>();
        for (Role role : grammar) {
            if (role.getLeftSide() == c)
                deleteList.add(role);
        }
        grammar.removeAll(deleteList);
    }

    public Grammar resetGrammarType() {
        for (Role temp : grammar) {
            int flagType = 0;
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
        }
        return this;
    }

    public char getStartVar() {
        return startVar;
    }

    public List<Character> getVariableList() {
        return variableList;
    }

    public boolean isExistVar(char c) {
        return variableList.contains(c);
    }

    @Override
    public String toString() {
        String a = "";
        for (Role role : grammar)
            a = a + role.toString() + "\t\t" + role.getRoleType() +"\n";
        return a;
    }
}
