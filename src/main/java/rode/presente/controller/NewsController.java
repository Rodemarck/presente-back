package rode.presente.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rode.presente.database.dao.NewsDao;
import rode.presente.database.dao.StaffDao;
import rode.presente.model.New;
import rode.presente.model.Staff;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("news")
@CrossOrigin(origins = "http://localhost:3000")
@Validated
public class NewsController {
    @GetMapping
    public CompletableFuture<LinkedList<New>> list() throws SQLException {
        return CompletableFuture.completedFuture(NewsDao.list());
    }
    
    @GetMapping("/{id}")
    public CompletableFuture<New> getById(@PathVariable int id) throws SQLException {
        return CompletableFuture
                .completedFuture(NewsDao.getById(id));
    }
}
