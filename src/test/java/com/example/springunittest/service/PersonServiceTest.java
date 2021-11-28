package com.example.springunittest.service;

import com.example.springunittest.convert.Converter;
import com.example.springunittest.dto.PersonDTO;
import com.example.springunittest.excetion.UserNotFoundException;
import com.example.springunittest.model.Address;
import com.example.springunittest.model.Person;
import com.example.springunittest.repo.AddressRepository;
import com.example.springunittest.repo.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    @InjectMocks // Testini yapacagimiz classin bir instanceni yaratiriz, mockito burada devreye girer
    private PersonService personService;

    @Mock // ihtiyacimiz olacak bilesenleride mocklariz ve InjectMocks ettigimiz bilesene gonderir
    private PersonRepository personRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private Converter converter;

    @Test
    public void whenSaveCalledWithValidRequest_itShouldReturnValidPersonDto() throws Exception{
        PersonDTO personDto = new PersonDTO();
        personDto.setName("Test-Name"); // Objemize test bilgilerimizi setleriz
        personDto.setLastname("Test-Lastname");
        personDto.setAddressList(Arrays.asList("Test-Address-1"));
        Person person = mock(Person.class);

        when(personRepository.save(ArgumentMatchers.any(Person.class))).thenReturn(person);
        when(person.getId()).thenReturn((1L));
        
        PersonDTO result = personService.save(personDto); //Mock servisimizle save islemi gerceklestiririz

        assertEquals(result.getName(),personDto.getName());
        assertEquals(result.getId(),1L);
    }
    @Test
    public void whenSaveCalledWithBlankName_itShouldReturnException() throws Exception{
        PersonDTO personDto = new PersonDTO();
        personDto.setLastname("Test-Lastname");
        personDto.setAddressList(Arrays.asList("Test-Address-1"));

        assertThrows( IllegalArgumentException.class,() ->{
           personService.save(personDto);
        });
    }
    @Test
    public void whenGetAllCalledWithoutAddressList_itShouldReturnPersonDtoList() throws Exception{
        Person person = new Person();
        person.setId(1L);
        person.setName("Test-Name");
        person.setLastname("Test-Lastname");

        when(personRepository.findAll()).thenReturn(Collections.singletonList(person));
        List<PersonDTO> personDTOList = personService.getAll();

        assertEquals(personDTOList.size(),1);
    }
    @Test
    public void whenGetAllCalled_itShouldReturnPersonDtoList() throws Exception{
        Person person = new Person();
        person.setId(1L);
        person.setName("Test-Name");
        person.setLastname("Test-Lastname");
        person.setAddressList((Collections.singletonList(new Address(1L,  "Istanbul", Address.AddressType.HOME,  true))));

        when(personRepository.findAll()).thenReturn(Collections.singletonList(person));
        List<PersonDTO> personDTOList = personService.getAll();

        assertEquals(personDTOList.size(),1);
        assertEquals(personDTOList.get(0).getAddressList().size(),1);
    }

    @Test
    public void whenFindPersonByIdCalled_itShouldReturnPerson() throws Exception{
        Long id = 1L;
        Person person = new Person();
        person.setId(id);
        person.setName("Test-Name");
        person.setLastname("Test-Lastname");

        when(personRepository.findById(id)).thenReturn(Optional.of(person));

        Person result = personService.findPersonById(id);

        assertEquals(result, person);

    }
    @Test
    public void testFindByPersonId_whenPersonIdDoesNotExist_itShouldThrowUserNotFoundException() throws Exception{

        when(personRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> personService.findPersonById(1L));

    }


    @Test
    public void whenGetByIdCalled_itShouldReturnPersonDto(){
        Long id = 1L;
        Person person = new Person();
        person.setId(id);
        person.setName("Test-Name");
        person.setLastname("Test-Lastname");


        when(personRepository.getById(id)).thenReturn(person);

        PersonDTO result = personService.getById(id);

        assertEquals(result.getId(), person.getId());

    }



}