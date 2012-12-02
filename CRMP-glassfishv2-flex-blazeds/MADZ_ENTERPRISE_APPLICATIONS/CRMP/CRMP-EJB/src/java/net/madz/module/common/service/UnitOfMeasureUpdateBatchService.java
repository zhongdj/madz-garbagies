package net.madz.module.common.service;

import java.util.Date;
import java.util.List;

import net.madz.infra.biz.core.ValidationException;
import net.madz.infra.biz.service.core.ApplicationServiceException;
import net.madz.infra.security.biz.core.TenantIdentifiedService;
import net.madz.infra.security.util.TenantResources;
import net.madz.module.common.entity.UnitOfMeasure;
import net.madz.module.common.to.update.UnitOfMeasureUTO;

public class UnitOfMeasureUpdateBatchService extends TenantIdentifiedService {

	private List<UnitOfMeasureUTO> unitOfMeasureList;

	public UnitOfMeasureUpdateBatchService(List<UnitOfMeasureUTO> unitOfMeasureList) {
		super(unitOfMeasureList);
		this.unitOfMeasureList = unitOfMeasureList;
	}

	@Override
	protected void execute() throws ApplicationServiceException {
		UnitOfMeasure unitOfMeasure = null;

		for (UnitOfMeasureUTO item : unitOfMeasureList) {
			unitOfMeasure = em.find(UnitOfMeasure.class, item.getId());
			unitOfMeasure.setName(item.getName());
			unitOfMeasure.setDescription(item.getDescription());
			try {
				unitOfMeasure.setUpdatedBy(TenantResources.getCurrentUser());
			} catch (Exception e) {
				e.printStackTrace();
			}
			unitOfMeasure.setUpdatedOn(new Date(System.currentTimeMillis()));
			unitOfMeasure.setDeleted(item.isDeleted());
		}
	}

	@Override
	protected void validate() throws ValidationException {
		if (unitOfMeasureList == null || unitOfMeasureList.size() <= 0) {
			throw new ValidationException("The unitOfMeasure list can not be empty");
		}

	}

}
