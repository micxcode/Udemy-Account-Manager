package com.avenuecode.udemy.account.manager.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccessRequestDTO {
    @NotNull
    private String email;
    @NotNull
    private String seniority;
    @NotNull
    private String technology;
    @NotNull
    private String hours;
    @NotNull
    private String goal;
}
