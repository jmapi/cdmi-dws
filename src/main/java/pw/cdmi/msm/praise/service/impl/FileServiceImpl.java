package pw.cdmi.msm.praise.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pw.cdmi.msm.praise.repositories.FileRepsitory;
import pw.cdmi.msm.praise.service.FileService;
@Component
public class FileServiceImpl implements FileService {
	
	@Autowired
	private FileRepsitory fileRepsitory;
	
	
	
	public FileServiceImpl() {
		// TODO Auto-generated constructor stub
		
	}
	@Override
	public boolean inspectExist(String id, String type) {
		// TODO Auto-generated method stub
		 
		if(fileRepsitory.countByTargetIdAndType(id, type)>0)
			return true;
		else
		return false;
	}

}
