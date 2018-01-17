package pw.cdmi.cse.demo.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pw.cdmi.cse.demo.common.MessageSourceService;
import pw.cdmi.cse.demo.exception.NotFoundException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Inject
    private MessageSourceService messageSourceService;

    @ExceptionHandler(value={NotFoundException.class})
    public ResponseEntity notFoundExceptionHandler(HttpServletRequest req, Exception e) {
        Map<String, Object> errorInfo = new HashMap<>();
        errorInfo.put("path", req.getRequestURI());
        errorInfo.put("timestamp", (new Date().getTime()));
        errorInfo.put("status", HttpStatus.NOT_FOUND.value());
        errorInfo.put("error", messageSourceService.getMessage("sys.error.notfound"));
        errorInfo.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorInfo);
    }

    @ExceptionHandler(value={Exception.class})
    public ResponseEntity serverErrorExceptionHandler(HttpServletRequest req, Exception e) {
        Map<String, Object> errorInfo = new HashMap<>();
        errorInfo.put("timestamp", (new Date().getTime()));
        errorInfo.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorInfo.put("error", messageSourceService.getMessage("sys.error.service"));
        errorInfo.put("message", e.getMessage());
        errorInfo.put("path", req.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorInfo);
    }
}
