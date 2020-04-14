package rode.presente;

import com.google.gson.Gson;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Contants {
    public static Gson gson = new Gson();
    public static BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
}
