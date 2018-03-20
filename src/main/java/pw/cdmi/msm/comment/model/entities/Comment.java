package pw.cdmi.msm.comment.model.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import lombok.Data;
import pw.cdmi.share.model.MultiApplication;
import pw.cdmi.share.model.MultiSite;
import pw.cdmi.share.model.MultiTenancy;


@Data
@Entity
@Table(name="t_comment")
public class Comment implements MultiTenancy, MultiSite,MultiApplication{
	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")
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
	@Column(name="commentator_name" ,nullable = false)
	private String comentatorName;			//评论人名字
	@Column(name="head_image",nullable=false)
	private String headImage;				//评论人头像
	@CreatedDate
	@Column(name="create_time",nullable= false)
	private Date createTime;				//评论时间

	@Column(name="app_id", nullable = true)
	private String appId;						//数据归属应用ID
	@Column(name="site_id", nullable = true)
	private String siteId;						//对应的平台内子站点Id，这个子站点可能是租户，可以是频道
	@Column(name="tenant_id", nullable = true)
	private String tenantId;					//对应的租户ID
	
	
}