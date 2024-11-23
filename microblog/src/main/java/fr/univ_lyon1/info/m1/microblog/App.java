package fr.univ_lyon1.info.m1.microblog;

import fr.univ_lyon1.info.m1.microblog.InitialConfiguration.InitialConfiguration;
import fr.univ_lyon1.info.m1.microblog.controleur.Controleur;
import fr.univ_lyon1.info.m1.microblog.model.Y;
import fr.univ_lyon1.info.m1.microblog.view.JfxView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class for the application (structure imposed by JavaFX).
 */
public class App extends Application {

    /**
     * With javafx, start() is called when the application is launched.
     */
    @Override
    public void start(final Stage stage) throws Exception {
        final Y y = new Y();

        // Load configuration from file
        InitialConfiguration config = new InitialConfiguration(y);
        config.loadConfiguration("config.json");

        //y.createExampleMessages();
        //System.out.println(y.getUsers().size());

        // Create the controller and the main view
        Controleur controleur = new Controleur(y);
        JfxView v = new JfxView(controleur, stage, 600, 600);
        y.addObserver(v);

        // Second view (uncomment to activate)
        //y.addObserver(new JfxView(controleur, new Stage(), 400, 400));

    }

    /**
     * A main method in case the user launches the application using
     * App as the main class.
     */
    public static void main(final String[] args) {
        Application.launch(args);
    }
}
