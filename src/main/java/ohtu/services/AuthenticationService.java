package ohtu.services;

import ohtu.domain.User;
import java.util.ArrayList;
import java.util.List;
import ohtu.data_access.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationService {

    private UserDao userDao;

    @Autowired
    public AuthenticationService(@Value("#{fileuserdao}") UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) {
        for (User user : userDao.listAll()) {
            if (user.hasCredentials(username, password)) {
                return true;
            }
        }
        return false;
    }

    public boolean createUser(String username, String password) {
        if (userDao.findByName(username) != null)
            return false;
        if (invalid(username, password))
            return false;
        userDao.add(new User(username, password));
        return true;
    }

    private boolean invalid(String username, String password) {
        if (invalidUsername(username))
            return true;
        return invalidPassword(password);
    }

    public boolean invalidPassword(String password) {
        if (password.length() < 8)
            return true;
        return !password.matches(".*[^A-Za-z].*");
    }

    public boolean invalidUsername(String username) {
        if (username.length() < 3)
            return true;
        return !username.matches("[a-z]*");
    }
}
