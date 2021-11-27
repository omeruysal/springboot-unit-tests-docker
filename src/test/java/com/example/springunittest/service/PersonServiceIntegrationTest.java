package com.example.springunittest.service;

import com.example.springunittest.dto.PersonDTO;
import com.example.springunittest.model.Address;
import com.example.springunittest.model.Person;
import com.example.springunittest.repo.AddressRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PersonServiceIntegrationTest {
    @Autowired
    private PersonService personService;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void testSave(){
        PersonDTO personDto = new PersonDTO();
        personDto.setName("Test-Name");
        personDto.setLastname("Test-Lastname");
        personDto.setAddressList(Arrays.asList("Test-Address-1"));

        PersonDTO result = personService.save(personDto);
        List<Address> list = addressRepository.findAll();

        assertTrue(result.getId() > 0L);
        assertEquals(list.size(),1);

    }
}
