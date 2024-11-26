package fr.univ_lyon1.info.m1.microblog.view;


import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;


import fr.univ_lyon1.info.m1.microblog.controleur.Controleur;
import fr.univ_lyon1.info.m1.microblog.model.Message;
import fr.univ_lyon1.info.m1.microblog.model.MessageData;
import fr.univ_lyon1.info.m1.microblog.model.User;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import fr.univ_lyon1.info.m1.microblog.model.BookmarkScoring;
import fr.univ_lyon1.info.m1.microblog.model.ForbiddenWordsScoring;
import fr.univ_lyon1.info.m1.microblog.model.RecentMessageScoring;
import fr.univ_lyon1.info.m1.microblog.model.ScoringStrategy;
import javafx.scene.control.ComboBox;

/**
 * Main class of the View (GUI) of the application.
 */
public class JfxView implements Observer {
    private final Controleur controller;
    private final HBox users;

    /**
     *
     * @param controller
     * @param stage
     * @param width
     * @param height
     */

    public JfxView(final Controleur controller, final Stage stage,
                   final int width, final int height) {
        this.controller = controller;
        stage.setTitle("Y Microblogging");

        // Création de la structure de base de l'interface utilisateur
        final VBox root = new VBox(10);

        // Création de la boîte contenant les utilisateurs
        users = new HBox(10);
        root.getChildren().add(users);

        createUsersPanes();
        afficheTous();
        final Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Create the pane containing all user's, from the users' registry contained in the model.
     */
    private void createUsersPanes() {
        users.getChildren().clear();
        for (User u : controller.getModel().getUsers()) {
            ScrollPane p = new ScrollPane();
            VBox userBox = new VBox();
            p.setMinWidth(300);
            p.setContent(userBox);
            users.getChildren().add(p);

            VBox userMsgBox = new VBox();
            Label userID = new Label(u.getId());

            // ComboBox for selecting the scoring strategy for each user
            ComboBox<ScoringStrategy> userStrategyComboBox = new ComboBox<>();
            userStrategyComboBox.setPromptText("Select scoring strategy :");
            userStrategyComboBox.getItems().addAll(
                    new BookmarkScoring(),
                    new RecentMessageScoring(),
                    new ForbiddenWordsScoring()
            );

            // Default display text to show selected strategy
            Label selectedStrategyLabel = new Label("");

            // On strategy selection, update the label and apply the strategy
            userStrategyComboBox.setOnAction(event -> {
                ScoringStrategy selectedStrategy =
                        userStrategyComboBox.getSelectionModel().getSelectedItem();
                if (selectedStrategy != null) {
                    selectedStrategyLabel.setText("Selected: " + selectedStrategy.toString());
                    controller.getModel().setScoringStrategy(u, selectedStrategy);
                    controller.sortMessages();
                }
            });

            Pane textBox = createInputWidget(u);
            userBox.getChildren().addAll(userID, userStrategyComboBox, userMsgBox, textBox);
        }
    }

    /**
     * .
     * @param user
     * @return vbox
     */
    public VBox getUserMessageBox(final User user) {
        // users est le conteneur principal qui contient toutes les interfaces utilisateur
        for (Node node : users.getChildren()) {
            if (node instanceof ScrollPane) {
                ScrollPane scrollPane = (ScrollPane) node;
                // Contient les informations d'un utilisateur
                VBox userBox = (VBox) scrollPane.getContent();
                // Le premier enfant est un Label avec l'ID utilisateur
                Label userIDLabel = (Label) userBox.getChildren().get(0);

                // Comparaison de l'ID de l'utilisateur pour trouver le bon VBox
                if (userIDLabel.getText().equals(user.getId())) {
                    // Le VBox contenant les messages est le deuxième enfant (par exemple)
                    // Retourne le VBox contenant les messages
                    return (VBox) userBox.getChildren().get(2);

                }
            }
        }
        return null;  // Si aucun VBox ne correspond à l'utilisateur
    }


    static final String MSG_STYLE = "-fx-background-color: white; "
            + "-fx-border-color: black; -fx-border-width: 1;"
            + "-fx-border-radius: 10px;"
            + "-fx-background-radius: 10px;"
            + "-fx-padding: 8px; "
            + "-fx-margin: 5px; ";


    private VBox createMessageWidget(final Message m, final MessageData d, final User user) {
        VBox msgBox = new VBox();

        // HBox pour le bouton de bookmark + le bouton de suppression
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.TOP_RIGHT);

        String bookmarkText = d.isBookmarked() ? " ★ " : " ☆ ";
        Button bookButton = new Button(bookmarkText);
        bookButton.setOnAction(e -> {
            controller.bookmarkMessage(m, user); // On passe l'action de bookmark au contrôleur
            controller.sortMessages(); // On trie les messages après un changement de bookmark
        });

        // Bouton de suppression
        Button deleteButton = new Button(" ❌ ");
        deleteButton.setOnAction(e -> {
            controller.getModel().deleteMessage(m);
            controller.sortMessages();
        });

        buttonBox.getChildren().addAll(bookButton, deleteButton);   // Ajout des boutons
        msgBox.getChildren().add(buttonBox); // Ajout de la HBox contenant les boutons

        Label label = new Label(m.getContent());
        msgBox.getChildren().add(label);
        Label dateLabel = new Label(m.getDate());
        dateLabel.setTextFill(Color.RED);
        msgBox.getChildren().add(dateLabel);
        Label score = new Label("Score: " + d.getScore());
        score.setTextFill(Color.LIGHTGRAY);
        msgBox.getChildren().add(score);
        msgBox.setStyle(MSG_STYLE);
        return msgBox;
    }
    private Pane createInputWidget(final User u) {
        final Pane input = new HBox();
        TextArea t = new TextArea();
        t.setMaxSize(200, 150);
        t.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER && e.isControlDown()) {
                controller.publishMessage(u, t.getText());
                t.clear();
            }
        });
        Button s = new Button("Publish");
        s.setOnAction(e -> {
            controller.publishMessage(u, t.getText());
            controller.sortMessages(); // On trie les messages après un ajout
            t.clear();
        });
        input.getChildren().addAll(t, s);
        return input;
    }

    @Override
    public void update(final Observable o, final Object arg) {
        //System.out.println("changement");
        if (arg instanceof User) {
            User updatedUser = (User) arg;
            Platform.runLater(() -> updateUserPane(updatedUser));
            Platform.runLater(this::refresh);
        } else {
            // Si l'argument n'est pas un utilisateur, on rafraîchit tout
            Platform.runLater(this::refresh);
        }
    }
    private void updateUserPane(final User user) {
        VBox userMessageBox = getUserMessageBox(user);
        if (userMessageBox != null) {
            userMessageBox.getChildren().clear();  // Efface les anciens messages
            // Récupérer les messages filtrés pour l'utilisateur
            LinkedHashMap<Message, MessageData> messages =
                    controller.getModel().getFilteredMessages(user);

            if (messages != null && !messages.isEmpty()) {
                for (Entry<Message, MessageData> entry : messages.entrySet()) {
                    // Ajouter chaque message au VBox correspondant à l'utilisateur
                    userMessageBox.getChildren().add(createMessageWidget(entry.getKey(),
                            entry.getValue(), user));
                }
            } else {
                // Si aucun message n'existe, afficher un message informatif
                userMessageBox.getChildren().add(new Label("Aucun message pour cet utilisateur."));
            }
        } else {
            System.out.println("Impossible de trouver la boîte de messages pour "
                    + "l'utilisateur : " + user.getId());
        }
    }
    private  void afficheTous() {
        for (User u:controller.getModel().getUsers()) {
            updateUserPane(u);
        }
    }
    private void refresh() {
        users.getChildren().clear();
        createUsersPanes();
        afficheTous();
    }
}


