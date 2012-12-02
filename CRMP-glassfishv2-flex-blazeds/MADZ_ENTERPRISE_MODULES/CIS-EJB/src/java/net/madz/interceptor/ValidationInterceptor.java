package net.madz.interceptor;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import net.madz.infra.biz.core.BusinessException;
import net.madz.infra.biz.core.IServiceArgument;
import net.madz.infra.biz.core.ValidationException;
import net.madz.infra.biz.service.core.ApplicationService;

public class ValidationInterceptor {
	private static ThreadLocal<Boolean> invoked = new ThreadLocal<Boolean>();

	@AroundInvoke
	public Object doValidate(InvocationContext ctx) {
		if (null != invoked.get() && invoked.get()) {

		} else {
			invoked.set(true);
			Object[] args = ctx.getParameters();
			if (null != args && 0 < args.length) {
				List<IServiceArgument> arguments = new LinkedList<IServiceArgument>();
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
				if (arguments != null && arguments.size() > 0) {
					for (IServiceArgument argument : arguments) {
						try {
							argument.validate();
						} catch (ValidationException e) {
							throw new BusinessException(e);
						}
					}
				}
			}
		}

		try {
			return ctx.proceed();
		} catch (Exception ex) {
			Logger.getLogger(ApplicationService.class.getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}
	}

}
