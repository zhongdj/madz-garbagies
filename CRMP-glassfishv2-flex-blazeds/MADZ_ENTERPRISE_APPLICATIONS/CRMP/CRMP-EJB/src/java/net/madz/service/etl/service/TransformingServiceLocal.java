package net.madz.service.etl.service;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;

import net.madz.infra.security.persistence.StandardObject;

@Local
@RolesAllowed({ "ADMIN" })
public interface TransformingServiceLocal {

	<RAW, TARGET extends StandardObject> TARGET[] doTransformRawData(Class<TARGET> targetEntityClass, String mixingPlantId,
			RAW[] rawDataArray);
}
