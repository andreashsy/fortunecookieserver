package fortunecookie;

import java.io.*;
import java.net.*;

public class CookieClientHandler implements Runnable {
    private final Socket socket;
    public CookieClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
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

        } catch (IOException i) {
            System.out.println(i);
            
        }
        
    }
    
}
