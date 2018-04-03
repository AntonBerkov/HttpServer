import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class HttpServer  {

    public void server() throws Throwable{

        ServerSocket serverSocket = new ServerSocket(1456);
        while (true) {
            View view0 = new View();
            view0.view("");
            Socket s = serverSocket.accept();
            System.out.println("Client accepted");
            Thread thread = new Thread(new SocketProcessor(s));
            String d= "S";
            thread.start();

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
            os.write(s.getBytes());
            os.flush();
        }

       private void readInputHeaders() throws Throwable {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while(true) {
                String s;
                s=br.readLine();
                if(s == null || s.trim().length() == 0) {
                    break;
                }else{
                    System.out.println(s);

                }

            }


       }
    }
}
