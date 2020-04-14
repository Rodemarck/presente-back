package rode.presente.database.dao;

import rode.presente.database.Connection;
import rode.presente.model.User;

import java.sql.SQLException;
import java.util.LinkedList;

public abstract class UserDao {

    public static User getByLogin(String userName) throws SQLException {
        LinkedList<User> list = new LinkedList<>();
        Connection.queryCRS("SELECT * FROM user\n" +
                                "    INNER JOIN permission_user\n" +
                                "    ON user.id = permission_user.user_id\n" +
                                "WHERE user.login LIKE '%?%'",
                new Object[]{userName},
                crs -> list.addFirst(new User(crs))
        );
        return list.getFirst();
    }
    public static User getById(int id) throws SQLException {
        LinkedList<User> list = new LinkedList<>();
        Connection.queryCRS("SELECT * FROM user\n" +
                            "    INNER JOIN permission_user\n" +
                            "    ON user.id = permission_user.user_id\n" +
                            "WHERE user.id=?",
                new Object[]{id},
                crs -> list.addFirst(new User(crs))
        );
        return list.getFirst();
    }
    public static LinkedList<User> list() throws SQLException {
        LinkedList<User> list = new LinkedList<>();
        Connection.queryCRS("SELECT * FROM user\n" +
                                "    INNER JOIN permission_user\n" +
                                "    ON user.id = permission_user.user_id",
                null,
                crs ->{
                    crs.next();
                    do{
                        crs.previous();
                        list.add(new User(crs));
                    }while (crs.next());
                }
        );
        return list;
    }
}
