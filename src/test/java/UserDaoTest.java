import ru.game.dao.UserDao;
import ru.game.entity.User;

public class UserDaoTest implements UserDao {
    @Override
    public User findUserByUsername(String username) {
        return new User(1, username, "password", "token_value");
    }

    @Override
    public boolean addCookieToken(String username, String token) {
        return true;
    }

    @Override
    public User createUser(String username, String password) {
        return new User(1, username, "password", "token_value");
    }

    @Override
    public void invalidateCookieToken(String username) {
    }
}
