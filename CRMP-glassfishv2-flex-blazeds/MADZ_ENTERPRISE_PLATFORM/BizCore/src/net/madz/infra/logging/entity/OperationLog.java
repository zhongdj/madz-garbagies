package net.madz.infra.logging.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * 
 * @author Barry Zhong
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "CRMP_OPERATION_LOG")
public class OperationLog implements Serializable {

	private static final long serialVersionUID = -6778452610461001006L;
	protected Long id;
	protected String serviceName;
	protected String userName;
	protected String sourceType;
	protected String sourceName;
	protected String failedCause;
	protected String description;
	protected Timestamp start;
	protected Timestamp complete;
	protected long timeCost;
	protected boolean succeed;

	public OperationLog() {
	}

	public OperationLog(String serviceName, String userName, String sourceType, String sourceName, String description, Timestamp start,
			Timestamp complete, long timeCost, boolean succeed) {
		setServiceName(serviceName);
		setUserName(userName);
		setSourceType(sourceType);
		setSourceName(sourceName);
		setDescription(description);
		setStart(start);
		setComplete(complete);
		setTimeCost(timeCost);
		setSucceed(succeed);
	}

	public void setId(Long id) {
		this.id = id;
	}

	@TableGenerator(name = "OPLOGGEN", table = "ESSENTIAL_ID_GEN", pkColumnName = "GEN_KEY", valueColumnName = "GEN_VALUE", pkColumnValue = "OPLOG_ID", allocationSize = 50)
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "OPLOGGEN")
	public Long getId() {
		return id;
	}

	public Timestamp getComplete() {
		return complete;
	}

	public void setComplete(Timestamp complete) {
		this.complete = complete;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public Timestamp getStart() {
		return start;
	}

	public void setStart(Timestamp start) {
		this.start = start;
	}

	public boolean isSucceed() {
		return succeed;
	}

	public void setSucceed(boolean succeed) {
		this.succeed = succeed;
	}

	public long getTimeCost() {
		return timeCost;
	}

	public void setTimeCost(long timeCost) {
		this.timeCost = timeCost;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFailedCause() {
		return failedCause;
	}

	public void setFailedCause(String failedCause) {
		this.failedCause = failedCause;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (null == object) {
			return false;
		}
		if (!(object.getClass() == this.getClass())) {
			return false;
		}
		OperationLog other = (OperationLog) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.nsn.persistence.entity.OperationLog[id=" + id + "]";
	}
}
