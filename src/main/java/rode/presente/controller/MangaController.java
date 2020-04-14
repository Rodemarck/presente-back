package rode.presente.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rode.presente.database.dao.MangaDao;
import rode.presente.database.dao.StaffDao;
import rode.presente.model.Manga;
import rode.presente.model.Staff;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("manga")
@CrossOrigin(origins = "http://localhost:3000")
@Validated
public class MangaController {
    @GetMapping
    public CompletableFuture<LinkedList<Manga>> list() throws SQLException {
        return CompletableFuture.completedFuture(MangaDao.list());
    }

    @GetMapping("/{id}")
    public CompletableFuture<Manga> getById(@PathVariable int id) throws SQLException {
        return CompletableFuture
                .completedFuture(MangaDao.getById(id));
    }
}
