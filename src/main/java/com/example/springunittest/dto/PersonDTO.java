package com.example.springunittest.dto;

import com.sun.istack.NotNull;

import java.util.List;

public class PersonDTO {

    private Long id;

    @NotNull
    private String name;

    private String lastname;

    private List<String> addressList;

    public PersonDTO(Long id, String name, String lastname, List<String> addressList) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.addressList = addressList;
    }
    public PersonDTO() {

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

    public List<String> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<String> addressList) {
        this.addressList = addressList;
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", addressList=" + addressList +
                '}';
    }
}
