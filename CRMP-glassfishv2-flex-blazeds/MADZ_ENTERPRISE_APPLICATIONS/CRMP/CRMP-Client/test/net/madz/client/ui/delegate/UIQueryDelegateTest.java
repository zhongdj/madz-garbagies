/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.madz.client.ui.delegate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.madz.client.security.delegate.UIQueryDelegate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.appserv.security.ProgrammaticLogin;

/**
 * 
 * @author CleaNEr
 */
public class UIQueryDelegateTest {
	private ProgrammaticLogin pl = new ProgrammaticLogin();
	private UIQueryDelegate instance;

	public UIQueryDelegateTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() {
		pl.login("administrator", "Barryzdjwin5631");
		try {
			instance = UIQueryDelegate.getInstance();
		} catch (Exception ex) {
			Logger.getLogger(UIQueryDelegateTest.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@After
	public void tearDown() {
		pl.logout();
	}

	/**
	 * Test of getInstance method, of class UIQueryDelegate.
	 */
	// @Test
	public void testGetInstance() throws Exception {
		System.out.println("getInstance");
		UIQueryDelegate expResult = null;
		UIQueryDelegate result = UIQueryDelegate.getInstance();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to
		// fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of findMenuItems method, of class UIQueryDelegate.
	 */
	// @Test
	// public void testFindMenuItems() throws Exception {
	// System.out.println("findMenuItems");
	// UIQueryDelegate instance = null;
	// List<MenuItem> expResult = null;
	// List<MenuItem> result = instance.findMenuItems();
	// assertEquals(expResult, result);
	// // TODO review the generated test code and remove the default call to
	// fail.
	// fail("The test case is a prototype.");
	// }

	/**
	 * Test of getMenuXMLDescription method, of class UIQueryDelegate.
	 */
	@Test
	public void testGetMenuXMLDescription() throws Exception {
		System.out.println("getMenuXMLDescription");

		String result = instance.getMenuXMLDescription();
		System.out.println(result);

	}

}