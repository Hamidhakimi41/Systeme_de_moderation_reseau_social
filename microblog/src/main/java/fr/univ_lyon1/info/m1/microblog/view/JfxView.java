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
import javafx.geometry.Insets;
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
import javafx.scene.layout.Priority;
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
     * Constructor.
     *
     * @param controller the controller
     * @param stage the stage
     * @param width the width
     * @param height the height
     */
    public JfxView(final Controleur controller, final Stage stage,
                   final int width, final int height) {
        this.controller = controller;
        stage.setTitle("Y Microblogging");

        // Create the root layout
        final VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: #f0f0f0;");

        // Create the user container
        users = new HBox(10);
        users.setPadding(new Insets(10));
        users.setStyle(
                "-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-width: 1px;");
        VBox.setVgrow(users, Priority.ALWAYS);
        root.getChildren().add(users);

        // Create the users panes
        createUsersPanes();
        afficheTous();

        // Create the scene
        final Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Create the pane containing all users.
     */
    private void createUsersPanes() {
        users.getChildren().clear();
        for (User u : controller.getModel().getUsers()) {
            VBox userBox = new VBox(10);
            userBox.setPadding(new Insets(10));
            userBox.setStyle(""
                    + "-fx-background-color: #ffffff; "
                    + "-fx-border-color: #cccccc; "
                    + "-fx-border-width: 1px;"
            );
            userBox.setMinWidth(300);
            userBox.setMaxWidth(300);

            Label userID = new Label(u.getId());
            userID.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

            // Scoring strategies
            Map<String, ScoringStrategyFactory> strategyFactories = new HashMap<>();
            strategyFactories.put("Bookmarked Messages", new BookmarkScoringFactory());
            strategyFactories.put("Recent Messages", new RecentMessageScoringFactory());
            strategyFactories.put("No Forbidden Words", new ForbiddenWordsScoringFactory());

            ComboBox<String> userStrategyComboBox = new ComboBox<>();
            userStrategyComboBox.setPromptText("Select scoring strategy:");
            userStrategyComboBox.getItems().addAll(strategyFactories.keySet());

            Label selectedStrategyLabel = new Label("");
            userStrategyComboBox.setOnAction(event -> {
                String selectedStrategyName =
                        userStrategyComboBox.getSelectionModel().getSelectedItem();
                ScoringStrategyFactory factory =
                        strategyFactories.get(selectedStrategyName);

                if (factory != null) {
                    selectedStrategyLabel.setText("Selected: " + selectedStrategyName);
                    controller.setScoringStrategy(u, factory);
                    controller.sortMessages();
                }
            });

            VBox userMsgBox = new VBox(10);
            userMsgBox.setStyle(
                    "-fx-background-color: #f9f9f9; "
                            + "-fx-border-color: #dddddd; "
                            + "-fx-border-width: 1px; "
                            + "-fx-padding: 10px;"
            );
            ScrollPane scrollPane = new ScrollPane(userMsgBox);
            scrollPane.setFitToWidth(true);

            Pane textBox = createInputWidget(u);

            VBox.setVgrow(scrollPane, Priority.ALWAYS);
            userBox.getChildren().addAll(
                    userID, userStrategyComboBox, selectedStrategyLabel, scrollPane, textBox
            );
            users.getChildren().add(userBox);
        }
    }

    /**
     * Get the user message box.
     *
     * @param user the user
     * @return the VBox containing the messages
     */
    public VBox getUserMessageBox(final User user) {
        for (Node node : users.getChildren()) {
            if (node instanceof VBox) {
                VBox userBox = (VBox) node;
                Label userIDLabel = (Label) userBox.getChildren().get(0);

                if (userIDLabel.getText().equals(user.getId())) {
                    ScrollPane scrollPane = (ScrollPane) userBox.getChildren().get(3);
                    return (VBox) scrollPane.getContent();
                }
            }
        }
        return null;
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
        VBox msgBox = new VBox(5);
        msgBox.setStyle(MSG_STYLE);

        // Ajout des boutons pour les actions (bookmark, delete)
        HBox buttonBox = new HBox(5);
        buttonBox.setAlignment(Pos.TOP_RIGHT);

        String bookmarkText = d.isBookmarked() ? " ★ " : " ☆ ";
        Button bookButton = new Button(bookmarkText);
        bookButton.setOnAction(e -> {
            controller.bookmarkMessage(m, user);
            controller.sortMessages();
        });

        Button deleteButton = new Button(" ❌ ");
        deleteButton.setOnAction(e -> {
            if (m.getOwner().equals(user)) {
                controller.getModel().deleteMessage(m);
                controller.sortMessages();
            } else {
                showAlert(
                        "Impossible de supprimer ce message, vous n'en êtes pas le propriétaire!");
            }
        });

        // Ajout des boutons à la boîte de boutons
        buttonBox.getChildren().addAll(bookButton, deleteButton);
        msgBox.getChildren().add(buttonBox);

        // Ajout du contenu du message
        Label ownerLabel = new Label("Owner: " + m.getOwner().getId());
        ownerLabel.setTextFill(Color.LIGHTGRAY);
        msgBox.getChildren().add(ownerLabel);

        TextFlow textFlow = new TextFlow();
        String content = m.getContent();
        String[] words = content.split(" ");
        boolean newLineAdded = false;
        for (String word : words) {
            Text text = new Text(word + " ");
            text.setStyle("-fx-font-weight: bold;");

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
            textFlow.getChildren().add(text);
        }
        msgBox.getChildren().add(textFlow);

        // Ajout de la date et du score
        Label dateLabel = new Label(m.getDate());
        dateLabel.setTextFill(Color.RED);
        msgBox.getChildren().add(dateLabel);

        Label score = new Label("Score: " + d.getScore());
        score.setTextFill(Color.LIGHTGRAY);
        msgBox.getChildren().add(score);

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
        input.setPadding(new Insets(10));
        input.setStyle(
                "-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-width: 1px;");

        TextArea t = new TextArea();
        t.setMaxSize(300, 150);

        // Ajout des champs pour les tags et les mentions
        TextField tagField = new TextField();
        tagField.setPromptText("Ajouter un tag : fyp, gaming");

        TextField mentionField = new TextField();
        mentionField.setPromptText("Ajouter une mention : Everyone, Alice");

        // Gestion de l'envoi du message
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

        // Ajout des champs à la boîte de saisie
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
        if (arg instanceof User) {
            User updatedUser = (User) arg;
            Platform.runLater(() -> updateUserPane(updatedUser));
            Platform.runLater(this::refresh);
        } else {
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
            userMessageBox.getChildren().clear();
            LinkedHashMap<Message, MessageData> messages =
                    controller.getModel().getFilteredMessages(user);

            if (messages != null && !messages.isEmpty()) {
                for (Entry<Message, MessageData> entry : messages.entrySet()) {
                    userMessageBox.getChildren().add(
                            createMessageWidget(entry.getKey(), entry.getValue(), user));
                }
            } else {
                userMessageBox.getChildren().add(
                        new Label("Aucun message pour cet utilisateur."));
            }
        } else {
            System.out.println(
                    "Impossible de trouver la boîte de messages pour l'utilisateur : "
                            + user.getId());
        }
    }

    /**
     * Show all users.
     */
    private void afficheTous() {
        for (User u : controller.getModel().getUsers()) {
            updateUserPane(u);
        }
    }

    /**
     * Refresh the view.
     */
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
