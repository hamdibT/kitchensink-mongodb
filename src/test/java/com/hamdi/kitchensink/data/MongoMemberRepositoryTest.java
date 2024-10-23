package com.hamdi.kitchensink.data;

import com.hamdi.kitchensink.model.MongoMember;
import com.hamdi.kitchensink.util.AbstractMongoTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ActiveProfiles("test")
class MongoMemberRepositoryTest extends AbstractMongoTest {

    @Autowired
    private MongoMemberRepository mongoMemberRepository;

    @AfterEach
    void cleanUp() {
        mongoMemberRepository.deleteAll();
    }

    @Test
    void testSaveAndFindById() {
        // Create a new MongoMember
        MongoMember member = new MongoMember();
        member.setId("999");
        member.setName("Hamdi");
        member.setEmail("hamdi@email.com");
        member.setPhoneNumber("1234567890");

        // Save the member
        mongoMemberRepository.save(member);

        // Retrieve the member by ID
        Optional<MongoMember> retrievedMember = mongoMemberRepository.findById("999");

        // Verify the member details
        assertThat(retrievedMember).isPresent();
        assertThat(retrievedMember.get().getName()).isEqualTo("Hamdi");
        assertThat(retrievedMember.get().getEmail()).isEqualTo("hamdi@email.com");
        assertThat(retrievedMember.get().getPhoneNumber()).isEqualTo("1234567890");
    }

    @Test
    void testNotFound() {
        // Attempt to retrieve a non-existent member by ID
        Optional<MongoMember> retrievedMember = mongoMemberRepository.findById("non-existent-id");

        // Verify the member is not found
        assertThat(retrievedMember).isNotPresent();
    }

    @Test
    void testFindAllByOrderByNameAsc() {
        // Create and save multiple MongoMembers
        MongoMember member1 = new MongoMember();
        member1.setId("1");
        member1.setName("Zidane");
        member1.setEmail("alice@example.com");
        member1.setPhoneNumber("1234567890");
        mongoMemberRepository.save(member1);

        MongoMember member2 = new MongoMember();
        member2.setId("2");
        member2.setName("Ronaldo");
        member2.setEmail("bob@example.com");
        member2.setPhoneNumber("0987654321");
        mongoMemberRepository.save(member2);

        // Retrieve all members ordered by name ascending
        List<MongoMember> members = mongoMemberRepository.findAllByOrderByNameAsc();

        // Verify the order of members
        assertThat(members).hasSize(2);
        assertThat(members.get(0).getName()).isEqualTo("Ronaldo");
        assertThat(members.get(1).getName()).isEqualTo("Zidane");
    }
}