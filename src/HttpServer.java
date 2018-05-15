import javafx.application.Platform;

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
               String info = "Client accepted";
             //  add.addInfo(info);
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

    }

    private static class SocketProcessor implements Runnable {

        private Socket s;
        public InputStream is;
        public OutputStream os;
        public static final String dir = "/path";
        public String info ="Client accepted"+"\r\n";





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
                info = info +"Resource: "+ url +"\r\n";
               int code = send(url);
                System.out.println("Result code: " + code + "\n");
                String a = "Content-Length: " + url.length() + "\r\n";
                 System.out.println(a);

            } catch (Throwable t) {

            } finally {
                try {

                    s.close();
                } catch (Throwable t) {

                }
            }

            System.out.println("Client processing finished");
            info = info +"Client processing finished"+"\r\n";
            sendInfo(info);
        }

        public void sendInfo(String info){
            Platform.runLater(()->{
                View add = new View();
                add.addInfo(info);
            });

        }

        public int send(String url) throws IOException {
            InputStream strm = HttpServer.class.getResourceAsStream(url);
            int code = 0;
            String a = info;
            GetCode code1 = new GetCode();
            code=code1.getCode(code,strm,a,url);

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
            GetAnswer answer = new GetAnswer();
            StringBuilder buffer = new StringBuilder();
            buffer.append("HTTP/1.1 " + code + " " + answer.getAnswer(code) + "\n");
            buffer.append("Date: " + new Date().toString() + "\n");
            buffer.append("Accept-Ranges: none\n");
            buffer.append("Content-Type: " + contentType + "\n");
            buffer.append("\n");
            a=buffer.toString();
            System.out.println(a);
            info = info+"\r\n"+a+"\r\n";
            return a;
        }


        private void writeResponse(String s) throws Throwable {
            String a =s+"\r\n";
            System.out.println(a);
            info = info+"\r\n"+a+"\r\n";

        }

        public String getURIFromHeader( String header) {
            int from = header.indexOf(" ") + 1;
            int to = header.indexOf(" ", from);
            String uri = header.substring(from, to);
            int paramIndex = uri.indexOf("?");
            if (paramIndex != -1) {
                uri = uri.substring(0, paramIndex);
            }
            return dir+ uri;
        }

        public String readInputHeaders() throws Throwable {

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
            return builder.toString();
        }


    }
}
