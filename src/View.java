import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by Anton on 03.04.2018.
 */
public class View {



    Text t = new Text();
    String a = ""+"\r\n";
    Pane pane = new Pane();
    ScrollPane pane0;
    VBox box = new VBox();
    Stage stage = new Stage();
    Scene scene;
    public void view() throws Throwable {

        addInfo("Start");


    }


    public void  addInfo(String info)
    {
        a =a+""+info;
        Platform.runLater(()->{
       //     stage.close();
          //  String b = a+" "+info;
        //    textField.setText(a);
          //  textField.setPrefSize(400,400);
            t.setText(a);
            box.getChildren().addAll(t);
            pane0 = new ScrollPane(box);
            pane.getChildren().addAll(pane0);
            scene = new Scene(pane, 800, 600);
            stage.setScene(scene);
            stage.show();
        });
    }

}
