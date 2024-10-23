package com.hamdi.kitchensink.service;

import com.hamdi.kitchensink.data.MemberRepository;
import com.hamdi.kitchensink.model.DbMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DbMemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save() {
        DbMember dbMember = new DbMember();
        when(memberRepository.save(dbMember)).thenReturn(dbMember);

        DbMember savedDbMember = memberService.save(dbMember);

        assertNotNull(savedDbMember);
        verify(memberRepository, times(1)).save(dbMember);
    }

    @Test
    void findAllOrderedByName() {
        DbMember dbMember = new DbMember();
        List<DbMember> dbMembers = Collections.singletonList(dbMember);
        when(memberRepository.findAllByOrderByNameAsc()).thenReturn(dbMembers);

        List<DbMember> foundDbMembers = memberService.findAllOrderedByName();

        assertNotNull(foundDbMembers);
        assertEquals(1, foundDbMembers.size());
        verify(memberRepository, times(1)).findAllByOrderByNameAsc();
    }

    @Test
    void findById() {
        Long id = 1L;
        DbMember dbMember = new DbMember();
        when(memberRepository.findById(id)).thenReturn(Optional.of(dbMember));

        DbMember foundDbMember = memberService.findById(id);

        assertNotNull(foundDbMember);
        verify(memberRepository, times(1)).findById(id);
    }
}