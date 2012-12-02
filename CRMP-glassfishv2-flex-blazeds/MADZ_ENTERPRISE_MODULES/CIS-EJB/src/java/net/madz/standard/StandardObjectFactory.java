package net.madz.standard;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.CascadeType;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import net.madz.infra.security.persistence.StandardObject;
import net.madz.infra.security.util.TenantResources;
import net.madz.utl.ClassUtils;
import net.vicp.madz.infra.binding.annotation.Binding;

public class StandardObjectFactory {

	public static <TARGET, SOURCE> Collection<TARGET> convertTO2Entity(Class<TARGET> clz, Collection<SOURCE> tos) {
		return null;
	}

	/**
	 * public class A_TO extends StandardTO<Entity> {
	 * 
	 * private static final long serialVersionUID = 9040139676573962484L; // should be ignored
	 * 
	 * @Binding(name="name"); //default binding
	 * 
	 *                        private String name;
	 * 
	 * @Binding(name="name"); //customized binding
	 * 
	 *                        private String xName;
	 * 
	 * @Binding(name = "b", bindingType = BindingTypeEnum.Entity, embeddedType = B_TO.class)
	 * 
	 *               private B_TO b;
	 * 
	 * @Binding(name = "bList", bindingType = BindingTypeEnum.Entity, embeddedType = B_TO.class)
	 * 
	 *               private List<B_TO> bList;
	 * @Binding(name = "bList", bindingType = BindingTypeEnum.Entity, embeddedType = B_TO.class) private List<B_TO> bList2;
	 * 
	 * @Binding(name="b.id") private String bId;
	 * 
	 * @Binding(name="b.name") private String bName; }
	 * 
	 *                         public class A extends StandardObject {
	 * 
	 *                         public static final long serialVersionUID = 1L;
	 * 
	 *                         private String name;
	 * @ManyToOne (cascade = CascadeType.REFRESH) private B b;
	 * @OneToMany(cascade = CascadeType.PERSIST) private List<B> bList;
	 * @OneToMany(cascade = CascadeType.MERGE) private List<B> bList2;
	 * 
	 *                    }
	 * 
	 *                    StandardObjectFactory convert transfer object to standard object within Persist or Merge contexts. In persist
	 *                    context, the following field of the transfer object will be converted into the corresponding fields target
	 *                    standard object:
	 * 
	 *                    1. Simple Type field, including primitive type and java.lang.String, java.util.Date, which can be found in target
	 *                    entity.
	 * 
	 *                    2. Custom Type field:
	 * 
	 *                    2.1 Single/Multi value(s)
	 * 
	 *                    2.2 cascade attribute: ALL, PERSIST
	 * 
	 *                    2.3 owning side/inverse side.
	 * 
	 *                    2.3.1 Owning Side should always maintain the relationships for both directional or bi-directional cases.
	 * 
	 *                    2.3.2 Inverse Side ONLY happens for bi-directional cases. And Inverse Side ONLY maintain relationships in Memory.
	 *                    So to maintain the relationships in database will depend on the synchronization of the owning side entity. If
	 *                    cascade Persist is ON at inverse side, and both inverse side and owning side is not persisted, to maintain the
	 *                    relationships just call em.perist will work. ONLY inverse side not persist, then need to find the owning side,
	 *                    maintain the relationship on owning side and call em.merge on owning side. If cascade Persist if OFF at inverse
	 *                    side, then the id field the owning side entity must NOT-NULL, and then using em.find to get a managed state owning
	 *                    side entity, maintain the relationship and then call em.merge to synchronize the relationship into database after
	 *                    em.persist is called on inverse side.
	 * 
	 *                    2.4 fetch attribute LAZY/EAGER
	 * 
	 *                    2.4.1 If fetch type defined as LAZY, then persistence provider runtime will use a system-generated-field to
	 *                    maintain the relationship, so at this case, the getter/setter should be used.
	 * 
	 *                    2.4.2 If fetch type defined as EAGER, then persistence provider runtime will use the field directly.
	 * 
	 * 
	 *                    In merge context,
	 * 
	 * @param <T>
	 * @param <S>
	 * @param targetClz
	 * @param sourceTo
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static <T extends StandardObject, S extends StandardTO<T>> T convertTO2Entity(Class<T> targetClz, S sourceTo, EntityManager em)
			throws Exception {

		if (null == sourceTo.getId()) {
			return convert4PersistContext(targetClz, sourceTo, em);
		} else {
			return convert4MergeContext(targetClz, sourceTo, em);
		}
	}

	public static <T extends StandardObject, S extends StandardTO<T>> T convertTO2Entity(S sourceTo, EntityManager em) throws Exception {
		Class<T> targetClz = getStandardObjectClass(sourceTo.getClass());
		if (null == sourceTo.getId()) {
			return convert4PersistContext(targetClz, sourceTo, em);
		} else {
			return convert4MergeContext(targetClz, sourceTo, em);
		}
	}

	private static <T extends StandardObject, S extends StandardTO<T>> T convert4PersistContext(Class<T> targetClz, S sourceTo,
			EntityManager em) throws Exception {

		final Map<Field, Field> fieldMapping = buildFieldMapping(targetClz, sourceTo.getClass());

		T t = TenantResources.newEntity(targetClz);

		Field targetField = null;
		Field sourceField = null;
		for (Entry<Field, Field> entry : fieldMapping.entrySet()) {
			sourceField = entry.getValue();
			if (null == sourceField) {
				continue;
			}
			targetField = entry.getKey();
			sourceField.setAccessible(true);
			targetField.setAccessible(true);

			final Object sourceValue = sourceField.get(sourceTo);
			if (null == sourceValue) {
				continue;
			}
			if (isEntity(targetField)) {
				if (!handleRelationship(t, targetField, sourceTo, sourceField, sourceValue, em)) {
					continue;
				}
			} else {
				targetField.set(t, sourceValue);
			}
			sourceField.setAccessible(false);
			targetField.setAccessible(false);

		}

		return t;
	}

	private static <S extends StandardTO<T>, T extends StandardObject> boolean handleRelationship(T targetEntityInstance,
			Field targetField, S sourceToInstance, Field sourceField, final Object sourceValue, EntityManager em) throws Exception {

		if (null == sourceValue && !isOptional(targetField)) {
			throw new IllegalStateException("TargetField: " + targetField.getName() + " is required. But source value is NULL");
		}
		if (null == sourceValue && isOptional(targetField)) {
			return false;
		}

		if (isOwningSide(targetField)) {
			return handleOwningSideRelationship(targetEntityInstance, targetField, sourceToInstance, sourceField, sourceValue, em);
		} else {
			return handleInverseSideRelationship(targetEntityInstance, targetField, sourceToInstance, sourceField, sourceValue, em);
		}
	}

	/**
	 * @param <R>
	 *        relationship type of T
	 * @param <RS>
	 *        source transferObject type of R
	 * @param <T>
	 *        target type
	 * @param <S>
	 *        source transferObject type of T
	 * @param targetEntityInstance
	 * @param targetField
	 * @param sourceToInstance
	 * @param sourceField
	 * @param sourceValue
	 * @param em
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private static <T extends StandardObject, S extends StandardTO<T>> boolean handleOwningSideRelationship(T targetEntityInstance,
			Field targetField, S sourceToInstance, Field sourceField, final Object sourceValue, EntityManager em) throws Exception {

		if (isCascade(targetField, CascadeType.PERSIST)) {
			if (sourceValue.getClass().isArray() || sourceValue instanceof Collection) {
				// @ManyToMany
				Collection<StandardTO<StandardObject>> sourceRelationships = (Collection<StandardTO<StandardObject>>) sourceValue;
				for (StandardTO<StandardObject> relationshipSourceTO : sourceRelationships) {
					StandardObject relationshipEntity = null;

					if (null == relationshipSourceTO.getId()) {
						relationshipEntity = convertTO2Entity(getSingleGenericType(targetField), relationshipSourceTO, em);
					} else {
						relationshipEntity = (StandardObject) em.find(targetField.getType(), relationshipSourceTO.getId());
					}

					if (isBidirectionalRelationship(targetField)) {
						handleInverseSideCorrespondingField(targetEntityInstance, targetField, relationshipEntity);
					}
					if (Collection.class.isAssignableFrom(targetField.getType())) {
						assignRelationshipToTargetField(targetEntityInstance, targetField, relationshipEntity);
					} else {
						throw new IllegalArgumentException("Only java.util.Collection support for Entity's multi-value");
					}
				}

			} else {
				// @ManyToOne or @OneToOne
				StandardObject relationshipEntity = null;
				StandardTO<StandardObject> relationshipSourceTO = null;
				String relationshipId = null;
				if (sourceValue instanceof String) {
					relationshipId = (String) sourceValue;
				} else {
					relationshipSourceTO = (StandardTO<StandardObject>) sourceValue;
					relationshipId = relationshipSourceTO.getId();
				}
				if (null == relationshipId) {
					if (null != relationshipSourceTO) {
						relationshipEntity = convertTO2Entity(getSingleGenericType(targetField), relationshipSourceTO, em);
					}
				} else {
					relationshipEntity = (StandardObject) em.find(targetField.getType(), relationshipId);
					if (isBidirectionalRelationship(targetField)) {
						handleInverseSideCorrespondingField(targetEntityInstance, targetField, relationshipEntity);
					}
				}
				assignRelationshipToTargetField(targetEntityInstance, targetField, relationshipEntity);
			}
		} else {
			if (sourceValue.getClass().isArray() || sourceValue instanceof Collection) {
				// @ManyToMany
				Collection<StandardTO<?>> sourceRelationships = (Collection<StandardTO<?>>) sourceValue;
				for (StandardTO<?> sourceRelationship : sourceRelationships) {
					if (null == sourceRelationship.getId()) {
						throw new IllegalArgumentException(sourceToInstance.getClass().getName() + "." + sourceField.getName()
								+ " contains NULL-PK " + sourceRelationship.getClass().getName() + "instance");
					}

					Object targetRelationshipEntity = em.find(targetField.getType(), sourceRelationship.getId());

					if (isBidirectionalRelationship(targetField)) {
						handleInverseSideCorrespondingField(targetEntityInstance, targetField, targetRelationshipEntity);
					}

					if (Collection.class.isAssignableFrom(targetField.getType())) {
						assignRelationshipToTargetField(targetEntityInstance, targetField, targetRelationshipEntity);
					} else {
						throw new IllegalArgumentException("Only java.util.Collection support for Entity's multi-value");
					}
				}

			} else {
				// @ManyToOne or @OneToOne
				String relationshipId = null;
				if (sourceValue instanceof String) {
					relationshipId = (String) sourceValue;
				} else {
					StandardTO<?> sourceRelationshipTO = (StandardTO<?>) sourceValue;
					if (null == sourceRelationshipTO.getId()) {
						throw new IllegalStateException("Relationship.Id cannot be NULL");
					}
					relationshipId = sourceRelationshipTO.getId();
				}

				Object inverseEntity = em.find(targetField.getType(), relationshipId);

				if (isBidirectionalRelationship(targetField)) {
					handleInverseSideCorrespondingField(targetEntityInstance, targetField, inverseEntity);
				}

				assignRelationshipToTargetField(targetEntityInstance, targetField, inverseEntity);
			}
		}
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static <T> void assignRelationshipToTargetField(T targetInstance, Field targetField, Object relationship)
			throws IllegalAccessException, InvocationTargetException {
		if (Collection.class.isAssignableFrom(targetField.getType())) {
			Collection<Object> inverseEntityCollection = null;
			if (isLazyLoading(targetField)) {
				Method getter = ClassUtils.findMethodThroughClassHierarchy(targetInstance.getClass(), ClassUtils.getGetter(targetField));
				if (null == getter) {
					throw new IllegalStateException("Cannot find inverse getter from class: " + targetInstance.getClass().getName());
				}
				inverseEntityCollection = (Collection<Object>) getter.invoke(targetInstance);
			} else {
				inverseEntityCollection = (Collection<Object>) targetField.get(targetInstance);
			}

			inverseEntityCollection.add(relationship);
		} else {
			if (isLazyLoading(targetField)) {

				Method targetSetter = ClassUtils.findMethodThroughClassHierarchy(targetInstance.getClass(),
						ClassUtils.getSetter(targetField.getName()), targetField.getType());
				if (null == targetSetter) {
					throw new IllegalStateException("Cannot find target setter from class: " + targetInstance.getClass().getName());
				}
				targetSetter.invoke(targetInstance, relationship);
			} else {
				targetField.set(targetInstance, relationship);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> void handleInverseSideCorrespondingField(T t, Field targetField, Object inverseEntity) throws Exception {
		Field inverseField = getInverseSideRelationshipField(targetField);
		inverseField.setAccessible(true);

		if (Collection.class.isAssignableFrom(inverseField.getType())) {
			// ManyToOne, ManyToMany
			Collection<T> owningEntityCollection = null;
			if (isLazyLoading(inverseField)) {
				Method getter = ClassUtils.findMethodThroughClassHierarchy(inverseEntity.getClass(), ClassUtils.getGetter(inverseField));
				if (null == getter) {
					throw new IllegalStateException("Cannot find inverse getter from class: " + inverseEntity.getClass().getName());
				}
				owningEntityCollection = (Collection<T>) getter.invoke(inverseEntity);
			} else {
				owningEntityCollection = (Collection<T>) inverseField.get(inverseEntity);
			}
			owningEntityCollection.add(t);
		} else {
			// OneToOne
			if (isLazyLoading(inverseField)) {
				Method inverseSetter = ClassUtils.findMethodThroughClassHierarchy(inverseEntity.getClass(),
						ClassUtils.getSetter(inverseField.getName()));
				if (null == inverseSetter) {
					throw new IllegalStateException("Cannot find inverse setter from class: " + inverseEntity.getClass().getName());
				}
				inverseSetter.invoke(inverseEntity, t);
			} else {
				inverseField.set(inverseEntity, t);
			}
		}
		inverseField.setAccessible(false);
	}

	@SuppressWarnings("unchecked")
	private static <T> void handleOwningSideCorrespondingField(T t, Field targetField, Object inverseEntity) throws Exception {
		Field owningField = getOwningSideRelationshipField(targetField);
		owningField.setAccessible(true);

		if (Collection.class.isAssignableFrom(owningField.getType())) {
			// ManyToOne, ManyToMany
			Collection<T> owningEntityCollection = null;
			if (isLazyLoading(owningField)) {
				Method getter = ClassUtils.findMethodThroughClassHierarchy(inverseEntity.getClass(), ClassUtils.getGetter(owningField));
				if (null == getter) {
					throw new IllegalStateException("Cannot find inverse getter from class: " + inverseEntity.getClass().getName());
				}
				owningEntityCollection = (Collection<T>) getter.invoke(inverseEntity);
			} else {
				owningEntityCollection = (Collection<T>) owningField.get(inverseEntity);
			}
			owningEntityCollection.add(t);
		} else {
			// OneToOne
			if (isLazyLoading(owningField)) {
				Method inverseSetter = ClassUtils.findMethodThroughClassHierarchy(inverseEntity.getClass(),
						ClassUtils.getSetter(owningField.getName()));
				if (null == inverseSetter) {
					throw new IllegalStateException("Cannot find inverse setter from class: " + inverseEntity.getClass().getName());
				}
				inverseSetter.invoke(inverseEntity, t);
			} else {
				owningField.set(inverseEntity, t);
			}
		}
		owningField.setAccessible(false);
	}

	@SuppressWarnings("unchecked")
	private static <T extends StandardObject, S extends StandardTO<T>> boolean handleInverseSideRelationship(T targetEntityInstance,
			Field targetField, S sourceToInstance, Field sourceField, final Object sourceValue, EntityManager em) throws Exception {
		if (isCascade(targetField, CascadeType.PERSIST)) {
			if (sourceValue.getClass().isArray() || sourceValue instanceof Collection) {
				// @ManyToMany, @OneToMany
				Collection<StandardTO<StandardObject>> sourceRelationships = (Collection<StandardTO<StandardObject>>) sourceValue;
				for (StandardTO<StandardObject> relationshipSourceTO : sourceRelationships) {
					StandardObject relationshipEntity = null;

					if (null == relationshipSourceTO.getId()) {
						relationshipEntity = convertTO2Entity(getSingleGenericType(targetField), relationshipSourceTO, em);
					} else {
						relationshipEntity = (StandardObject) em.find(getSingleGenericType(targetField), relationshipSourceTO.getId());
					}

					// if (isBidirectionalRelationship(targetField)) {
					handleOwningSideCorrespondingField(targetEntityInstance, targetField, relationshipEntity);
					// }
					if (Collection.class.isAssignableFrom(targetField.getType())) {
						assignRelationshipToTargetField(targetEntityInstance, targetField, relationshipEntity);
					} else {
						throw new IllegalArgumentException("Only java.util.Collection support for Entity's multi-value");
					}
				}

			} else {
				// @OneToOne
				StandardObject relationshipEntity = null;
				StandardTO<StandardObject> relationshipSourceTO = null;
				String relationshipId = null;
				if (sourceValue instanceof String) {
					relationshipId = (String) sourceValue;
				} else {
					relationshipSourceTO = (StandardTO<StandardObject>) sourceValue;
					relationshipId = relationshipSourceTO.getId();
				}
				if (null == relationshipId) {
					if (null != relationshipSourceTO) {
						relationshipEntity = convertTO2Entity(getSingleGenericType(targetField), relationshipSourceTO, em);
					}
				} else {
					relationshipEntity = (StandardObject) em.find(targetField.getType(), relationshipId);
					if (isBidirectionalRelationship(targetField)) {
						handleOwningSideCorrespondingField(targetEntityInstance, targetField, relationshipEntity);
					}
				}
				if (null != relationshipEntity) {
					assignRelationshipToTargetField(targetEntityInstance, targetField, relationshipEntity);
				}
			}
			return true;
		} else {
			return false;
		}
	}

	private static boolean isLazyLoading(Field targetField) {
		OneToOne oneToOne = targetField.getAnnotation(OneToOne.class);
		if (null != oneToOne) {
			return oneToOne.fetch() == FetchType.LAZY;
		}

		OneToMany oneToMany = targetField.getAnnotation(OneToMany.class);
		if (null != oneToMany) {
			return oneToMany.fetch() == FetchType.LAZY;
		}

		ManyToOne manyToOne = targetField.getAnnotation(ManyToOne.class);
		if (null != manyToOne) {
			return manyToOne.fetch() == FetchType.LAZY;
		}

		ManyToMany manyToMany = targetField.getAnnotation(ManyToMany.class);
		if (null != manyToMany) {
			return manyToMany.fetch() == FetchType.LAZY;
		}

		throw new IllegalArgumentException(
				"Cannot find annotation: OneToOne, or OneToMany, or ManyToOne, or ManyToMany on the targetField: " + targetField.getName());
	}

	private static boolean isBidirectionalRelationship(Field owningTypeField) {
		if (null == getInverseSideRelationshipField(owningTypeField)) {
			return false;
		} else {
			return true;
		}
	}

	private static Field getInverseSideRelationshipField(Field owningTypeField) {
		Class<?> relationshipType = owningTypeField.getType();
		Class owningClass = owningTypeField.getDeclaringClass();
		// Do we need to handle Array?
		if (Collection.class.isAssignableFrom(relationshipType)) {
			relationshipType = getSingleGenericType(owningTypeField);
			if (null == relationshipType) {
				relationshipType = getTargetEntityClass(owningTypeField);
			}
			if (null == relationshipType) {
				throw new IllegalArgumentException("Cannot find Relationship Type from field: " + owningTypeField.getName());
			}
		}

		Field[] allFields = ClassUtils.getAllFields(relationshipType);
		for (Field field : allFields) {
			if (!getSingleGenericType(field).equals(owningClass)) {
				continue;
			}
			if (isInverseSideCorrespondingField(field, owningTypeField.getName())) {
				return field;
			}
		}
		return null;
	}

	private static Field getOwningSideRelationshipField(Field inverseTypeField) {
		Class<?> owningSideType = inverseTypeField.getType();
		if (Collection.class.isAssignableFrom(owningSideType)) {
			owningSideType = getSingleGenericType(inverseTypeField);
			if (null == owningSideType) {
				owningSideType = getTargetEntityClass(inverseTypeField);
			}
			if (null == owningSideType) {
				throw new IllegalArgumentException("Cannot find Relationship Type from field: " + inverseTypeField.getName());
			}
		}

		Field[] allFields = ClassUtils.getAllFields(owningSideType);
		for (Field field : allFields) {
			if (getMappedBy(inverseTypeField).equals(field.getName())) {
				return field;
			}
		}
		return null;

	}

	private static Class<StandardObject> getSingleGenericType(Field field) {
		if (Collection.class.isAssignableFrom(field.getType())) {
			Class<StandardObject> relationshipType;
			Type collectionType = field.getGenericType();

			ParameterizedType pType = (ParameterizedType) collectionType;
			Type[] actualTypes = pType.getActualTypeArguments();

			if (null != actualTypes && 1 == actualTypes.length) {
				relationshipType = (Class<StandardObject>) actualTypes[0];
			} else {
				throw new IllegalArgumentException("ONLY One generic parameter is expected for field: " + field.getName());
			}
			return relationshipType;
		} else {
			return (Class<StandardObject>) field.getType();
		}
	}

	private static boolean isInverseSideCorrespondingField(Field inverseSideField, String owningSideFieldName) {
		return owningSideFieldName.equals(getMappedBy(inverseSideField));
	}

	private static String getMappedBy(Field inverseSideField) {
		OneToOne oneToOne = inverseSideField.getAnnotation(OneToOne.class);
		if (null != oneToOne) {
			return oneToOne.mappedBy();
		}
		OneToMany oneToMany = inverseSideField.getAnnotation(OneToMany.class);
		if (null != oneToMany) {
			return oneToMany.mappedBy();
		}

		ManyToMany manyToMany = inverseSideField.getAnnotation(ManyToMany.class);
		if (null != manyToMany) {
			return manyToMany.mappedBy();
		}

		return "";
	}

	private static Class<?> getTargetEntityClass(Field targetField) {
		OneToOne oneToOne = targetField.getAnnotation(OneToOne.class);
		if (null != oneToOne) {
			return oneToOne.targetEntity();
		}
		OneToMany oneToMany = targetField.getAnnotation(OneToMany.class);
		if (null != oneToMany) {
			return oneToMany.targetEntity();
		}

		ManyToOne manyToOne = targetField.getAnnotation(ManyToOne.class);
		if (null != manyToOne) {
			return manyToOne.targetEntity();
		}

		ManyToMany manyToMany = targetField.getAnnotation(ManyToMany.class);
		if (null != manyToMany) {
			return manyToMany.targetEntity();
		}
		return null;
	}

	private static boolean isCascade(Field targetField, CascadeType cascadeType) {
		OneToOne oneToOne = targetField.getAnnotation(OneToOne.class);
		if (null != oneToOne) {
			return contains(cascadeType, oneToOne.cascade());
		}

		OneToMany oneToMany = targetField.getAnnotation(OneToMany.class);
		if (null != oneToMany) {
			return contains(cascadeType, oneToMany.cascade());
		}

		ManyToOne manyToOne = targetField.getAnnotation(ManyToOne.class);
		if (null != manyToOne) {
			return contains(cascadeType, manyToOne.cascade());
		}

		ManyToMany manyToMany = targetField.getAnnotation(ManyToMany.class);
		if (null != manyToMany) {
			return contains(cascadeType, manyToMany.cascade());

		}

		throw new IllegalArgumentException(
				"Cannot find annotation: OneToOne, or OneToMany, or ManyToOne, or ManyToMany on the targetField: " + targetField.getName());

	}

	private static boolean isOwningSide(Field targetField) {
		OneToOne oneToOne = targetField.getAnnotation(OneToOne.class);
		if (null != oneToOne) {
			return oneToOne.mappedBy().length() == 0;
		}

		OneToMany oneToMany = targetField.getAnnotation(OneToMany.class);
		if (null != oneToMany) {
			return false;
		}

		ManyToOne manyToOne = targetField.getAnnotation(ManyToOne.class);
		if (null != manyToOne) {
			return true;
		}

		ManyToMany manyToMany = targetField.getAnnotation(ManyToMany.class);
		if (null != manyToMany) {
			return manyToMany.mappedBy().length() == 0;
		}

		throw new IllegalArgumentException(
				"Cannot find annotation: OneToOne, or OneToMany, or ManyToOne, or ManyToMany on the targetField: " + targetField.getName());

	}

	private static boolean contains(CascadeType targetCascadeType, CascadeType[] cascadeTypes) {
		if (null == cascadeTypes) {
			return false;
		}
		for (CascadeType actualCascadeType : cascadeTypes) {
			if (targetCascadeType.equals(actualCascadeType)) {
				return true;
			} else if (CascadeType.ALL.equals(actualCascadeType)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isOptional(Field targetField) {
		OneToOne oneToOne = targetField.getAnnotation(OneToOne.class);
		if (null != oneToOne) {
			return oneToOne.optional();
		}

		OneToMany oneToMany = targetField.getAnnotation(OneToMany.class);
		if (null != oneToMany) {
			return true;
		}

		ManyToOne manyToOne = targetField.getAnnotation(ManyToOne.class);
		if (null != manyToOne) {
			return manyToOne.optional();
		}

		ManyToMany manyToMany = targetField.getAnnotation(ManyToMany.class);
		if (null != manyToMany) {
			return true;
		}

		throw new IllegalArgumentException(
				"Cannot find annotation: OneToOne, or OneToMany, or ManyToOne, or ManyToMany on the targetField: " + targetField.getName());
	}

	private static boolean isEntity(Field targetField) {
		return StandardObject.class.isAssignableFrom(targetField.getType()) || Collection.class.isAssignableFrom(targetField.getType());
	}

	private static <T, S extends StandardTO<?>> T convert4MergeContext(Class<T> targetClz, S sourceTo, EntityManager em) {
		throw new UnsupportedOperationException();
		// return null;
	}

	private static Map<Field, Field> buildFieldMapping(Class<?> targetClass, Class<?> sourceClass) {
		final HashMap<Field, Field> result = new HashMap<Field, Field>();
		Field[] allTargetFields = ClassUtils.getAllFields(targetClass);
		Field[] allSourceFields = ClassUtils.getAllFields(sourceClass);
		for (Field targetField : allTargetFields) {
			if (Modifier.isStatic(targetField.getModifiers())) {
				continue;
			}
			for (Field sourceField : allSourceFields) {
				Binding binding = sourceField.getAnnotation(Binding.class);
				if (null == binding) {
					if (sourceField.getName().equals(targetField.getName())) {
						result.put(targetField, sourceField);
						break;
					}
				} else {
					if (0 == binding.name().length()) {
						if (sourceField.getName().equals(targetField.getName())) {
							result.put(targetField, sourceField);
							break;
						}
					} else {
						if (binding.name().equals(targetField.getName())) {
							result.put(targetField, sourceField);
							break;
						}

						if (0 < binding.name().indexOf(".")) {
							int firstDot = binding.name().indexOf(".");
							if (binding.name().substring(firstDot + 1).equals("id")) {
								String firstLevelFieldName = binding.name().substring(0, firstDot);
								if (targetField.getName().equals(firstLevelFieldName)) {
									result.put(targetField, sourceField);
									break;
								}
							}

						}
					}
				}
			}
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	private static <B extends StandardObject, T extends StandardTO<B>> Class<B> getStandardObjectClass(Class<T> standardTOClass) {
		Type genericSuperclass = standardTOClass.getGenericSuperclass();
		if (genericSuperclass instanceof ParameterizedType) {
			ParameterizedType genericType = (ParameterizedType) genericSuperclass;
			Type[] actualTypeArguments = genericType.getActualTypeArguments();
			Type standardObjectType = actualTypeArguments[0];
			return (Class<B>) standardObjectType;
		}
		throw new IllegalArgumentException("Class " + standardTOClass.getName() + " is not ParameterizedType");
	}
}
