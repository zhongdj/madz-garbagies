/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.infra.biz.service.event;

import java.util.Properties;

import net.madz.infra.biz.service.core.ApplicationService;
import net.vicp.madz.infra.messaging.core.IEvent;

/**
 * 
 * @author CleaNEr
 */
public class ApplicationServiceEvent implements IEvent {

	private static final long serialVersionUID = 8371313097339416562L;
	private ApplicationService service;
	private String userName;

	private ApplicationServiceEvent(ApplicationService service) {
		this.service = service;
		this.userName = service.getUserName();
		service.setUserName(userName);
	}

	public static ApplicationServiceEvent createApplicationServiceEvent(ApplicationService service) {
		ApplicationServiceEvent event = new ApplicationServiceEvent(service);
		return event;
	}

	public ApplicationService getService() {
		return service;
	}

	public void setService(ApplicationService service) {
		this.service = service;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Properties getMessageProperties() {
		return new Properties();
	}

	public void setMessageProperties(Properties properties) {
		return;
	}
}
