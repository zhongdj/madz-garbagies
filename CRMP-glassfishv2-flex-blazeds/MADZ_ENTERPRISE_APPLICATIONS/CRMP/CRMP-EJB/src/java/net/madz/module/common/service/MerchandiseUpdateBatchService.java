/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.module.common.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import net.madz.infra.biz.core.ValidationException;
import net.madz.infra.biz.service.core.ApplicationServiceException;
import net.madz.infra.security.biz.core.TenantIdentifiedService;
import net.madz.infra.security.util.TenantResources;
import net.madz.module.common.entity.Merchandise;
import net.madz.module.common.entity.Merchandise.CategoryEnum;
import net.madz.module.common.to.update.MerchandiseUTO;

/**
 * 
 * @author Administrator
 */
public class MerchandiseUpdateBatchService extends TenantIdentifiedService {

	private static final long serialVersionUID = 2922596060369075034L;
	private List<MerchandiseUTO> merchandiseList;

	public MerchandiseUpdateBatchService(List<MerchandiseUTO> merchandiseList) {
		super(merchandiseList);
		this.merchandiseList = merchandiseList;
	}

	@Override
	protected void execute() throws ApplicationServiceException {

		// 3. For Each Update Task
		for (MerchandiseUTO merchandiseUTO : merchandiseList) {

			// 3.1 Declare Each
			Merchandise merchandise = null;

			// 3.2 find original entity
			merchandise = em.find(Merchandise.class, merchandiseUTO.getId());

			// 3.3 set relationships

			// 3.3.1 set bidirection relationships

			// 3.3.1.1 set bidirection mandatory relationships

			// 3.3.1.1.1 find mandatory relationship
			String updatedCategory = merchandiseUTO.getCategory();

			// 3.3.3.1.2 find original relationship
			String originalCategory = merchandise.getCategory().name();
			
			

			// 3.3.3.1.3 maintain relationships if relationship changes
			if (!originalCategory.equals(updatedCategory)) {
				// set relationship
				if (null == updatedCategory) {
					merchandise.setCategory(CategoryEnum.CONCRETE);
				} else {
				    merchandise.setCategory(CategoryEnum.valueOf(updatedCategory));
				}
			}

			// 3.3.1.2 set bidirection optional relationships

			// 3.3.1.2.1 find new relatioship
			// Category updatedCategory = em.find(Category.class,
			// merchandiseUTO.getCategoryId());

			// 3.3.1.2.2 get original relationship
			// Category originalCategory = merchandise.getCategory();

			// 3.3.1.2.3 if relations not changes
			// if ((null == originalCategory && null == updatedCategory) ||
			// (null != originalCategory &&
			// originalCategory.equals(updatedCategory))) {
			// continue;
			// }

			// 3.3.1.2.4 maintian orginal relationship if needed
			// if (null != originalCategory) {
			// originalCategory.removeMerchandise(merchandise);
			// }

			// 3.3.1.2.5 maintain new reltaionship if needed
			// if (null != updatedCategory) {
			// updatedCategory.addMerchandise(merchandise);
			// }

			// 3.3.1.2.6 set new relationship
			// merchandise.setCategory(updatedCategory);

			// 3.3.1.3 set direction mandatory or optional relationships

			// 3.4 set properties
			merchandise.setName(merchandiseUTO.getName());
			merchandise.setCode(merchandiseUTO.getCode());
			merchandise.setSuggestPrice(merchandiseUTO.getSuggestPrice());
			merchandise.setDescription(merchandiseUTO.getDescription());
			try {
				merchandise.setUpdatedBy(TenantResources.getCurrentUser());
			} catch (Exception e) {
				throw new ApplicationServiceException(e);
			}
			merchandise.setUpdatedOn(new Date());
			em.merge(merchandise);
		}
	}

	@Override
	protected void validate() throws ValidationException {
		// 0. validation null argument
		if (merchandiseList == null || merchandiseList.size() <= 0) {
			throw new ValidationException("The merchandiseList can not be empty");
		}

		// 1. For Each Update Task
		List<MerchandiseUTO> copyList = new LinkedList<MerchandiseUTO>(merchandiseList);
		for (MerchandiseUTO merchandiseUTO : copyList) {
			// 1.1 Declare and Intialize Each
			Merchandise merchandise = em.find(Merchandise.class, merchandiseUTO.getId());
			// 1.2 if original entity exists
			if (null == merchandise) {
				// 1.2.1 add invalid to error message
				appendErrorMessage(merchandise.toString() + " not exists");
				merchandiseList.remove(merchandiseUTO);
				continue;
			}

			// 1.3 validate mandatory both direction and bidirection
			// relationships
			// 1.3.1 find mandatory relationship
//			Category updatedCategory = em.find(Category.class, merchandiseUTO.getCategory());
			// 1.3.2 validate mandatory both direction and bidirection
			// relationships

			// 1.3.2.1 validate relationship
			// if (null == updatedCategory) {
			// appendErrorMessage(merchandise.toString() +
			// " has invalid relation ship with Category id = "
			// + merchandiseUTO.getCategory());
			// merchandiseList.remove(merchandiseUTO);
			// continue;
			// }
		}
	}
}
