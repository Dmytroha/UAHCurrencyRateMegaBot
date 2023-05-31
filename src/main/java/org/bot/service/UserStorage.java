package org.bot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.bot.model.UserSettings;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserStorage {
    private ObjectMapper objectMapper;
    private String usersJsonPath = "model/user.json";

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
            OutputStream outputStream = new FileOutputStream(
                    new File(getClass().getClassLoader().getResource(usersJsonPath).getFile()));
            objectMapper.writeValue(outputStream, userSettings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Запис нового юзера використовуючи id
    public void writeNewUser(String id) throws IOException {
        List<UserSettings> users = getUserList();
        UserSettings UserSettings = new UserSettings(id);
        users.add(UserSettings);
        Gson gson = new Gson();
        String json = gson.toJson(users);
        try (FileWriter writer = new FileWriter("src/main/resources/model/user.json")) {
            writer.write(json);
        }
    }

    // Перезаписати юзера. Передавати об'єкт юзера
    public void rewriteUser(UserSettings UserSettings){
        List<UserSettings> users = getUserList();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(UserSettings.getId()))
                users.set(i, UserSettings);
        }
        Gson gson = new Gson();
        String json = gson.toJson(users);
        try (FileWriter writer = new FileWriter("src/main/resources/model/user.json")) {
            writer.write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeUsers(String id) throws IOException {
        List<UserSettings> users = getUserList();
        Gson gson = new Gson();
        String json = gson.toJson(users);
        try (FileWriter writer = new FileWriter("src/main/resources/model/user.json")) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // отримати список юзерів
    public List<UserSettings> getUserList() {
        FileReader reader;
        Gson gson = new Gson();
        try {
            reader = new FileReader("src/main/resources/model/user.json");
            Type listType = new TypeToken<List<UserSettings>>() {
            }.getType();
            List<UserSettings> users = gson.fromJson(reader, listType);
            return users;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    // Отримати юзера, повертає об'єкт
    public UserSettings getUser(String id) {
        List<UserSettings> users = getUserList();
        UserSettings val = null;
        for (UserSettings user : users) {
            if (user.getId().equals(id)) {
                val = user;
                break;
            }
        }
        return val;
    }

    // Перевірка, чи новий юзер. Приймає id
    public boolean isNewPerson(String id) {
        List<UserSettings> users = getUserList();
        boolean val = true;
        for (int i = 0; i < users.size(); i++) {
            System.out.println(users.get(i).getId());
            if (users.get(i).getId().equals(id)) {
                val = false;
                break;
            }
        }
        return val;
    }
}