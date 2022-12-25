package com.example.demoJwtSignupAuthenticationMailGeneration.Controller;

import com.example.demoJwtSignupAuthenticationMailGeneration.Entity.RoleEntity;
import com.example.demoJwtSignupAuthenticationMailGeneration.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping({"/createNewRole"})
    public Map<String ,Object> createNewRole(@RequestBody RoleEntity role) {
        RoleEntity role1= roleService.createNewRole(role);
        Map<String,Object> map=new HashMap<>();
        map.put("Status: ", HttpStatus.CREATED);
        map.put("Message: ","Records are stored inside Role table.");
        map.put("Objects: ",role1);
        return map;
    }
}

