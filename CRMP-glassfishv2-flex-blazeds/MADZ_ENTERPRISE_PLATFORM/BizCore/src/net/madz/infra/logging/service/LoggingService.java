/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.infra.logging.service;

import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;

import net.madz.infra.biz.core.BusinessException;
import net.madz.infra.biz.core.ValidationException;
import net.madz.infra.biz.service.core.ApplicationService;
import net.madz.infra.biz.service.core.ApplicationServiceException;
import net.madz.infra.logging.entity.OperationLog;
import net.madz.infra.util.ResourceHelper;

/**
 * 
 * @author CleaNEr
 */
public class LoggingService extends ApplicationService {

	private static final long serialVersionUID = 3702477500462601087L;
	protected ApplicationService service;

	public LoggingService(ApplicationService service) {
		super();
		this.service = service;
	}

	@Override
	public void process() throws ApplicationServiceException {
		start = new Timestamp(System.currentTimeMillis());
		try {
			setSucceed(false);
			preExec();
			execute();
			postExec();
			setSucceed(true);
		} catch (BusinessException ex) {
			throw new ApplicationServiceException(ex);
		} catch (ValidationException ex) {
			// this exception will never be catched
		} finally {
			complete = new Timestamp(System.currentTimeMillis());
			timeCost = complete.getTime() - start.getTime();
		}
	}

	@Override
	protected void execute() {

		String sourceName = service.getSourceName();
		if (sourceName != null && sourceName.length() > 100) {
			sourceName = sourceName.substring(0, 96) + "...";
		}
		OperationLog log = new OperationLog(service.getServiceName(), service.getUserName(), service.getSourceType(), sourceName,
				service.getDescription(), service.getStart(), service.getComplete(), service.getTimeCost(), service.isSucceed());
		log.setFailedCause(service.getFailedCause());
		try {
			ResourceHelper.getEntityManager().persist(log);
		} catch (NamingException ex) {
			Logger.getLogger(LoggingService.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	protected void preExec() throws ValidationException {
		// doing nothing
	}

	@Override
	protected void initializeSource(Object... args) {
		// doing nothing
	}

	@Override
	protected void initializeAguments(Object... args) {
		// doing nothing
	}

	@Override
	protected void validate() throws ValidationException {
		// doing nothing
	}
}
