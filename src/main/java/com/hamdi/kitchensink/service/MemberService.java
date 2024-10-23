/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hamdi.kitchensink.service;

import com.hamdi.kitchensink.data.MemberRepository;

import com.hamdi.kitchensink.model.DbMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class MemberService {


    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public DbMember save(DbMember dbMember) {
        log.info("Saving member {} ", dbMember);
        DbMember savedDbMember = memberRepository.save(dbMember);
        log.info("Saved member {} ", dbMember);
        return savedDbMember;
    }

    public List<DbMember> findAllOrderedByName() {
        return memberRepository.findAllByOrderByNameAsc();
    }

    public DbMember findById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteAll() {
        log.warn("Deleting all members");
        memberRepository.deleteAll();
    }

}
