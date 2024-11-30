package fr.univ_lyon1.info.m1.microblog.view;


import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Observer;
import java.util.Observable;
import java.util.LinkedHashMap;
import java.util.Map;


import fr.univ_lyon1.info.m1.microblog.controleur.Controleur;
import fr.univ_lyon1.info.m1.microblog.model.Message;
import fr.univ_lyon1.info.m1.microblog.model.MessageData;
import fr.univ_lyon1.info.m1.microblog.model.User;
import fr.univ_lyon1.info.m1.microblog.model.scoring.BookmarkScoringFactory;
import fr.univ_lyon1.info.m1.microblog.model.scoring.ForbiddenWordsScoringFactory;
import fr.univ_lyon1.info.m1.microblog.model.scoring.RecentMessageScoringFactory;
import fr.univ_lyon1.info.m1.microblog.model.scoring.ScoringStrategyFactory;


import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

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


            // Map associant les noms des stratégies aux factories correspondantes
            Map<String, ScoringStrategyFactory> strategyFactories = new HashMap<>();
            strategyFactories.put("Bookmarked Messages", new BookmarkScoringFactory());
            strategyFactories.put("Recent Messages", new RecentMessageScoringFactory());
            strategyFactories.put("No ForbiddenWords", new ForbiddenWordsScoringFactory());

            // ComboBox pour la sélection de la stratégie de scoring
            ComboBox<String> userStrategyComboBox = new ComboBox<>();
            userStrategyComboBox.setPromptText("Select scoring strategy :");
            userStrategyComboBox.getItems().addAll(strategyFactories.keySet());

            // Label pour afficher la stratégie sélectionnée
            Label selectedStrategyLabel = new Label("");

            // Gestion de la sélection de la stratégie de scoring
            userStrategyComboBox.setOnAction(event -> {
                String selectedStrategyName =
                        userStrategyComboBox.getSelectionModel().getSelectedItem();
                ScoringStrategyFactory factory = strategyFactories.get(selectedStrategyName);

                if (factory != null) {
                    selectedStrategyLabel.setText("Selected: " + selectedStrategyName);
                    controller.setScoringStrategy(u, factory);
                    controller.sortMessages();
                }
            });

            Pane textBox = createInputWidget(u);
            userBox.getChildren().addAll(
                    userID, userStrategyComboBox, selectedStrategyLabel, userMsgBox, textBox);
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
                    // Le 1er enfant est le Label avec l'ID utilisateur
                    // Le 2ème enfant est le ComboBox pour la sélection de la stratégie de scoring
                    // Le 3ème enfant est le Label pour afficher la stratégie sélectionnée
                    // Le 4ème enfant est le VBox contenant les messages
                    // Retourne le VBox contenant les messages
                    return (VBox) userBox.getChildren().get(3);
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


    /**
     * Create a message widget.
     *
     * @param m the message
     * @param d the message data
     * @param user the user
     * @return the VBox containing the message
     */
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
            if (m.getOwner().equals(user)) {
                // On passe l'action de suppression au modèle
                controller.getModel().deleteMessage(m);
                controller.sortMessages();
            } else {
                showAlert(
                        "Impossible de supprimer ce message, vous n'en êtes pas le propriétaire!");
            }
        });

        // Désactiver le btn sup si l'utilisateur n'est pas le propriétaire
        //deleteButton.setDisable(!m.getOwner().equals(user));

        buttonBox.getChildren().addAll(bookButton, deleteButton);   // Ajout des boutons
        msgBox.getChildren().add(buttonBox); // Ajout de la HBox contenant les boutons

        // Ajout du propriétaire du message
        Label ownerLabel = new Label("Owner: " + m.getOwner().getId());
        ownerLabel.setTextFill(Color.LIGHTGRAY);
        msgBox.getChildren().add(ownerLabel);

        // Création du TextFlow pour le contenu du message
        TextFlow textFlow = new TextFlow();
        String content = m.getContent();
        String[] words = content.split(" ");    // Séparer les mots du contenu
        boolean newLineAdded = false;
        for (String word : words) {
            Text text = new Text(word + " ");
            text.setStyle("-fx-font-weight: bold;");

            // Vérifier si le mot est un tag ou une mention
            if (word.startsWith("@")) {
                if (!newLineAdded) {
                    textFlow.getChildren().add(new Text("\n"));
                    newLineAdded = true;
                }
                text.setStyle("-fx-fill: #21A90DFF; -fx-font-weight: bold;");
            } else if (word.startsWith("#")) {
                if (!newLineAdded) {
                    textFlow.getChildren().add(new Text("\n"));
                    newLineAdded = true;
                }
                text.setStyle("-fx-fill: blue; -fx-underline: true;");
            }
            textFlow.getChildren().add(text);   // Ajout du mot au TextFlow
        }
        msgBox.getChildren().add(textFlow); // Ajout du TextFlow au VBox

        // Ajout de la date du message
        Label dateLabel = new Label(m.getDate());
        dateLabel.setTextFill(Color.RED);
        msgBox.getChildren().add(dateLabel);

        // Ajout du score du message
        Label score = new Label("Score: " + d.getScore());
        score.setTextFill(Color.LIGHTGRAY);
        msgBox.getChildren().add(score);
        msgBox.setStyle(MSG_STYLE);
        return msgBox;
    }

    /**
     * Create the input widget for the user.
     *
     * @param u the user
     * @return the input widget
     */
    private Pane createInputWidget(final User u) {
        final VBox input = new VBox(5);
        TextArea t = new TextArea();
        t.setMaxSize(300, 150);

        // Champ de saisie pour les tags
        TextField tagField = new TextField();
        tagField.setPromptText("Ajouter un tag : fyp, gaming");

        // Champ de saisie pour les mentions
        TextField mentionField = new TextField();
        mentionField.setPromptText("Ajouter une mention : Everyone, Alice");

        // Gestion de l'envoi du message avec les touches Ctrl + Enter
        t.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER && e.isControlDown()) {
                publishMessageWithDecorators(
                        u, t.getText(), tagField.getText(), mentionField.getText());
                t.clear();
                tagField.clear();
                mentionField.clear();
            }
        });

        Button submit = new Button("Publish");
        submit.setOnAction(e -> {
            publishMessageWithDecorators(
                    u, t.getText(), tagField.getText(), mentionField.getText());
            t.clear();
            tagField.clear();
            mentionField.clear();
        });

        // Ajout des éléments à la boîte de saisie
        input.getChildren().addAll(tagField, mentionField, t, submit);
        return input;
    }

    /**
     * Publish a message with decorators.
     *
     * @param u the user
     * @param text the text
     * @param tags the tags
     * @param mentions the mentions
     */
    private void publishMessageWithDecorators(
            final User u, final String text, final String tags, final String mentions) {
        controller.publishMessageWithDecorators(u, text, tags, mentions);
    }

    /**
     * Update the view.
     *
     * @param o the observable
     * @param arg the argument
     */
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

    /**
     * Update the user pane.
     *
     * @param user the user
     */
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

    /**
     * Show an alert.
     *
     * @param message the message
     */
    private void showAlert(final String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Permission Denied");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


