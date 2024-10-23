package com.hamdi.kitchensink.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hamdi.kitchensink.model.MongoMember;
import com.hamdi.kitchensink.util.AbstractMongoTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import com.hamdi.kitchensink.data.MongoMemberRepository;
import org.testcontainers.containers.MongoDBContainer;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MongoMemberControllerTest extends AbstractMongoTest {
     @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MongoMemberRepository memberRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
    }

    @Test
    void shouldRegisterMember() throws Exception {
        MongoMember member = new MongoMember();
        member.setName("Hamdi");
        member.setEmail("this@mail.com");
        member.setPhoneNumber("1234567890");

        mockMvc.perform(post("/mongo/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Hamdi"))
                .andExpect(jsonPath("$.email").value("this@mail.com"))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void shouldGetAllMembers() throws Exception {
        MongoMember member1 = new MongoMember();
        member1.setName("Hamdi");
        member1.setEmail("this@mail.com");
        member1.setPhoneNumber("1234567890");

        MongoMember member2 = new MongoMember();
        member2.setName("Bebeto");
        member2.setEmail("Bebeto@mail.com");
        member2.setPhoneNumber("1234567890");

        memberRepository.save(member1);
        memberRepository.save(member2);

        mockMvc.perform(get("/mongo/members"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Bebeto"))
                .andExpect(jsonPath("$[1].name").value("Hamdi"));
    }

    @Test
    void shouldGetMemberById() throws Exception {
        MongoMember member = new MongoMember();
        member.setName("Hamdi");
        member.setEmail("this@mail.com");
        MongoMember savedMember = memberRepository.save(member);

        mockMvc.perform(get("/mongo/members/{id}", savedMember.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Hamdi"))
                .andExpect(jsonPath("$.email").value("this@mail.com"));
    }

    @Test
    void shouldReturn404WhenMemberNotFound() throws Exception {
        mockMvc.perform(get("/mongo/members/{id}", "9999"))
                .andExpect(status().isNotFound());
    }



    @Test
    void shouldRetur409WhenEmailIsDuplicate() throws Exception {
        MongoMember member1 = new MongoMember();
        member1.setName("Hamdi");
        member1.setEmail("duplicate@mail.com");
        member1.setPhoneNumber("1234567890");

        MongoMember member2 = new MongoMember();
        member2.setName("Bebeto");
        member2.setEmail("duplicate@mail.com");
        member2.setPhoneNumber("0987654321");

        mockMvc.perform(post("/mongo/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member1)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/mongo/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(member2)))
                .andExpect(status().isConflict());
    }

}