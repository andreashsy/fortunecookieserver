package fortunecookie;

import java.io.*;
import java.net.*;

public class CookieServer {
    public static void main(String[] args) throws IOException {
        long lastCookieTime = 0;
        long timeOut = 30;
        long nanoPerSec = 1000000000;
        long timeDiff;
        String Wdir = System.getProperty("user.dir") + "\\cookie";
        String cookieFilename = args[1];
        new File(Wdir).mkdirs();
        
        LoginHandler loginhandler = new LoginHandler();
        Cookie cookie = new Cookie();
        cookie.createCookie(Wdir, cookieFilename);

        System.out.println("Args: " + String.join(",",args));

        int port = Integer.parseInt(args[0]);
        System.out.println("Listening on " + port);
        ServerSocket serversocket = new ServerSocket(port);
        Socket socket = serversocket.accept();
        
        try (InputStream is = socket.getInputStream(); OutputStream os = socket.getOutputStream()) {
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);


            String line = dis.readUTF();
            while (!line.contentEquals("close")) {
                System.out.println("Msg: " + line);

                if (line.equals("get-cookie")) {
                    System.out.println("Login status: " + loginhandler.getLoginStatus());
                    if (loginhandler.getLoginStatus()) {
                        if (lastCookieTime == 0) {
                            lastCookieTime = System.nanoTime();
                        } else {
                            timeDiff = System.nanoTime() - lastCookieTime;
                            lastCookieTime =System.nanoTime();
                            System.out.println("Seconds since last get-cookie: " + timeDiff/nanoPerSec);
                            if (timeDiff/nanoPerSec >= timeOut) {
                                loginhandler.logOut();
                                System.out.println("Logged out due to timeout");
                                dos.writeUTF("Logged out due to timeout, please login again");
                                dos.flush();
                                line = dis.readUTF();
                                continue;                         
                            }
                        }
                        String listOfCookies = cookie.loadFile(Wdir, cookieFilename);
                        String returnCookie = cookie.randomCookie(listOfCookies);
                        System.out.println("Sending cookie: " + returnCookie);
                        dos.writeUTF("cookie-text " + returnCookie);
                    } else {
                        System.out.println("User not logged in");
                        dos.writeUTF("You are not logged in. Please register or login to continue. Type 'register <username>/<password>' or 'login <username>/password'");
                    }
                    
                } else if (line.startsWith("login ")) {
                    loginhandler.logIn(line.split(" ")[1]);
                    System.out.println("Is login successful? " + loginhandler.getLoginStatus());
                    if (loginhandler.getLoginStatus()) {
                        dos.writeUTF(line.split(" ")[1].split("/")[0] + " logged in.");
                    } else {
                        dos.writeUTF("Login failed.");
                    }
                    
                } else if (line.startsWith("register ")) {
                    loginhandler.addUser(line.split(" ")[1]);
                    System.out.println(line.split(" ")[1].split("/")[0] + " registered.");
                    dos.writeUTF(line.split(" ")[1].split("/")[0] + " registered.");
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
