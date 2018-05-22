import java.util.List;

public class Grammar {
    private List<Role> grammar;

    public Grammar(List<Role> grammar) {
        this.grammar = grammar;
    }

    public List<Role> getGrammar() {
        return grammar;
    }

    @Override
    public String toString() {
        String a = "";
        for (Role role : grammar)
            a = a + role.toString() + "\n";
        return a;
    }
}
