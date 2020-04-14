package rode.presente.database.dao;

import rode.presente.database.Connection;
import rode.presente.model.New;

import java.sql.SQLException;
import java.util.LinkedList;

public class NewsDao {
    public static LinkedList<New> list() throws SQLException {
        LinkedList<New> list = new LinkedList<>();
        Connection.queryCRS("SELECT * FROM news n\n" +
                            "    INNER JOIN staff s\n" +
                            "    ON n.author = s.id\n" +
                            "        INNER JOIN role_staff rs\n" +
                            "        ON s.id = rs.staff_id\n" +
                            "            INNER JOIN role r\n" +
                            "            ON rs.role_id = r.id",
                null,
                crs ->{
                    crs.next();
                    do{
                        crs.previous();
                        list.add(new New(crs));
                    }while (crs.next());
                });
        return list;
    }
    public static New getById(int id) throws SQLException{
        LinkedList<New> list = new LinkedList<>();
        Connection.queryCRS("SELECT * FROM news n\n" +
                            "    INNER JOIN staff s\n" +
                            "    ON n.author = s.id\n" +
                            "        INNER JOIN role_staff rs\n" +
                            "        ON s.id = rs.staff_id\n" +
                            "            INNER JOIN role r\n" +
                            "            ON rs.role_id = r.id",
                null,
                crs ->list.addFirst(new New(crs))
        );
        return list.getFirst();
    }
}
