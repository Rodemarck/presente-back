package rode.presente.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import rode.presente.database.Connection;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@AllArgsConstructor
public class Chapter {
    private int id;
    private double number;
    private Manga manga;
    private Staff translator;
    private Staff typesetter;
    private Staff cleaner;
    private Staff reviser;
    private Staff redraw;
    private String imgur;
    private String union;
    private String media;

    public Chapter(CachedRowSet crs) throws SQLException{
        Connection.DataGetter getter = new Connection.DataGetter(crs, "chapter");
        int i_translator = getter.getInt("translator");
        int i_typesetter = getter.getInt("typesetter");
        int i_cleaner = getter.getInt("cleaner");
        int i_reviser = getter.getInt("reviser");
        int i_redraw = getter.getInt("redraw");

        this.id = getter.getInt("id");
        this.number = getter.getInt("number");
        this.manga = new Manga(crs.createCopy());
        this.imgur = getter.getString("imgur_link");
        this.union = getter.getString("union_link");
        this.media = getter.getString("media_link");

        this.translator = getStaffAndMoveCursor(crs, i_translator);
        this.typesetter = getStaffAndMoveCursor(crs, i_typesetter);
        this.cleaner = getStaffAndMoveCursor(crs, i_cleaner);
        this.reviser = getStaffAndMoveCursor(crs, i_reviser);
        this.redraw = getStaffAndMoveCursor(crs, i_redraw);

        while (getter.getInt("id") == this.id && !crs.isLast())
            crs.next();
    }

    private Staff getStaffAndMoveCursor(CachedRowSet crs, int value) throws SQLException {
        Staff staff = new Staff(crs);
        Connection.DataGetter getter = new Connection.DataGetter(crs, "chapter");
        while(getter.getInt("id") == value && !crs.isLast())
            crs.next();
        return staff;
    }
}
