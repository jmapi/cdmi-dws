package pw.cdmi.msm.sms.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pw.cdmi.msm.commons.NumberGenerate;
import pw.cdmi.msm.sms.repository.AuthMobileRepository;
import pw.cdmi.msm.sms.service.AuthMobileService;
import pw.cdmi.msm.sms.service.SendMessageService;


@Component
public class DefaultAuthMobileService implements AuthMobileService {
	@Autowired
	private AuthMobileRepository authMobileRepository;
	@Autowired
	private SendMessageService sendMessageService;
	@Override
	public void sendMessage(String mobile,String headMessage) {
		String authNumber = NumberGenerate.authNumber();
		sendMessageService.send(mobile, headMessage+authNumber);
		authMobileRepository.save(mobile, authNumber);		
	}

	@Override
	public boolean AuthMobile(String mobile, String AuthNumber) {
		Object value = authMobileRepository.getValue(mobile);
		if(value==null){
			return false;
		}
		if(!AuthNumber.equals(value)){
			
			return false;
		}
		authMobileRepository.deleteObject(mobile);
		return true;
	}

}
