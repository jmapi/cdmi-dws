package pw.cdmi.cse.demo.jersey.filters;

import javax.ws.rs.container.*;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@PreMatching
@Provider
public class CorsSupportFilter implements ContainerResponseFilter, ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext reqCtx, ContainerResponseContext respCtx) {
        MultivaluedMap<String, Object> headers = respCtx.getHeaders();
        headers.putSingle("Access-Control-Allow-Origin", "*");
        headers.putSingle("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        
        String reqHead = reqCtx.getHeaderString("Access-Control-Request-Headers");

        if (null != reqHead) {
            headers.putSingle("Access-Control-Allow-Headers", reqHead);
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (requestContext.getMethod().equals("OPTIONS")) {
            requestContext.abortWith(Response.ok().build());
        }
    }


}