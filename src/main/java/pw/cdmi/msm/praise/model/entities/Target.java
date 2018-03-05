package pw.cdmi.msm.praise.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="t_target")
public class Target {
	@Id
	@GeneratedValue
	private long id;
	@Column(name="target_id")
	private String targetId;
	@Column(name="type")
	private String type;
}
