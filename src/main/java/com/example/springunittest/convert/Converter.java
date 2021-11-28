package com.example.springunittest.convert;

import com.example.springunittest.dto.PersonDTO;
import com.example.springunittest.model.Person;
import org.springframework.stereotype.Service;

@Service
public class Converter {
    public  PersonDTO getPersonDTO(Person person) {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(person.getId());
        personDTO.setName(person.getName());
        personDTO.setLastname(person.getLastname());
        return personDTO;
    }
}
