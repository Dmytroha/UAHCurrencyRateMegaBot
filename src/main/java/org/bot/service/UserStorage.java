package org.bot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bot.model.UserSettings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserStorage {
    private File storageFile;
    private ObjectMapper objectMapper;

    public UserStorage() {
        this.storageFile = new File(getClass().getClassLoader().getResource("model/users.json").getFile());
        this.objectMapper = new ObjectMapper();
    }

    public List<UserSettings> getUsers() {
        try {
            UserSettings[] usersArray = objectMapper.readValue(storageFile, UserSettings[].class);
            return new ArrayList<>(Arrays.asList(usersArray));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void saveUsers(List<UserSettings> userSettings) {
        try {
            objectMapper.writeValue(storageFile, userSettings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}