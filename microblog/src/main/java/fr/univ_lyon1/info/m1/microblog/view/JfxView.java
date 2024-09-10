package fr.univ_lyon1.info.m1.microblog.view;


//import java.text.SimpleDateFormat;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import fr.univ_lyon1.info.m1.microblog.model.BookmarkScoring;
import fr.univ_lyon1.info.m1.microblog.model.Message;
import fr.univ_lyon1.info.m1.microblog.model.MessageData;
import fr.univ_lyon1.info.m1.microblog.model.User;
import fr.univ_lyon1.info.m1.microblog.model.Y;
//import javafx.geometry.Pos;
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

/**
 * Main class of the View (GUI) of the application.
 */
public class JfxView {
    private Y model;
    private HBox users;

    /**
     * Main view of the application.
     */
        // TODO: style error in the following line. Check that checkstyle finds it, and then fix it.
        public JfxView(final Y y, final Stage stage,  final int width, final int height) {
        stage.setTitle("Y Microblogging");

        this.model = y;
        y.setView(this);
        final VBox root = new VBox(10);

        // final Pane search = createSearchWidget();
        // root.getChildren().add(search);

        users = new HBox(10);
        root.getChildren().add(users);

        createUsersPanes();

        // Everything's ready: add it to the scene and display it
        final Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Create the pane containing all user's, from the users' registry contained in the model.
     */
    public void createUsersPanes() {
        users.getChildren().clear();
        for (User u : model.getUsers()) {
            ScrollPane p = new ScrollPane();
            VBox userBox = new VBox();
            p.setMinWidth(300);
            p.setContent(userBox);
            users.getChildren().add(p);

            VBox userMsgBox = new VBox();
            
            Label userID = new Label(u.getId());

            Pane textBox = createInputWidget(u);
            userBox.getChildren().addAll(userID, userMsgBox, textBox);
        }

    }

    /**
     * Add message m to all users.
     */
    public void addMessage(final Message m) {
        for (Node u : users.getChildren()) {
            ScrollPane scroll = (ScrollPane) u;
            VBox userBox = (VBox) scroll.getContent();
            VBox userMsg = (VBox) userBox.getChildren().get(1);

            VBox msgBox = createMessageWidget(m, new MessageData());
            userMsg.getChildren().add(msgBox);
        }
        //sortMessages();
    }

    void bookmarkMessage(final Message m, final User user) {
        for (Node u : users.getChildren()) {
            ScrollPane scroll = (ScrollPane) u;
            VBox userBox = (VBox) scroll.getContent();
            Label userID = (Label) userBox.getChildren().get(0);
            VBox userMsg = (VBox) userBox.getChildren().get(1);

            String content = null;
            Button bookBtn = null;
            for (Node n : userMsg.getChildren()) {
                if (n instanceof Button) {
                    bookBtn = (Button) n;
                } else if (n instanceof Label) {
                    content = ((Label) n).getText();
                }
            }
            if (content.equals(m.getContent()) && userID.getText().equals(user.getId())) {
                bookBtn.setText("⭐");
            }
        }
        sortMessages();
    }
    /**
     * Get a clean list of messages and the associated metadata from the GUI.
     *
     * TODO: never, ever do that. This is the opposite of what a good programmer
     * would do: information is stored directly in the GUI. This method must be
     * deleted before the final version (using a proper MVC).
     */
    LinkedHashMap<Message, MessageData> getMessageList(final User user) {
        LinkedHashMap<Message, MessageData> l = new LinkedHashMap<>();
        for (Node u : users.getChildren()) {
            ScrollPane scroll = (ScrollPane) u;
            VBox userBox = (VBox) scroll.getContent();
            Label userID = (Label) userBox.getChildren().get(0);
            if (userID.getText().equals(user.getId())) {
                VBox userMsg = (VBox) userBox.getChildren().get(1);

                for (Node mNode : userMsg.getChildren()) {
                    Pane mPane = (Pane) mNode;
                    Message m = null;
                    MessageData d = new MessageData();
                    for (Node n : mPane.getChildren()) {
                        if (n instanceof Button) {
                            Button bookBtn = (Button) n;
                            if (bookBtn.getText().equals("⭐")) {
                                d.setBookmarked(true);
                            }
                        } else if (n instanceof Label && m == null) {
                            m = new Message(((Label) n).getText());
                        }
                    }
                    assert m != null;
                    l.put(m, d);
                }
                break;
            }
        }
        return l;
    }

    void setMessageList(final LinkedHashMap<Message, MessageData> messagesData, final User user) {
        for (Node u : users.getChildren()) {
            ScrollPane scroll = (ScrollPane) u;
            VBox userBox = (VBox) scroll.getContent();
            Label userID = (Label) userBox.getChildren().get(0);
            if (userID.getText().equals(user.getId())) {
                VBox userMsg = (VBox) userBox.getChildren().get(1);

                userMsg.getChildren().clear();
                for (Entry<Message, MessageData> e : messagesData.entrySet()) {
                    Message m = e.getKey();
                    MessageData d = e.getValue();
                    userMsg.getChildren().add(createMessageWidget(m, d));
                }
                break;
            }
        }
    }

    static final String MSG_STYLE = "-fx-background-color: white; "
            + "-fx-border-color: black; -fx-border-width: 1;"
            + "-fx-border-radius: 10px;"
            + "-fx-background-radius: 10px;"
            + "-fx-padding: 8px; "
            + "-fx-margin: 5px; ";


    private VBox createMessageWidget(
            final Message m,
            final MessageData d) {
        VBox msgBox = new VBox();

        String bookmarkText;
        boolean bookmarked = d.isBookmarked();
        if (bookmarked) {
            bookmarkText = "⭐";
        } else {
            bookmarkText = "Click to bookmark";
        }
        Button bookButton = new Button(bookmarkText);
        bookButton.setOnAction(e -> {
            if (bookButton.getText().equals("Click to bookmark")) {
                bookButton.setText("⭐");
            } else {
                bookButton.setText("Click to bookmark");
            }
            sortMessages();
        });
        msgBox.getChildren().add(bookButton);


        final Label label = new Label(m.getContent());
        msgBox.getChildren().add(label);

        final Label dateLabel = new Label(m.getDate());
        dateLabel.setTextFill(Color.RED);
        msgBox.getChildren().add(dateLabel);
        //System.out.println(m.getDate());


        final Label score = new Label("Score: " + d.getScore());
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
                publish(t, u);
                t.clear();
            }
        });
        Button s = new Button("Publish");
        s.setOnAction(e -> {
            publish(t, u);
            t.clear();
        });
        input.getChildren().addAll(t, s);
        return input;
    }

    private void publish(final TextArea t, final User u) {
        addMessage(new Message(t.getText()));
    }

    private void sortMessages() {
        for (User u : model.getUsers()) {
            LinkedHashMap<Message, MessageData> messageList = getMessageList(u);
            new BookmarkScoring().computeScores(messageList);
            LinkedHashMap<Message, MessageData> sortedMessageList = new LinkedHashMap<>();
            messageList.entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder((Entry<Message, MessageData> left,
                            Entry<Message, MessageData> right) -> {
                        return left.getValue().compare(right.getValue());
                    }))
                    .forEach((Entry<Message, MessageData> e) -> {
                        sortedMessageList.put(e.getKey(), e.getValue());
                    });
            setMessageList(sortedMessageList, u);
        }
    }
}
