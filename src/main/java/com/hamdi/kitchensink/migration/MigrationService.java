package com.hamdi.kitchensink.migration;

import com.hamdi.kitchensink.data.MongoMemberRepository;
import com.hamdi.kitchensink.model.Migration;
import com.hamdi.kitchensink.model.MongoMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.hamdi.kitchensink.data.MemberRepository;
import com.hamdi.kitchensink.model.DbMember;

import java.util.List;

@Service
@Slf4j
public class MigrationService {

    private final MemberRepository h2MemberRepository;

    private final MongoMemberRepository mongoMemberRepository;

    public MigrationService(MemberRepository h2MemberRepository, MongoMemberRepository mongoMemberRepository) {
        this.h2MemberRepository = h2MemberRepository;
        this.mongoMemberRepository = mongoMemberRepository;
    }

    /**
     * This method migrate all members from H2 database to MongoDB
     * if one of the DB isn't available it will throw an IllegalStateException
     */
    public Migration migrateMembers() {

        try {
            h2MemberRepository.count();
        } catch (Exception e) {
            log.error("Failed to connect to H2 database", e);
            throw new IllegalStateException("H2 database connection is not available", e);
        }

        try {
            mongoMemberRepository.count();
        } catch (Exception e) {
            log.error("Failed to connect to MongoDB", e);
            throw new IllegalStateException("MongoDB connection is not available", e);
        }


        List<DbMember> h2DbMembers = h2MemberRepository.findAll();
        for (DbMember h2DbMember : h2DbMembers) {
            MongoMember member = new MongoMember();
            member.setId(String.valueOf(h2DbMember.getId()));
            member.setName(h2DbMember.getName());
            member.setEmail(h2DbMember.getEmail());
            member.setPhoneNumber(h2DbMember.getPhoneNumber());
            mongoMemberRepository.save(member);
            log.info("Migrated member: {}", member);
        }


        return new Migration(h2DbMembers.size(), mongoMemberRepository.count());
    }
}