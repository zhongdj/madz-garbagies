package net.madz.client.contract.delegate;

import static org.junit.Assert.fail;
import net.madz.client.contract.biz.delegate.ContractDelegate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.appserv.security.ProgrammaticLogin;

public class ContractTest {

	ProgrammaticLogin pl = new ProgrammaticLogin();
	ContractDelegate instance = null;
	private static String contractId = "";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		pl.login("administrator", "Barryzdjwin5631", "MadzRealm", false);
		instance = ContractDelegate.getInstance();
	}

	@After
	public void tearDown() throws Exception {
		pl.logout();
	}

	@Test
	public void testGetInstance() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateConstructionPart() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindConstructionParts() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateConstructionParts() {
		fail("Not yet implemented");
	}

	@Test
	public void testSoftDeleteConstructionParts() {
		fail("Not yet implemented");
	}

	@Test
	public void testHardDeleteConstructionParts() {
		fail("Not yet implemented");
	}

	@Test
	public void testHardDeleteAllConstructionParts() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateUnitOfProject() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindUnitOfProjects() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateUnitOfProjects() {
		fail("Not yet implemented");
	}

	@Test
	public void testSoftDeleteUnitOfProjects() {
		fail("Not yet implemented");
	}

	@Test
	public void testHardDeleteUnitOfProjects() {
		fail("Not yet implemented");
	}

	@Test
	public void testHardDeleteAllUnitOfProjects() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateProject() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindProjects() {
		fail("Not yet implemented");
	}

	@Test
	public void testSoftDeleteProjects() {
		fail("Not yet implemented");
	}

	@Test
	public void testHardDeleteProjects() {
		fail("Not yet implemented");
	}

	@Test
	public void testHardDeleteAllProjects() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateContract() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindContracts() {
		fail("Not yet implemented");
	}

	@Test
	public void testSoftDeleteContracts() {
		fail("Not yet implemented");
	}

	@Test
	public void testHardDeleteContracts() {
		fail("Not yet implemented");
	}

	@Test
	public void testHardDeleteAllContracts() {
		fail("Not yet implemented");
	}

	@Test
	public void testCascadeCreate() {
		instance.testCascadeCreate();
	}
}
