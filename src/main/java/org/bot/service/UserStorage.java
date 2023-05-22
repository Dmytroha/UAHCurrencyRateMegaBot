package org.bot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bot.model.User;

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

    public List<User> getUsers() {
        try {
            User[] usersArray = objectMapper.readValue(storageFile, User[].class);
            return new ArrayList<>(Arrays.asList(usersArray));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void saveUsers(List<User> users) {
        try {
            objectMapper.writeValue(storageFile, users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}