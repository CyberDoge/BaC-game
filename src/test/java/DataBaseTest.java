import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.game.dao.UserDao;
import ru.game.util.DbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DataBaseTest {
    private Connection connection;
    private UserDao userDao;

    @BeforeAll
    @DisplayName("init database")
    public void init() {
        assertDoesNotThrow(() -> DbUtil.init("/db-test.properties"));
        userDao = new UserDao();
    }

    @Test
    void simpleInsert() {
        assertNotNull(userDao.createUser("SimpleName", "unhashed password"));
    }

    @Test
    void nameEq40length() {
        StringBuilder longName = new StringBuilder();
        for (int i = 0; i < 40; i++) {
            longName.append('a');
        }
        assertNotNull(userDao.createUser(longName.toString(), "unhashed password"));
    }

    @Test
    void nameMore40length() {
        StringBuilder longName = new StringBuilder();
        for (int i = 0; i < 41 + (int) (Math.random() * 20); i++) {
            longName.append('a');
        }
        assertNull(userDao.createUser(longName.toString(), "unhashed password"));
    }

    @Test
    void passEq60length() {
        StringBuilder longPass = new StringBuilder();
        for (int i = 0; i < 60; i++) {
            longPass.append('a');
        }
        assertNotNull(userDao.createUser("simpleName", longPass.toString()));
    }

    @Test
    void passMore60length() {
        StringBuilder longPass = new StringBuilder();
        for (int i = 0; i < 61 + (int) (Math.random() * 20); i++) {
            longPass.append('a');
        }
        assertNull(userDao.createUser("simpleName", longPass.toString()));
    }

    @Test
    void addUsersWithEqlNames() {
        userDao.createUser("username", "password");
        assertNull(userDao.createUser("username", "123"));
    }

    @Test
    void addUsersWithEqlPass() {
        userDao.createUser("username", "password");
        assertNotNull(userDao.createUser("newUser", "password"));
    }

    @Test
    void addEqlUsers() {
        userDao.createUser("username", "password");
        assertNull(userDao.createUser("username", "password"));
    }
}
