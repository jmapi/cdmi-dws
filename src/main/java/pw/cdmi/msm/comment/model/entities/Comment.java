package pw.cdmi.msm.comment.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pw.cdmi.radio.model.MultiApplication;
import pw.cdmi.radio.model.MultiSite;
import pw.cdmi.radio.model.MultiTenancy;


@Data
@Entity
@Table(name="t_comment")
public class Comment implements MultiTenancy, MultiSite,MultiApplication{
	@Id
	@GeneratedValue
	private String id;
	@Column(name="target_id", nullable = false)
	private String targetId;               //评论对象的ID
	@Column(name="terget_sid" ,nullable = true )
	private String targetSid;				//评论对象的应用内账号id
	@Column(name="target_type" ,nullable = true)
	private String targetType;				//评论对象类型，动态信息，主播，房间
	@Column(name="content",nullable = false)
	private String content;					//评论内容
	@Column(name="commentator_id" ,nullable = false)
	private String commentatorId;			//评论人的id
	@Column(name="commentator_sid" ,nullable = true)
	private String commentatorSid;			//评论人的应用内账号ID
	
	@CreatedDate
	@Column(name="createTime",nullable= false)
	private Date create_time;				//评论时间

	@Override
	public void setAppId(String app_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAppId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSiteId(String site_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSiteId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTenantId(String tenant_id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTenantId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}