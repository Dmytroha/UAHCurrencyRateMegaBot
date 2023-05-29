package org.bot.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bot.model.User;
import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class UserStorage {

    // Запис нового юзера використовуючи id
    public void writeNewUser(Long id) throws IOException {
        List<User> users = getUserList();
        User user = new User(id);
        users.add(user);
        Gson gson = new Gson();
        String json = gson.toJson(users);
        try (FileWriter writer = new FileWriter("src/main/resources/model/user.json")) {
            writer.write(json);
        }
    }

    // Перезаписати юзера. Передавати об'єкт юзера
    public void rewriteUser(User user) throws IOException {
        List<User> users = getUserList();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId())
                users.set(i, user);
        }
        Gson gson = new Gson();
        String json = gson.toJson(users);
        try (FileWriter writer = new FileWriter("src/main/resources/model/user.json")) {
            writer.write(json);
        }
    }

    // отримати список юзерів
    public List<User> getUserList() throws IOException {
        FileReader reader = new FileReader("src/main/resources/model/user.json");
        Gson gson = new Gson();
        Type listType = new TypeToken<List<User>>() {
        }.getType();
        List<User> users = gson.fromJson(reader, listType);
        reader.close();
        return users;
    }

    // Отримати юзера, повертає об'єкт
    public User getUser(Long id) throws IOException {
        List<User> users = getUserList();
        User val = null;
        for (User user : users) {
            if (user.getId() == id) {
                val = user;
                break;
            }
        }
        return val;
    }

    // Перевірка, чи новий юзер. Приймає id
    public boolean isNewPerson(Long id) throws IOException {
        List<User> users = getUserList();
        boolean val = true;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                val = false;
                break;
            }
        }
        return val;
    }

    // private ObjectMapper objectMapper;
    // private String usersJsonPath = "model/user.json";

    // public UserStorage() {
    // this.objectMapper = new ObjectMapper();
    // objectMapper.registerModule(new JavaTimeModule());
    // }

    // public List<UserSettings> getUsers() {
    // try {
    // InputStream inputStream =
    // getClass().getClassLoader().getResourceAsStream(usersJsonPath);
    // UserSettings[] usersArray = objectMapper.readValue(inputStream,
    // UserSettings[].class);
    // return new ArrayList<>(Arrays.asList(usersArray));
    // } catch (IOException e) {
    // e.printStackTrace();
    // return new ArrayList<>();
    // }
    // }
    //
    // public void saveUsers(List<UserSettings> userSettings) {
    // try {
    // OutputStream outputStream = new FileOutputStream(new
    // File(getClass().getClassLoader().getResource(usersJsonPath).getFile()));
    // objectMapper.writeValue(outputStream, userSettings);
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }

}