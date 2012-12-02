package samples.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import net.madz.core.state.annotation.ReactiveObject;
import net.madz.core.state.annotation.StateIndicator;
import net.madz.core.state.annotation.TransitionMethod;

@ReactiveObject
@Entity
public class LadingRequest implements ILadingRequest {

	@StateIndicator
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
		System.out.println("do confirm");
	}

	@Override
	@TransitionMethod(name = "request")
	public void doRequest() {
		System.out.println("do request");
	}

	@Override
	@TransitionMethod(name = "pick")
	public void doPick() {
		System.out.println("do pick");
	}
}
