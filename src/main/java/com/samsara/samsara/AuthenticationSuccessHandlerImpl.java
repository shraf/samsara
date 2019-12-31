package com.samsara.samsara;

import com.samsara.samsara.Securityimp.UserPrincipal;
import com.samsara.samsara.entities.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
public class AuthenticationSuccessHandlerImpl extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        
        HttpSession session=request.getSession();
        System.out.println(authentication.getName());
        session.setAttribute("user",authentication.getName());
        session.setMaxInactiveInterval(-1);
        System.out.println("system time out is:"+session.getMaxInactiveInterval());
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
