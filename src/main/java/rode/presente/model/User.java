package rode.presente.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import rode.presente.database.Connection;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.LinkedList;

@Data
public class User {
    private int id;
    private String login;
    @JsonIgnore
    private String password;
    private String email;
    private String[] permissions;

    public User(CachedRowSet crs) throws SQLException {
        LinkedList<String> list = new LinkedList<>();
        Connection.DataGetter getter = new Connection.DataGetter(crs, "user");

        this.id = getter.getInt("id");
        this.login = getter.getString("login");
        this.password = getter.getString("password");
        this.email = getter.getString("email");

        while(crs.getInt(Connection.DataGetter.getColumnIndex(crs, "permission_user", "user_id")) == this.id && !crs.isLast()){
            list.add(crs.getString(Connection.DataGetter.getColumnIndex(crs, "permission", "name")));
            crs.next();
        }
        this.permissions = list.toArray(new String[list.size()]);
        while(getter.getInt("id") == this.id && !crs.isLast())
            crs.next();
    }
}
