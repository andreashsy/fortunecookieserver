package fortunecookie;

import java.io.*;
import java.util.ArrayList;

public class LoginHandler {
    String Wdir;
    Boolean isLoggedin = false;

    public LoginHandler() throws IOException {
        this.Wdir = System.getProperty("user.dir") + "\\cookie";
        File cookieFile = new File("" + Wdir + "\\users.txt");
        cookieFile.createNewFile();
    }

    public void addUser(String input) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Wdir  + "\\users.txt", true))) {
            bw.newLine();
            bw.write(input);
        }
    }
    
    public boolean logIn(String input) throws IOException{
        String everything;
        String[] sl;
        ArrayList<String> al = new ArrayList<>();
        
        try(BufferedReader br = new BufferedReader(new FileReader(Wdir + "\\users.txt" ))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
            sl = everything.split("\\r\\n");
            for (String i:sl)
            al.add(i);
        }

        if (al.contains(input) && input.indexOf('/') != -1) {
            this.isLoggedin = true;
            System.out.println("Login Successful");
            return true;
        } else {
            System.out.println("Incorrect login credentials");
            return false;
        }
    }

    public boolean getLoginStatus() {
        return isLoggedin;
    }

    public void logOut() {
        this.isLoggedin = false;
    }
}
