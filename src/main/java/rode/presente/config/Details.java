package rode.presente.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import rode.presente.database.dao.UserDao;
import rode.presente.model.User;

import java.sql.SQLException;
import java.util.List;

@Component
public class Details implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            if(username.equalsIgnoreCase("usuario")){
                System.out.println("logando como usuario");
                List<GrantedAuthority> grantedAuthority = AuthorityUtils.createAuthorityList("USER");
                return new org.springframework.security.core.userdetails.User("usuario", "$2a$10$.ogq62ddFiSeLVHzQ32DU.zyx.4QEX8iukdSHrv9KAe1u/.e36tOu", grantedAuthority);
            }
            if(username.equalsIgnoreCase("admin")){
                List<GrantedAuthority> grantedAuthority = AuthorityUtils.createAuthorityList("USER","ADMIN");
                return new org.springframework.security.core.userdetails.User("usuario", "$2a$10$rCyD4pIOyl6bie08CmrDAO/pQQAI9SwVsBVNCiO2F39dozOIAFneu", grantedAuthority);
            }
            throw new SQLException("trolador vc ein!!![" + username + "]");
        } catch (SQLException e) {
            System.out.println("login erro :" + e.getMessage());
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
