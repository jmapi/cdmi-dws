package pw.cdmi.cse.demo.jersey.filters;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.ext.Provider;
import java.util.Locale;
import java.util.Map;

@PreMatching
@Provider
public class LocaleChangeFilter implements ContainerRequestFilter {
    @Inject
    private LocaleResolver localeResolver;
    @Inject
    private LocaleChangeInterceptor localeChangeInterceptor;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        Map<String, Cookie> cookies = containerRequestContext.getCookies();
        String cookieName = ((CookieLocaleResolver) localeResolver).getCookieName();
        String locale = "zh_CN";
        if (cookies.containsKey(cookieName)) {
            for (String key : cookies.keySet()) {
                if ("locale".equals(key)) {
                    locale = cookies.get(key).getValue();
                    break;
                }
            }
        }
        String paramName = localeChangeInterceptor.getParamName();
        String lang = containerRequestContext.getUriInfo().getQueryParameters().getFirst(paramName);
        if (lang != null) {
            locale = lang;
        }
        String[] arr = locale.split("_");
        LocaleContextHolder.setLocale(new Locale(arr[0], arr[1]));
    }

}
