package net.madz.module.contract.facade;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;

import net.madz.module.contract.ConstructionPartTO;
import net.madz.module.contract.ContractTO;
import net.madz.module.contract.ProjectTO;
import net.madz.module.contract.UnitOfProjectTO;
import net.madz.module.contract.entity.ConstructionPart;
import net.madz.module.contract.entity.ContractBO;
import net.madz.module.contract.entity.Project;
import net.madz.module.contract.entity.UnitOfProject;

@Local
@RolesAllowed({ "ADMIN" })
public interface ContractFacadeLocal {
	public String createConstructionPart(ConstructionPartTO part);

	public String createContract(ContractTO contract);

	public String createProject(ProjectTO project);

	public String createUnitOfProject(UnitOfProjectTO unit);

	public List<ConstructionPartTO> findConstructionParts();

	public List<ContractTO> findContracts();

	public List<ProjectTO> findProjects();

	public List<UnitOfProjectTO> findUnitOfProjects();

	ConstructionPart getUnspecifiedConstructionPart();

	ContractBO getUnspecifiedContract();

	Project getUnspecifiedProject();

	UnitOfProject getUnspecifiedUnitOfProject();

	public void hardDeleteAllConstructionParts();

	public void hardDeleteAllContracts();

	public void hardDeleteAllProjects();

	public void hardDeleteAllUnitOfProjects();

	public void hardDeleteConstructionParts(String[] ids);

	public void hardDeleteContracts(String[] ids);

	public void hardDeleteProjects(String[] ids);

	public void hardDeleteUnitOfProjects(String[] ids);

	public void softDeleteConstructionParts(String[] ids);

	public void softDeleteContracts(String[] ids);

	public void softDeleteProjects(String[] ids);

	public void softDeleteUnitOfProjects(String[] ids);

	public void updateConstructionParts(List<ConstructionPartTO> parts);

	public void updateUnitOfProjects(List<UnitOfProjectTO> unitOfProjects);
}
