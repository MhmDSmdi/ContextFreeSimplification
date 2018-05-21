public class Role {
    private char leftSide;
    private String rightSide;
    private RoleType roleType;

    public Role(char leftSide, String rightSide) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    public char getLeftSide() {
        return leftSide;
    }

    public void setLeftSide(char leftSide) {
        this.leftSide = leftSide;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public String getRightSide() {
        return rightSide;
    }

    public void setRightSide(String rightSide) {
        this.rightSide = rightSide;
    }

    @Override
    public String toString() {
        String a = leftSide + " => " + rightSide;
        return a;
    }
}
