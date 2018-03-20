package pw.cdmi.paas.developer.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pw.cdmi.paas.developer.model.entities.App;
import pw.cdmi.paas.developer.repositories.AppRepository;
import pw.cdmi.paas.developer.rs.AppRequst;
import pw.cdmi.paas.developer.rs.ListAppResponce;
import pw.cdmi.paas.developer.service.AppDetailService;
@Component
public class AppDetailServiceImp implements AppDetailService {
	@Autowired
	private AppRepository appRepository;
	
	public AppDetailServiceImp() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void appObject(AppRequst appRequst,String userId) {
		// TODO Auto-generated method stub
		App app = new App();
		app.setAppName(appRequst.getName());
		app.setAppType(appRequst.getType());
		app.setCreateTime(new Date());
		app.setUrlIcon(appRequst.getIcon());
		app.setUserId(userId);
		appRepository.save(app);
	}

	@Override
	public void deleteObject(String id) {
		// TODO Auto-generated method stub
		appRepository.deleteById(id);
	}

	@Override
	public List<ListAppResponce> listObject(String userId) {
		// TODO Auto-generated method stub
		List<App> listApp = appRepository.findByUserId(userId);
		Iterator<App> iterator = listApp.iterator();
		List<ListAppResponce> listResponce = new ArrayList<ListAppResponce>();
		while(iterator.hasNext()){
			ListAppResponce responce = new ListAppResponce();
			App next = (App)iterator.next();
			responce.setId(next.getId());
			responce.setName(next.getAppName());
			responce.setType(next.getAppType());
			responce.setIcon(next.getUrlIcon());
			listResponce.add(responce);
		}
		
		return listResponce;
	}

}
