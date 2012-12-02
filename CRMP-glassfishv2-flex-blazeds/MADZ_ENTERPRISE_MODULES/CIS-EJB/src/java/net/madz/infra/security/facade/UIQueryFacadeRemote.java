/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.infra.security.facade;

import java.util.List;

import javax.ejb.Remote;
import javax.naming.NamingException;

import net.madz.infra.security.persistence.MenuItem;

/**
 * 
 * @author CleaNEr
 */
@Remote
public interface UIQueryFacadeRemote {

	List<MenuItem> findMenuItems() throws NamingException;

	String getMenuXMLDescription() throws NamingException;
}
