package rode.presente.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import rode.presente.database.Connection;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@AllArgsConstructor
public class Manga {
    private int id;
    private String name;
    private String alternativeName;
    private String genres;
    private String author;
    private String artist;
    private String publisher;
    private String synopsis;
    private byte[] image;
    private boolean active;
    private double progress;
    private double chapter;

    public Manga(CachedRowSet crs) throws SQLException {
        Connection.DataGetter getter = new Connection.DataGetter(crs, "manga");
        this.id = getter.getInt("id");
        this.name = getter.getString("name");
        this.image = getter.getBytes("image");
        this.genres = getter.getString("genres");
        this.author = getter.getString("author");
        this.artist = getter.getString("artist");
        this.publisher = getter.getString("publisher");
        this.synopsis = getter.getString("synopsis");
        this.alternativeName = getter.getString("alternativeName");
        this.active = getter.getBoolean("active");
        this.progress = getter.getDouble("progress");
        this.chapter = getter.getDouble("chapter");
        crs.next();
    }
}
