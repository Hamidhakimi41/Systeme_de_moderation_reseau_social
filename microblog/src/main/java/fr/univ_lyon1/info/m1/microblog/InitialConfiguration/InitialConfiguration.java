package fr.univ_lyon1.info.m1.microblog.InitialConfiguration;

import com.google.gson.Gson;
import fr.univ_lyon1.info.m1.microblog.model.Y;
import fr.univ_lyon1.info.m1.microblog.model.User;

import java.io.*;
import java.util.List;

/**
 * Class to load the initial configuration of the application from a JSON file.
 */
public class InitialConfiguration {

    private final Y y;

    public InitialConfiguration(Y y) {
        this.y = y;
    }

    /**
     * Load the configuration from a JSON file.
     *
     * @param filePath the path to the JSON file
     */
    public void loadConfiguration(String filePath) {
        System.out.println("Loading configuration from: " + filePath);
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new FileNotFoundException("Resource not found: " + filePath);
            }

            try (Reader reader = new InputStreamReader(inputStream)) {
                Gson gson = new Gson();
                Config config = gson.fromJson(reader, Config.class);

                System.out.println("Number of users loaded: " + config.users.size());
                for (UserConfig userConfig : config.users) {
                    System.out.println("Creating user with ID: " + userConfig.id);
                    y.createUser(userConfig.id);
                }

                for (UserConfig userConfig : config.users) {
                    for (String message : userConfig.messages) {
                        System.out.println("Adding message: " + message);
                        y.add(null, message); // Add message to all users
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading configuration: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Classes to represent the JSON structure
    private static class Config {
        List<UserConfig> users;
    }
    private static class UserConfig {
        String id;
        List<String> messages;
    }
}