package pw.cdmi.paas.developer.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

@Data
@Entity
@Table(name="t_app")
public class App {
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String id;
	@Column(name="app_name", nullable=false)
	private String appName;
	@Column(name="app_type", nullable=false)
	private String appType;
	@Column(name="url_icon",nullable=true)
	private String urlIcon;
	@CreatedDate
	@Column(name="create_time",nullable=false)
	private Date createTime;
	@CreatedDate
	@Column(name="update_time",nullable=true)
	private Date updateTime;
	@Column(name="user_id",nullable=false)
	private String userId;
	public App() {
		// TODO Auto-generated constructor stub
	}

}
