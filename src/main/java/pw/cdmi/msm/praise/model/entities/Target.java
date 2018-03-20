package pw.cdmi.msm.praise.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.mongodb.repository.CountQuery;

import lombok.Data;

@Data
@Entity
@Table(name="t_file")
public class Target {
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
	private String id;
	@Column(name = "target_id",nullable = false )
	private String targetId;
	@Column(name = "type",nullable = false )
	private String type;
}
