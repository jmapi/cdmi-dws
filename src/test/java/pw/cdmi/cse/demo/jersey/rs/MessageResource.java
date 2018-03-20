package pw.cdmi.cse.demo.jersey.rs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pw.cdmi.cse.demo.model.Result;
import pw.cdmi.cse.demo.mq.Sender;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("/message")
public class MessageResource {
    @Autowired
    private Sender sender;

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Result sendMessage() {
        sender.send();
        return Result.ok("发送成功");
    }
}
