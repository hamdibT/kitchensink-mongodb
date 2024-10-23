package com.hamdi.kitchensink.controller;

import com.hamdi.kitchensink.data.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DbMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    public void tearDown() {
        memberRepository.deleteAll(); // Clean up data after each test
    }

    @Test
    void testRegisterMemberSuccess() throws Exception {
        String json = "{\"name\":\"Jane Doe\",\"email\":\"jane.doe@mail.com\",\"phoneNumber\":\"1234567890\"}";

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.email").value("jane.doe@mail.com"))
                .andExpect(jsonPath("$.phoneNumber").value("1234567890"));
    }

    @Test
    void testRegisterMemberValidationFailure() throws Exception {
        String json = "{\"name\":\"\",\"email\":\"invalid-email\",\"phoneNumber\":\"\"}";

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").doesNotExist())
                .andExpect(jsonPath("$.email").doesNotExist())
                .andExpect(jsonPath("$.phoneNumber").doesNotExist());
    }

    @Test
    @Sql(statements = "INSERT INTO members (id, name, email, phone_number) VALUES (1, 'John Doe', 'john.doe@mail.com', '1234567890')")
    void testRegisterMemberEmailConflict() throws Exception {
        String json = "{\"name\":\"John Doe\",\"email\":\"john.doe@mail.com\",\"phoneNumber\":\"1234567890\"}";

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.email").value("Email taken"));
    }

    @Test
    @Sql(statements = "INSERT INTO members (id, name, email, phone_number) VALUES (1, 'John Doe', 'john.doe@mail.com', '1234567890')")
    void testLookupMemberByIdSuccess() throws Exception {
        long memberId = 1;
        mockMvc.perform(get("/members/{id}", memberId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(memberId));
    }

    @Test
    void testLookupMemberByIdNotFound() throws Exception {
        long memberId = 99999; // Assume this ID does not exist

        mockMvc.perform(get("/members/{id}", memberId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
     void testListAllMembers() throws Exception {
        mockMvc.perform(get("/members")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}




