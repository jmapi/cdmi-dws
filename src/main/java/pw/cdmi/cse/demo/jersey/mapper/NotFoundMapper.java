package pw.cdmi.cse.demo.jersey.mapper;

import org.springframework.http.HttpStatus;
import pw.cdmi.cse.demo.common.MessageSourceService;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Provider
public class NotFoundMapper implements ExceptionMapper<NotFoundException> {

    @Inject
    private MessageSourceService messageSourceService;

    @Override
    public Response toResponse(NotFoundException e) {
        Map<String, Object> errorInfo = new HashMap<>();
        errorInfo.put("timestamp", (new Date().getTime()));
        errorInfo.put("status", HttpStatus.NOT_FOUND.value());
        errorInfo.put("error", messageSourceService.getMessage("sys.error.notfound"));
        errorInfo.put("message", e.getMessage());
        return Response.status(Response.Status.NOT_FOUND).
                entity(errorInfo).
                type(MediaType.APPLICATION_JSON).
                build();
    }
}
