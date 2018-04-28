import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by Anton on 03.04.2018.
 */
public class View {


    public static void view() throws Throwable {

            Pane pane = new Pane();
        Stage stage = new Stage();
        TextField textField = new TextField("HI");
        pane.getChildren().addAll(textField);
        Scene scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
        stage.show();

            HttpServer server1 = new HttpServer();
            server1.server();
    }

}
