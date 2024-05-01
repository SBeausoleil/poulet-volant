package com.pouletvolant.test.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.persistence.Table;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pouletvolant.models.Organization;
import com.pouletvolant.models.OrganizationHolderImpl;
import com.pouletvolant.repositories.OrganizationHolderImplRepository;
import com.pouletvolant.repositories.OrganizationRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrganizationRepositoryTest {

	@Autowired
	private OrganizationRepository organizationRepo;

	@Autowired
	private OrganizationHolderImplRepository holderRepo;

	@Test
	public void injectedComponentsNotNull() {
		assertNotNull(organizationRepo);
		assertNotNull(holderRepo);
	}
	
	@Test
	public void testFindByOwnerIdAndOwnerType_forOrganizationHolderImpl() {
		OrganizationHolderImpl holder = new OrganizationHolderImpl("email@email.ca", "123-123-1234", "The holder");
		holderRepo.save(holder);

		String description = "My description";
		Organization org = new Organization("org", description, holder);
		organizationRepo.save(org);

		Organization orgHeldByHolder = organizationRepo.findByHolder(holder.getId(),
				holder.getClass().getAnnotation(Table.class).name());
		assertNotNull(orgHeldByHolder);
		assertEquals(org.getDescription(), orgHeldByHolder.getDescription());
	}
}
