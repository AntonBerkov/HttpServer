import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by Anton on 03.04.2018.
 */
public class View {
    public void view(String s){
        Pane pane = new Pane();
        Stage stage = new Stage();
        TextField textField = new TextField();
        textField.setText(s);
        pane.getChildren().addAll(textField);
        Scene scene = new Scene(pane,800,600);
        stage.setScene(scene);
        stage.show();
    }
}