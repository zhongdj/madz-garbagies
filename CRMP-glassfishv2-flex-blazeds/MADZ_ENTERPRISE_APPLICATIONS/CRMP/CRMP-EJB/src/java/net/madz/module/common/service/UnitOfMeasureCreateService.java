package net.madz.module.common.service;

import net.madz.infra.biz.core.ValidationException;
import net.madz.infra.biz.service.core.ApplicationServiceException;
import net.madz.infra.security.biz.core.TenantIdentifiedService;
import net.madz.infra.security.util.TenantResources;
import net.madz.module.common.entity.UnitOfMeasure;
import net.madz.module.common.to.create.UnitOfMeasureCTO;

public class UnitOfMeasureCreateService extends TenantIdentifiedService {

	private static final long serialVersionUID = -8221708088295766504L;
	private UnitOfMeasureCTO unitOfMeasureCTO;

	public UnitOfMeasureCreateService(UnitOfMeasureCTO unitOfMeasureCTO) {
		super(unitOfMeasureCTO);
		this.unitOfMeasureCTO = unitOfMeasureCTO;
	}

	@Override
	protected void execute() throws ApplicationServiceException {
		try {

			UnitOfMeasure unitOfMeasure = TenantResources.newEntity(UnitOfMeasure.class);
			unitOfMeasure.setName(unitOfMeasureCTO.getName());
			unitOfMeasure.setDescription(unitOfMeasureCTO.getDescription());
			em.persist(unitOfMeasure);
			setResult(unitOfMeasure.getId());
		} catch (Exception ex) {
			throw new ApplicationServiceException(ex);
		}
	}

	@Override
	protected void validate() throws ValidationException {
		if (unitOfMeasureCTO == null) {
			throw new ValidationException("The UnitOfMeasure object can not be null");
		}

	}

}
