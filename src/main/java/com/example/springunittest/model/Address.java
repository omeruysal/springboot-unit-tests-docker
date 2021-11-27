package com.example.springunittest.model;

import javax.persistence.*;

@Entity
public class Address {


    @Id
    @SequenceGenerator(name="seq_person_address",allocationSize = 1) // Mysql de default olarak handle edilir
    @GeneratedValue(generator = "seq_person_address")
    private Long id;
    private String address;
    @Enumerated
    private AddressType adressType;
    private Boolean active;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "person_address_id")
    private  Person person;

    public enum AddressType{
        HOME,
        WORK,
        OTHER
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", adressType=" + adressType +
                ", active=" + active +
                '}';
    }

    public Address(Long id, String address, AddressType adressType, Boolean active) {
        this.id = id;
        this.address = address;
        this.adressType = adressType;
        this.active = active;
    }

    public Address() {
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public AddressType getAdressType() {
        return adressType;
    }

    public void setAdressType(AddressType adressType) {
        this.adressType = adressType;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
