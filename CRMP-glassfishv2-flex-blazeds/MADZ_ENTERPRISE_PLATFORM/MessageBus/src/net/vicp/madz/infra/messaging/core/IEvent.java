/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.vicp.madz.infra.messaging.core;

import java.io.Serializable;
import java.util.Properties;

/**
 * 
 * @author Barry Zhong
 */
public interface IEvent extends Serializable {

	public Properties getMessageProperties();

	public void setMessageProperties(Properties properties);
}
