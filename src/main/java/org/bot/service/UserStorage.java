package org.bot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.bot.model.UserSettings;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserStorage {
    private final ObjectMapper objectMapper;
    private final String usersJsonPath = "model/user.json";

    public UserStorage() {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    public List<UserSettings> getUsers() {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(usersJsonPath);
            UserSettings[] usersArray = objectMapper.readValue(inputStream, UserSettings[].class);
            return new ArrayList<>(Arrays.asList(usersArray));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void saveUsers(List<UserSettings> userSettings) {
        try {
            OutputStream outputStream = new FileOutputStream(new File(getClass().getClassLoader().getResource(usersJsonPath).getFile()));
            objectMapper.writeValue(outputStream, userSettings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}