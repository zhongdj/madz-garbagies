/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.client.common.delegate;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.madz.client.common.biz.delegate.CommonObjectDelegate;
import net.madz.client.common.biz.delegate.CommonObjectQueryDelegate;
import net.madz.module.common.entity.Merchandise.CategoryEnum;
import net.madz.module.common.to.create.MerchandiseCTO;
import net.madz.module.common.to.create.UnitOfMeasureCTO;
import net.madz.module.common.to.update.MerchandiseUTO;

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
public class CommonObjectDelegateTest {

	private ProgrammaticLogin pl = new ProgrammaticLogin();
	private CommonObjectDelegate instance;
	private static ThreadLocal<String> merchandiseId = new ThreadLocal<String>();
	private static ThreadLocal<String> unitOfMeasureId = new ThreadLocal<String>();

	public CommonObjectDelegateTest() {
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
			instance = CommonObjectDelegate.getInstance();
		} catch (Exception ex) {
			Logger.getLogger(CommonObjectDelegateTest.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@After
	public void tearDown() {
		pl.logout();
	}

	@Test
	public void testCreateUom() throws Exception {
		UnitOfMeasureCTO to = new UnitOfMeasureCTO();
		to.setName("方");
		String uomId = instance.createUnitOfMeasure(to);
		unitOfMeasureId.set(uomId);
		System.out.println(uomId);
	}

	/**
	 * Test of createMerchandise method, of class CommonObjectDelegate.
	 */
	@Test
	public void testCreateMerchandise() throws Exception {
		System.out.println("createMerchandise");
		MerchandiseCTO merchandise = new MerchandiseCTO();
		merchandise.setName("Tracy");
		merchandise.setCode("SEXY CAT");
		merchandise.setDescription("Sexy tracy cat");
		merchandise.setSuggestPrice(1d);
		merchandise.setCategory(CategoryEnum.CONCRETE.name());
		merchandise.setUnitOfMeasureId(unitOfMeasureId.get());
		merchandiseId.set(instance.createMerchandise(merchandise));
		System.out.println(merchandiseId.get());
	}

	/**
	 * Test of updateMerchandise method, of class CommonObjectDelegate.
	 */
	@Test
	public void testUpdateMerchandise() throws Exception {
		System.out.println("updateMerchandise");
		System.out.println(merchandiseId.get());
		MerchandiseUTO merchandise = CommonObjectQueryDelegate.getInstance().findMerchandise(merchandiseId.get()).valueofMerchandiseUTO();
		merchandise.setSuggestPrice(2.84);
	}

	/**
	 * Test of removeMerchandise method, of class CommonObjectDelegate.
	 */
	// @Test
	public void testRemoveMerchandise() throws Exception {
		System.out.println("removeMerchandise");
	}
}