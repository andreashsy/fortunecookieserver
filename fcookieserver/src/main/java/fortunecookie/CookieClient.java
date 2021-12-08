package fortunecookie;

import java.io.*;
import java.net.*;

// java -cp fortunecookie.jar fc.Client locahost:12345
//        0       1                2          3
public class CookieClient {
    public static void main(String[] args) throws IOException {
        String hostAddress = args[3].split(":")[0];
        int port = Integer.parseInt(args[3].split(":")[1]);

        Socket socket = new Socket(hostAddress, port);
        try (OutputStream os = socket.getOutputStream(); InputStream is = socket.getInputStream()) {
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);

            System.err.println("Ready to send message...");
            Console cons = System.console();
            mainloop: while (1 <= 2) {
                String input = cons.readLine("What message to send? ");
                System.out.println("Sending: " + input);
                if (input.contentEquals("close")) {
                    break mainloop;
                }
                dos.writeUTF(input);
                dos.flush();
                String line = dis.readUTF();
                if (line.startsWith("cookie-text ")) {
                    System.out.println(line.replaceFirst("cookie-text ", ""));
                } else {
                    System.out.println(line);
                }
                

                

            }
            
    } 
        socket.close();
    }
    
}
