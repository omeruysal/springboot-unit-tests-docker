package com.example.springunittest.controller;

import com.example.springunittest.dto.PersonDTO;
import com.example.springunittest.model.Person;
import com.example.springunittest.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

    private final static String CONTENT_TYPE = "application/json";

    @Autowired // Ilgili methoda post istegi atabilmek icin

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonService personService;

    @Test
    void whenValidInput_thenReturns200() throws Exception{
        PersonDTO person = new PersonDTO();
        person.setName("Test-name");
        person.setLastname("Test-lastname");

        ResultActions actions = mockMvc.perform(post("/person") //End pointi belirtiriz
                .contentType(CONTENT_TYPE) //Spring default olarak JSON data ister
                .content(objectMapper.writeValueAsString(person))); //Objemizi JSON'a cevirip veririz

        // Http istegi yapildiktan sonrasi
        ArgumentCaptor<PersonDTO> captor = ArgumentCaptor.forClass(PersonDTO.class);
        verify(personService, times(1)).save(captor.capture()); //personService sadece bir kere cagirilicaginn verifyini yapariz
        assertThat(captor.getValue().getName()).isEqualTo("Test-name"); // captor.capture ile save methoduna gelen parametreyi bir ust satirda yakaladik
        assertThat(captor.getValue().getLastname()).isEqualTo("Test-lastname"); //assertThat kullanarak captor icindeki objemiz ile yukardaki objemizin ayni olmasini istedigimiz kisimlari karsilastiririz
        actions.andExpect(status().isOk()); // Beklenen status
    }

    @Test
    void whenValidInput_thenReturns400() throws Exception {
        // given // post methodu ile obje gelmedigi senaryo oldugu icin bps obje

        // when
        ResultActions actions = mockMvc.perform(post("/person")
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString("test-value")));

        // then
        actions.andExpect(status().isBadRequest());
    }

    @Test
    void whenInvalidInput_thenReturns400() throws Exception {
        // given
        PersonDTO person = new PersonDTO();
        person.setLastname("test-lastname");

        // when
        ResultActions actions = mockMvc.perform(post("/person")
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(person)));

        // then
        actions.andExpect(status().isBadRequest());
    }

    @Test
    void whenCallGetAll_thenReturns200() throws Exception {
        // given
        PersonDTO person = new PersonDTO();
        person.setName("test-name");
        person.setLastname("test-lastname");
        when(personService.getAll()).thenReturn(Arrays.asList(person));

        // when
        MvcResult mvcResult = mockMvc.perform(get("/person")
                .accept(CONTENT_TYPE)).andReturn();


        // then
        String responseBody = mvcResult.getResponse().getContentAsString();
        verify(personService, times(1)).getAll();
        assertThat(objectMapper.writeValueAsString(Arrays.asList(person)))
                .isEqualToIgnoringWhitespace(responseBody);
    }

    @Test
    void whenCallGetAll_thenReturnsNoData() throws Exception {
        // given
        when(personService.getAll()).thenReturn(Collections.emptyList());

        // when
        MvcResult mvcResult = mockMvc.perform(get("/person")
                .accept(CONTENT_TYPE)).andReturn();

        // then
        String responseBody = mvcResult.getResponse().getContentAsString();
        verify(personService, times(1)).getAll();
        assertThat(objectMapper.writeValueAsString(Collections.emptyList()))
                .isEqualToIgnoringWhitespace(responseBody);
    }
}
