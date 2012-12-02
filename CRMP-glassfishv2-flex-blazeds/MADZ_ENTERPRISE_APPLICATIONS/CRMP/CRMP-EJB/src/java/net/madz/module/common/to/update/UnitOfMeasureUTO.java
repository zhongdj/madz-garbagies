/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.module.common.to.update;

import java.io.Serializable;

import net.madz.infra.biz.core.IServiceArgument;
import net.madz.infra.biz.core.ValidationException;

/**
 * 
 * @author Barry
 */
public class UnitOfMeasureUTO implements IServiceArgument, Serializable {

	private static final long serialVersionUID = 620083963565552558L;
	private String description;
	private String name;
	private String id;
	private boolean deleted;

	public UnitOfMeasureUTO() {
		super();
	}

	public UnitOfMeasureUTO(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public UnitOfMeasureUTO(String id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public UnitOfMeasureUTO(String id, String name, String description, boolean deleted) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.deleted = deleted;
	}

	public String getId() {
		return id;
	}

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

	public void validate() throws ValidationException {
		if (name == null || name.trim().length() <= 0) {
			throw new ValidationException("name is invalid.");
		}
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getArgumentName() {
		return "Measure Unit: " + name;
	}

	public void setId(String id) {
		this.id = id;
	}

}
