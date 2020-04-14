package rode.presente.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rode.presente.database.dao.StaffDao;
import rode.presente.model.Staff;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("staff")
@CrossOrigin(origins = "http://localhost:3000")
@Validated
public class StaffController {

    @GetMapping
    public CompletableFuture<LinkedList<Staff>> list() throws SQLException {
        return CompletableFuture.completedFuture(StaffDao.list());
    }

    @GetMapping("/{id}")
    public CompletableFuture<Staff> getById(@PathVariable int id) throws SQLException {
        return CompletableFuture
                .completedFuture(StaffDao.getById(id));
    }
}
