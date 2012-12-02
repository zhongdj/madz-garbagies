package net.madz.module.common.service;

import net.madz.infra.biz.core.ValidationException;
import net.madz.infra.biz.service.core.ApplicationServiceException;
import net.madz.infra.security.biz.core.TenantIdentifiedService;
import net.madz.infra.security.util.TenantResources;
import net.madz.module.common.entity.Merchandise;
import net.madz.module.common.entity.Merchandise.CategoryEnum;
import net.madz.module.common.entity.UnitOfMeasure;
import net.madz.module.common.to.create.MerchandiseCTO;

/**
 * 
 * @author CleaNEr
 */
public class MerchandiseCreateService extends TenantIdentifiedService {

	private static final long serialVersionUID = -2647615801112126864L;
	private MerchandiseCTO merchandiseCTO;

	public MerchandiseCreateService(MerchandiseCTO merchandise) {
		super(merchandise);
		this.merchandiseCTO = merchandise;
	}

	@Override
	protected void execute() throws ApplicationServiceException {

		// 1. find mandatory relationships
		// Category category = em.find(Category.class,
		// merchandiseCTO.getCategory());

		// 2. construct with mandatory fields and relationships
		Merchandise merchandise = TenantResources.newEntity(Merchandise.class);

		// 3. set optional fields
		merchandise.setSuggestPrice(merchandiseCTO.getSuggestPrice());
		merchandise.setDescription(merchandiseCTO.getDescription());
		merchandise.setName(merchandiseCTO.getName());
		merchandise.setCode(merchandiseCTO.getCode());
		merchandise.setUom(em.find(UnitOfMeasure.class, merchandiseCTO.getUnitOfMeasureId()));
		merchandise.setCategory(CategoryEnum.valueOf(merchandiseCTO.getCategory()));
		// 4. set optional relationships

		// 5. maintain bidirection relationships

		// 5.1. maintain ManyToOne
		// 5.2 maintain ManyToMany

		// 5.3 maintain OneToOne

		// 6. persist
		em.persist(merchandise);

		// 7. set result
		setResult(merchandise.getId());

	}

	@Override
	protected void validate() throws ValidationException {
		// 1. validation null argument
		if (merchandiseCTO == null) {
			throw new ValidationException("The merchandise object can not be null");
		}
	}
}
