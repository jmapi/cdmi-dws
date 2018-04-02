package pw.cdmi.msm.sms.service.imp;

import org.springframework.stereotype.Component;



import pw.cdmi.msm.sms.service.SendMessageService;

import com.smn.client.AkskSmnClient;
import com.smn.client.SmnClient;
import com.smn.request.sms.SmsPublishRequest;
import com.smn.response.sms.SmsPublishResponse;




@Component
public class SmnSendMessagerService implements SendMessageService {

	@Override
	public void send(String mobile, String value) {
		SmnClient smnClient = new AkskSmnClient(
		        "526GZIMDCZ2JIZQJQX1P",
		        "diKMQshRF63CpMeDVatp0zGf0GU9ls4SqW4nt2cd",
		        "cn-north-1");
		
		
		// 构造请求对象
        SmsPublishRequest smnRequest = new SmsPublishRequest();
        // 设置参数,接收手机号，短信内容，短信签名ID
        smnRequest.setEndpoint(mobile)
                .setMessage(value)
                .setSignId("36778e025ec747b385c3673410587246");
        // 发送短信
        try {
            SmsPublishResponse res = smnClient.sendRequest(smnRequest);
            System.out.println("httpCode:" + res.getHttpCode()
                    + ",message_id:" + res.getMessageId()
                    + ", request_id:" + res.getRequestId()
                    + ", errormessage:" + res.getMessage());
        } catch (Exception e) {
            // 处理异常
            System.out.println("发送出现错误");
        }
		

	}

}
