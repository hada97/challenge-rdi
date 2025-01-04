package com.rdi.MenuApp.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductRequestDTO {

    private String name;

    private int type;

    private ProductStatusRequestDTO status;

    // Lista de IDs dos componentes, ao invés de objetos completos de ProductComponents
    private List<Long> componentIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ProductStatusRequestDTO getStatus() {
        return status;
    }

    public void setStatus(ProductStatusRequestDTO status) {
        this.status = status;
    }

    public List<Long> getComponentIds() {
        return componentIds;
    }

    public void setComponentIds(List<Long> componentIds) {
        this.componentIds = componentIds;
    }
}
