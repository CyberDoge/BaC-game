import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.game.dao.UserDaoImpl;
import ru.game.util.DbUtil;

import java.io.File;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DataBaseTest {
    private static final String CLEAR_TABLE = "truncate user";

    @BeforeAll
    static void init() {
        File file = new File("src/test/resources/db-test.properties");
        assertDoesNotThrow(() -> DbUtil.init(file));
        clearTable();
    }

    @Test
    void simpleInsert() {
        var userDao = new UserDaoImpl();
        assertNotNull(userDao.createUser("NormalTest1", "unhashed password"));
    }

    @Test
    void nameEq40length() {
        var userDao = new UserDaoImpl();
        StringBuilder longName = new StringBuilder();
        for (int i = 0; i < 40; i++) {
            longName.append('a');
        }
        assertNotNull(userDao.createUser(longName.toString(), "unhashed password"));
    }

    @Test
    void nameMore40length() {
        var userDao = new UserDaoImpl();
        StringBuilder longName = new StringBuilder();
        for (int i = 0; i < 41 + (int) (Math.random() * 20); i++) {
            longName.append('b');
        }
        assertNull(userDao.createUser(longName.toString(), "unhashed password"));
    }

    @Test
    void passEq60length() {
        var userDao = new UserDaoImpl();
        StringBuilder longPass = new StringBuilder();
        for (int i = 0; i < 60; i++) {
            longPass.append('c');
        }
        assertEquals(longPass.length(), 60);
        assertNotNull(userDao.createUser("simpleName60lenghtpass", longPass.toString()));
    }

    @Test
    void passMore60length() {
        var userDao = new UserDaoImpl();

        StringBuilder longPass = new StringBuilder();
        for (int i = 0; i < 61 + (int) (Math.random() * 20); i++) {
            longPass.append('a');
        }
        assertNull(userDao.createUser("simpleNamemore50lenghtpass", longPass.toString()));
    }

    @Test
    void addUsersWithEqlNames() {
        var userDao = new UserDaoImpl();

        userDao.createUser("username", "password");
        assertNull(userDao.createUser("username", "123"));
    }

    @Test
    void addUsersWithEqlPass() {
        var userDao = new UserDaoImpl();

        userDao.createUser("usernameEqPass", "password");
        assertNotNull(userDao.createUser("newUserEqPass", "password"));
    }

    @Test
    void addEqlUsers() {
        var userDao = new UserDaoImpl();

        userDao.createUser("usernameEqUser", "password");
        assertNull(userDao.createUser("usernameEqUser", "password"));
    }

    @Test
    void addNullUsername() {
        var userDao = new UserDaoImpl();
        assertThrows(AssertionError.class, () -> userDao.createUser(null, "not null!!"));
    }

    @Test
    void addNullPassword() {
        var userDao = new UserDaoImpl();
        assertThrows(AssertionError.class, () -> userDao.createUser("NotNullUsername", null));
    }

    @Test
    void addNullUser() {
        var userDao = new UserDaoImpl();
        assertThrows(AssertionError.class, () -> userDao.createUser(null, null));
    }


    @Test
    void getUserNotNull() {
        var userDao = new UserDaoImpl();
        userDao.createUser("userForGet", "jooooooooooojo");
        assertNotNull(userDao.findUserByUsername("userForGet"));
    }

    @Test
    void getLongUsername() {
        var userDao = new UserDaoImpl();
        StringBuilder longName = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            longName.append(((char) (i + 10)));
        }
        assertNull(userDao.findUserByUsername(longName.toString()));
    }

    @Test
    void getNoUser() {
        var userDao = new UserDaoImpl();
        assertNull(userDao.findUserByUsername("nouser"));
    }

    @Test
    void getUserEmpty() {
        var userDao = new UserDaoImpl();
        assertNull(userDao.findUserByUsername(""));
    }

    @Test
    void getUserNull() {
        var userDao = new UserDaoImpl();
        assertNull(userDao.findUserByUsername(null));
    }


    @Test
    void addNormCookie() {
        var userDao = new UserDaoImpl();
        userDao.createUser("testToken", "123123123123123");
        assertTrue(userDao.addCookieToken("testToken", "asdasadf"));
    }

    @Test
    void addBigTokenCookie() {
        var userDao = new UserDaoImpl();
        StringBuilder longToken = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            longToken.append(((char) (i + 10)));
        }
        assertEquals(100, longToken.length());
        userDao.createUser("test", "123");
        assertFalse(userDao.addCookieToken("test", longToken.toString()));
    }

    @Test
    void addCookieToNullUser() {
        var userDao = new UserDaoImpl();
        assertFalse(userDao.addCookieToken("NullUserForCookies", "opa"));
    }

    @Test
    void addEmptyOrNullUsernameCookie() {
        var userDao = new UserDaoImpl();
        assertFalse(userDao.addCookieToken(null, "asdasadf"));
        assertFalse(userDao.addCookieToken("", "asdasadf"));
        assertFalse(userDao.addCookieToken("", ""));
        assertFalse(userDao.addCookieToken(null, null));
        assertFalse(userDao.addCookieToken("asd", null));
        assertFalse(userDao.addCookieToken("asd", ""));
    }

    @Test
    void invalidToken() {
        var userDao = new UserDaoImpl();
        userDao.createUser("invalidTokenUsername", "longpass");
        userDao.addCookieToken("invalidTokenUsername", "sdfghjk");
        userDao.invalidateCookieToken("invalidTokenUsername");
    }

    @Test
    void invalidLongUsernameToken() {
        var userDao = new UserDaoImpl();
        StringBuilder longName = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            longName.append(((char) (i + 12)));
        }
        userDao.invalidateCookieToken(longName.toString());
    }

    @Test
    void invalidEmptyToken() {
        var userDao = new UserDaoImpl();
        assertThrows(AssertionError.class, () -> userDao.invalidateCookieToken(null));
        assertThrows(AssertionError.class, () -> userDao.invalidateCookieToken(""));
    }


    @AfterAll
    static void close() {
        clearTable();
        assertDoesNotThrow(DbUtil::close);
    }

    static void clearTable() {
        try (var con = DbUtil.getConnection();
             var s = con.prepareStatement(CLEAR_TABLE)) {
            s.execute();
        } catch (SQLException e) {
            assertEquals("", e);
            System.exit(-1);
        }
    }
}
