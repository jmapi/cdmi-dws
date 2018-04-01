package pw.cdmi.msm.commons.SendMessage;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pw.cdmi.msm.commons.AuthMobileService;

@RestController
@RequestMapping("/messages/v1")
public class SendMessageResouce {
	
	@Autowired
	private AuthMobileService authMobileService;
	
	@PostMapping("/sms/checkcode")
	public void SendMessage(@RequestParam String mobile, @RequestParam(required=false) String messageTemplateId){
		
		if(StringUtils.isBlank(mobile)){
			throw new SecurityException("参数错误");
		}
		String content = "";
		authMobileService.sendMessage("+86"+mobile, content);
	}
	@PutMapping("/sms/checkcode")
	public @ResponseBody Map<String, Object> authMobile(@RequestBody CheckRequest checkRequest){
		if(checkRequest==null||StringUtils.isBlank(checkRequest.getMobile())||StringUtils.isBlank(checkRequest.getCode())){
			throw new SecurityException("参数错误");
		}
		boolean authMobile = authMobileService.AuthMobile("+86"+checkRequest.getMobile(), checkRequest.getCode());
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("check", authMobile);
		return hashMap;
		
	}
}