package org.com.deloitte.raml.impl.jpa;

import org.com.deloitte.raml.impl.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface AddressRepository extends JpaRepository<Address, String> {
}
