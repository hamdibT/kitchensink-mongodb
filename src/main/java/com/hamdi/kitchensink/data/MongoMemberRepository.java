
package com.hamdi.kitchensink.data;

import com.hamdi.kitchensink.model.MongoMember;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoMemberRepository extends MongoRepository<MongoMember, String> {
   List<MongoMember> findAllByOrderByNameAsc ();
}
