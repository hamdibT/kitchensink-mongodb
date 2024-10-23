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
package com.hamdi.kitchensink.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serializable;
@Entity
@Data
@Table(name = "members", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Schema(description = "Member entity representing a member in the system")
public class DbMember implements Serializable {

    @Id
    @GeneratedValue
    @Schema(hidden = true)
    private Long id;

    @Size(min = 1, max = 25)
    @NotNull
    @Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
    @Schema(description = "Name of the member", example = "HBT")
    private String name;

    @NotEmpty
    @NotNull
    @Email
    @Schema(description = "Email address of the member and it's unique identifier", example = "hbt@example.com")
    private String email;

    @Size(min = 10, max = 12)
    @NotNull
    @Digits(fraction = 0, integer = 12)
    @Column(name = "phone_number")
    @Schema(description = "Phone number of the member", example = "1234567890")
    private String phoneNumber;

}