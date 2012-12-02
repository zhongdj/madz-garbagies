package net.madz.infra.biz.core;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.naming.NamingException;

import net.madz.infra.biz.BizObjectManager;

public class BizBean {

	public BizBean() {
		super();
	}

	@PostConstruct
	public void injectBizObjectManager() {
		try {
			BizObjectManager.bind(this);
		} catch (NamingException e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "BizObjectManager Cannot be bound.", e);
		}
	}

	@PreDestroy
	public void removeBizObjectManager() {
		BizObjectManager.unbind(this);
	}

}