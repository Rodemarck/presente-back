package rode.presente.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rode.presente.database.dao.StaffDao;
import rode.presente.database.dao.UserDao;
import rode.presente.model.Staff;
import rode.presente.model.User;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("user")
@CrossOrigin(origins = "http://localhost:3000")
@Validated
public class UserController {
    @GetMapping
    public CompletableFuture<LinkedList<User>> list() throws SQLException {
        return CompletableFuture.completedFuture(UserDao.list());
    }

    @GetMapping("/{id}")
    public CompletableFuture<User> getById(@PathVariable int id) throws SQLException {
        return CompletableFuture
                .completedFuture(UserDao.getById(id));
    }
}
