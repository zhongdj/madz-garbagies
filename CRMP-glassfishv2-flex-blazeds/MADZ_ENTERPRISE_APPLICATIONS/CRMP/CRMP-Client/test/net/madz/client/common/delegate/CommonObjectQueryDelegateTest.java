/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.client.common.delegate;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.madz.client.common.biz.delegate.CommonObjectQueryDelegate;
import net.madz.module.common.to.query.MerchandiseQTO;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.sun.appserv.security.ProgrammaticLogin;

/**
 * 
 * @author CleaNEr
 */
public class CommonObjectQueryDelegateTest {

	private static ProgrammaticLogin pl = new ProgrammaticLogin();
	private CommonObjectQueryDelegate instance;

	public CommonObjectQueryDelegateTest() {
		try {
			instance = CommonObjectQueryDelegate.getInstance();
		} catch (Exception ex) {
			Logger.getLogger(CommonObjectQueryDelegateTest.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		pl.login("administrator", "Barryzdjwin5631");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		pl.logout();
	}

	@Before
	public void setUp() {

	}

	@After
	public void tearDown() {

	}

	/**
	 * Test of findMerchandise method, of class CommonObjectQueryDelegate.
	 */
	// @Test
	public void testFindMerchandise_Long() {
		System.out.println("findMerchandise");
		MerchandiseQTO result = instance.findMerchandise("1");
		assertEquals("6907376500988", result.getCode());
	}

	/**
	 * Test of findMerchandise method, of class CommonObjectQueryDelegate.
	 */
	// @Test
	public void testFindMerchandise_0args() {
		for (int i = 0; i < 100; i++) {
			System.out.println("findMerchandise");
			List<MerchandiseQTO> result = instance.findMerchandise();
			assertEquals(2446, result.size());
		}
	}

}
