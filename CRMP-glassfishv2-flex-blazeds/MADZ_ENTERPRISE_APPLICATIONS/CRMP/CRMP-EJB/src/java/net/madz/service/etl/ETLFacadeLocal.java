package net.madz.service.etl;

import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;

import net.madz.service.etl.to.ETLColumnContentMappingDescriptorTO;

@Local
@RolesAllowed({ "ADMIN" })
public interface ETLFacadeLocal {
	Map<String, ETLColumnContentMappingDescriptorTO> findContentMappingDescriptorsByETLTableName(String plantId, String databaseName,
			String tableName);
}
