package user.logic;

public class Autorization {
    private String name;
    private String password;

    public Autorization() {
    }

    public boolean isAutorized() {
        if ("name".equals(name)){
            if ("123".equals(password)){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
