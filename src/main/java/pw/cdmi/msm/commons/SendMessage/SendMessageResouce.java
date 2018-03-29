package pw.cdmi.msm.commons.SendMessage;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pw.cdmi.msm.commons.AuthMobileService;

@RestController
@RequestMapping("/Message/v1")
public class SendMessageResouce {
	
	@Autowired
	private AuthMobileService authMobileService;
	
	@PostMapping("/send")
	public void SendMessage(@RequestBody Message message){
		
		if(message == null|| StringUtils.isBlank(message.getMobile())||StringUtils.isBlank(message.getHeadMessage())){
			throw new SecurityException("参数错误");
		}
		authMobileService.sendMessage("+86"+message.getMobile(), message.getHeadMessage());
	}
	@GetMapping("/auth/{mobile}")
	public @ResponseBody Map<String, Object> authMobile(@PathVariable("mobile") String mobile,
			@RequestParam("authNumber")String authNumber){
		if(StringUtils.isBlank(mobile)||StringUtils.isBlank(authNumber)){
			throw new SecurityException("参数错误");
		}
		boolean authMobile = authMobileService.AuthMobile("+86"+mobile, authNumber);
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("auth", authMobile);
		return hashMap;
		
	}
}
