package fortunecookie;

import java.io.*;
import java.net.*;
// java -cp fortunecookie.jar fc.Server 12345 cookie_file.txt
//        0        1               2       3         4
public class CookieServer {
    public static void main(String[] args) throws IOException {
        for (String i: args) {
            System.out.println(i);
        }
        String Wdir = System.getProperty("user.dir") + "\\cookie";
        new File(Wdir).mkdirs();
        
        Cookie cookie = new Cookie();
        cookie.createCookie(Wdir);

        int port = Integer.parseInt(args[3]);
        System.out.println("Listening on " + port);
        ServerSocket serversocket = new ServerSocket(port);
        Socket socket = serversocket.accept();
        
        try (InputStream is = socket.getInputStream(); OutputStream os = socket.getOutputStream()) {
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);


            String line = dis.readUTF();
            //Console cons = System.console();
            while (!line.contentEquals("close")) {
                System.out.println("Msg: " + line);

                if (line.equals("get-cookie")) {
                    String listOfCookies = cookie.loadFile(Wdir);
                    String returnCookie = cookie.randomCookie(listOfCookies);
                    System.out.println("Sending cookie " + returnCookie);
                    dos.writeUTF("cookie-text " + returnCookie);
                } else {
                    dos.writeUTF("Message received: " + line);
                }
                dos.flush();
                line = dis.readUTF();
            }
        } 
            socket.close();
            serversocket.close(); 
    }
    
}
