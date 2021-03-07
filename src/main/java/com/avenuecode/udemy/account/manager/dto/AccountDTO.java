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
public class AccountDTO {
    @NotNull
    private String email;
    @NotNull
    private String password;
    private Boolean scheduled;
}
