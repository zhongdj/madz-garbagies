package net.madz.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import net.madz.infra.security.util.TenantResources;

public class TenantCacheInterceptor {

    @AroundInvoke
    public Object doAudit(InvocationContext ctx) throws Exception {
        try {
            TenantResources.getTenant();
            return ctx.proceed();
        } finally {
            TenantResources.clearCache();
        }
    }
}
