package com.ttps.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ttps.backend.models.AppUser;
import com.ttps.backend.repositories.UserRepo;

@SpringBootTest
class AppUserTest {
    @Autowired
    UserRepo userRepo;

    AppUser userAdded;
    @BeforeEach
    void setUp() throws Exception {
        if(userRepo.findByEmail("testAdded")==null){
            userAdded = new AppUser(null,"testAdded","in","setUp",new ArrayList<>());
            userRepo.save(userAdded);
        }
    }

    @Test
	void testUserAdd() {
        AppUser user = new AppUser(null,"newAdd","newAdd","newAdd",new ArrayList<>());
        userRepo.save(user);
        assertEquals(user.getEmail(), userRepo.findByEmail("newAdd").getEmail());
	}

    @Test
	void testUserUpdate() {
        AppUser user = userRepo.findByEmail("testAdded");
        user.setFullName("testUpdate");
        userRepo.save(user);
        assertEquals(user.getFullName(), userRepo.findByEmail("testAdded").getFullName());
	}

    @Test
    void testUserListAll(){
        assertEquals(1, userRepo.findAll().size());
        AppUser user = new AppUser(null,"newUpdate","newUpdate","newUpdate",new ArrayList<>());
        userRepo.save(user);
        assertEquals(2, userRepo.findAll().size());
    }

    @Test
    void testUserDelete(){
        userAdded=userRepo.findByEmail("testAdded");
        userRepo.delete(userAdded);
        assertNull(userRepo.findByEmail("testAdded"));
    }
}

