package com.example.springunittest.service;

import com.example.springunittest.dto.PersonDTO;
import com.example.springunittest.excetion.UserNotFoundException;
import com.example.springunittest.model.Address;
import com.example.springunittest.model.Person;
import com.example.springunittest.repo.AddressRepository;
import com.example.springunittest.repo.PersonRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;

    public PersonService(PersonRepository personRepository, AddressRepository addressRepository) {
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
    }

    @Transactional //Saves person and addresses of person in one transaction
    public PersonDTO save(PersonDTO personDTO){
        Assert.notNull(personDTO.getName(),"Alan adi zorunlu");
        Person person = new Person();
        //mapping
        person.setName(personDTO.getName());
        person.setLastname(personDTO.getLastname());
        person.setLastname(personDTO.getLastname());
        //saving person
        final Person personFromDB = personRepository.save(person);
        List<Address> addressList = new ArrayList<>();
        personDTO.getAddressList().forEach(item->{
            Address address = new Address();
            address.setAddress(item);
            address.setAdressType(Address.AddressType.OTHER);
            address.setActive(true);
            address.setPerson(personFromDB);
            addressList.add(address);

        });
        //saving address of person
        addressRepository.saveAll(addressList);

        personDTO.setId((personFromDB.getId()));
        return personDTO;
    }

    public List<PersonDTO> getAll(){
        List<Person> personList = personRepository.findAll();
        List<PersonDTO> personDTOList = new ArrayList<>();

        personList.forEach(person ->{
            PersonDTO personDto = new PersonDTO();
            personDto.setId(person.getId());
            personDto.setName(person.getName());
            personDto.setLastname(person.getLastname());
            personDto.setAddressList(
                    person.getAddressList() != null ?
                            person.getAddressList().stream().map(Address::getAddress).collect(Collectors.toList())
                            : null);
            personDTOList.add(personDto);

        });
        return personDTOList;
    }

    public Person findPersonById(Long id ){
        return personRepository.findById(id).orElseThrow(() -> new UserNotFoundException("The person could find by id : "+ id));
    }

    public PersonDTO getById(Long id){
        Person person = personRepository.getById(id);
        PersonDTO personDTO = new PersonDTO();
        personDTO.setId(person.getId());
        personDTO.setName(person.getName());
        personDTO.setLastname(person.getLastname());

        return personDTO;
    }

}
