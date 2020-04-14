package rode.presente.database.dao;

import rode.presente.database.Connection;
import rode.presente.model.Staff;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.SQLException;
import java.util.LinkedList;

public final class StaffDao {
    public static Staff getById(int id) throws SQLException {
        LinkedList<Staff> list = new LinkedList<>();
        Connection.queryCRS("SELECT * FROM news n\n" +
                                "    INNER JOIN staff s\n" +
                                "    ON n.author = s.id\n" +
                                "        INNER JOIN role_staff rs\n" +
                                "        ON s.id = rs.staff_id\n" +
                                "            INNER JOIN role r\n" +
                                "            ON rs.role_id = r.id\n" +
                                "WHERE n.id=?",
                new Object[]{id},
                crs -> {
                    list.addFirst(new Staff(crs));
                }
        );
        return list.getFirst();
    }

    public static LinkedList<Staff> list() throws SQLException{
        LinkedList<Staff> list = new LinkedList<>();
        Connection.queryCRS("SELECT * FROM staff\n" +
                            "    INNER JOIN role_staff\n" +
                            "    ON staff.id = role_staff.staff_id\n" +
                            "        INNER JOIN role \n" +
                            "        ON role_staff.role_id = role.id",
                null,
                crs -> {
                    crs.next();
                    do {
                        crs.previous();
                        list.add(new Staff(crs));
                    }while (crs.next());
                });
        return list;
    }


    private StaffDao(){}
}
