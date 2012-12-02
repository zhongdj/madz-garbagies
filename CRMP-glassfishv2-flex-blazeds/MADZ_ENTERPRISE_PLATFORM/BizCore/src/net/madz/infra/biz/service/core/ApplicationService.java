package net.madz.infra.biz.service.core;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import net.madz.infra.biz.BizObjectManager;
import net.madz.infra.biz.core.BusinessException;
import net.madz.infra.biz.core.IServiceArgument;
import net.madz.infra.biz.core.ValidationException;
import net.madz.infra.logging.util.ServiceLogger;
import net.madz.infra.util.ResourceHelper;

/**
 * 
 * @author dezhong
 */
public abstract class ApplicationService implements Serializable {

	private static final long serialVersionUID = 1L;
	protected transient final EntityManager em;
	protected transient BizObjectManager bm;
	protected transient ApplicationServiceContext context;
	protected transient UserTransaction utx;

	protected boolean succeed;
	protected List<IServiceArgument> arguments;
	protected Object result;
	protected String sourceName;
	protected String sourceType;
	protected String failedCause;
	protected Timestamp start;
	protected Timestamp complete;
	protected long timeCost;
	protected String userName;
	protected String userId;
	protected StringBuffer errorMessage;

	public ApplicationService(Object... args) {
		try {
			userName = ResourceHelper.getPrincipalName();
			em = ResourceHelper.getEntityManager();

			if (userName == null) {
				userName = "UNKNOWN USER";
			}
		} catch (NamingException ex) {
			Logger.getLogger(ApplicationService.class.getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}

		errorMessage = new StringBuffer();
		initializeSource(args);
		initializeAguments(args);
	}

	protected void setContext(final ApplicationServiceContext context) {
		this.context = context;
		this.bm = context.getBizObjectManager();
	}

	public void process() throws ApplicationServiceException {
		start = new Timestamp(System.currentTimeMillis());
		try {
			setSucceed(false);
			preExec();
			execute();
			postExec();
			setSucceed(true);
		} catch (BusinessException ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
			setFailedCause(ex.getMessage());
			throw new ApplicationServiceException(ex.getMessage(), ex);
		} catch (ApplicationServiceException ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
			setFailedCause(ex.getMessage());
			throw ex;
		} catch (ValidationException ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
			setFailedCause(ex.getMessage());
			throw new ApplicationServiceException(ex.getMessage(), ex);
		} finally {
			complete = new Timestamp(System.currentTimeMillis());
			timeCost = complete.getTime() - start.getTime();
			ServiceLogger.log(this);
		}
	}

	protected void preExec() throws ValidationException {
		// Because we can't identify whether the argument should be null, so we
		// just leave it to the specific object.
		if (arguments != null && arguments.size() > 0) {
			for (IServiceArgument argument : arguments) {
				argument.validate();
			}
		}
		validate();
	}

	protected void postExec() throws ApplicationServiceException {
		if (errorMessage.length() > 0) {
			errorMessage.deleteCharAt(errorMessage.length() - 1);
			throw new ApplicationServiceException(errorMessage.toString());
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

	public String getSourceName() {
		return this.sourceName;
	}

	public String getSourceType() {
		return this.sourceType;
	}

	public Timestamp getComplete() {
		return complete;
	}

	public void setComplete(Timestamp complete) {
		this.complete = complete;
	}

	public Object getResult() {
		return context.getResult();
	}

	public void setResult(Object result) {
		context.setResult(result);
	}

	public boolean isSucceed() {
		return succeed;
	}

	public void setSucceed(boolean succeed) {
		this.succeed = succeed;
	}

	public Timestamp getStart() {
		return start;
	}

	public void setStart(Timestamp start) {
		this.start = start;
	}

	public long getTimeCost() {
		return timeCost;
	}

	public void setTimeCost(long timeCost) {
		this.timeCost = timeCost;
	}

	public String getServiceName() {
		return getClass().getSimpleName();
	}

	public String getUserName() {
		if (this.userName != null && this.userName.length() > 0) {
			return this.userName;
		}
		return "UNKNOWN USER";
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	protected abstract void validate() throws ValidationException;

	protected abstract void execute() throws ApplicationServiceException;

	protected void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	protected void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	protected void initializeAguments(Object[] args) {
		if (args.length > 0) {
			arguments = new LinkedList<IServiceArgument>();
			for (Object arg : args) {
				if (arg == null) {
					continue;
				}
				if (arg instanceof Collection) {
					for (Object item : (Collection) arg) {
						if (item instanceof IServiceArgument) {
							arguments.add((IServiceArgument) item);
						}
					}
				} else if (arg instanceof IServiceArgument) {
					arguments.add((IServiceArgument) arg);
				}
			}
		}
	}

	protected void initializeSource(Object... args) {
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

	protected void appendErrorMessage(String message) {
		errorMessage.append(message).append(",");
	}

	protected void initUserTransaction() throws NamingException {
		utx = ResourceHelper.getUserTransaction();
	}

	private String isFailed() {
		if (succeed) {
			return "Succeed";
		} else {
			return "failed";
		}
	}

}
