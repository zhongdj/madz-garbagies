/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.module.common.to.create;

import java.io.Serializable;

import net.madz.infra.biz.core.IServiceArgument;
import net.madz.infra.biz.core.ValidationException;

/**
 * 
 * @author Tracy
 */
public class UnitOfMeasureCTO implements Serializable, IServiceArgument {

	protected String name;
	protected String description;

	public void validate() throws ValidationException {
		if (null == name || name.trim().length() <= 0) {
			throw new ValidationException("name can not be empty.");
		}
	}

	public String getArgumentName() {
		return "Measure Unit : " + name + " Description :" + description;
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

}
