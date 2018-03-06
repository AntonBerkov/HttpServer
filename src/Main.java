
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
public class Main {

    public static void main(String[] args) throws Throwable {
        ServerSocket serverSocket = new ServerSocket(1456);
        while (true) {
            Socket s = serverSocket.accept();
            System.out.println("Client accepted");
            new Thread(new SocketProcessor(s)).start();
        }
    }
    private static class SocketProcessor implements Runnable {

        private Socket s;
        private InputStream is;
        private OutputStream os;


         private SocketProcessor(Socket s) throws Throwable {
              this.s = s;
              this.is = s.getInputStream();
              this.os = s.getOutputStream();
          }
        public void run() {
            try {
                readInputHeaders();
                String a = "Works";
                writeResponse(a);
            } catch (Throwable t) {

            } finally {
                try {
                    s.close();
                } catch (Throwable t) {

                }
            }
            System.out.println("Client processing finished");
        }

        private void writeResponse(String s) throws Throwable {

            System.out.println ("HTTP/1.0 200 OK\r\n" +
                    "User-Agent: Mozilla/58.0.2\r\n"+
                    "Server: MyServer\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: " + s.length() + "\r\n" +
                    "Connection: keep-alive\r\n\r\n");
            os.write(s.getBytes());
            os.flush();
        }
       private void readInputHeaders() throws Throwable {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while(true) {
                String s = br.readLine();
                if(s == null || s.trim().length() == 0) {
                    break;
                }
            }
        }
    }
}