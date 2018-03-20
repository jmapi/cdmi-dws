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
@Table(name="t_voucher")
public class Voucher {
	
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String id;			
	@Column(name="client_id",nullable=false)
	private String clientId;
	@Column(name="user_id",nullable=false)
	private String userId;
	
	
	@CreatedDate
	private Date createTime;
	@CreatedDate
	private Date upDateTime;
	public Voucher() {
		// TODO Auto-generated constructor stub
	}

}
