package rode.presente.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import rode.presente.database.Connection;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.LinkedList;

@Data
@AllArgsConstructor
public class Staff {
    private int id;
    private String name;
    private String[] role;
    private boolean active;


    public Staff(CachedRowSet crs) throws SQLException {
        LinkedList<String> list = new LinkedList<>();
        Connection.DataGetter getter = new Connection.DataGetter(crs, "staff");
        this.id = getter.getInt("id");
        this.name = getter.getString("name");
        this.active = getter.getBoolean("active");

        Connection.DataGetter getter2 = new Connection.DataGetter(crs, "role_staff");
        Connection.DataGetter getter3 = new Connection.DataGetter(crs, "role");
        while (getter2.getInt("staff_id") == id){
            list.add(getter3.getString("name"));
            if(crs.isLast())
                break;
            crs.next();

        }
        this.role = list.toArray(new String[list.size()]);
        while (getter.getInt("id") == this.id && !crs.isLast())
            crs.next();
    }
}
