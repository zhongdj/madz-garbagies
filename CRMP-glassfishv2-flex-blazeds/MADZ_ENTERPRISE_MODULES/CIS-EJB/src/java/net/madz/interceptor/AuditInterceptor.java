package net.madz.interceptor;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.madz.infra.biz.core.BusinessException;
import net.madz.infra.biz.core.IServiceArgument;
import net.madz.infra.biz.service.core.ApplicationService;
import net.madz.infra.security.logging.entity.TenantOperationLog;
import net.madz.infra.security.messaging.event.outbound.TenantLogEvent;
import net.madz.infra.security.persistence.Tenant;
import net.madz.infra.security.util.TenantResources;

public class AuditInterceptor {

	private static ThreadLocal<Boolean> invoked = new ThreadLocal<Boolean>();

	@Resource
	private SessionContext context;
	@PersistenceContext(name = "persistence/EntityManager")
	private EntityManager em;

	protected boolean succeed;
	protected String sourceName;
	protected String sourceType;
	protected String failedCause;
	protected Timestamp start;
	protected Timestamp complete;
	protected long timeCost;
	protected String userName;
	protected String userId;
	protected StringBuffer errorMessage;
	protected String serviceName;

	@AroundInvoke
	public Object doAudit(InvocationContext ctx) {

		Object result = null;
		long before = System.currentTimeMillis();
		start = new Timestamp(before);
		Tenant tenant = null;
		try {
			if (null != invoked.get() && invoked.get()) {
				return ctx.proceed();
			} else {
				invoked.set(true);
				userName = context.getCallerPrincipal().getName();
				tenant = TenantResources.getTenant();
				errorMessage = new StringBuffer();
				final Object[] args = ctx.getParameters();
				initializeSource(args);
				result = ctx.proceed();
				succeed = true;
			}
		} catch (Exception ex) {
			succeed = false;
			Logger.getLogger(ApplicationService.class.getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		} finally {
			long after = System.currentTimeMillis();
			complete = new Timestamp(after);
			timeCost = after - before;
			TenantOperationLog log = new TenantOperationLog();
			log.setComplete(complete);
			log.setDescription(getDescription());
			log.setFailedCause(failedCause);
			log.setServiceName(getServiceName());
			log.setSourceName(getSourceName());
			log.setSourceType(getSourceType());
			log.setSucceed(succeed);
			log.setTenant(tenant);
			log.setTimeCost(timeCost);
			log.setUserName(userName);

			TenantLogEvent event = new TenantLogEvent(log);
			// MessageBusProxy.sendEvent(event);
		}
		return result;
	}

	protected void initializeSource(Object... args) {
		if (null == args) {
			return;
		}
		StringBuffer resultMessage = new StringBuffer();
		if (args.length > 1) {
			setSourceType("Multi-Arguments");
			for (Object arg : args) {
				if (arg == null) {
					continue;
				}
				if (arg instanceof Collection) {
					for (Object item : (Collection) arg) {
						if (item == null) {
							continue;
						}
						if (item instanceof IServiceArgument) {
							resultMessage.append(", ");
							resultMessage.append(item.getClass().getSimpleName());
							resultMessage.append(" : ");
							resultMessage.append(((IServiceArgument) item).getArgumentName());
						} else {
							resultMessage.append(", ");
							resultMessage.append(item.getClass().getSimpleName());
							resultMessage.append(" : ");
							resultMessage.append(item.toString());
						}
					}
				} else if (arg instanceof IServiceArgument) {
					resultMessage.append(", ");
					resultMessage.append(arg.getClass().getSimpleName());
					resultMessage.append(" : ");
					resultMessage.append(((IServiceArgument) arg).getArgumentName());

				} else {
					resultMessage.append(", ");
					resultMessage.append(arg.getClass().getSimpleName());
					resultMessage.append(" : ");
					resultMessage.append(arg.toString());
				}
			}
		} else if (args.length <= 0) {
			setSourceType("No Arguments");
			resultMessage.append("No Arguments");
		} else {
			if (args[0] == null) {
				setSourceType("The only argument is null.");
				resultMessage.append("The only argument is null.");
			} else if (args[0] instanceof IServiceArgument) {
				setSourceType(args[0].getClass().getSimpleName());
				resultMessage.append(((IServiceArgument) args[0]).getArgumentName());
			} else if (args[0] instanceof Collection) {
				if (((Collection) args[0]).size() > 0) {
					Object[] objects = ((Collection) args[0]).toArray();
					Object uniqueArg = objects[0];
					setSourceType(uniqueArg.getClass().getSimpleName());
					for (Object object : objects) {
						if (object == null) {
							continue;
						}
						if (object instanceof IServiceArgument) {
							resultMessage.append(", ");
							resultMessage.append(((IServiceArgument) object).getArgumentName());
						} else {
							resultMessage.append(", ");
							resultMessage.append(object.toString());
						}
					}
				} else {
					setSourceType("Empty Collection");
					resultMessage.append("Empty Collection");
				}
			}
		}
		String message = resultMessage.toString();
		if (message.startsWith(", ")) {
			setSourceName(message.substring(2, message.length() - 1));
		} else {
			setSourceName(message);
		}
	}

	private String getSourceType() {
		return this.sourceType;
	}

	private String getSourceName() {
		return this.sourceName;
	}

	private String getServiceName() {
		// TODO Auto-generated method stub
		return null;
	}

	protected void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	protected void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	protected void appendErrorMessage(String message) {
		errorMessage.append(message).append(",");
	}

	private String isFailed() {
		if (succeed) {
			return "Succeed";
		} else {
			return "failed";
		}
	}

	public String getFailedCause() {
		return failedCause;
	}

	public void setFailedCause(String failedCause) {
		this.failedCause = failedCause;
	}

	public String getDescription() {
		return "Service: " + getServiceName() + " executed on " + getSourceName() + ":" + getSourceType() + " " + isFailed();
	}
}
