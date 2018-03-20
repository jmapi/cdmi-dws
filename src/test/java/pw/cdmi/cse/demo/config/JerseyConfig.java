package pw.cdmi.cse.demo.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import pw.cdmi.cse.demo.jersey.filters.CorsSupportFilter;
import pw.cdmi.cse.demo.jersey.filters.LocaleChangeFilter;

import javax.ws.rs.ApplicationPath;

@Configuration
@ApplicationPath("/api/v1")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        packages("pw.cdmi.cse.demo.jersey");
    }
}
