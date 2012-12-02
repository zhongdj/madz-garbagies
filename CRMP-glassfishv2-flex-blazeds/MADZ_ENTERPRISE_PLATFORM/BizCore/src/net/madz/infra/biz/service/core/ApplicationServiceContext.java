package net.madz.infra.biz.service.core;

import javax.naming.NamingException;

import net.madz.infra.biz.BizObjectManager;

public class ApplicationServiceContext {

	private final BizObjectManager bm;
	private final Object bean;
	private Object result;

	public ApplicationServiceContext(Object bean) throws NamingException {
		super();
		this.bean = bean;
		bm = BizObjectManager.getBizObjectManager(bean);
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public Object getBean() {
		return bean;
	}

	public BizObjectManager getBizObjectManager() {
		return bm;
	}

}
