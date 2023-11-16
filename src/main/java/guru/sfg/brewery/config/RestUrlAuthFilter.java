package guru.sfg.brewery.config;

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

public class RestUrlAuthFilter extends AbstractRestAuthFilter{

    public RestUrlAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    protected String getUserName(HttpServletRequest request){
        return request.getParameter("apikey");
    }

    protected String getPassword(HttpServletRequest request){
        return request.getParameter("apisecret");
    }
}
