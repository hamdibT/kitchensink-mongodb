
package com.hamdi.kitchensink.controller;

import com.hamdi.kitchensink.data.MongoMemberRepository;
import com.hamdi.kitchensink.model.DbMember;
import com.hamdi.kitchensink.model.MongoMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mongo/members")
@Tag(name = "Member Mongo", description = "API for managing members by Mongo DB")
public class MongoMemberController {


    private final MongoMemberRepository memberRepository;

    public MongoMemberController(MongoMemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @PostMapping
    @Operation(summary = "Register a new member", description = "Registers a new member with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member successfully registered",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MongoMember.class)
                    )),
            @ApiResponse(responseCode = "409", description = "Email already exists"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<MongoMember> registerMember(@Valid @RequestBody MongoMember member) {
        log.info("Registering member: {} ", member);
        MongoMember savedMember = memberRepository.save(member);
        log.info("Member registered : {} ", member);
        return ResponseEntity.ok(savedMember);
    }

    @GetMapping
    @Operation(summary = "List all members", description = "Lists all members ordered by name")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of members",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MongoMember.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public List<MongoMember> getAllMembers() {
        log.info("Listing all members");
        return memberRepository.findAllByOrderByNameAsc();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get member by ID", description = "Retrieves a member by their ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved member",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MongoMember.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Member not found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid ID format"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public ResponseEntity<MongoMember> getMemberById(@PathVariable String id) {
        return memberRepository.findById(id)
                .map(member -> {
                    log.info("Retrieved member: {}", member);
                    return ResponseEntity.ok(member);
                })
                .orElseGet(() -> {
                    log.info("Member not found with id: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }



    @DeleteMapping
    @Operation(summary = "Delete All members", description = "Used only for testing purposes. It will delete all members")
    public void deleteAll() {
         memberRepository.deleteAll();
         log.warn("All members deleted");
    }

}
