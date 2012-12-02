package net.madz.module.contract.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import net.madz.infra.security.persistence.StandardObject;

@Entity
@Table(name = "crmp_construction_part", catalog = "crmp", schema = "")
public class ConstructionPart extends StandardObject {

	private static final long serialVersionUID = 1L;
	@Column(name = "NAME")
	private String name;
	@Column(name = "DESCRIPTION")
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "ConstructionPart [name=" + name + ", description=" + description + "]";
	}

}
