package net.madz.module.account.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.madz.infra.biz.core.BusinessException;
import net.madz.infra.security.util.TenantResources;
import net.madz.interceptor.AuditInterceptor;
import net.madz.interceptor.TenantCacheInterceptor;
import net.madz.interceptor.ValidationInterceptor;
import net.madz.module.account.AccountTO;
import net.madz.module.account.ContactTO;
import net.madz.module.account.entity.Account;
import net.madz.module.account.entity.Contact;
import net.madz.module.account.entity.Contact.ContactType;
import net.vicp.madz.infra.binding.TransferObjectFactory;

@Stateless
@RolesAllowed({ "ADMIN" })
@Interceptors({ TenantCacheInterceptor.class, AuditInterceptor.class, ValidationInterceptor.class })
public class AccountFacade implements AccountFacadeRemote, AccountFacadeLocal {

	private static final String UNSPECIFIED = "UNSPECIFIED";
	@PersistenceContext(name = "persistence/EntityManager")
	private EntityManager em;

	@SuppressWarnings("unused")
	@Resource(name = "SessionContext")
	private SessionContext context;

	@Override
	public String createAccount(AccountTO account) {
		if (null == account) {
			throw new NullPointerException();
		}
		try {
			Account result = TenantResources.newEntity(Account.class);
			result.setName(account.getName());
			result.setShortName(account.getShortName());
			result.setDeleted(false);
			em.persist(result);
			return result.getId();
		} catch (Exception ex) {
			throw new BusinessException(ex);
		}
	}

	@Override
	public String createContact(ContactTO contact) {
		if (null == contact) {
			throw new NullPointerException();
		}
		try {
			Contact result = TenantResources.newEntity(Contact.class);
			result.setContactType(ContactType.valueOf(contact.getContactType()));
			result.setEmail(contact.getEmail());
			result.setFax(contact.getFax());
			result.setMobile(contact.getMobile());
			result.setTelephone(contact.getTelephone());
			result.setName(contact.getName());
			result.setSex(contact.getSex());
			result.setDeleted(false);
			em.persist(result);
			return result.getId();
		} catch (Exception ex) {
			throw new BusinessException(ex);
		}
	}

	private void deleteAccount(String id) {
		if (null == id || 0 >= id.trim().length()) {
			throw new NullPointerException();
		}
		try {
			Account result = em.find(Account.class, id);
			result.setDeleted(true);
			result.setUpdatedBy(TenantResources.getCurrentUser());
			result.setUpdatedOn(new Date());
			em.merge(result);
		} catch (Exception ex) {
			throw new BusinessException(ex);
		}
	}

	private void deleteAccountHard(String id) {
		if (null == id || 0 >= id.trim().length()) {
			throw new NullPointerException();
		}
		try {
			Account account = em.find(Account.class, id);
			if (null != account) {
				em.remove(em.find(Account.class, id));
			}
		} catch (Exception ex) {
			throw new BusinessException(ex);
		}

	}

	@Override
	public AccountTO findAccount(String id) {
		if (null == id || 0 >= id.trim().length()) {
			throw new NullPointerException();
		}
		Account account = em.find(Account.class, id);
		AccountTO accountTO = null;
		try {
			accountTO = TransferObjectFactory.createTransferObject(AccountTO.class, account);
		} catch (Exception ex) {
			throw new BusinessException(ex);
		}
		return accountTO;
	}

	@SuppressWarnings("unchecked")
	public List<AccountTO> findAccounts() {
		List<Account> list = null;
		try {
			list = em.createQuery("select Object(o) from Account as o WHERE o.tenant.id = :tenantId AND o.deleted = false")
					.setParameter("tenantId", TenantResources.getTenant().getId()).getResultList();

			List<AccountTO> result = TransferObjectFactory.assembleTransferObjectList(list, AccountTO.class);
			return result;
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccountTO> findAccountsByName(String name) {
		if (null == name || 0 >= name.trim().length()) {
			return new ArrayList<AccountTO>(0);
		}
		List<Account> list = null;
		try {
			list = em
					.createQuery(
							"SELECT Object(o) FROM Account as o WHERE o.tenant.id = :tenantId AND o.deleted = false AND o.name LIKE :accountName")
					.setParameter("tenantId", TenantResources.getTenant().getId()).setParameter("accountName", "%" + name + "%")
					.getResultList();
			List<AccountTO> result = TransferObjectFactory.assembleTransferObjectList(list, AccountTO.class);
			return result;
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccountTO> findAccountsByShortName(String shortName) {
		if (null == shortName || 0 >= shortName.trim().length()) {
			return new ArrayList<AccountTO>(0);
		}
		List<Account> list = null;
		try {
			list = em
					.createQuery(
							"SELECT Object(o) FROM Account as o WHERE o.tenant.id = :tenantId AND o.deleted = false AND o.shortName LIKE :shortName")
					.setParameter("tenantId", TenantResources.getTenant().getId()).setParameter("shortName", "%" + shortName + "%")
					.getResultList();
			List<AccountTO> result = TransferObjectFactory.assembleTransferObjectList(list, AccountTO.class);
			return result;
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContactTO> findContactByName(String name) {
		List<Contact> list = null;
		try {
			list = em
					.createQuery(
							"select Object(o) from Contact as o WHERE o.tenant.id = :tenantId AND o.deleted = false AND o.name like :name")
					.setParameter("tenantId", TenantResources.getTenant().getId()).setParameter("name", "%" + name + "%").getResultList();

			List<ContactTO> result = TransferObjectFactory.assembleTransferObjectList(list, ContactTO.class);
			return result;
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContactTO> findContacts() {
		List<Contact> list = null;
		try {
			list = em.createQuery("select Object(o) from Contact as o WHERE o.tenant.id = :tenantId AND o.deleted = false")
					.setParameter("tenantId", TenantResources.getTenant().getId()).getResultList();

			List<ContactTO> result = TransferObjectFactory.assembleTransferObjectList(list, ContactTO.class);
			return result;
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContactTO> findContactsWithDeleted() {
		List<Contact> list = null;
		try {
			list = em.createQuery("select Object(o) from Contact as o WHERE o.tenant.id = :tenantId AND o.deleted = true")
					.setParameter("tenantId", TenantResources.getTenant().getId()).getResultList();

			List<ContactTO> result = TransferObjectFactory.assembleTransferObjectList(list, ContactTO.class);
			return result;
		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}
	}

	@Override
	public synchronized Contact getUnspecifiedContact() {
		try {
			return (Contact) em.createQuery("SELECT o FROM Contact o WHERE o.tenant.id = :tenantId AND o.name = :name")
					.setParameter("name", UNSPECIFIED).setParameter("tenantId", TenantResources.getTenant().getId()).getSingleResult();
		} catch (Exception ex) {
			Contact contact = TenantResources.newEntity(Contact.class);// new
																		// Contact();
			contact.setName(UNSPECIFIED);
			contact.setAccount(getUnspecifiedAccount());
			em.persist(contact);
			return contact;
		}
	}

	@Override
	public Account getUnspecifiedAccount() {
		try {
			return (Account) em.createQuery("SELECT o FROM Account o WHERE o.tenant.id = :tenantId AND o.name = :name")
					.setParameter("name", UNSPECIFIED).setParameter("tenantId", TenantResources.getTenant().getId()).getSingleResult();
		} catch (Exception ex) {
			Account account = TenantResources.newEntity(Account.class);
			account.setName(UNSPECIFIED);
			account.setShortName(UNSPECIFIED);
			em.persist(account);
			return account;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void hardDeleteAllAccounts() {
		List<Account> list = null;
		try {
			final String query = "select Object(o) from Account as o WHERE o.tenant.id = :tenantId";
			list = em.createQuery(query).setParameter("tenantId", TenantResources.getTenant().getId()).getResultList();

		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}
		for (Account account : list) {
			em.remove(account);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void hardDeleteAllContacts() {
		List<Contact> list = null;
		try {
			list = em.createQuery("select Object(o) from Contact as o WHERE o.tenant.id = :tenantId")
					.setParameter("tenantId", TenantResources.getTenant().getId()).getResultList();

		} catch (Exception ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			throw new BusinessException(ex);
		}
		for (Contact contact : list) {
			em.remove(contact);
		}
	}

	@Override
	public void hardDeleteMultiAccounts(String[] ids) {
		if (null == ids) {
			throw new NullPointerException();
		}
		for (String id : ids) {
			deleteAccountHard(id);
		}

	}

	@Override
	public void hardDeleteMultiContacts(String[] ids) {
		if (null == ids || 0 >= ids.length) {
			throw new NullPointerException();
		}
		for (String id : ids) {
			em.remove(em.find(Contact.class, id));
		}
	}

	@Override
	public boolean isAccountDeleted(String id) {
		if (null == id || 0 >= id.trim().length()) {
			throw new NullPointerException();
		}
		Account account = em.find(Account.class, id);
		return account.getDeleted();
	}

	@Override
	public boolean isContactDeleted(String id) {
		if (null == id || 0 >= id.trim().length()) {
			throw new NullPointerException();
		}
		Contact result = em.find(Contact.class, id);
		return result.getDeleted();
	}

	private void softDeleteContact(String id) {
		if (null == id || 0 >= id.trim().length()) {
			throw new NullPointerException();
		}
		try {
			Contact result = em.find(Contact.class, id);
			result.setDeleted(true);
			result.setUpdatedBy(TenantResources.getCurrentUser());
			result.setUpdatedOn(new Date());
			em.merge(result);
		} catch (Exception ex) {
			throw new BusinessException(ex);
		}
	}

	@Override
	public void softDeleteMultiAccounts(String[] ids) {
		if (null == ids) {
			throw new NullPointerException();
		}
		for (String id : ids) {
			deleteAccount(id);
		}
	}

	@Override
	public void softDeleteMultiContacts(String[] ids) {
		if (null == ids) {
			throw new NullPointerException();
		}
		for (String id : ids) {
			softDeleteContact(id);
		}
	}

	@Override
	public void updateAccount(AccountTO account) {
		if (null == account) {
			throw new NullPointerException();
		}
		try {
			Account result = em.find(Account.class, account.getId());
			result.setName(account.getName());
			result.setShortName(account.getShortName());
			result.setUpdatedBy(TenantResources.getCurrentUser());
			result.setUpdatedOn(new Date());
			em.merge(result);
		} catch (Exception ex) {
			throw new BusinessException(ex);
		}
	}

	private void updateContact(ContactTO contact) {
		if (null == contact) {
			throw new NullPointerException();
		}
		try {
			Contact result = em.find(Contact.class, contact.getId());
			result.setName(contact.getName());
			result.setContactType(ContactType.valueOf(contact.getContactType()));
			result.setEmail(contact.getEmail());
			result.setFax(contact.getFax());
			result.setMobile(contact.getMobile());
			result.setTelephone(contact.getTelephone());
			result.setName(contact.getName());
			result.setSex(contact.getSex());
			result.setUpdatedBy(TenantResources.getCurrentUser());
			result.setUpdatedOn(new Date());
			em.merge(result);
		} catch (Exception ex) {
			throw new BusinessException(ex);
		}
	}

	@Override
	public void updateContacts(ContactTO[] contacts) {
		if (null == contacts) {
			throw new NullPointerException();
		}
		for (ContactTO to : contacts) {
			updateContact(to);
		}
	}

	@Override
	public void updateMultiAccounts(AccountTO[] accounts) {
		if (null == accounts) {
			throw new NullPointerException();
		}
		for (AccountTO to : accounts) {
			updateAccount(to);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContactTO> findContacts(String accountId, String contactType) {
		final String sql = "SELECT Object(c) " + "FROM Contact c " + "WHERE c.tenantId = :tenantId " + "AND c.account.id = :accountId "
				+ "AND c.contactType = :contactType";
		final Query query = em.createQuery(sql).setParameter("tenantId", TenantResources.getTenant().getId())
				.setParameter("accountId", accountId).setParameter("contactType", contactType);

		final List<Contact> resultList = query.getResultList();
		try {
			return TransferObjectFactory.assembleTransferObjectList(resultList, ContactTO.class);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

}
