import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;


public class HttpServer  {

    public void server() throws Throwable{

        ServerSocket serverSocket = new ServerSocket(1456);
        Thread thread0 = new Thread(()->{
           while (true){
               Socket s = null;
               try {
                   s = serverSocket.accept();
               } catch (IOException e) {
                   e.printStackTrace();
               }
               System.out.println("Client accepted");
               Thread thread = null;
               try {
                   thread = new Thread(new SocketProcessor(s));
               } catch (Throwable throwable) {
                   throwable.printStackTrace();
               }
               assert thread != null;
               thread.start();
           }
        });
        thread0.start();
   /*   while (true) {
          try {
              Socket s = serverSocket.accept();
              System.out.println("Client accepted");
              Thread thread = new Thread(new SocketProcessor(s));
              thread.start();
            //  thread.sleep(20000);

          }
            catch (IOException e){
                System.out.println("Failed to establish connection.");
                System.out.println(e.getMessage());
                System.exit(-1);
            }
        }*/

    }

    private static class SocketProcessor implements Runnable {

        private Socket s;
        private InputStream is;
        private OutputStream os;
        private static final String dir = "/path";


        private SocketProcessor(Socket s) throws Throwable {
            this.s = s;
            this.is = s.getInputStream();
            this.os = s.getOutputStream();
        }

        public void run() {
            try {
                String header= readInputHeaders();
                String url = getURIFromHeader(header);
                System.out.println("Resource: " + url + "\n");
                send(url);
                String a = url+"\r\n"+"Content-Length: " + url.length() + "\r\n";


            } catch (Throwable t) {

            } finally {
                try {

                    s.close();
                } catch (Throwable t) {

                }
            }

            System.out.println("Client processing finished");
        }

        private int send(String url) throws IOException {
            InputStream strm = HttpServer.class.getResourceAsStream(url);
            int code = (strm != null) ? 200 : 404;
            String header = getHeader(code);
            PrintStream answer = new PrintStream(os, true, "UTF-8");
            answer.print(header);
            if (code == 200) {
                int count = 0;
                byte[] buffer = new byte[1024];
                while((count = strm.read(buffer)) != -1) {
                    os.write(buffer, 0, count);
                    os.flush();
                }
                strm.close();
            }
            return code;
        }

        private String getHeader(int code) {
            String contentType = "text/html";
            String a = null;
            StringBuilder buffer = new StringBuilder();
            buffer.append("HTTP/1.1 " + code + " " + getAnswer(code) + "\n");
            buffer.append("Date: " + new Date().toString() + "\n");
            buffer.append("Accept-Ranges: none\n");
            buffer.append("Content-Type: " + contentType + "\n");
            buffer.append("\n");
            a=buffer.toString();
            return a;
        }

        private String getAnswer(int code) {
            switch (code) {
                case 200: {
                    System.out.println("200 OK");
                    return "OK";
                }
                case 404:
                {
                    System.out.println("404 Not Found");
                    return "Not Found";
                }
                default:
                    return "Internal Server Error";
            }

        }
        private void writeResponse(String s) throws Throwable {
            String a =s+"\r\n";
            System.out.println(a);
           // os.write(a.getBytes());
            os.flush();
        }

        private String getURIFromHeader(String header) {
            int from = header.indexOf(" ") + 1;
            int to = header.indexOf(" ", from);
            String uri = header.substring(from, to);
            int paramIndex = uri.indexOf("?");
            if (paramIndex != -1) {
                uri = uri.substring(0, paramIndex);
            }
            return dir+ uri;
        }

       private String readInputHeaders() throws Throwable {

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
           String s;
           StringBuilder builder = new StringBuilder();

            while(true) {
                s=br.readLine();

                if(s == null || s.trim().length() == 0) {break;}
                else {
                    writeResponse(s);
                    builder.append(s + System.getProperty("line.separator"));
                }

            }
           String response = "Content-Type: text/html\r\n"+"Date: " + new Date().toString() + "\r\n";
           writeResponse(response);
           return builder.toString();
       }
    }
}
