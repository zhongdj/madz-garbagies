/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.module.common.service;

import java.util.LinkedList;
import java.util.List;

import net.madz.infra.biz.core.ValidationException;
import net.madz.infra.biz.service.core.ApplicationServiceException;
import net.madz.infra.security.biz.core.TenantIdentifiedService;
import net.madz.module.common.entity.Merchandise;

/**
 * 
 * @author Administrator
 */
public class MerchandiseRemoveBatchService extends TenantIdentifiedService {

	private static final long serialVersionUID = -3663206689364132910L;
	private List<String> idList;

	public MerchandiseRemoveBatchService(List<String> idSet) {
		super(idSet);
		this.idList = idSet;
	}

	@Override
	protected void execute() throws ApplicationServiceException {
		for (String id : idList) {
			Merchandise merchandise = em.find(Merchandise.class, id);
			// maintain relationships

			em.remove(merchandise);

			// merchandise.getCategory().removeMerchandise(merchandise);
		}
	}

	@Override
	protected void validate() throws ValidationException {
		if (idList == null || idList.size() <= 0) {
			throw new ValidationException("Argument idSet is empty.");
		}

		List<String> copyList = new LinkedList<String>(idList);
		for (String id : copyList) {
			Merchandise merchandise = em.find(Merchandise.class, id);
			if (null == merchandise) {
				idList.remove(id);
				appendErrorMessage("Merchandise id = " + id + " cannot be found.");
			}
		}
	}
}
