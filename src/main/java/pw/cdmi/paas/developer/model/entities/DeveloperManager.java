package pw.cdmi.paas.developer.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name="t_manager")
public class DeveloperManager {
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String id;
	
	@Column(name="manager_name", nullable=true)
	private String managerName;		//账号管理员名称
	
	@Column(name="manager_phone", nullable=true)
	private String managerPhone;	//账号管理员手机号码
	
	@Column(name="manager_email", nullable=true)
	private String managerEmail;	//账号管理员邮箱
	
	@Column (name="user_id",nullable=false)
	private String userId;
	public DeveloperManager() {
		// TODO Auto-generated constructor stub
	}

}
