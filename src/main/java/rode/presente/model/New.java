package rode.presente.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import rode.presente.database.Connection;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class New {
    private int id;
    private int type;
    private String title;
    private String content;
    private String url;
    private LocalDateTime postDate;
    private Staff author;

    public New(CachedRowSet crs) throws SQLException {
        Connection.DataGetter getter = new Connection.DataGetter(crs, "news");
        this.id = getter.getInt("id");
        this.type = getter.getInt("type");
        this.title = getter.getString("title");
        this.content = getter.getString("content");
        this.url = getter.getString("url");
        this.postDate = LocalDateTime.parse(getter.getString("postDate"));
        this.author = new Staff(crs.createCopy());
        crs.next();
    }
}