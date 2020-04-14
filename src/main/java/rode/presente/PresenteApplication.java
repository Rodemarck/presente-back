package rode.presente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.unit.DataSize;
import rode.presente.database.Connection;
import rode.presente.database.dao.MangaDao;
import rode.presente.database.dao.StaffDao;
import rode.presente.model.Manga;

import javax.servlet.MultipartConfigElement;
import java.io.*;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class PresenteApplication {

    public static void main(String[] args) throws IOException, SQLException {
        //System.out.println(Contants.bCrypt.encode("os mamilos de fe sao so melhores"));
        //System.out.println(LocalDateTime.now());
        //StaffDao.list().stream().forEach(System.out::println);
        /*MangaDao.insert(new Manga(0,
                "nome",
                "nomeAlternativo",
                "generos",
                "aturor",
                "artista",
                "editora",
                "sinopse",
                read("01.jpg"),
                true,
                0,
                0)
        );
        //System.out.println(MangaDao.getImage(1).length);
        //System.out.println(read("01.jpg").length);
        //Connection.create();
        */
        SpringApplication.run(PresenteApplication.class, args);


    }

    private static void cu(){

    }

    private static byte[] read(String path) throws IOException {
        File f = new File(path);
        FileInputStream fis = new FileInputStream(f);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for(int len; ((len = fis.read(buffer)) != -1);)
            baos.write(buffer, 0, buffer.length);

        return baos.toByteArray();
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofMegabytes(20));
        factory.setMaxRequestSize(DataSize.ofMegabytes(20));
        return factory.createMultipartConfig();
    }
    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("lampada-");
        executor.initialize();
        return executor;
    }

}
