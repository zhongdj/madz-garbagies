package net.madz.module.common.service;

import java.util.List;

import net.madz.infra.biz.core.ValidationException;
import net.madz.infra.biz.service.core.ApplicationServiceException;
import net.madz.infra.security.biz.core.TenantIdentifiedService;
import net.madz.module.common.entity.UnitOfMeasure;

public class UnitOfMeasureRemoveBatchService extends TenantIdentifiedService {

	private static final long serialVersionUID = 9012113557198856537L;
	private List<String> idList;

	public UnitOfMeasureRemoveBatchService(List<String> idList) {
		super(idList);
		this.idList = idList;
	}

	@Override
	protected void execute() throws ApplicationServiceException {
		for (String id : idList) {
			UnitOfMeasure unitOfMeasure = em.find(UnitOfMeasure.class, id);
			unitOfMeasure.setDeleted(true);
			em.merge(unitOfMeasure);
		}

	}

	@Override
	protected void validate() throws ValidationException {
		if (idList == null || idList.size() <= 0) {
			throw new ValidationException("idList is null!");
		}

	}

}
