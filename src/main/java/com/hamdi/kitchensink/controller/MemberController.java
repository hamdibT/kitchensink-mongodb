package com.hamdi.kitchensink.controller;

import com.hamdi.kitchensink.model.DbMember;
import com.hamdi.kitchensink.model.MongoMember;
import com.hamdi.kitchensink.service.MemberService;
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
@RequestMapping("/members")
@Tag(name = "Member", description = "API for managing members by JPA")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @PostMapping
    @Operation(summary = "Register a new member", description = "Registers a new member with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member successfully registered", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DbMember.class)
            )),
            @ApiResponse(responseCode = "409", description = "Email already exists"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public DbMember registerMember(@Valid @RequestBody DbMember dbMember) {
        log.info("Registering member: {}", dbMember);
        return memberService.save(dbMember);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Get member by ID", description = "Retrieves a member by their ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved member",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DbMember.class)
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
    public ResponseEntity<DbMember> lookupMemberById(@PathVariable("id") long id) {
        DbMember dbMember = memberService.findById(id);
        if (dbMember == null) {
            log.info("Member not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
        log.info("Retrieved member: {}", dbMember);
        return ResponseEntity.ok(dbMember);
    }

    @GetMapping
    @Operation(summary = "List all members", description = "Lists all members ordered by name")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved list of members",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DbMember.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    public List<DbMember> listAllMembers() {
        return memberService.findAllOrderedByName();
    }

}