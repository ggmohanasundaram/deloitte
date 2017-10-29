package org.com.deloitte.raml.impl.controller;


import org.com.deloitte.raml.api.CustomerController;
import org.com.deloitte.raml.api.model.Address;
import org.com.deloitte.raml.api.model.Customer;
import org.com.deloitte.raml.api.model.object;
import org.com.deloitte.raml.impl.exceptions.APIException;
import org.com.deloitte.raml.impl.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerControllerImpl implements CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerControllerImpl.class);

    @Autowired
    private CustomerService customerService;

    private static org.com.deloitte.raml.impl.model.Customer populate_db_model(Customer customer) {
        org.com.deloitte.raml.impl.model.Customer customer_model = new org.com.deloitte.raml.impl.model.Customer();
        org.com.deloitte.raml.impl.model.Address address_model = new org.com.deloitte.raml.impl.model.Address();
        customer_model.setCustomer_id(customer.getCustomerId());
        customer_model.setFirst_name(customer.getFirstName());
        customer_model.setLast_name(customer.getLastName());
        Address address = customer.getAddress();
        if (address != null) {
            address_model.setUnit_number(address.getUnitNumber());
            address_model.setStreet_name(address.getStreetName());
            address_model.setState(address.getState());
            address_model.setSuburb(address.getSuburb());
            address_model.setPostal_code(address.getPostalCode());
        }
        customer_model.setAddress(address_model);
        return customer_model;
    }

    private static Customer populate_response_object(org.com.deloitte.raml.impl.model.Customer customer) {
        Customer customer_response = new Customer();
        Address address_response = new Address();
        customer_response.setCustomerId(customer.getCustomer_id());
        customer_response.setFirstName(customer.getFirst_name());
        customer_response.setLastName(customer.getLast_name());
        org.com.deloitte.raml.impl.model.Address address = customer.getAddress();
        if (address != null) {
            address_response.setUnitNumber(address.getUnit_number());
            address_response.setPostalCode(address.getPostal_code());
            address_response.setState(address.getState());
            address_response.setSuburb(address.getSuburb());
            address_response.setStreetName(address.getStreet_name());
            customer_response.setAddress(address_response);
        }
        return customer_response;
    }

    @Override
    @PreAuthorize("#oauth2.hasScope('read')")
    public ResponseEntity<List<Customer>> getCustomers() {
        List<Customer> customerList = new ArrayList<>();
        Page<org.com.deloitte.raml.impl.model.Customer> allCustomers = customerService.getAllCustomers(0, 10);
        for (org.com.deloitte.raml.impl.model.Customer allCustomer : allCustomers.getContent()) {
            customerList.add(populate_response_object(allCustomer));
        }

        return ResponseEntity.<Customer>ok().body(customerList);
    }

    @Override
    @PreAuthorize("#oauth2.hasScope('write')")
    public ResponseEntity<object> createCustomer(@RequestBody Customer customer) {

        Long customer_id = customerService.addCustomer(populate_db_model(customer));
        URI uri = UriComponentsBuilder.fromPath("/api/customers/{id}").buildAndExpand(customer_id).toUri();
        return ResponseEntity.created(uri).build();

    }


    @Override
    @PreAuthorize("#oauth2.hasScope('write')")
    public ResponseEntity<object> updateCustomers(@RequestBody Customer customer) {
        if (customer != null && customer.getCustomerId() != null) {
            if (customerService.findCustomer(customer.getCustomerId()) != null) {
                customerService.updateCustomer(populate_db_model(customer));
            } else {
                return ResponseEntity.notFound().build();
            }
            URI uri = UriComponentsBuilder.fromPath("/api/customers/{id}").buildAndExpand(customer.getCustomerId()).toUri();
            return ResponseEntity.created(uri).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @Override
    @PreAuthorize("#oauth2.hasScope('write')")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        log.info("getCustomerById Starts");
        org.com.deloitte.raml.impl.model.Customer customer = customerService.findCustomer(id);

        if (customer != null) {
            return ResponseEntity.<Customer>ok().body(populate_response_object(customer));

        } else {
            throw new APIException("Customer Not Found");
        }


    }


}
