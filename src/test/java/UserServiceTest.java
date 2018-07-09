import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.game.service.UserServiceImpl;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

public class UserServiceTest {
    @Test
    void regUserNormal() {
        var userService = new UserServiceImpl(new UserDaoTest());
        Assertions.assertDoesNotThrow(() -> userService.registerUser("username", "123123"));
    }

    @Test
    void regWithNullUsername() {
        var userService = new UserServiceImpl(new UserDaoTest());
        Assertions.assertDoesNotThrow(() -> userService.registerUser(null, "123123"));
    }

    @Test
    void regWithEmptyUsername() {
        var userService = new UserServiceImpl(new UserDaoTest());
        Assertions.assertDoesNotThrow(() -> userService.registerUser(null, "123123"));
    }

    @Test
    void regWithNullPass() {
        var userService = new UserServiceImpl(new UserDaoTest());
        Assertions.assertThrows(AssertionError.class, () -> userService.registerUser("username", null));
    }

    @Test
    void regWithNullEmpty() {
        var userService = new UserServiceImpl(new UserDaoTest());
        Assertions.assertThrows(AssertionError.class, () -> userService.registerUser("username", ""));
    }

    @Test
    void regWithNullAll() {
        var userService = new UserServiceImpl(new UserDaoTest());
        Assertions.assertThrows(AssertionError.class, () -> userService.registerUser(null, null));
    }

    @Test
    void saveCookiesNormal() {
        var userService = new UserServiceImpl(new UserDaoTest());
        Assertions.assertDoesNotThrow(() -> userService.saveCookies("username"));
    }

    @Test
    void saveCookiesNull() {
        var userService = new UserServiceImpl(new UserDaoTest());
        Assertions.assertDoesNotThrow(() -> userService.saveCookies(null));
    }

    @Test
    void saveCookiesEmpty() {
        var userService = new UserServiceImpl(new UserDaoTest());
        Assertions.assertDoesNotThrow(() -> userService.saveCookies(""));
    }

    @Test
    void authByCookiesNormal() {
        var userService = new UserServiceImpl(new UserDaoTest());
        Assertions.assertNotNull(userService.authByCookies(
                new Cookie[]{new Cookie("username", "username"), new Cookie("token", "token_value")}));
    }

    @Test
    void authByCookiesWithoutUsername() {
        var userService = new UserServiceImpl(new UserDaoTest());
        Assertions.assertNotNull(userService.authByCookies(
                new Cookie[]{new Cookie("username", ""), new Cookie("token", "token_value")}));
    }


    @Test
    void authByCookiesUsernameNull() {
        var userService = new UserServiceImpl(new UserDaoTest());
        Assertions.assertNull(userService.authByCookies(
                new Cookie[]{new Cookie("username", null), new Cookie("token", "token_value")}));
    }


    @Test
    void authByCookiesTokenEmpty() {
        var userService = new UserServiceImpl(new UserDaoTest());
        Assertions.assertNull(userService.authByCookies(
                new Cookie[]{new Cookie("username", "username"), new Cookie("token", "")}));
    }


    @Test
    void authByCookiesTokenNull() {
        var userService = new UserServiceImpl(new UserDaoTest());
        Assertions.assertNull(userService.authByCookies(
                new Cookie[]{new Cookie("username", "username"), new Cookie("token", null)}));
    }


    @Test
    void authByCookiesValuesNull() {
        var userService = new UserServiceImpl(new UserDaoTest());
        Assertions.assertNull(userService.authByCookies(
                new Cookie[]{new Cookie("username", null), new Cookie("token", null)}));
    }


    @Test
    void invalidCookiesNormal() {
        var userService = new UserServiceImpl(new UserDaoTest());
        Assertions.assertDoesNotThrow(() -> userService.invalidCookies("username"));
    }


    @Test
    void invalidCookiesEmpty() {
        var userService = new UserServiceImpl(new UserDaoTest());
        Assertions.assertThrows(AssertionError.class, () -> userService.invalidCookies(""));
    }


    @Test
    void invalidCookiesNull() {
        var userService = new UserServiceImpl(new UserDaoTest());
        Assertions.assertThrows(AssertionError.class, () -> userService.invalidCookies(null));
    }
}
