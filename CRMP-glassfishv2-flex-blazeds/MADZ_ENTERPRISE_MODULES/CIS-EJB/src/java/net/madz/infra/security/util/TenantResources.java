package net.madz.infra.security.util;

import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Query;

import net.madz.infra.biz.core.BusinessException;
import net.madz.infra.security.persistence.StandardObject;
import net.madz.infra.security.persistence.Tenant;
import net.madz.infra.security.persistence.User;
import net.madz.infra.util.ResourceHelper;

public class TenantResources extends ResourceHelper {

    private static final ThreadLocal<Tenant> cacheTenant = new ThreadLocal<Tenant>();

    public static void clearCache() {
        cacheTenant.set(null);
    }
    public static Tenant getTenant() {
        if ( null == cacheTenant.get() ) {
            cacheTenant.set(getOwnedCompany());
        }
        return cacheTenant.get();
    }

    public static Tenant getOwnedCompany() {
        User account = getCurrentUser();
        if ( account == null ) {
            return null;
        } else {
            return account.getTenant();
        }
    }

    public static User getCurrentUser() {
        try {
            String username = getPrincipalName();
            User account = null;
            Query query = ResourceHelper.getEntityManager().createNamedQuery("User.findByUsername").setParameter("username", username);
            account = (User) query.getSingleResult();
            return account;
        } catch (Exception ex) {
            Logger.getLogger(ResourceHelper.class.getName()).log(Level.SEVERE, "No such account exists.", ex);
            return null;
        }
    }

    public static <T extends StandardObject> T newEntity(Class<T> tClass) {
        try {
            T t = tClass.newInstance();
            setAuditProperties(t);
            t.setId(UUID.randomUUID().toString());
            return t;
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    private static void setAuditProperties(StandardObject standardObject) {
        try {
            if ( null == standardObject.getId() ) {
                standardObject.setCreatedBy(getCurrentUser());
                standardObject.setCreatedOn(new Date());
                standardObject.setTenant(getTenant());
            } else {
                standardObject.setUpdatedBy(getCurrentUser());
                standardObject.setUpdatedOn(new Date());
            }
        } catch (Exception ex) {
            Logger.getLogger(ResourceHelper.class.getName()).log(Level.SEVERE, "No such account exists.", ex);
        }
    }
}
