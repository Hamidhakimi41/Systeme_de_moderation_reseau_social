package fr.univ_lyon1.info.m1.microblog.InitialConfiguration;

import com.google.gson.Gson;
import fr.univ_lyon1.info.m1.microblog.model.Y;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;

/**
 * Class to load the initial configuration of the application from a JSON file.
 */
public final class InitialConfiguration {

    private final Y y;

    /**
     * Constructor.
     *
     * @param y the Y object to configure.
     */
    public InitialConfiguration(final Y y) {
        this.y = y;
    }

    /**
     * Load the configuration from a JSON file.
     *
     * @param filePath the path to the JSON file.
     */
    public void loadConfiguration(final String filePath) {
        System.out.println("Loading configuration from: " + filePath);
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new FileNotFoundException("Resource not found: " + filePath);
            }

            try (Reader reader = new InputStreamReader(inputStream)) {
                Gson gson = new Gson();
                Config config = gson.fromJson(reader, Config.class);

                //System.out.println("Number of users loaded: " + config.users.size());
                for (UserConfig userConfig : config.users) {
                    //System.out.println("Creating user with ID: " + userConfig.id);
                    y.createUser(userConfig.id);
                }

                for (UserConfig userConfig : config.users) {
                    for (String message : userConfig.messages) {
                        y.add(null, message); // Add message to all users
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading configuration: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Configuration class for Gson.
     */
    private static final class Config {
        private List<UserConfig> users;

        public void setUsers(final List<UserConfig> users) {
            this.users = users;
        }

        public List<UserConfig> getUsers() {
            return users;
        }
    }

    /**
     * User configuration class for Gson.
     */
    private static final class UserConfig {
        private String id;


        private List<String> messages;

        public void setId(final String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setMessages(final List<String> messages) {
            this.messages = messages;
        }

        public List<String> getMessages() {
            return messages;
        }
    }
}
