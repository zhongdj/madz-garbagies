/*
 * MessageBusXMBean.java
 *
 * Created on 2009年2月18日, 下午8:25
 */
package net.vicp.madz.management;

/**
 * Interface MessageBusXMBean
 * 
 * @author CleaNEr
 */
public interface MessageBusXMBean {

	void reload();

	void restart();

	void start();

	void stop();
}
