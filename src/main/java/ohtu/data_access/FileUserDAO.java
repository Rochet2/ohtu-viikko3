/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.data_access;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import ohtu.domain.User;
import org.springframework.stereotype.Component;

/**
 *
 * @author rimi
 */
public class FileUserDAO implements UserDao {

    private final String filename;

    ArrayList<User> users;

    public FileUserDAO(String filename) {
        this.filename = filename;
        this.users = new ArrayList<User>();
        readUsersToFile(filename);
    }

    protected void readUsersToFile(String filename1) {
        try {
            FileInputStream fileIn = new FileInputStream(filename1);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            users = (ArrayList<User>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("ArrayList<User> class not found");
            c.printStackTrace();
        }
    }

    @Override
    public List<User> listAll() {
        return users;
    }

    @Override
    public User findByName(String name) {
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void add(User user) {
        users.add(user);
        writeUsersToFile();
    }

    protected void writeUsersToFile() {
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(users);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

}
