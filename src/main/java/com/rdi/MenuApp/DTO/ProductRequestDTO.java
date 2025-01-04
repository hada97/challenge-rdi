package com.rdi.MenuApp.DTO;


import com.rdi.MenuApp.domain.ProductComponents;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {

    private String name;
    private int type;
    private ProductStatusRequestDTO status; // Status como um objeto separado
    private List<ProductComponents> components;

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

    public List<ProductComponents> getComponents() {
        return components;
    }

    public void setComponents(List<ProductComponents> components) {
        this.components = components;
    }
}
