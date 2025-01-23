package com.tpe.service;

import com.tpe.domain.Role;
import com.tpe.domain.RoleType;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    //role tipi verildiginde db'ye gidip bu rolu bulmamiz gerek
    public Role getRoleByType(RoleType roleType){
        Role role = roleRepository.findByType(roleType).
                orElseThrow(()->new ResourceNotFoundException("Role Is Not Found!!"));
        return role;
    }
}
