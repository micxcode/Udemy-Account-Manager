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
public class AccountDTO {
    @NonNull
    private String email;
    @NonNull
    private String password;
    private Boolean scheduled;
}
