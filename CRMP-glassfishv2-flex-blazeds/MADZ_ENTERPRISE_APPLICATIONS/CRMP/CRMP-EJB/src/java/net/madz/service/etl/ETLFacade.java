package net.madz.service.etl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.madz.infra.biz.core.BusinessException;
import net.madz.infra.security.persistence.Tenant;
import net.madz.infra.security.persistence.User;
import net.madz.infra.security.util.TenantResources;
import net.madz.interceptor.AuditInterceptor;
import net.madz.interceptor.TenantCacheInterceptor;
import net.madz.interceptor.ValidationInterceptor;
import net.madz.module.account.entity.Contact;
import net.madz.module.account.facade.AccountFacadeLocal;
import net.madz.module.common.entity.UnitOfMeasure;
import net.madz.module.common.facade.CommonObjectQueryFacadeLocal;
import net.madz.module.contract.entity.ConstructionPart;
import net.madz.module.contract.entity.UnitOfProject;
import net.madz.module.contract.facade.ContractFacadeLocal;
import net.madz.module.production.entity.ConcreteMixingPlant;
import net.madz.module.production.entity.ProductionRecordBO;
import net.madz.module.production.facade.ProductionFacadeLocal;
import net.madz.service.etl.entity.ColumnDescriptor;
import net.madz.service.etl.entity.DatabaseDescriptor;
import net.madz.service.etl.entity.DatabaseImportIndicator;
import net.madz.service.etl.entity.DatabaseSyncIndicator;
import net.madz.service.etl.entity.ETLColumnContentMappingDescriptor;
import net.madz.service.etl.entity.ETLColumnDescriptor;
import net.madz.service.etl.entity.ETLTableDescriptor;
import net.madz.service.etl.entity.TableDescriptor;
import net.madz.service.etl.service.TransformingServiceLocal;
import net.madz.service.etl.to.ColumnDescriptorTO;
import net.madz.service.etl.to.DatabaseDescriptorTO;
import net.madz.service.etl.to.DatabaseImportIndicatorTO;
import net.madz.service.etl.to.DatabaseSyncIndicatorTO;
import net.madz.service.etl.to.ETLColumnContentMappingDescriptorTO;
import net.madz.service.etl.to.ETLColumnDescriptorTO;
import net.madz.service.etl.to.ETLTableDescriptorTO;
import net.madz.service.etl.to.RawProductionRecordTO;
import net.madz.service.etl.to.TableDescriptorTO;
import net.vicp.madz.infra.binding.TransferObjectFactory;

@Stateless
@RolesAllowed({ "ADMIN" })
@Interceptors({ TenantCacheInterceptor.class, AuditInterceptor.class, ValidationInterceptor.class })
public class ETLFacade implements ETLFacadeRemote, ETLFacadeLocal {

	@PersistenceContext(name = "persistence/EntityManager")
	private EntityManager em;
	@Resource(name = "SessionContext")
	private SessionContext ctx;
	@EJB
	private ProductionFacadeLocal productionFacade;
	@EJB
	private ContractFacadeLocal contractFacade;
	@EJB
	private AccountFacadeLocal accountFacade;
	@EJB
	private CommonObjectQueryFacadeLocal commonFacade;

	@EJB
	private TransformingServiceLocal transformer;

	public String createDatabase(DatabaseDescriptorTO db) {
		DatabaseDescriptor database = TenantResources.newEntity(DatabaseDescriptor.class);
		database.setName(db.getName());
		try {
			final User user = TenantResources.getCurrentUser();
			database.setCreatedBy(user);
			final Tenant tenant = TenantResources.getTenant();
			database.setTenant(tenant);
			final Date createdOn = new Date();
			database.setCreatedOn(createdOn);
			database.setOdbcDatasourceName(db.getOdbcDatasourceName());

			String plantId = db.getPlantId();
			if (null != plantId) {
				database.setPlant(em.find(ConcreteMixingPlant.class, plantId));
			}
			em.persist(database);

			TableDescriptor table = null;
			String tableName = null;
			List<ColumnDescriptorTO> columns = null;
			ColumnDescriptor column = null;
			List<TableDescriptorTO> tables = db.getTables();
			for (TableDescriptorTO tableTO : tables) {
				tableName = tableTO.getName();
				table = TenantResources.newEntity(TableDescriptor.class);
				table.setName(tableName);
				table.setDatabase(database);
				table.setCreatedOn(createdOn);
				table.setCreatedBy(user);
				table.setTenant(tenant);
				em.persist(table);

				columns = tableTO.getColumns();
				for (ColumnDescriptorTO columnTO : columns) {
					column = TenantResources.newEntity(ColumnDescriptor.class);
					column.setColumnDisplaySize(columnTO.getColumnDisplaySize());
					column.setColumnLabel(columnTO.getColumnLabel());
					column.setColumnName(columnTO.getColumnName());
					column.setColumnType(columnTO.getColumnType());
					column.setColumnTypeName(columnTO.getColumnTypeName());
					column.setColumnPrecision(columnTO.getColumnPrecision());
					column.setAutoIncremental(columnTO.isAutoIncremental());
					column.setKey(columnTO.isKey());
					column.setPrimaryKey(columnTO.isPrimaryKey());
					column.setCreatedBy(user);
					column.setCreatedOn(createdOn);
					column.setTenant(tenant);
					column.setTable(table);
					em.persist(table);
				}
			}

			return database.getId();
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).severe(e.getMessage());
			throw new BusinessException(e);
		}
	}

	@Override
	public void createETLColumnContentMappingDescriptorList(List<ETLColumnContentMappingDescriptorTO> contentMappingDescriptorList) {
		try {
			ETLColumnContentMappingDescriptor content = null;
			for (ETLColumnContentMappingDescriptorTO contentTO : contentMappingDescriptorList) {
				content = TenantResources.newEntity(ETLColumnContentMappingDescriptor.class);// new
																								// ETLColumnContentMappingDescriptor();
				content.setMappedData(contentTO.getMappedData());
				content.setRawData(contentTO.getRawData());
				ETLColumnDescriptor column = em.find(ETLColumnDescriptor.class, contentTO.getColumnDescriptorId());
				content.setColumnDescriptor(column);
				em.persist(content);
			}
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).severe(e.getMessage());
			throw new BusinessException(e);
		}
	}

	@Override
	public String createETLTableDescriptor(ETLTableDescriptorTO tableTO) {
		try {
			ETLTableDescriptor table = TenantResources.newEntity(ETLTableDescriptor.class);// new
																							// ETLTableDescriptor();
			table.setDatabase(findDatabaseByName(tableTO.getDatabaseName()));
			table.setDescription(tableTO.getDescription());
			table.setTargetClassName(tableTO.getTargetClassName());
			table.setRawTableName(tableTO.getRawTableName());

			em.persist(table);

			List<ETLColumnDescriptorTO> columnDescriptors = tableTO.getColumnDescriptors();

			ETLColumnDescriptor column = null;
			if (null != columnDescriptors && 0 < columnDescriptors.size()) {
				for (ETLColumnDescriptorTO columnTO : columnDescriptors) {
					column = TenantResources.newEntity(ETLColumnDescriptor.class);// new
																					// ETLColumnDescriptor();
					column.setDescription(columnTO.getDescription());
					column.setRawColumnName(columnTO.getRawColumnName());
					column.setTableDescriptor(table);
					column.setTargetFieldName(columnTO.getTargetFieldName());
					em.persist(column);
				}
			}

			return table.getId();
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).severe(ex.getMessage());
			throw new BusinessException(ex);
		}

	}

	@Override
	public void deleteUpdateContentMappingDescriptors(String[] contentMappingDescriptorIds) {
		ETLColumnContentMappingDescriptor descriptor = null;
		for (String id : contentMappingDescriptorIds) {
			descriptor = em.find(ETLColumnContentMappingDescriptor.class, id);
			descriptor.setDeleted(true);
		}

	}

	@Override
	public void doSyncRawProductionRecords(String mixingPlantId, RawProductionRecordTO[] rawDataArray) {

		ProductionRecordBO[] productionRecords = transformer.doTransformRawData(ProductionRecordBO.class, mixingPlantId, rawDataArray);
		em.setFlushMode(FlushModeType.COMMIT);
		for (ProductionRecordBO productionRecord : productionRecords) {
			em.persist(productionRecord);
		}
		em.flush();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ETLColumnContentMappingDescriptorTO> findContentMappingDescriptors() {
		final Query query = em
				.createQuery(
						"SELECT o FROM ETLColumnContentMappingDescriptor o "
								+ "WHERE o.tenant.id = :tenantId ")
								//+ "ORDER BY o.plant.id, o.columnDescriptor.tableDescriptor.database.name, o.columnDescriptor.tableDescriptor.rawTableName ")
				.setParameter("tenantId", TenantResources.getTenant().getId());
		final List<ETLColumnContentMappingDescriptor> result = query.getResultList();
		try {
			return TransferObjectFactory.assembleTransferObjectList(result, ETLColumnContentMappingDescriptorTO.class);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<ETLColumnContentMappingDescriptorTO> findContentMappingDescriptors(String plantId, String databaseName, String tableName) {
		final List<ETLColumnContentMappingDescriptor> result = em
				.createQuery(
						"SELECT o FROM ETLColumnContentMappingDescriptor o WHERE o.tenant.id = :tenantId"
								+ " AND o.columnDescriptor.tableDescriptor.rawTableName = :tableName "
								+ " AND o.columnDescriptor.plant.id = :plantId "
								+ " AND o.columnDescriptor.tableDescriptor.database.name = :databaseName")
				.setParameter("tenantId", TenantResources.getTenant().getId()).setParameter("databaseName", databaseName)
				.setParameter("tableName", tableName).setParameter("plantId", plantId).getResultList();

		try {
			return TransferObjectFactory.assembleTransferObjectList(result, ETLColumnContentMappingDescriptorTO.class);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public Map<String, ETLColumnContentMappingDescriptorTO> findContentMappingDescriptorsByETLTableName(String plantId,
			String databaseName, String tableName) {
		final List<ETLColumnContentMappingDescriptorTO> resultList = findContentMappingDescriptors(plantId, databaseName, tableName);
		final Map<String, ETLColumnContentMappingDescriptorTO> result = new HashMap<String, ETLColumnContentMappingDescriptorTO>();
		for (ETLColumnContentMappingDescriptorTO descriptor : resultList) {
			result.put(descriptor.getMappingKey(), descriptor);
		}
		return result;
	}

	private DatabaseDescriptor findDatabaseByName(String databaseName) throws NamingException {
		DatabaseDescriptor descriptor = (DatabaseDescriptor) em.createNamedQuery("findDatabaseDescriptorByName")
				.setParameter("name", databaseName).setParameter("tenantId", TenantResources.getTenant().getId()).getSingleResult();
		return descriptor;
	}

	@Override
	public DatabaseDescriptorTO findDatabaseDescriptorByPlantId(String plantId) {

		try {
			final DatabaseDescriptor descriptor = (DatabaseDescriptor) em.createNamedQuery("findDatabaseDescriptorByPlantId")
			.setParameter("plantId", plantId).setParameter("tenantId", TenantResources.getTenant().getId()).getSingleResult();

			DatabaseDescriptorTO result = TransferObjectFactory.createTransferObject(DatabaseDescriptorTO.class, descriptor);

			final List<TableDescriptor> tables = descriptor.getTables();
			final List<TableDescriptorTO> resultTables = new ArrayList<TableDescriptorTO>();
			TableDescriptorTO resultTable = null;
			for (TableDescriptor tableDescriptor : tables) {
				resultTable = TransferObjectFactory.createTransferObject(TableDescriptorTO.class, tableDescriptor);
				resultTable.setColumns(TransferObjectFactory.assembleTransferObjectList(tableDescriptor.getColumns(),
						ColumnDescriptorTO.class));
				resultTables.add(resultTable);
			}
			result.setTables(resultTables);
			return result;
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).severe(e.getMessage());
			throw new BusinessException(e);
		}

	
	}

	@Override
	public DatabaseDescriptorTO getDatabaseDescriptor(String databaseName) {
		try {
			DatabaseDescriptor descriptor = findDatabaseByName(databaseName);
			DatabaseDescriptorTO result = TransferObjectFactory.createTransferObject(DatabaseDescriptorTO.class, descriptor);

			final List<TableDescriptor> tables = descriptor.getTables();
			final List<TableDescriptorTO> resultTables = new ArrayList<TableDescriptorTO>();
			TableDescriptorTO resultTable = null;
			for (TableDescriptor tableDescriptor : tables) {
				resultTable = TransferObjectFactory.createTransferObject(TableDescriptorTO.class, tableDescriptor);
				resultTable.setColumns(TransferObjectFactory.assembleTransferObjectList(tableDescriptor.getColumns(),
						ColumnDescriptorTO.class));
				resultTables.add(resultTable);
			}
			result.setTables(resultTables);
			return result;
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).severe(e.getMessage());
			throw new BusinessException(e);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DatabaseImportIndicatorTO> getDatabaseImportIndicator(String databaseName) {
		try {
			List<DatabaseImportIndicator> descriptors = em.createNamedQuery("findDatabaseImportIndicatorByDatabaseName")
					.setParameter("databaseName", databaseName).setParameter("tenantId", TenantResources.getTenant().getId())
					.getResultList();

			if (null == descriptors || 0 >= descriptors.size()) {
				DatabaseDescriptor db = findDatabaseByName(databaseName);
				List<TableDescriptor> tables = db.getTables();
				DatabaseImportIndicator indicator = null;
				for (TableDescriptor table : tables) {
					indicator = TenantResources.newEntity(DatabaseImportIndicator.class);// new
																							// DatabaseImportIndicator();
					indicator.setDatabase(db);
					indicator.setTable(table);
					indicator.setCreatedBy(TenantResources.getCurrentUser());
					indicator.setTenant(TenantResources.getTenant());
					indicator.setCreatedOn(new Date());
					List<ColumnDescriptor> columns = table.getColumns();

					ColumnDescriptor incrementalColumn = null;
					ColumnDescriptor primaryKeyColumn = null;
					for (ColumnDescriptor column : columns) {
						if (column.isAutoIncremental()) {
							incrementalColumn = column;
						} else if (column.isPrimaryKey()) {
							primaryKeyColumn = column;
						}
					}
					if (null != incrementalColumn) {
						indicator.setIndicatorColumn(incrementalColumn);
					} else if (null != primaryKeyColumn) {
						indicator.setIndicatorColumn(primaryKeyColumn);
					} else {
						continue;
						// throw new IllegalStateException(
						// "No indicator column found.");
					}
					em.persist(indicator);
				}
			}
			descriptors = em.createNamedQuery("findDatabaseImportIndicatorByDatabaseName").setParameter("databaseName", databaseName)
					.setParameter("tenantId", TenantResources.getTenant().getId()).getResultList();

			return TransferObjectFactory.assembleTransferObjectList(descriptors, DatabaseImportIndicatorTO.class);
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).severe(e.getMessage());
			throw new BusinessException(e);
		}
	}

	@Override
	public DatabaseImportIndicatorTO getDatabaseImportIndicator(String mysqlDatabaseName, String rawTableName) {
		try {
			getDatabaseImportIndicator(mysqlDatabaseName);
			DatabaseImportIndicator descriptor = (DatabaseImportIndicator) em
					.createNamedQuery("findDatabaseImportIndicatorByDatabaseNameAndTableName")

					.setParameter("databaseName", mysqlDatabaseName).setParameter("tableName", rawTableName)
					.setParameter("tenantId", TenantResources.getTenant().getId()).getSingleResult();
			return TransferObjectFactory.createTransferObject(DatabaseImportIndicatorTO.class, descriptor);
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).severe(e.getMessage());
			throw new BusinessException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DatabaseSyncIndicatorTO> getDatabaseSyncIndicator(String databaseName) {

		try {
			List<DatabaseSyncIndicator> descriptors = em.createNamedQuery("findDatabaseSyncIndicatorByDatabaseName")
					.setParameter("databaseName", databaseName).setParameter("tenantId", TenantResources.getTenant().getId())
					.getResultList();

			if (null == descriptors || 0 >= descriptors.size()) {
				DatabaseDescriptor db = findDatabaseByName(databaseName);
				List<TableDescriptor> tables = db.getTables();
				DatabaseSyncIndicator indicator = null;
				for (TableDescriptor table : tables) {
					indicator = TenantResources.newEntity(DatabaseSyncIndicator.class);// new
																						// DatabaseSyncIndicator();
					indicator.setDatabase(db);
					indicator.setTable(table);
					indicator.setCreatedBy(TenantResources.getCurrentUser());
					indicator.setTenant(TenantResources.getTenant());
					indicator.setCreatedOn(new Date());
					List<ColumnDescriptor> columns = table.getColumns();

					ColumnDescriptor incrementalColumn = null;
					ColumnDescriptor primaryKeyColumn = null;
					for (ColumnDescriptor column : columns) {
						if (column.isAutoIncremental()) {
							incrementalColumn = column;
						} else if (column.isPrimaryKey()) {
							primaryKeyColumn = column;
						}
					}
					if (null != incrementalColumn) {
						indicator.setIndicatorColumn(incrementalColumn);
					} else if (null != primaryKeyColumn) {
						indicator.setIndicatorColumn(primaryKeyColumn);
					} else {
						continue;
						// throw new IllegalStateException(
						// "No indicator column found.");
					}
					em.persist(indicator);
				}
			}
			descriptors = em.createNamedQuery("findDatabaseSyncIndicatorByDatabaseName").setParameter("databaseName", databaseName)
					.setParameter("tenantId", TenantResources.getTenant().getId()).getResultList();

			return TransferObjectFactory.assembleTransferObjectList(descriptors, DatabaseSyncIndicatorTO.class);
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).severe(e.getMessage());
			throw new BusinessException(e);
		}

	}

	private ConcreteMixingPlant getMatchedMixingPlantById(String id) {
		ConcreteMixingPlant matched = null;
		if (null != id) {
			matched = em.find(ConcreteMixingPlant.class, id);
		}
		if (null == matched || !matched.getTenant().equals(TenantResources.getTenant())) {
			return productionFacade.getUnspecifiedConcreteMixingPlant();
		} else {
			return matched;
		}
	}

	private ConstructionPart getMatchedPartById(String partId) {
		ConstructionPart matched = null;
		if (null != partId) {
			matched = em.find(ConstructionPart.class, partId);
		}

		if (null == matched || !matched.getTenant().equals(TenantResources.getTenant())) {
			return contractFacade.getUnspecifiedConstructionPart();
		} else {
			return matched;
		}
	}

	private UnitOfMeasure getMatchedUnitOfMeasureById(String unitOfMeasureId) {
		UnitOfMeasure matched = null;
		if (null != unitOfMeasureId) {
			matched = em.find(UnitOfMeasure.class, unitOfMeasureId);
		}

		if (null == matched || !matched.getTenant().equals(TenantResources.getTenant())) {
			return commonFacade.getUnspecifiedUnitOfMeasure();
		} else {
			return matched;
		}

	}

	private UnitOfProject getMatchedUnitOfProjectById(String unitOfProjectId) {
		UnitOfProject matched = null;
		if (null != unitOfProjectId) {
			matched = em.find(UnitOfProject.class, unitOfProjectId);
		}

		if (null == matched || !matched.getTenant().equals(TenantResources.getTenant())) {
			return contractFacade.getUnspecifiedUnitOfProject();
		} else {
			return matched;
		}
	}

	@Override
	public ETLTableDescriptorTO getProductionRecordETLTableDescriptor(String databaseName) {
		try {
			ETLTableDescriptor table = (ETLTableDescriptor) em.createNamedQuery("findETLTableDescriptorByDatabaseName")
					.setParameter("dbName", databaseName).setParameter("targetClassName", RawProductionRecordTO.class.getName())
					.setParameter("tenantId", TenantResources.getTenant().getId()).getSingleResult();
			return TransferObjectFactory.createTransferObject(ETLTableDescriptorTO.class, table);
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).severe(ex.getMessage());
			throw new BusinessException(ex);
		}
	}

	public void initialImportRawUnitOfProject(String[] names, String plantId) {
		if (null == names || 0 >= names.length || null == plantId) {
			throw new IllegalArgumentException("Arguments cannot be empty.");
		}

		final Contact unspecifiedContact = accountFacade.getUnspecifiedContact();

		final ConcreteMixingPlant plant = em.find(ConcreteMixingPlant.class, plantId);

		final String mappingKey = "Production.ProductionRecord.unitOfProject.id";
		for (String name : names) {
			UnitOfProject u = TenantResources.newEntity(UnitOfProject.class);// new
																				// UnitOfProject();
			u.setName(name);
			u.setBillToContact(unspecifiedContact);
			u.setPayerContact(unspecifiedContact);
			u.setShipToContact(unspecifiedContact);
			u.setSoldToContact(unspecifiedContact);
			em.persist(u);

			ETLColumnContentMappingDescriptor d = TenantResources.newEntity(ETLColumnContentMappingDescriptor.class);// new
																														// ETLColumnContentMappingDescriptor();
			d.setColumnDescriptor(null);
			d.setMappedData(u.getId());
			d.setMappingKey(mappingKey);
			d.setPlant(plant);
			d.setRawData(name);
			em.persist(d);
		}

	}

	@Override
	public void updateContentMappingDescriptors(ETLColumnContentMappingDescriptorTO[] contentMappingDescriptors) {
		ETLColumnContentMappingDescriptor descriptor = null;
		for (ETLColumnContentMappingDescriptorTO to : contentMappingDescriptors) {
			descriptor = em.find(ETLColumnContentMappingDescriptor.class, to.getId());
			descriptor.setMappingKey(to.getMappingKey());
			descriptor.setMappedData(to.getMappedData());
			em.merge(descriptor);
		}

	}

	@Override
	public void updateDatabaseImportIndicator(DatabaseImportIndicatorTO indicator) {
		final DatabaseImportIndicator indicatorEntity = em.find(DatabaseImportIndicator.class, indicator.getId());
		indicatorEntity.setIndicatorValue(indicator.getIndicatorValue());
		em.merge(indicatorEntity);
	}

	@Override
	public void updateDatabaseSyncIndicator(DatabaseSyncIndicatorTO indicator) {
		final DatabaseSyncIndicator indicatorEntity = em.find(DatabaseSyncIndicator.class, indicator.getId());
		indicatorEntity.setIndicatorValue(indicator.getIndicatorValue());
		em.merge(indicatorEntity);
	}

}
