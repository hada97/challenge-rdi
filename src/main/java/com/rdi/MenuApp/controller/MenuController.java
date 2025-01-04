package com.rdi.MenuApp.controller;

import com.rdi.MenuApp.DTO.ProductStatusRequestDTO;
import com.rdi.MenuApp.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @PutMapping("/{menuItemId}/status")
    public ResponseEntity<String> changeStatus(@PathVariable Long menuItemId, @RequestBody ProductStatusRequestDTO status) {
        try {
            menuService.changeStatus(menuItemId, status.getStatus());
            return ResponseEntity.ok("Status updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update status.");
        }
    }
}
