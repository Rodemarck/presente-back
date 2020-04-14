package rode.presente.config;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.Arrays;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] ARTEFATOS  =  {
            "/css/**",
            "/img/**",
            "/fonts/**",
            "/js/**",
            "/components/**",
            "/templates/**",
            "/resources/**"
    };

    @Autowired
    private Details details;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)throws Exception{
        auth.inMemoryAuthentication()
                .withUser("user")
                    .password("users")//.password("$2a$10$.ogq62ddFiSeLVHzQ32DU.zyx.4QEX8iukdSHrv9KAe1u/.e36tOu")
                    .roles("USER").and()
                .withUser("admin")
                    .password("admin")
                //.password("$2a$10$rCyD4pIOyl6bie08CmrDAO/pQQAI9SwVsBVNCiO2F39dozOIAFneu")
                    .roles("USER","ADMIN")

        ;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return charSequence.toString().equals(s);
            }
        };
    }

    @Override
    protected void configure(HttpSecurity https) throws Exception {
        https
                .httpBasic().and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/image/**","/chapter/**", "/manga/**", "/staff/**", "/news/**")
                        .hasRole("USER")
                    .antMatchers(HttpMethod.GET, "/user/**")
                        .hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST,"/user/**","/image/**","/chapter/**", "/manga/**", "/staff/**", "/news/**")
                        .hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE,"/user/**","/image/**","/chapter/**", "/manga/**", "/staff/**", "/news/**")
                        .hasRole("ADMIN")
                    .antMatchers(ARTEFATOS)
                        .permitAll().and()
                .formLogin()
                    .disable()
                .csrf()
                    .disable();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "OPTIONS"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
