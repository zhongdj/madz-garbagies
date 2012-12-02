package net.madz.service.etl.service;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.madz.infra.biz.core.BusinessException;
import net.madz.infra.security.persistence.StandardObject;
import net.madz.infra.security.util.TenantResources;
import net.madz.module.common.facade.CommonObjectQueryFacadeLocal;
import net.madz.module.contract.facade.ContractFacadeLocal;
import net.madz.module.production.facade.ProductionFacadeLocal;
import net.madz.service.etl.IMappingStrategy;
import net.madz.service.etl.annotation.EntityMappingDescriptor;
import net.madz.service.etl.annotation.EntityMappingDescriptors;
import net.madz.service.etl.annotation.FieldMappingDescriptor;
import net.madz.service.etl.annotation.FieldMappingDescriptor.Type;
import net.madz.service.etl.annotation.FieldMappingDescriptors;
import net.madz.service.etl.entity.ETLColumnContentMappingDescriptor;

@Stateless
@RolesAllowed({ "ADMIN" })
public class TransformingService implements TransformingServiceLocal {

	private static final String UNSPECIFIED = "UNSPECIFIED";
	@PersistenceContext(name = "persistence/EntityManager")
	private EntityManager em;
	@Resource(name = "SessionContext")
	private SessionContext ctx;
	@EJB
	private ProductionFacadeLocal productionFacade;
	@EJB
	private ContractFacadeLocal contractFacade;
	@EJB
	private CommonObjectQueryFacadeLocal commonFacade;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <RAW, TARGET extends StandardObject> TARGET[] doTransformRawData(Class<TARGET> targetEntityClass, String mixingPlantId,
			RAW[] rawDataArray) {

		final Class<?> rawDataType = rawDataArray.getClass().getComponentType();
		final EntityMappingDescriptor targetEntityMappingDescriptor = getEntityMappingDescriptor(targetEntityClass, rawDataType);

		final Field descriptionField;
		String rawDescriptionField = targetEntityMappingDescriptor.rawDescriptionField();

		if (0 >= rawDescriptionField.trim().length()) {
			rawDescriptionField = null;
			descriptionField = null;
		} else {
			try {
				descriptionField = findFieldThroughClassHierarchy(targetEntityClass, rawDescriptionField);
				// descriptionField = targetEntityClass
				// .getDeclaredField(rawDescriptionField);
			} catch (Exception e) {
				throw new BusinessException("Cannot find Description Field '" + rawDescriptionField + "' from target entity class: "
						+ targetEntityClass.getName(), e);
			}

		}
		final HashMap<Field, FieldMappingDescriptor> fieldDescriptorMap = buildFieldMetaData(targetEntityClass, rawDataArray);

		final Set<Entry<Field, FieldMappingDescriptor>> entrySet = fieldDescriptorMap.entrySet();

		final ArrayList<TARGET> result = new ArrayList<TARGET>();

		TARGET targetInstance = null;
		Field rawField = null;
		FieldMappingDescriptor rawFieldMappingDescriptor = null;
		Type type = null;
		String mappingKey = null;
		String targetFieldName = null;
		Class strategyClass = null;
		try {
			for (RAW raw : rawDataArray) {
				targetInstance = TenantResources.newEntity(targetEntityClass);
				for (Entry<Field, FieldMappingDescriptor> entry : entrySet) {
					rawField = entry.getKey();
					rawField.setAccessible(true);

					final String rawData = (String) rawField.get(raw);

					rawFieldMappingDescriptor = entry.getValue();
					type = rawFieldMappingDescriptor.type();
					targetFieldName = rawFieldMappingDescriptor.mapToField();
					strategyClass = rawFieldMappingDescriptor.mappingStrategyClass();
					mappingKey = rawFieldMappingDescriptor.mappingKey();

					Field targetField = findFieldThroughClassHierarchy(targetEntityClass, targetFieldName);
					boolean accessible = targetField.isAccessible();
					if (!accessible) {
						targetField.setAccessible(true);
					}

					if (Type.Field == type) {

						if (String.class.equals(targetField.getType())) {
							targetField.set(targetInstance, rawData);
						} else if (Date.class.equals(targetField.getType())) {
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							targetField.set(targetInstance, format.parse(rawData));
						} else {
							if (Integer.class.equals(targetField.getType())) {
								targetField.set(targetInstance, Integer.valueOf(rawData));
							} else if (Long.class.equals(targetField.getType())) {
								targetField.set(targetInstance, Long.valueOf(rawData));
							} else if (Double.class.equals(targetField.getType())) {
								targetField.set(targetInstance, Double.valueOf(rawData));
							} else if (Float.class.equals(targetField.getType())) {
								targetField.set(targetInstance, Float.valueOf(rawData));
							}
						}

					} else if (Type.Relationship == type) {
						if (null != strategyClass && !strategyClass.equals(IMappingStrategy.class)) {
							IMappingStrategy<?> strategy = (IMappingStrategy<?>) strategyClass.newInstance();
							Object value = strategy.covert(rawData);
							if (null == value) {

							} else {
								targetField.set(targetInstance, value);
								continue;
							}
						}

						String tenantId = TenantResources.getTenant().getId();

						@SuppressWarnings("unchecked")
						final List<ETLColumnContentMappingDescriptor> contentMappingList = (List<ETLColumnContentMappingDescriptor>) em
								.createNamedQuery("findETLColumnContentMappingDescriptor").setParameter("tenantId", tenantId)
								.setParameter("plantId", mixingPlantId).setParameter("rawData", rawData)
								.setParameter("mappingKey", mappingKey).getResultList();

						if (0 >= contentMappingList.size() || 1 < contentMappingList.size()) {
							Object relatedEntity = null;

							if (null != rawData && 0 < rawData.trim().length()) {

							}

							// ASSIGN Unspecified
							String unspecifiedProviderMethod = rawFieldMappingDescriptor.unspecifiedProviderMethod();

							if (null != unspecifiedProviderMethod && 0 < unspecifiedProviderMethod.length()) {
								relatedEntity = getUnspecifiedEntity(rawDataArray, rawField, unspecifiedProviderMethod);
							}

							assert null != relatedEntity;

							setRelationship(targetInstance, targetField, relatedEntity);
						} else {
							final ETLColumnContentMappingDescriptor contentMappingDescriptor = contentMappingList.get(0);
							String targetRelationshipId = contentMappingDescriptor.getMappedData();
							Class<?> targetRelationshipType = targetField.getType();
							Object targetRelationship = em.find(targetRelationshipType, targetRelationshipId);
							if (null != targetRelationship) {
								targetField.set(targetInstance, targetRelationship);
							} else {
								// THROW EXCEPTION
							}
						}
					}

					if (!accessible) {
						targetField.setAccessible(false);
					}

					rawField.setAccessible(false);
				}

				if (null != rawDescriptionField) {
					final String description = raw.toString();

					if (null != descriptionField) {
						if (descriptionField.isAccessible()) {
							descriptionField.set(targetInstance, description);
						} else {
							descriptionField.setAccessible(true);
							descriptionField.set(targetInstance, description);
							descriptionField.setAccessible(false);
						}
					}
				}
				result.add(targetInstance);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IllegalStateException(ex);
		}

		TARGET[] tempArray = (TARGET[]) Array.newInstance(targetEntityClass, 0);
		return result.toArray(tempArray);

	}

	private <TARGET> void setRelationship(TARGET targetInstance, Field targetField, Object relatedEntity) throws IllegalAccessException {
		if (!relatedEntity.getClass().isAssignableFrom(targetField.getType())) {

			if (targetField.getType().isAssignableFrom(Set.class)) {

				HashSet set = new HashSet();
				set.add(relatedEntity);
				targetField.set(targetInstance, set);
			} else if (targetField.getType().isAssignableFrom(List.class)) {
				ArrayList list = new ArrayList();
				list.add(relatedEntity);
				targetField.set(targetInstance, list);
			} else {
				// TODO Support Array
			}

		} else {
			targetField.set(targetInstance, relatedEntity);
		}
	}

	private <RAW> Object getUnspecifiedEntity(RAW[] rawDataArray, Field rawField, String unspecifiedProviderMethod)
			throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		String[] segments = unspecifiedProviderMethod.split("\\.");
		if (2 != segments.length) {
			throw new IllegalStateException("Invalid unspecifiedProviderMethod on FieldMappingDescriptor of Field: "
					+ rawDataArray.getClass().getComponentType().getName() + "." + rawField.getName());
		}

		String ejbInstanceName = segments[0];
		String methodName = segments[1];
		Field ejbField = findFieldThroughClassHierarchy(getClass(), ejbInstanceName);
		Object ejbInstance = ejbField.get(this);
		Method providerMethod = ejbInstance.getClass().getMethod(methodName);

		Object unspecifiedEntity = providerMethod.invoke(ejbInstance);

		// if (0 == contentMappingList.size()) {
		// final ETLColumnContentMappingDescriptor
		// contentMapping = new
		// ETLColumnContentMappingDescriptor();
		// TenantIdentifiedResourceHelper
		// .setAuditProperties(contentMapping);
		// contentMapping
		// .setMappedData(((StandardObject)
		// unspecifiedEntity)
		// .getId());
		// contentMapping.setMappingKey(mappingKey);
		// contentMapping.setPlant(em.find(
		// ConcreteMixingPlant.class,
		// mixingPlantId));
		// //
		// contentMapping.setColumnDescriptor(columnDescriptor);
		// em.persist(contentMapping);
		// }
		return unspecifiedEntity;
	}

	private <RAW, TARGET extends StandardObject> HashMap<Field, FieldMappingDescriptor> buildFieldMetaData(Class<TARGET> targetEntityClass,
			RAW[] rawDataArray) {
		final Class<?> rawDataType = rawDataArray.getClass().getComponentType();
		final EntityMappingDescriptor targetEntityMappingDescriptor = getEntityMappingDescriptor(targetEntityClass, rawDataType);

		// targetEntityMappingDescriptor is not used in the following code
		// segment right now.

		final HashMap<Field, FieldMappingDescriptor> fieldDescriptorMap = new HashMap<Field, FieldMappingDescriptor>();
		final Field[] allFields = getAllFields(rawDataType);

		FieldMappingDescriptors fieldMappingDescriptors = null;
		FieldMappingDescriptor fieldDescriptorOftargetEntity = null;
		for (Field field : allFields) {
			fieldMappingDescriptors = field.getAnnotation(FieldMappingDescriptors.class);

			if (null == fieldMappingDescriptors) {
				fieldDescriptorOftargetEntity = field.getAnnotation(FieldMappingDescriptor.class);
				if (null == fieldDescriptorOftargetEntity || fieldDescriptorOftargetEntity.unsupport()) {
					fieldDescriptorOftargetEntity = null;
				}
			} else {

				for (FieldMappingDescriptor fieldDescriptor : fieldMappingDescriptors.value()) {
					if (targetEntityClass.equals(fieldDescriptor.entityClass())) {
						if (fieldDescriptor.unsupport()) {
							fieldDescriptorOftargetEntity = null;
							break;
						} else {
							fieldDescriptorOftargetEntity = fieldDescriptor;
							break;
						}
					} else {
						continue;
					}
				}
			}

			if (null != fieldDescriptorOftargetEntity) {
				fieldDescriptorMap.put(field, fieldDescriptorOftargetEntity);
				fieldDescriptorOftargetEntity = null;
			}

		}
		return fieldDescriptorMap;
	}

	private <TARGET> EntityMappingDescriptor getEntityMappingDescriptor(Class<TARGET> targetEntityClass, final Class<?> rawDataType) {
		final EntityMappingDescriptors entityMappingDescriptorArray = rawDataType.getAnnotation(EntityMappingDescriptors.class);

		EntityMappingDescriptor targetTypeDescriptor = null;
		if (null == entityMappingDescriptorArray) {
			targetTypeDescriptor = rawDataType.getAnnotation(EntityMappingDescriptor.class);
		} else {
			final EntityMappingDescriptor[] entityMappingDescriptors = entityMappingDescriptorArray.value();
			for (EntityMappingDescriptor entityMappingDescriptor : entityMappingDescriptors) {
				if (targetEntityClass.equals(entityMappingDescriptor.mapToEntity())) {
					targetTypeDescriptor = entityMappingDescriptor;
					break;
				} else {
					continue;
				}
			}
		}

		if (null == targetTypeDescriptor) {
			throw new IllegalStateException("Cannot find any EntityMappingDescriptor annotation of Type " + targetEntityClass.getName()
					+ " on RAW type " + rawDataType.getName());
		}

		return targetTypeDescriptor;
	}

	private static <T> Field[] getAllFields(Class<T> clazz) {
		ArrayList<Field> result = new ArrayList<Field>();

		List<Field> fieldList = Arrays.asList(clazz.getDeclaredFields());
		result.addAll(fieldList);
		Class<?> cl = clazz.getSuperclass();
		while (null != cl && Object.class != cl) {
			fieldList = Arrays.asList(cl.getDeclaredFields());
			result.addAll(fieldList);
			cl = cl.getSuperclass();
		}
		return result.toArray(new Field[fieldList.size()]);
	}

	private static Field findFieldThroughClassHierarchy(Class cl, String name) {
		try {
			return cl.getDeclaredField(name);
		} catch (SecurityException e) {
			throw new IllegalStateException(e);
		} catch (NoSuchFieldException e) {
			if (null != cl.getSuperclass()) {
				return findFieldThroughClassHierarchy(cl.getSuperclass(), name);
			} else {
				throw new IllegalStateException(e);
			}
		}
	}
}
