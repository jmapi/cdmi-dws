package pw.cdmi.paas.developer.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

@Data
@Entity
@Table(name="t_baseUser")
public class BaseUser {
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String id;			//开发者id
	@Column(name="account_id",nullable=false)
	private String accountId;
	@Column(nullable=false)
	private String passWord;
	@CreatedDate
	private Date createTime;
	public BaseUser() {
		// TODO Auto-generated constructor stub
	}
	
}
