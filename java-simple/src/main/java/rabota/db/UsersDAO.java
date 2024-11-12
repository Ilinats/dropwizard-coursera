package rabota.db;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import rabota.core.User;

public interface UsersDAO {
    @SqlUpdate("CREATE TABLE IF NOT EXISTS users (" +
            "username VARCHAR(50) NOT NULL UNIQUE, " +
            "password VARCHAR(50) NOT NULL, " +
            "email VARCHAR(50) NOT NULL UNIQUE)")
    void createUsersTable();

    @SqlUpdate("INSERT INTO users (username, password, email) VALUES (:username, :password, :email)")
    void insertUser(@Bind("username") String username, @Bind("password") String password, @Bind("email") String email);

    @SqlQuery("SELECT * FROM users WHERE username = :username")
    User getUserByUsername(@Bind("username") String username);
}
