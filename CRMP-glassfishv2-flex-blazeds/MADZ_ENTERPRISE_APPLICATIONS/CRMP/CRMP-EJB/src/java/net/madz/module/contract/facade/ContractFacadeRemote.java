package net.madz.module.contract.facade;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Remote;

import net.madz.module.contract.ConstructionPartTO;
import net.madz.module.contract.ContractRatePlanComponentTO;
import net.madz.module.contract.ContractTO;
import net.madz.module.contract.ProjectTO;
import net.madz.module.contract.UnitOfProjectTO;

@Remote
@RolesAllowed({ "ADMIN" })
public interface ContractFacadeRemote {
	String createConstructionPart(ConstructionPartTO part);

	String createContract(ContractTO contract);
	
	void confirmContract(String contractId);

	String createContractRatePlanComponent(ContractRatePlanComponentTO component);

	String createProject(ProjectTO project);

	String createUnitOfProject(UnitOfProjectTO unit);

	List<ConstructionPartTO> findConstructionParts();

	ContractTO findContractById(String id);

	List<ContractTO> findContracts();

	ProjectTO findProjectById(String id);

	List<ProjectTO> findProjects();

	UnitOfProjectTO findUnitOfProjectById(String id);

	List<UnitOfProjectTO> findUnitOfProjects();

	void hardDeleteAllConstructionParts();

	void hardDeleteAllContracts();

	void hardDeleteAllProjects();

	void hardDeleteAllUnitOfProjects();

	void hardDeleteConstructionParts(String[] ids);

	void hardDeleteContracts(String[] ids);

	void hardDeleteProjects(String[] ids);

	void hardDeleteRatePlanComponent(String[] ids);

	void hardDeleteUnitOfProjects(String[] ids);

	void softDeleteConstructionParts(String[] ids);

	void softDeleteContracts(String[] ids);

	void softDeleteProjects(String[] ids);

	void softDeleteRatePlanComponent(String[] ids);

	void softDeleteUnitOfProjects(String[] ids);

	void updateConstructionParts(List<ConstructionPartTO> parts);

	void updateContracts(List<ContractTO> contracts);

	void updateProjects(List<ProjectTO> projects);

	void updateRatePlanComponents(
			List<ContractRatePlanComponentTO> ratePlanComponents);

	void updateUnitOfProjects(List<UnitOfProjectTO> unitOfProjects);
	
	//TEST METHOD
	void testCascadeCreate();
}
