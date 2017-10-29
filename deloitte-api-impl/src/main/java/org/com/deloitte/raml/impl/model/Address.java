package org.com.deloitte.raml.impl.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @SequenceGenerator(name = "addr_seq", initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addr_seq")
    @Column(name = "address_id")
    private Integer address_id;


    @Column(name = "unit_number")
    private Long unit_number;

    @Column(name = "street_name")
    private String street_name;

    @Column(name = "suburb")
    private String suburb;

    @Column(name = "state")
    private String state;

    @Column(name = "postal_code")
    private Long postal_code;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Customer> customers;

    public Address() {

    }

    public Address(Long unit_number, String street_name, String suburb, String state, Long postal_code) {

        this.unit_number = unit_number;
        this.street_name = street_name;
        this.suburb = suburb;
        this.state = state;
        this.postal_code = postal_code;
    }

    public Integer getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Integer address_id) {
        this.address_id = address_id;
    }

    public Long getUnit_number() {
        return unit_number;
    }

    public void setUnit_number(Long unit_number) {
        this.unit_number = unit_number;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(Long postal_code) {
        this.postal_code = postal_code;
    }


    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }
}
