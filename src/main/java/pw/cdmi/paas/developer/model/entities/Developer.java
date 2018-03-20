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
@Table(name = "t_developer")
public class Developer {
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String id;			//开发者id
	@Column(name="developer_name", nullable=false)
	private String developerName;	//开发者名称
	
	@Column(name="developer_type", nullable=false)
	private String developerType;	//开发者类型，个人用户、企业用户
	
	@Column(name="user_id", nullable = false)
	private String userId;		//平台用户id
	
	
	
	
}
