import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.game.dao.UserDao;
import ru.game.util.DbUtil;

import java.io.File;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DataBaseTest {
    private static final String CLEAR_TABLE = "truncate user";
    @BeforeAll
    static void clearTable() {
        File file = new File("src/test/resources/db-test.properties");
        assertDoesNotThrow(() -> DbUtil.init(file));
        try (var con = DbUtil.getConnection();
             var s = con.prepareStatement(CLEAR_TABLE)) {
            s.execute();
        } catch (SQLException e) {
            assertEquals("", e);
            System.exit(-1);
        }

    }

    @Test
    void simpleInsert() {
        var userDao = new UserDao();
        assertNotNull(userDao.createUser("NormalTest1", "unhashed password"));
    }

    @Test
    void nameEq40length() {
        var userDao = new UserDao();
        StringBuilder longName = new StringBuilder();
        for (int i = 0; i < 40; i++) {
            longName.append('a');
        }
        assertNotNull(userDao.createUser(longName.toString(), "unhashed password"));
    }

    @Test
    void nameMore40length() {
        var userDao = new UserDao();
        StringBuilder longName = new StringBuilder();
        for (int i = 0; i < 41 + (int) (Math.random() * 20); i++) {
            longName.append('b');
        }
        assertNull(userDao.createUser(longName.toString(), "unhashed password"));
    }

    @Test
    void passEq60length() {
        var userDao = new UserDao();
        StringBuilder longPass = new StringBuilder();
        for (int i = 0; i < 60; i++) {
            longPass.append('c');
        }
        assertEquals(longPass.length(), 60);
        assertNotNull(userDao.createUser("simpleName60lenghtpass", longPass.toString()));
    }

    @Test
    void passMore60length() {
        var userDao = new UserDao();

        StringBuilder longPass = new StringBuilder();
        for (int i = 0; i < 61 + (int) (Math.random() * 20); i++) {
            longPass.append('a');
        }
        assertNull(userDao.createUser("simpleNamemore50lenghtpass", longPass.toString()));
    }

    @Test
    void addUsersWithEqlNames() {
        var userDao = new UserDao();

        userDao.createUser("username", "password");
        assertNull(userDao.createUser("username", "123"));
    }

    @Test
    void addUsersWithEqlPass() {
        var userDao = new UserDao();

        userDao.createUser("usernameEqPass", "password");
        assertNotNull(userDao.createUser("newUserEqPass", "password"));
    }

    @Test
    void addEqlUsers() {
        var userDao = new UserDao();

        userDao.createUser("usernameEqUser", "password");
        assertNull(userDao.createUser("usernameEqUser", "password"));
    }


    @AfterAll
    static void close() {
        try (var con = DbUtil.getConnection();
             var s = con.prepareStatement(CLEAR_TABLE)) {
            s.execute();
        } catch (SQLException e) {
            assertEquals("", e);
        }
        assertDoesNotThrow(DbUtil::close);
    }
}
