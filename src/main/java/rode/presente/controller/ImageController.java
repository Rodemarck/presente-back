package rode.presente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rode.presente.database.dao.MangaDao;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("image")
@CrossOrigin(origins = "http://localhost:3000")
@Validated
public class ImageController {

    @Autowired
    private HttpServletRequest request;

    private CompletableFuture<ResponseEntity<byte[]>> func(byte[] img){
        return CompletableFuture.completedFuture(
                ResponseEntity
                        .ok()
                        .contentType(MediaType.IMAGE_PNG)
                        .body(img)
        );
    }
    @RequestMapping("manga/{id}")
    public CompletableFuture<ResponseEntity<byte[]>> mangaCover(@PathVariable int id) throws SQLException {
        return func(MangaDao.getMangaImage(id));
    }

    @PostMapping("character/{id}")
    public  CompletableFuture<ResponseEntity<byte[]>> postManga(@RequestParam("id") int id, @RequestParam("photo") MultipartFile photo) throws  SQLException {
        return func(MangaDao.getMangaCharacterImage(id));
    }
}
