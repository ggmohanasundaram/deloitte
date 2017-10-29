package org.com.deloitte.raml.impl.service;

import org.com.deloitte.raml.impl.exceptions.APIException;
import org.com.deloitte.raml.impl.jpa.CustomerRepository;
import org.com.deloitte.raml.impl.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    CounterService counterService;

    public CustomerService() {

    }

    public Long addCustomer(Customer customer) {
        try {
            Customer record = customerRepository.save(customer);
            return record.getCustomer_id();
        } catch (Exception e) {
            throw new APIException("Exception while Adding the Customer");
        }

    }

    public Customer findCustomer(Long customer_id) throws APIException {
        try {
            log.info("Inside Customer Service--" + customer_id);
            Customer customer = customerRepository.findOne(customer_id);
            return customer;
        } catch (Exception e) {
            throw new APIException("Exception while fetching the Customer");
        }
    }

    public void updateCustomer(Customer customer) {
        try {
            customerRepository.save(customer);
        } catch (Exception e) {
            throw new APIException("Exception while Updating the Customer");
        }

    }


    public void deleteCustomer(Long customer_id) {
        customerRepository.delete(customer_id);
    }

    public Page<Customer> getAllCustomers(Integer page, Integer size) {
        Page pageofCustomers = customerRepository.findAll(new PageRequest(page, size));
        if (size > 50) {
            counterService.increment("deloitte.CustomerService.getAll.largePayload");
        }
        return pageofCustomers;
    }
}
