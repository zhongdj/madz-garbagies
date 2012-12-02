package samples.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.madz.infra.biz.BizObjectManager;
import net.madz.infra.biz.core.BizBean;
import samples.domain.ILadingRequest;
import samples.domain.LadingRequest;
import samples.to.LadingOrderCTO;

@Stateless
public class SampleFacadeBean extends BizBean implements SampleFacade {

	@PersistenceContext
	private EntityManager em;
	private BizObjectManager bm;

	public void doLadingRequest(LadingOrderCTO cto) {
		LadingRequest request = new LadingRequest();
		// binding cto fields to request
		em.persist(request);
	}

	public void doLadingConfirm(Long requestId) {
		ILadingRequest ladingRequest = bm.findBizObject(LadingRequest.class, requestId);
		ladingRequest.doConfirm();
	}

	public void doPickup(Long requestId) {
		ILadingRequest ladingRequest = bm.findBizObject(LadingRequest.class, requestId);
		ladingRequest.doPick();
	}
}
