package com.hamdi.kitchensink.migration;

import com.hamdi.kitchensink.data.MongoMemberRepository;
import com.hamdi.kitchensink.data.MemberRepository;
import com.hamdi.kitchensink.model.DbMember;
import com.hamdi.kitchensink.model.MongoMember;
import com.hamdi.kitchensink.util.AbstractMongoTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class MongoMigrationTest extends AbstractMongoTest {

    @Autowired
    private MemberRepository h2MemberRepository;

    @Autowired
    private MongoMemberRepository mongoRepository;

    @Autowired
    private MigrationService migrationService;

    @Test
    void testMigration() {
        // create a member on DB
        DbMember h2DbMember = new DbMember();
        h2DbMember.setId(1L);
        h2DbMember.setName("John Doe");
        h2DbMember.setEmail("john.doe@example.com");
        h2DbMember.setPhoneNumber("1234567890");
        h2MemberRepository.save(h2DbMember);

        // Run the migration
        migrationService.migrateMembers();

        // Verify the data in MongoDB
        List<MongoMember> members = mongoRepository.findAll();
        assertThat(members).hasSize(1);
        MongoMember member = members.get(0);
        assertThat(member.getId()).isEqualTo("1");
        assertThat(member.getName()).isEqualTo("John Doe");
        assertThat(member.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(member.getPhoneNumber()).isEqualTo("1234567890");
    }
}