package com.avenuecode.udemy.account.manager.dto;

import lombok.NonNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccessRequestDTO {
    @NonNull
    private String email;
    @NonNull
    private String seniority;
    @NonNull
    private String technology;
    @NonNull
    private String hours;
    @NonNull
    private String goal;
}
