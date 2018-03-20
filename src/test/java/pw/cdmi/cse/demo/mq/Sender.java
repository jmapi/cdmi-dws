package pw.cdmi.cse.demo.mq;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Sender {
    protected final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send() {
        String context = "hello," + new Date();
        logger.info("sender:"+context);
        amqpTemplate.convertAndSend("hello", context);
    }
}
