/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.vicp.madz.infra.messaging.core;

/**
 * 
 * @author dezhong
 */
public interface LifeCycle {

	void reload();

	void restart();

	void start();

	void stop();

}
