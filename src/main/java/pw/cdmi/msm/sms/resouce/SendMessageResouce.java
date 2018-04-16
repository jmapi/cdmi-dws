package pw.cdmi.msm.sms.resouce;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pw.cdmi.msm.sms.model.CheckRequest;
import pw.cdmi.msm.sms.model.MsmRequest;
import pw.cdmi.msm.sms.service.AuthMobileService;


@RestController
@RequestMapping("/messages/v1")
@ConfigurationProperties(prefix="MessageTemplates")
@Data
public class SendMessageResouce {
	private Map<String,String> templates;
	@Autowired
	private AuthMobileService authMobileService;
	
	@PostMapping("/sms/checkcode")
	public void SendMessage(@RequestBody MsmRequest message){
		
		if(StringUtils.isBlank(message.getMobile())){
			throw new SecurityException("参数错误");
		}
		String content = null;
		if(StringUtils.isBlank(message.getTemplatesId())){
			 content = templates.get("default");
		}else{
			
			 content = templates.get(message.getTemplatesId());
			 if(content==null){
				 content = templates.get("default");
			 }
		}
		authMobileService.sendMessage(message.getMobile(), content ,message.getSignType());
	}
	@PutMapping("/sms/checkcode")
	public @ResponseBody Map<String, Object> authMobile(@RequestBody CheckRequest checkRequest){
		if(checkRequest==null||StringUtils.isBlank(checkRequest.getMobile())||StringUtils.isBlank(checkRequest.getCode())){
			throw new SecurityException("参数错误");
		}
		boolean authMobile = authMobileService.AuthMobile(checkRequest.getMobile(), checkRequest.getCode());
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("check", authMobile);
		return hashMap;
		
	}
}
