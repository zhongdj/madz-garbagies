package net.madz.client.base;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import net.madz.client.account.biz.delegate.AccountDelegate;
import net.madz.client.common.biz.delegate.CommonObjectDelegate;
import net.madz.client.common.biz.delegate.CommonObjectQueryDelegate;
import net.madz.client.contract.biz.delegate.ContractDelegate;
import net.madz.client.production.delegate.ProductionDelegate;
import net.madz.infra.biz.service.core.ApplicationServiceException;
import net.madz.module.account.AccountTO;
import net.madz.module.account.ContactTO;
import net.madz.module.account.entity.Contact.ContactType;
import net.madz.module.common.entity.Merchandise.CategoryEnum;
import net.madz.module.common.to.create.MerchandiseCTO;
import net.madz.module.common.to.create.UnitOfMeasureCTO;
import net.madz.module.contract.ConstructionPartTO;
import net.madz.module.contract.ContractRatePlanComponentTO;
import net.madz.module.contract.ContractTO;
import net.madz.module.contract.UnitOfProjectTO;
import net.madz.module.contract.entity.Contract;
import net.madz.module.contract.entity.Contract.BillPeriodBaselineUnit;
import net.madz.module.contract.entity.Contract.PaymentTerm;
import net.madz.module.production.ConcreteMixingPlantTO;

import org.junit.After;
import org.junit.Before;

import com.sun.appserv.security.ProgrammaticLogin;

public abstract class BaseFixture {
	protected static ProgrammaticLogin pl = new ProgrammaticLogin();

	protected ContractDelegate contractInstance = null;
	protected AccountDelegate accountInstance = null;
	protected CommonObjectDelegate commonInstance = null;
	protected CommonObjectQueryDelegate commonQueryInstance = null;
	protected ProductionDelegate productionInstance = null;

	protected static String contractId;
	protected static String merchandiseIdA;
	protected static String merchandiseIdB;
	protected static String merchandiseIdC;
	protected static String productionId1;
	protected static String productionId2;
	protected static String unitOfMeasureId;
	protected static String concreteMixingPlantIdA = "";
	protected static String concreteMixingPlantIdB = "";
	protected static String concreteMixingPlantIdC = "";
	protected static String unitOfProjectIdA = "";
	protected static String unitOfProjectIdB = "";
	protected static String unitOfProjectIdC = "";
	protected static String unitOfProjectIdD = "";
	protected static String constructionPartId = "";
	protected static String projectId;

	protected ContractTO contractTOC1 = null;
	protected ContractTO contractTOC2 = null;
	protected ContractTO contractTOC3 = null;
	protected static String contractTOIdC1;
	protected static String contractTOIdC2;
	protected static String contractTOIdC3;

	protected UnitOfProjectTO unitOfProjectTOA = null;
	protected UnitOfProjectTO unitOfProjectTOB = null;
	protected UnitOfProjectTO unitOfProjectTOC = null;
	protected UnitOfProjectTO unitOfProjectTOD = null;

	// For Account A's related accounts
	protected AccountTO accountA = null;
	protected static String accountIdA;

	// For Account B's related accounts
	protected AccountTO accountB = null;
	protected static String accountIdB;

	// For Account A's contacts
	protected ContactTO billToContactA = null;
	protected ContactTO shipToContactA = null;
	protected ContactTO soldToContactA = null;
	protected ContactTO payerContactA = null;

	protected static String billToContactIdA;
	protected static String payerContactIdA;
	protected static String shipToContactIdA;
	protected static String soldToContactIdA;

	// For Account B's contacts
	protected ContactTO billToContactB = null;
	protected ContactTO shipToContactB = null;
	protected ContactTO soldToContactB = null;
	protected ContactTO payerContactB = null;

	protected static String billToContactIdB;
	protected static String payerContactIdB;
	protected static String shipToContactIdB;
	protected static String soldToContactIdB;

	@Before
	public void setUp() throws Exception {
		pl.login("administrator", "Barryzdjwin5631");
		productionInstance = ProductionDelegate.getInstance();
		contractInstance = ContractDelegate.getInstance();
		accountInstance = AccountDelegate.getInstance();
		commonInstance = CommonObjectDelegate.getInstance();
		commonQueryInstance = CommonObjectQueryDelegate.getInstance();

		configureAccounts();
		configureContacts();
		configureUnitOfMeasure();
		configureUnitOfProjects();
		configureContracts();
		configureConstructionPart();
		configureConcreteMixingPlants();
		configureMerchandises();
		configureRatePlanComponent();
	}

	@After
	public void tearDown() throws Exception {
		pl.logout();
	}

	private void configureAccounts() {
		accountA = new AccountTO();
		accountIdA = configureAccount(accountA, "�»�ӡˢ��", "�»�ӡˢ��");
		accountA.setId(accountIdA);

		accountB = new AccountTO();
		accountIdB = configureAccount(accountB, "Ⱥ��", "Ⱥ��");
		accountB.setId(accountIdB);
	}

	private String configureAccount(AccountTO to, String name, String shortName) {
		to.setName(name);
		to.setShortName(shortName);
		return accountInstance.createAccount(to);
	}

	private void configureContacts() {
		configureContactsForAccountA();
		configureContactsForAccountB();
	}

	private void configureContactsForAccountA() {
		billToContactA = new ContactTO();
		billToContactIdA = configureContact(accountIdA, billToContactA, "����", "1581022343", "BillTo");
		billToContactA.setId(billToContactIdA);

		shipToContactA = new ContactTO();
		shipToContactIdA = configureContact(accountIdA, shipToContactA, "����", "1581022345", "ShipTo");
		shipToContactA.setId(shipToContactIdA);

		soldToContactA = new ContactTO();
		soldToContactIdA = configureContact(accountIdA, soldToContactA, "����", "1581022348", "SoldTo");
		soldToContactA.setId(soldToContactIdA);

		payerContactA = new ContactTO();
		payerContactIdA = configureContact(accountIdA, payerContactA, "����", "1581022358", "Payer");
		;
		payerContactA.setId(payerContactIdA);
	}

	private void configureContactsForAccountB() {
		billToContactB = new ContactTO();
		billToContactIdB = configureContact(accountIdB, billToContactB, "�", "1681022343", "BillTo");
		billToContactB.setId(billToContactIdB);

		shipToContactB = new ContactTO();
		shipToContactIdB = configureContact(accountIdB, shipToContactB, "����", "1681022345", "ShipTo");
		shipToContactB.setId(shipToContactIdB);

		soldToContactB = new ContactTO();
		soldToContactIdB = configureContact(accountIdB, soldToContactB, "����", "1681022348", "SoldTo");
		soldToContactB.setId(soldToContactIdB);

		payerContactB = new ContactTO();
		payerContactIdB = configureContact(accountIdB, payerContactB, "���", "1681022358", "Payer");
		;
		payerContactB.setId(payerContactIdB);
	}

	private String configureContact(String accountId, ContactTO to, String name, String phoneNumber, String contactType) {
		to.setAccountId(accountId);
		to.setName(name);
		to.setMobile(phoneNumber);
		to.setContactType(ContactType.valueOf(contactType).name());
		return accountInstance.createContact(to);
	}

	// Configure 2 Contracts: C1 and C2
	// Configure 3 UnitOfProjects: A, B and C
	// A and B belonged to Account A, Contract C1;
	// C belonged to Account B, Contract C2
	private void configureUnitOfProjects() {
		unitOfProjectTOA = new UnitOfProjectTO();
		unitOfProjectIdA = configureUnitOfProject(unitOfProjectTOA, "A");
		unitOfProjectTOB = new UnitOfProjectTO();
		unitOfProjectIdB = configureUnitOfProject(unitOfProjectTOB, "B");
		unitOfProjectTOC = new UnitOfProjectTO();
		unitOfProjectIdC = configureUnitOfProject(unitOfProjectTOC, "C");
		unitOfProjectTOD = new UnitOfProjectTO();
		unitOfProjectIdD = configureUnitOfProject(unitOfProjectTOD, "D");
	}

	private String configureUnitOfProject(UnitOfProjectTO to, String name) {
		to.setName(name);
		ContactTO billToContact = null, payerContact = null, soldToContact = null, shipToContact = null;
		if (name.equalsIgnoreCase("C")) {
			billToContact = billToContactB;
			payerContact = payerContactB;
			soldToContact = soldToContactB;
			shipToContact = shipToContactB;
		} else {
			billToContact = billToContactA;
			payerContact = payerContactA;
			soldToContact = soldToContactA;
			shipToContact = shipToContactA;
		}
		to.setBillToContact(billToContact);
		to.setPayerContact(payerContact);
		to.setSoldToContact(soldToContact);
		to.setShipToContact(shipToContact);
		to.setProjectId(projectId);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			to.setStartDate(dateFormat.parse("2011-10-01"));
			to.setEndDate(dateFormat.parse("2012-12-01"));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String resultId = contractInstance.createUnitOfProject(to);
		to.setId(resultId);
		return resultId;
	}

	private void configureUnitOfMeasure() {
		UnitOfMeasureCTO unitOfMeasure = new UnitOfMeasureCTO();
		unitOfMeasure.setName("������");
		unitOfMeasure.setDescription("������");
		try {
			unitOfMeasureId = commonInstance.createUnitOfMeasure(unitOfMeasure);
		} catch (ApplicationServiceException e) {
			e.printStackTrace();
		}
	}

	private void configureContracts() {
		configureContractC1();
		configureContractC2();
		configureContractC3();
	}

	private void configureContractC1() {
		contractTOC1 = new ContractTO();
		contractTOC1.setName("�»�ӡˢ�������ڽ����ͬ");
		contractTOC1.setPaymentTerm(PaymentTerm.NET15.name());
		contractTOC1.setPayerAccount(accountA);
		contractTOC1.setShipToAccount(accountA);
		contractTOC1.setSoldToAccount(accountA);
		contractTOC1.setBillToAccount(accountA);
		final ArrayList<UnitOfProjectTO> unitOfProjects = new ArrayList<UnitOfProjectTO>();
		unitOfProjects.add(unitOfProjectTOA);
		unitOfProjects.add(unitOfProjectTOB);
		contractTOC1.setUnitOfProjects(unitOfProjects);
		contractTOC1.setActive(true);
		contractTOC1.setBillBaselineCombination(Contract.BillBaselineCombination.PERIOD_ONLY.name());
		contractTOC1.setBillPeriodBaseline(1);
		contractTOC1.setBillPeriodBaselineUnit(BillPeriodBaselineUnit.MONTH.name());
		contractTOC1.setBillVolumnBaseline(1000);
		contractTOC1.setBillVolumeBaselineUnitId(unitOfMeasureId);
		contractTOIdC1 = contractInstance.createContract(contractTOC1);
		contractTOC1.setId(contractTOIdC1);
		contractInstance.confirmContract(contractTOIdC1);
	}

	private void configureContractC2() {
		contractTOC2 = new ContractTO();
		contractTOC2.setName("Ⱥ���һ�ڽ����ͬ");
		contractTOC2.setPaymentTerm(PaymentTerm.NET15.name());
		contractTOC2.setPayerAccount(accountB);
		contractTOC2.setShipToAccount(accountB);
		contractTOC2.setSoldToAccount(accountB);
		contractTOC2.setBillToAccount(accountB);
		final ArrayList<UnitOfProjectTO> unitOfProjects = new ArrayList<UnitOfProjectTO>();
		unitOfProjects.add(unitOfProjectTOC);
		contractTOC2.setUnitOfProjects(unitOfProjects);
		contractTOC2.setActive(true);
		contractTOC2.setBillBaselineCombination(Contract.BillBaselineCombination.VOLUME_ONLY.name());
		contractTOC2.setBillPeriodBaseline(1);
		contractTOC2.setBillPeriodBaselineUnit(BillPeriodBaselineUnit.MONTH.name());
		contractTOC2.setBillVolumnBaseline(10);
		contractTOC2.setBillVolumeBaselineUnitId(unitOfMeasureId);
		contractTOIdC2 = contractInstance.createContract(contractTOC2);
		contractTOC2.setId(contractTOIdC2);
		contractInstance.confirmContract(contractTOIdC2);
	}

	// add 1 draft contract
	private void configureContractC3() {
		contractTOC3 = new ContractTO();
		contractTOC3.setName("Ⱥ���һ�ڽ����ͬ");
		contractTOC3.setPaymentTerm(PaymentTerm.NET15.name());
		contractTOC3.setPayerAccount(accountB);
		contractTOC3.setShipToAccount(accountB);
		contractTOC3.setSoldToAccount(accountB);
		contractTOC3.setBillToAccount(accountB);
		final ArrayList<UnitOfProjectTO> unitOfProjects = new ArrayList<UnitOfProjectTO>();
		unitOfProjects.add(unitOfProjectTOD);
		contractTOC3.setUnitOfProjects(unitOfProjects);
		contractTOC3.setActive(true);
		contractTOC3.setBillBaselineCombination(Contract.BillBaselineCombination.PERIOD_OR_VOLUME.name());
		contractTOC3.setBillPeriodBaseline(1);
		contractTOC3.setBillPeriodBaselineUnit(BillPeriodBaselineUnit.MONTH.name());
		contractTOC3.setBillVolumnBaseline(10);
		contractTOC3.setBillVolumeBaselineUnitId(unitOfMeasureId);
		contractTOIdC3 = contractInstance.createContract(contractTOC3);
	}

	private void configureConcreteMixingPlants() {
		concreteMixingPlantIdA = configureConcreteMixingPlant("#1", "1", "������");
		concreteMixingPlantIdB = configureConcreteMixingPlant("#2", "2", "������");
		concreteMixingPlantIdC = configureConcreteMixingPlant("#3", "3", "�Ӵ�ѧ");
	}

	private String configureConcreteMixingPlant(String name, String operatorId, String operatorName) {
		ConcreteMixingPlantTO plant = new ConcreteMixingPlantTO();
		plant.setName(name);
		plant.setOperatorId(operatorId);
		plant.setOperatorName(operatorName);
		return productionInstance.createConcreteMixingPlant(plant);
	}

	private void configureConstructionPart() {
		ConstructionPartTO part = new ConstructionPartTO();
		part.setName("c32");
		part.setDescription("C32");
		constructionPartId = contractInstance.createConstructionPart(part);
	}

	private void configureMerchandises() {
		merchandiseIdA = configureMerchandise("C10", "C10", "C10", CategoryEnum.CONCRETE.name(), 12.3, unitOfMeasureId);
		merchandiseIdB = configureMerchandise("C20", "C20", "C20", CategoryEnum.ADDITIVE.name(), 24.6, unitOfMeasureId);
		merchandiseIdC = configureMerchandise("C30", "C30", "C30", CategoryEnum.ADDITIVE.name(), 36.9, unitOfMeasureId);

	}

	private String configureMerchandise(String code, String name, String description, String category, double suggestPrice,
			String unitOfMeasureId) {
		String resultId = null;
		MerchandiseCTO merchandiseCTO = new MerchandiseCTO();
		merchandiseCTO.setCode(code);
		merchandiseCTO.setName(name);
		merchandiseCTO.setDescription(description);
		merchandiseCTO.setCategory(category);
		merchandiseCTO.setSuggestPrice(suggestPrice);
		merchandiseCTO.setUnitOfMeasureId(unitOfMeasureId);
		try {
			resultId = commonInstance.createMerchandise(merchandiseCTO);
		} catch (ApplicationServiceException e) {
			e.printStackTrace();
		}
		return resultId;
	}

	private void configureRatePlanComponent() {
		configureRatePlanComponentForContractC1();
		configureRatePlanComponentForContractC2();
	}

	private void configureRatePlanComponentForContractC1() {
		ContractRatePlanComponentTO planA = new ContractRatePlanComponentTO();
		createRatePlanComponent(1000.0, contractTOC1, merchandiseIdA, planA);
		ContractRatePlanComponentTO planB = new ContractRatePlanComponentTO();
		createRatePlanComponent(2000.0, contractTOC1, merchandiseIdB, planB);
		ContractRatePlanComponentTO planC = new ContractRatePlanComponentTO();
		createRatePlanComponent(3000.0, contractTOC1, merchandiseIdC, planC);
	}

	private void configureRatePlanComponentForContractC2() {
		ContractRatePlanComponentTO planA = new ContractRatePlanComponentTO();
		createRatePlanComponent(1000.0, contractTOC2, merchandiseIdA, planA);
		ContractRatePlanComponentTO planB = new ContractRatePlanComponentTO();
		createRatePlanComponent(2000.0, contractTOC2, merchandiseIdB, planB);
		ContractRatePlanComponentTO planC = new ContractRatePlanComponentTO();
		createRatePlanComponent(3000.0, contractTOC2, merchandiseIdC, planC);
	}

	private void createRatePlanComponent(double chargeRate, ContractTO contract, String merchandiseId, ContractRatePlanComponentTO plan) {
		// ContractRatePlanComponentCTO cto = new
		// ContractRatePlanComponentCTO();
		// cto.setContractId(contract.getId());
		// cto.setRate(chargeRate);
		// cto.setUnitOfMeasureId(unitOfMeasureId);
		// cto.setMerchandiseId(merchandiseId);
		// contractInstance.createRatePlanComponent(cto);
		plan.setActive(true);
		plan.setChargeRate(chargeRate);
		plan.setContract(contractInstance.findContractById(contract.getId()));
		plan.setMerchandise(commonQueryInstance.findMerchandise(merchandiseId));
		plan.setUnitOfMeasure(commonQueryInstance.findUnitOfMeasureById(unitOfMeasureId));
		contractInstance.createContractRatePlanComponent(plan);
	}
}
