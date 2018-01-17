package pw.cdmi.cse.demo.jersey.mapper;

import org.springframework.http.HttpStatus;
import pw.cdmi.cse.demo.common.MessageSourceService;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Provider
public class ServiceErrorMapper implements ExceptionMapper<Exception> {
  @Inject
  private MessageSourceService messageSourceService;

  @Override
  public Response toResponse(Exception e) {
    Map<String, Object> errorInfo = new HashMap<>();
    errorInfo.put("timestamp", (new Date().getTime()));
    errorInfo.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
    errorInfo.put("error", messageSourceService.getMessage("sys.error.service"));
    errorInfo.put("message", e.getMessage());
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
      entity(errorInfo).
      type(MediaType.APPLICATION_JSON).
      build();
  }
}