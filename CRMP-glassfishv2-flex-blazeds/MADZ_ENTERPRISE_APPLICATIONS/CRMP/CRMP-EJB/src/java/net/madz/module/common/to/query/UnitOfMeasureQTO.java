/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.madz.module.common.to.query;

import net.madz.module.common.entity.UnitOfMeasure;
import net.madz.standard.StandardTO;

/**
 * 
 * @author Barry
 */
public class UnitOfMeasureQTO extends StandardTO<UnitOfMeasure> {

	private static final long serialVersionUID = -8971326924511755290L;
	private String name;
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
}
