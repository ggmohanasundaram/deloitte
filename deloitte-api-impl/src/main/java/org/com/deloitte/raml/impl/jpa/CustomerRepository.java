package org.com.deloitte.raml.impl.jpa;

import org.com.deloitte.raml.impl.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;

@Transactional
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {
    Page findAll(Pageable pageable);
}
