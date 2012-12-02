package net.madz.client.contract.biz.delegate;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;

import net.madz.client.CachingServiceLocator;
import net.madz.module.contract.ConstructionPartTO;
import net.madz.module.contract.ContractRatePlanComponentTO;
import net.madz.module.contract.ContractTO;
import net.madz.module.contract.ProjectTO;
import net.madz.module.contract.UnitOfProjectTO;
import net.madz.module.contract.facade.ContractFacadeRemote;

public class ContractDelegate implements ContractFacadeRemote {

	private static ContractDelegate instance;

	public static synchronized ContractDelegate getInstance() throws Exception {
		if (null == instance) {
			instance = new ContractDelegate();
		}
		return instance;
	}

	private ContractFacadeRemote server;

	private ContractDelegate() throws Exception {
		try {
			server = (ContractFacadeRemote) CachingServiceLocator.getInstance().getEJBObject(ContractFacadeRemote.class.getName());
		} catch (NamingException ex) {
			Logger.getLogger(ContractDelegate.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		}
	}

	@Override
	public String createConstructionPart(ConstructionPartTO part) {
		return server.createConstructionPart(part);
	}

	@Override
	public String createContract(ContractTO contract) {
		return server.createContract(contract);
	}

	@Override
	public String createContractRatePlanComponent(ContractRatePlanComponentTO component) {
		return server.createContractRatePlanComponent(component);
	}

	@Override
	public String createProject(ProjectTO project) {
		return server.createProject(project);
	}

	@Override
	public String createUnitOfProject(UnitOfProjectTO unit) {
		return server.createUnitOfProject(unit);
	}

	@Deprecated
	@Override
	public List<ConstructionPartTO> findConstructionParts() {
		return server.findConstructionParts();
	}

	@Override
	public ContractTO findContractById(String id) {
		return server.findContractById(id);
	}

	@Override
	public List<ContractTO> findContracts() {
		return server.findContracts();
	}

	@Override
	public ProjectTO findProjectById(String id) {
		return server.findProjectById(id);
	}

	@Override
	public List<ProjectTO> findProjects() {
		return server.findProjects();
	}

	@Override
	public UnitOfProjectTO findUnitOfProjectById(String id) {
		return server.findUnitOfProjectById(id);
	}

	@Override
	public List<UnitOfProjectTO> findUnitOfProjects() {
		return server.findUnitOfProjects();
	}

	@Override
	public void hardDeleteAllConstructionParts() {
		server.hardDeleteAllConstructionParts();
	}

	@Override
	public void hardDeleteAllContracts() {
		server.hardDeleteAllContracts();
	}

	@Override
	public void hardDeleteAllProjects() {
		server.hardDeleteAllProjects();
	}

	@Override
	public void hardDeleteAllUnitOfProjects() {
		server.hardDeleteAllUnitOfProjects();
	}

	@Override
	public void hardDeleteConstructionParts(String[] ids) {
		server.hardDeleteConstructionParts(ids);
	}

	@Override
	public void hardDeleteContracts(String[] ids) {
		server.hardDeleteContracts(ids);
	}

	@Override
	public void hardDeleteProjects(String[] ids) {
		server.hardDeleteProjects(ids);
	}

	@Override
	public void hardDeleteRatePlanComponent(String[] ids) {
		server.hardDeleteRatePlanComponent(ids);
	}

	@Override
	public void hardDeleteUnitOfProjects(String[] ids) {
		server.hardDeleteUnitOfProjects(ids);
	}

	@Override
	public void softDeleteConstructionParts(String[] ids) {
		server.softDeleteConstructionParts(ids);
	}

	@Override
	public void softDeleteContracts(String[] ids) {
		server.softDeleteContracts(ids);
	}

	@Override
	public void softDeleteProjects(String[] ids) {
		server.softDeleteProjects(ids);
	}

	@Override
	public void softDeleteRatePlanComponent(String[] ids) {
		server.softDeleteRatePlanComponent(ids);
	}

	@Override
	public void softDeleteUnitOfProjects(String[] ids) {
		server.softDeleteUnitOfProjects(ids);
	}

	@Deprecated
	@Override
	public void updateConstructionParts(List<ConstructionPartTO> parts) {
		server.updateConstructionParts(parts);
	}

	@Override
	public void updateContracts(List<ContractTO> contracts) {
		server.updateContracts(contracts);
	}

	@Override
	public void updateProjects(List<ProjectTO> projects) {
		server.updateProjects(projects);
	}

	@Override
	public void updateRatePlanComponents(List<ContractRatePlanComponentTO> ratePlanComponents) {
		server.updateRatePlanComponents(ratePlanComponents);
	}

	@Override
	public void updateUnitOfProjects(List<UnitOfProjectTO> unitOfProjects) {
		server.updateUnitOfProjects(unitOfProjects);
	}

	@Override
	public void testCascadeCreate() {
		server.testCascadeCreate();
	}

	@Override
	public void confirmContract(String contractId) {
		server.confirmContract(contractId);
	}

}
