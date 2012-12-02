package net.madz.flex.proxy.common;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.madz.client.common.biz.delegate.CommonObjectQueryDelegate;
import net.madz.module.common.to.query.MerchandiseQTO;
import net.madz.module.common.to.query.UnitOfMeasureQTO;

public class CommonObjectQueryProxy {

	private CommonObjectQueryDelegate delegate;

	public CommonObjectQueryProxy() {
		try {
			delegate = CommonObjectQueryDelegate.getInstance();
		} catch (Exception ex) {
			Logger.getLogger(CommonObjectQueryProxy.class.getName()).log(Level.SEVERE, null, ex);
			throw new IllegalStateException(ex);
		}
	}

	public MerchandiseQTO findMerchandise(String id) {
		return delegate.findMerchandise(id);
	}

	public List<MerchandiseQTO> findMerchandise() {
		System.out.println("CommonObjectQueryProxy : findMerchandise");
		List result = delegate.findMerchandise();
		System.out.println("Size : " + result.size());
		return result;
	}

	public List<MerchandiseQTO> findMerchandiseByQTO(MerchandiseQTO mqto) {
		return delegate.findMerchandise(mqto);
	}

	public List<UnitOfMeasureQTO> findUnitOfMeasure(boolean deleted) {
		return delegate.findUnitOfMeasure(deleted);
	}

	public UnitOfMeasureQTO findUnitOfMeasureById(String id) {
		return delegate.findUnitOfMeasureById(id);
	}
}
