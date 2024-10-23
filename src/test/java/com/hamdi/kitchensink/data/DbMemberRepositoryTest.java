package com.hamdi.kitchensink.data;

import com.hamdi.kitchensink.model.DbMember;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ExtendWith(SpringExtension.class)
class DbMemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void testSave() {
        DbMember dbMember = new DbMember();
        dbMember.setEmail("test@example.com");
        dbMember.setPhoneNumber("1234567890");
        dbMember.setName("Test User");

        DbMember savedDbMember = memberRepository.save(dbMember);

        assertThat(savedDbMember).isNotNull();
        assertThat(savedDbMember.getId()).isNotNull();
        assertThat(savedDbMember.getEmail()).isEqualTo("test@example.com");
        assertThat(savedDbMember.getName()).isEqualTo("Test User");
    }

    @Test
    void testGetAll() {
        DbMember dbMember1 = new DbMember();
        dbMember1.setName("Alice");
        dbMember1.setPhoneNumber("1234567890");
        dbMember1.setEmail("alice@example.com");
        memberRepository.save(dbMember1);

        DbMember dbMember2 = new DbMember();
        dbMember2.setName("Bob");
        dbMember2.setPhoneNumber("1234567890");
        dbMember2.setEmail("bob@example.com");
        memberRepository.save(dbMember2);

        List<DbMember> dbMembers = memberRepository.findAll();

        assertThat(dbMembers).hasSize(2);
        assertThat(dbMembers.get(0).getName()).isEqualTo("Alice");
        assertThat(dbMembers.get(1).getName()).isEqualTo("Bob");
    }

    @Test
    void testFindByEmail() {
        DbMember dbMember = new DbMember();
        dbMember.setEmail("test@example.com");
        dbMember.setPhoneNumber("1234567890");
        dbMember.setName("Test User");
        memberRepository.save(dbMember);

        DbMember foundDbMember = memberRepository.findByEmail("test@example.com");
        assertThat(foundDbMember).isNotNull();
        assertThat(foundDbMember.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void testFindAllByOrderByNameAsc() {
        DbMember dbMember1 = new DbMember();
        dbMember1.setName("Alice");
        dbMember1.setPhoneNumber("1234567890");
        dbMember1.setEmail("alice@example.com");
        memberRepository.save(dbMember1);

        DbMember dbMember2 = new DbMember();
        dbMember2.setName("Bob");
        dbMember2.setPhoneNumber("1234567890");
        dbMember2.setEmail("bob@example.com");
        memberRepository.save(dbMember2);

        List<DbMember> dbMembers = memberRepository.findAllByOrderByNameAsc();
        assertThat(dbMembers).hasSize(2);
        assertThat(dbMembers.get(0).getName()).isEqualTo("Alice");
        assertThat(dbMembers.get(1).getName()).isEqualTo("Bob");
    }
}