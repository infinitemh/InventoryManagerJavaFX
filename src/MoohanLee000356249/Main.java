package MoohanLee000356249;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class. Extends the Application abstract class.
 */
public class Main extends Application
{
    /**
     * Main entry point for the application.
     *
     * @param primaryStage reference the window that the GUI will be displayed in.
     *
     * @throws Exception top level exception catcher
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent parent = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene mainScene = new Scene(parent);
        primaryStage.setTitle("Software 1 Performance Assessment");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    /**
     * javadocs located in ../../javadocs (javadocs folder located in the root folder)
     * <p>
     * Main method. calls the launch method, which is inherited from the Application class, to launch the application.
     *
     * @param args String array of arguments for the launch method
     */
    public static void main(String[] args)
    {
        launch(args);
    }
}