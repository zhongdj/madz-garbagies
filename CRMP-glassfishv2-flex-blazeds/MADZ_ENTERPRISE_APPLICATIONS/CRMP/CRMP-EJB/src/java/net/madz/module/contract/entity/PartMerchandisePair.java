package net.madz.module.contract.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.madz.infra.security.persistence.StandardObject;
import net.madz.module.common.entity.Merchandise;

@Entity
@Table(name = "crmp_part_merchandise_pair", catalog = "crmp", schema = "")
public class PartMerchandisePair extends StandardObject {

	private static final long serialVersionUID = -8644625190155071532L;

	@ManyToOne
	private Merchandise merchandise;

	@ManyToOne
	private ConstructionPart part;

	@ManyToOne
	private ContractBO contract;

	public Merchandise getMerchandise() {
		return merchandise;
	}

	public void setMerchandise(Merchandise merchandise) {
		this.merchandise = merchandise;
	}

	public ConstructionPart getPart() {
		return part;
	}

	public void setPart(ConstructionPart part) {
		this.part = part;
	}

	public ContractBO getContract() {
		return contract;
	}

	public void setContract(ContractBO contract) {
		this.contract = contract;
	}

}
