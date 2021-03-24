package com.example.tfg.Controllers.Role;

import com.example.tfg.Entities.Role.Role;
import com.example.tfg.Repositories.Role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/create")
    public ResponseEntity createRole(@RequestBody Role role) {
        roleRepository.save(role);
        return new ResponseEntity(role, HttpStatus.valueOf(200));
    }
}
