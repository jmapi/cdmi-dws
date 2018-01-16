package pw.cdmi.cse.demo.rs.mapper;

import org.springframework.http.HttpStatus;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Provider
public class ServiceErrorMapper implements ExceptionMapper<Exception> {

  @Override
  public Response toResponse(Exception e) {
    Map<String, Object> errorInfo = new HashMap<>();
    errorInfo.put("timestamp", (new Date().getTime()));
    errorInfo.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
    errorInfo.put("error", "系统发生异常");
    errorInfo.put("message", e.getMessage());
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
      entity(errorInfo).
      type(MediaType.APPLICATION_JSON).
      build();
  }
}