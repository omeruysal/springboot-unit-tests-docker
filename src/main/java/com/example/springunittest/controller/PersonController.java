package com.example.springunittest.controller;

import com.example.springunittest.dto.PersonDTO;
import com.example.springunittest.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping
    public ResponseEntity<List<PersonDTO>> getAll(){
        return ResponseEntity.ok(personService.getAll());
    }

    @PostMapping
    public ResponseEntity<PersonDTO> save(@RequestBody PersonDTO personDTO){
        return ResponseEntity.ok(personService.save(personDTO));

    }
}
