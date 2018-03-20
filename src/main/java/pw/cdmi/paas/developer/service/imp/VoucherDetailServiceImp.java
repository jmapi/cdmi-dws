package pw.cdmi.paas.developer.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.builders.ClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configuration.ClientDetailsServiceConfiguration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Component;

import pw.cdmi.paas.developer.model.entities.Voucher;
import pw.cdmi.paas.developer.repositories.VoucherRepository;
import pw.cdmi.paas.developer.rs.VoucherRequest;
import pw.cdmi.paas.developer.service.VoucherDetailService;
@Component
public class VoucherDetailServiceImp implements VoucherDetailService {
	@Autowired
	private VoucherRepository voucherRepository;
	@Autowired
	private ClientDetailsServiceConfigurer clientDetailsServiceConfigurer;
	
	@Autowired
    private DataSource dataSource;
	public VoucherDetailServiceImp() {
		// TODO Auto-generated constructor stub
	}

	@Bean 
	public ClientDetailsServiceConfigurer detailService(){
		return new ClientDetailsServiceConfigurer(new ClientDetailsServiceBuilder());
	}
	@Override
	public VoucherRequest voucherObject(String userId) throws Exception {
		// TODO Auto-generated method stub
		
		Voucher voucher = new Voucher();
		VoucherRequest voucherRequest = new VoucherRequest();
		clientDetailsServiceConfigurer.withClientDetails(new JdbcClientDetailsService(dataSource));
		String client = UUID.randomUUID().toString().replaceAll("-", "");
		String secret = UUID.randomUUID().toString().replaceAll("-", "");
		clientDetailsServiceConfigurer.inMemory()
							.withClient(client)
							.secret(secret)
							.authorizedGrantTypes("authorization_code")
							.scopes("app");
		//TODO voucher信息
		voucher.setCreateTime(new Date());
		voucher.setClientId(client);
		voucher.setUserId(userId);
		//TODO voucherRequest信息
		voucherRequest.setAppKey(client);
		voucherRequest.setSecretkey(secret);
		
		return voucherRequest;
	}

	@Override
	public List<VoucherRequest> findVoucher(String userId) {
		// TODO Auto-generated method stub
		ArrayList<VoucherRequest> listRequest = new ArrayList<VoucherRequest>(); 
		List<Voucher> listVoucher = voucherRepository.findByUserId(userId);
		Iterator<Voucher> it = listVoucher.iterator();
		while(it.hasNext()){
			Voucher next = (Voucher)it.next();
			
		}
		return listRequest;
	}

	@Override
	public VoucherRequest upDateVoucher(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteVoucher(String id) {
		// TODO Auto-generated method stub

	}
	
	

}
