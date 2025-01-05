package com.rdi.MenuApp.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ProductStatusRequestDTO {

    private int status; // 0 ou 1

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
