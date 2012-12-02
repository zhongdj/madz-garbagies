package net.madz.flex.proxy.contract;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.madz.client.contract.biz.delegate.ContractDelegate;
import net.madz.module.contract.ConstructionPartTO;
import net.madz.module.contract.ContractRatePlanComponentTO;
import net.madz.module.contract.ContractTO;
import net.madz.module.contract.ProjectTO;
import net.madz.module.contract.UnitOfProjectTO;
import net.madz.module.contract.facade.ContractFacadeRemote;

public class ContractProxy implements ContractFacadeRemote {

	private final ContractDelegate delegate;

	public ContractProxy() {
		try {
			delegate = ContractDelegate.getInstance();
		} catch (Exception ex) {
			Logger.getLogger(ContractProxy.class.getName()).log(Level.SEVERE, null, ex);
			throw new IllegalStateException(ex);
		}
	}

	@Override
	public String createConstructionPart(ConstructionPartTO part) {
		return delegate.createConstructionPart(part);
	}

	@Override
	public String createContract(ContractTO contract) {
		return delegate.createContract(contract);
	}

	@Override
	public String createProject(ProjectTO project) {
		return delegate.createProject(project);
	}

	@Override
	public String createUnitOfProject(UnitOfProjectTO unit) {
		return delegate.createUnitOfProject(unit);
	}

	@Override
	public List<ConstructionPartTO> findConstructionParts() {
		return delegate.findConstructionParts();
	}

	@Override
	public List<ContractTO> findContracts() {
		return delegate.findContracts();
	}

	@Override
	public List<ProjectTO> findProjects() {
		return delegate.findProjects();
	}

	@Override
	public List<UnitOfProjectTO> findUnitOfProjects() {
		return delegate.findUnitOfProjects();
	}

	@Override
	public void hardDeleteAllConstructionParts() {
		delegate.hardDeleteAllConstructionParts();
	}

	@Override
	public void hardDeleteAllContracts() {
		delegate.hardDeleteAllContracts();
	}

	@Override
	public void hardDeleteAllProjects() {
		delegate.hardDeleteAllProjects();
	}

	@Override
	public void hardDeleteAllUnitOfProjects() {
		delegate.hardDeleteAllUnitOfProjects();
	}

	@Override
	public void hardDeleteConstructionParts(String[] ids) {
		delegate.hardDeleteConstructionParts(ids);
	}

	@Override
	public void hardDeleteContracts(String[] ids) {
		delegate.hardDeleteContracts(ids);
	}

	@Override
	public void hardDeleteProjects(String[] ids) {
		delegate.hardDeleteProjects(ids);
	}

	@Override
	public void hardDeleteUnitOfProjects(String[] ids) {
		delegate.hardDeleteUnitOfProjects(ids);
	}

	@Override
	public void softDeleteConstructionParts(String[] ids) {
		delegate.softDeleteConstructionParts(ids);
	}

	@Override
	public void softDeleteContracts(String[] ids) {
		delegate.softDeleteContracts(ids);
	}

	@Override
	public void softDeleteProjects(String[] ids) {
		delegate.softDeleteProjects(ids);
	}

	@Override
	public void softDeleteUnitOfProjects(String[] ids) {
		delegate.softDeleteUnitOfProjects(ids);
	}

	@Override
	public void updateConstructionParts(List<ConstructionPartTO> parts) {
		delegate.updateConstructionParts(parts);
	}

	@Override
	public void updateUnitOfProjects(List<UnitOfProjectTO> unitOfProjects) {
		delegate.updateUnitOfProjects(unitOfProjects);
	}

	@Override
	public String createContractRatePlanComponent(ContractRatePlanComponentTO component) {
		return delegate.createContractRatePlanComponent(component);
	}

	@Override
	public void hardDeleteRatePlanComponent(String[] ids) {
		delegate.hardDeleteRatePlanComponent(ids);
	}

	@Override
	public void softDeleteRatePlanComponent(String[] ids) {
		delegate.softDeleteRatePlanComponent(ids);
	}

	@Override
	public void updateRatePlanComponents(List<ContractRatePlanComponentTO> ratePlanComponents) {
		delegate.updateRatePlanComponents(ratePlanComponents);
	}

	@Override
	public void updateProjects(List<ProjectTO> projects) {
		delegate.updateProjects(projects);
	}

	@Override
	public ContractTO findContractById(String id) {
		return delegate.findContractById(id);
	}

	@Override
	public ProjectTO findProjectById(String id) {
		return delegate.findProjectById(id);
	}

	@Override
	public UnitOfProjectTO findUnitOfProjectById(String id) {
		return delegate.findUnitOfProjectById(id);
	}

	@Override
	public void updateContracts(List<ContractTO> contracts) {
		delegate.updateContracts(contracts);
	}

	@Override
	public void testCascadeCreate() {
		delegate.testCascadeCreate();
	}

	@Override
	public void confirmContract(String contractId) {
		delegate.confirmContract(contractId);
	}

}
