/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.infra.biz.service.core;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author CleaNEr
 */
public class ApplicationServiceBus {

	private ApplicationServiceBus() {
	}

	public static Object invokeService(Object bean, String serviceName, Object... args) throws ApplicationServiceException {
		try {
			Constructor constructor = findConstructor(serviceName, args);
			Object object = constructor.newInstance(args);
			if (object instanceof ApplicationService) {
				ApplicationService service = (ApplicationService) object;

				// Prepare Context
				ApplicationServiceContext context = new ApplicationServiceContext(bean);
				service.setContext(context);

				service.process();

				if (context.getResult() != null) {
					System.out.println("service:" + serviceName + " returns value:" + context.getResult());
					return context.getResult();
				}
			}
		} catch (ApplicationServiceException ex) {
			Logger.getLogger(ApplicationServiceBus.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		} catch (Exception ex) {
			Logger.getLogger(ApplicationServiceBus.class.getName()).log(Level.SEVERE, null, ex);
			throw new ApplicationServiceException("", ex);
		}

		return null;
	}

	public static Class locateService(String serviceName) throws ClassNotFoundException, ApplicationServiceException {
		Properties configs = loadServiceProperties();
		if (configs == null) {
			throw new ApplicationServiceException("");
		}
		String clzName = configs.getProperty(serviceName);
		if (clzName == null || clzName.length() <= 0) {
			throw new ApplicationServiceException("");
		}
		return Class.forName(clzName);
	}

	private static Constructor findConstructor(String serviceName, Object[] args) throws ApplicationServiceException, SecurityException {
		try {

			Constructor result = null;
			Class serviceClz = locateService(serviceName);
			Constructor[] constructors = serviceClz.getConstructors();

			for (Constructor constructor : constructors) {
				Class[] types = constructor.getParameterTypes();
				if (types.length == args.length) {
					for (int i = 0; i < types.length; i++) {
						Object obj = args[i];
						Class clz = types[i];
						if (clz.isInstance(obj)) {
							if (i == types.length - 1) {
								result = constructor;
								break;
							} else {
								continue;
							}
						} else {
							break;
						}
					}

				} else {
					continue;
				}
				if (result != null) {
					break;
				} else {
					continue;
				}
			}

			if (result == null) {
				throw new ApplicationServiceException("No corresponding constructor in " + serviceName + " class");
			}
			return result;
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(ApplicationServiceBus.class.getName()).log(Level.SEVERE, null, ex);
			throw new ApplicationServiceException("Service Class Not Found", ex);
		}
	}

	private static Properties loadServiceProperties() throws ApplicationServiceException {
		InputStream is = null;
		try {
			ClassLoader cl = ApplicationServiceBus.class.getClassLoader();
			// is =
			// cl.getResourceAsStream("/net/vicp/madz/infra/biz/service/core/Service.properties");
			is = cl.getResourceAsStream("/conf/service.properties");
			Properties prop = new Properties();
			prop.load(is);
			return prop;
		} catch (IOException ex) {
			Logger.getLogger(ApplicationServiceBus.class.getName()).log(Level.SEVERE, null, ex);
			throw new ApplicationServiceException("Load Service.properties file failed!", ex);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ex) {
					Logger.getLogger(ApplicationServiceBus.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}

	}
}
