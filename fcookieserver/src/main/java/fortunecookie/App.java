package fortunecookie;

import java.io.File;
import java.io.IOException;

public class App 
{
    public static void main(String[] args) throws IOException{

        //create cookie directory
        String Wdir = System.getProperty("user.dir") + "\\cookie";
        new File(Wdir).mkdirs();

        Cookie cookie = new Cookie();

        cookie.createCookie(Wdir);
        String cL = cookie.loadFile(Wdir);
        System.out.println(cL);

        cookie.addCookie("newcookie", Wdir);


    }
}
