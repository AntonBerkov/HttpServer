import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
      HttpServer httpServer0 = new HttpServer();
        try {
            httpServer0.server();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }

    public static void main(String[] args){launch(args);}
}