package rode.presente.config;

import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import rode.presente.database.dao.UserDao;
import rode.presente.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;

public class LoginHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String userName = "";
        if(authentication.getPrincipal() instanceof Principal) {
            userName = ((Principal)authentication.getPrincipal()).getName();

        }else {
            userName = ((org.springframework.security.core.userdetails.User)authentication.getPrincipal()).getUsername();
        }
        System.out.println("parabens por logar " + userName);
        HttpSession session = httpServletRequest.getSession();
        try {
            User user = UserDao.getByLogin(userName);
            session.setAttribute("login", userName);
            session.setAttribute("conta", user);
            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/");
        } catch (SQLException  e) {
            e.printStackTrace();
        }
    }

    protected void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        System.out.println("erro mamãe");
        try{
            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/");
        }catch (IOException ee){
            System.out.println(ee.getMessage());
        }
    }
}