package fortunecookie;

import java.io.*;
import java.util.*;

public class Cookie {
    private String initialCookie;

    public Cookie() {
        this.initialCookie = "cookie1,cookie2,cookie3,cookie4";
    }

    public void createCookie(String dir, String fileName) throws IOException {
        File cookieFile = new File("" + dir + "\\" + fileName);
        cookieFile.createNewFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dir + "\\cookie_file.txt"))) {
            for (String item: initialCookie.split(",")) {
                writer.write(item);
                writer.newLine();
            }
            System.out.println("cookie_file created at " + dir);
        }
    }

    public String loadFile(String dir, String fileName) throws IOException {

        try(BufferedReader br = new BufferedReader(new FileReader(dir + "\\" + fileName))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
            return everything;
        }

    }
    
    public String randomCookie(String cookieList) {

        Random randomGenerator = new Random();
        String[] cookies = cookieList.split("\\r?\\n");
        int idx = randomGenerator.nextInt(cookies.length);
        String randCookie = cookies[idx];
        return randCookie;
        
    }

    public void addCookie(String newCookie, String dir) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(dir  + "\\cookie_file.txt", true))) {
            bw.write(newCookie);
        }
       
    }
}
