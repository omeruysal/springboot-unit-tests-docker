package com.example.springunittest.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Person {
    @Id
    @SequenceGenerator(name="seq_person",allocationSize = 1) // Mysql de default olarak handle edilir
    @GeneratedValue(generator = "seq_person")
    private Long id;

    private String name;

    private String lastname;

    @OneToMany
    @JoinColumn(name = "person_address_id")
    private List<Address> addressList;

    public Person(){

    }

    public Person(Long id, String name, String lastname, List<Address> addressList) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.addressList = addressList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", addressList=" + addressList +
                '}';
    }
}
