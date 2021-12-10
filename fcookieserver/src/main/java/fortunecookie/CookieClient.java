package fortunecookie;

import java.io.*;
import java.net.*;

public class CookieClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        String hostAddress = args[0].split(":")[0];
        int port = Integer.parseInt(args[0].split(":")[1]);
        Socket socket = new Socket(hostAddress, port);
        CookieClientHandler cch = new CookieClientHandler(socket);
        Thread thread = new Thread(cch);
        thread.start();

    }
    
}
