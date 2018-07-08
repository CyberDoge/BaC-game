import org.junit.jupiter.api.*;
import ru.game.dao.UserDao;
import ru.game.util.DbUtil;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class DataBaseTest {
    private UserDao userDao;

    @BeforeEach
    @DisplayName("init database")
    void init() {
        File file = new File("src/test/resources/db-test.properties");
        assertDoesNotThrow(() -> DbUtil.init(file.getAbsolutePath()));
        userDao = new UserDao();
    }

    @Test
    void simpleInsert() {
        assertNotNull(userDao.createUser("NormalTest1", "unhashed password"));
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
            longName.append('b');
        }
        assertNull(userDao.createUser(longName.toString(), "unhashed password"));
    }

    @Test
    void passEq60length() {
        StringBuilder longPass = new StringBuilder();
        for (int i = 0; i < 60; i++) {
            longPass.append('c');
        }
        assertEquals(longPass.length(), 60);
        assertNotNull(userDao.createUser("simpleName60lenghtpass", longPass.toString()));
    }

    @Test
    void passMore60length() {
        StringBuilder longPass = new StringBuilder();
        for (int i = 0; i < 61 + (int) (Math.random() * 20); i++) {
            longPass.append('a');
        }
        assertNull(userDao.createUser("simpleNamemore50lenghtpass", longPass.toString()));
    }

    @Test
    void addUsersWithEqlNames() {
        userDao.createUser("username", "password");
        assertNull(userDao.createUser("username", "123"));
    }

    @Test
    void addUsersWithEqlPass() {
        userDao.createUser("usernameEqPass", "password");
        assertNotNull(userDao.createUser("newUserEqPass", "password"));
    }

    @Test
    void addEqlUsers() {
        userDao.createUser("usernameEqUser", "password");
        assertNull(userDao.createUser("usernameEqUser", "password"));
    }


    @AfterAll
    static void close() {
//        userDao = null;
        assertDoesNotThrow(DbUtil::close);
    }
}
