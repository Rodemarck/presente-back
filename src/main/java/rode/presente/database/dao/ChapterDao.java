package rode.presente.database.dao;

import rode.presente.database.Connection;
import rode.presente.model.Chapter;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.LinkedList;

public final class ChapterDao {
    public static Chapter getById(int id) throws SQLException {
        LinkedList<Chapter> list = new LinkedList<>();
        Connection.queryCRS("SELECT * FROM chapter ch\n" +
                             "    INNER JOIN manga m\n" +
                             "    ON ch.manga_id = m.id\n" +
                             "        INNER JOIN staff s\n" +
                             "        on s.id in (ch.translator_id, ch.typesetter_id, ch.cleaner_id, ch.reviser_id, ch.redraw_id)\n" +
                             "            INNER JOIN role_staff rs\n" +
                             "            ON s.id = rs.staff_id\n" +
                             "                INNER JOIN role r\n" +
                             "                ON rs.role_id = r.id\n" +
                             "WHERE ch.redraw_id=?",
                new Object[]{id},
                crs -> list.add(new Chapter(crs))
        );
        return list.getFirst();
    }

    public static void insert(Chapter chapter) throws SQLException{
        Connection.update("INSERT INTO chapter (number, manga_id, translator_id, typesetter_id, cleaner_id, reviser_id, redraw_id, imgur_link, union_link, media_link) VALUES \n" +
                          "(?,?,?,?,?,?,?,?,?,?)",
                new Object[]{
                        chapter.getNumber(),
                        chapter.getManga().getId(),
                        chapter.getTranslator().getId(),
                        chapter.getTypesetter().getId(),
                        chapter.getCleaner().getId(),
                        chapter.getReviser().getId(),
                        chapter.getRedraw().getId(),
                        chapter.getImgur(),
                        chapter.getUnion(),
                        chapter.getMedia()
                },
                null);
    }


    private ChapterDao(){}
}
