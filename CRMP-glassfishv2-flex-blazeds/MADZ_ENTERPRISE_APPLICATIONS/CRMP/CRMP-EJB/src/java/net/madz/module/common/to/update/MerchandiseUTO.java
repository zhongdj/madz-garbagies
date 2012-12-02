package net.madz.module.common.to.update;

import net.madz.infra.biz.core.ValidationException;
import net.madz.module.common.to.create.MerchandiseCTO;

public class MerchandiseUTO extends MerchandiseCTO {

	private static final long serialVersionUID = -8383357148195335258L;
	protected String id;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	@Override
	public void validate() throws ValidationException {
		super.validate();
		if (null == getId() || 0 >= getId().length()) {
			throw new ValidationException("Merchandise id must be not null or positive!");
		}
	}

	@Override
	public String toString() {
		return "MerchandiseUTO [id=" + id + "]";
	}

}
