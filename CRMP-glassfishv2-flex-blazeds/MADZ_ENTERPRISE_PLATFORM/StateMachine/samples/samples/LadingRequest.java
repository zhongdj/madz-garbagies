package samples;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import net.vicp.madz.infra.state.annotation.BizObject;
import net.vicp.madz.infra.state.annotation.State;
import net.vicp.madz.infra.state.annotation.TransitionMethod;

@BizObject(property = "samples/bl.properties")
@Entity
public class LadingRequest implements ILadingRequest {

	@State
	private String stateName;

	@Id
	private Long id;
	private Date startDate;

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Override
	@TransitionMethod(name = "confirm")
	public void doConfirm() {

	}

	@Override
	@TransitionMethod(name = "request")
	public void doRequest() {

	}

	@Override
	@TransitionMethod(name = "pick")
	public void doPick() {
		
	}
}
