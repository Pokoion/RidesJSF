package eredua;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("*.xhtml")
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String url = httpRequest.getRequestURI();

        boolean isLoginOrRegisterPage = url.endsWith("Login.xhtml") || url.endsWith("Register.xhtml");

        LoggedUser sesionUsuario = (session != null) ? (LoggedUser) session.getAttribute("loggedUser") : null;

        if (sesionUsuario == null || !sesionUsuario.isLoggedIn()) {
            if (!isLoginOrRegisterPage) {
            	httpResponse.sendRedirect(httpRequest.getContextPath() + "/faces/Login.xhtml");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}