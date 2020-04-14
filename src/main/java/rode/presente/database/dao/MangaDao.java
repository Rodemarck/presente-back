package rode.presente.database.dao;

import rode.presente.database.Connection;
import rode.presente.model.Manga;

import javax.sql.rowset.CachedRowSet;
import java.sql.Blob;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;

public final class MangaDao {
    public static void insert(Manga manga) throws SQLException {
        Connection.update("INSERT INTO manga" +
                          "(name, altenativeName, image, genres, author, artist, publisher, active, progress, chapter) VALUES \n" +
                          "(?,?,?,?,?,?,?,?,?,?);",
                new Object[]{
                        manga.getName(),
                        manga.getAlternativeName(),
                        manga.getImage(),
                        manga.getGenres(),
                        manga.getAuthor(),
                        manga.getArtist(),
                        manga.getPublisher(),
                        manga.isActive(),
                        manga.getProgress(),
                        manga.getChapter()
                },
                null);
    }

    public static Manga getById(int id) throws SQLException {
        LinkedList<Manga> list = new LinkedList<>();
        Connection.queryCRS("SELECT * FROM manga WHERE id=?",
                new Object[]{id},
                crs -> list.addFirst(new Manga(crs))
        );
        return list.getFirst();
    }

    public static byte[] getImage(int id) throws SQLException {
        LinkedList<byte[]> list = new LinkedList<>();
        Connection.query("SELECT image FROM manga WHERE id=?",
                new Object[]{id},
                (rs, rows) -> {
                    System.out.println("entrando");
                    list.addFirst(rs.getBytes(1));
                }

        );
        System.out.println(list.size());
        return list.getFirst();
    }

    private MangaDao(){}

    public static LinkedList<Manga> list() throws SQLException {
        LinkedList<Manga> list = new LinkedList<>();
        Connection.queryCRS("SELECT * FROM manga WHERE id=?",
                null,
                crs ->{
                    crs.next();
                    do{
                        crs.previous();
                        list.addFirst(new Manga(crs));
                    }while (crs.next());
                }
        );
        return list;
    }

    public static byte[] getMangaImage(int id) throws SQLException {
        LinkedList<byte[]> list = new LinkedList<>();
        Connection.query("select img from images i\n" +
                         "    inner join\n" +
                         "    manga m on i.id = m.image\n" +
                         "where m.id=?",
                new Object[]{id},
                (rs, rows) -> list.addFirst(rs.getBytes(1))
        );
        return list.getFirst();
    }
    public static byte[] getMangaCharacterImage(int id) throws SQLException {
        LinkedList<byte[]> list = new LinkedList<>();
        Connection.query("select img from images i\n" +
                         "    inner join\n" +
                         "    manga m on i.id = m.imageCharacter\n" +
                         "where m.id=1",
                new Object[]{id},
                (rs, rows) -> list.addFirst(rs.getBytes(1))
        );
        return list.getFirst();
    }
}
