package net.madz.service.etl.strategy;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.persistence.EntityManager;

import net.madz.infra.biz.core.BusinessException;
import net.madz.infra.security.util.TenantResources;
import net.madz.module.common.entity.Merchandise;
import net.madz.service.etl.IMappingStrategy;

public class MechandiseSetMappingStrategy implements IMappingStrategy<List<Merchandise>> {

	private static final int MIN_LENGTH = 3;

	private static final String QUERY = "SELECT o FROM Merchandise o WHERE o.code LIKE :code AND o.tenant.id = :tenantId";

	private EntityManager em;

	public MechandiseSetMappingStrategy() {
		try {
			em = TenantResources.getEntityManager();
		} catch (NamingException e) {
			throw new BusinessException("Cannot find Entity Manager");
		}
	}

	@Override
	public List<Merchandise> covert(String rawData) {
		final List<Merchandise> result = new ArrayList<Merchandise>();
		// TODO whether to throw an exception to interrupt whole process.
		if (null == rawData || MIN_LENGTH > rawData.length()) {
			return result;
		}

		String baseProductCode = rawData.substring(0, MIN_LENGTH);
		String admixtures = rawData.substring(3);
		try {
			final Merchandise baseProduct = (Merchandise) em.createQuery(QUERY).setParameter("code", baseProductCode + "%")
					.setParameter("tenantId", TenantResources.getTenant().getId()).getSingleResult();

			final String admixturePrefix = "---";

			for (int i = 0; i < admixtures.length(); i++) {
				StringBuilder sb = new StringBuilder(admixturePrefix);
				for (int j = 0; j < i; j++) {
					sb.append("-");
				}
				final char admixtureCode = admixtures.charAt(i);
				if ('-' == admixtureCode) {
					continue;
				}
				sb.append(admixtureCode);
				sb.append("%");
				try {
					final Merchandise admixtureMerchandise = (Merchandise) em.createQuery(QUERY).setParameter("code", sb.toString())
							.setParameter("tenantId", TenantResources.getTenant().getId()).getSingleResult();
					result.add(admixtureMerchandise);
				} catch (Exception ex) {
				}
			}

			result.add(baseProduct);
		} catch (Exception ex) {
		}
		return result;
	}

}
