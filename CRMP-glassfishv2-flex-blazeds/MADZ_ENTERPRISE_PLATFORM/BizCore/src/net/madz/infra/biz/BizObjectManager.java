package net.madz.infra.biz;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;

import net.madz.infra.util.BizObjectUtil;
import net.madz.infra.util.ResourceHelper;

public class BizObjectManager implements EntityManager {

	protected static final Map<Object, BizObjectManager> BIZ_OBJECT_MAP = Collections
			.synchronizedMap(new HashMap<Object, BizObjectManager>());

	public static void bind(Object bean) throws NamingException {
		if (!BIZ_OBJECT_MAP.containsKey(bean)) {
			BizObjectManager bm = new BizObjectManager(ResourceHelper.getEntityManager());
			BIZ_OBJECT_MAP.put(bean, bm);
		}
	}

	public static void unbind(Object bean) {
		if (BIZ_OBJECT_MAP.containsKey(bean)) {
			BIZ_OBJECT_MAP.remove(bean);
		}
	}

	public static BizObjectManager getBizObjectManager(Object bean) throws NamingException {
		BizObjectManager bm = null;
		if (!BIZ_OBJECT_MAP.containsKey(bean)) {
			bm = new BizObjectManager(ResourceHelper.getEntityManager());
			BIZ_OBJECT_MAP.put(bean, bm);
		} else {
			bm = BIZ_OBJECT_MAP.get(bean);
		}
		return bm;
	}

	private EntityManager em;

	private BizObjectManager(EntityManager em) {
		this.em = em;
	}

	/**
	 * Return dynamic biz proxy instance of bizClass if bizClass is annotated
	 * with BizObject, otherwise return an entity instance of bizClass.
	 * 
	 * @param <T>
	 * @param bizClass
	 * @param pk
	 * @return
	 */
	public <T> T findBizObject(Class<? extends T> bizClass, Object pk) {
		T entity = em.find(bizClass, pk);
		if (BizObjectUtil.isBizObject(entity)) {
			return BizObjectUtil.newProxy(entity);
		} else {
			return em.find(bizClass, pk);
		}
	}

	@Override
	public void clear() {
		em.clear();
	}

	@Override
	public void close() {
		em.close();
	}

	@Override
	public boolean contains(Object entity) {
		return em.contains(entity);
	}

	@Override
	public Query createNamedQuery(String query) {
		return em.createNamedQuery(query);
	}

	@Override
	public Query createNativeQuery(String query) {
		return em.createNativeQuery(query);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Query createNativeQuery(String query, Class entityClass) {
		return em.createNativeQuery(query, entityClass);
	}

	@Override
	public Query createNativeQuery(String query, String resultSetMapping) {
		return em.createNativeQuery(query, resultSetMapping);
	}

	@Override
	public Query createQuery(String query) {
		return em.createQuery(query);
	}

	@Override
	public <T> T find(Class<T> bizClass, Object pk) {
		return em.find(bizClass, pk);
	}

	@Override
	public void flush() {
		em.flush();
	}

	@Override
	public Object getDelegate() {
		return em.getDelegate();
	}

	@Override
	public FlushModeType getFlushMode() {
		return em.getFlushMode();
	}

	@Override
	public <T> T getReference(Class<T> arg0, Object arg1) {
		return em.getReference(arg0, arg1);
	}

	@Override
	public EntityTransaction getTransaction() {
		return em.getTransaction();
	}

	@Override
	public boolean isOpen() {
		return em.isOpen();
	}

	@Override
	public void joinTransaction() {
		em.joinTransaction();
	}

	@Override
	public void lock(Object entity, LockModeType lockModeType) {
		em.lock(entity, lockModeType);
	}

	@Override
	public <T> T merge(T entity) {
		return em.merge(entity);
	}

	@Override
	public void persist(Object entity) {
		em.persist(entity);
	}

	@Override
	public void refresh(Object entity) {
		em.refresh(entity);
	}

	@Override
	public void remove(Object entity) {
		em.remove(entity);
	}

	@Override
	public void setFlushMode(FlushModeType modeType) {
		em.setFlushMode(modeType);
	}

}
