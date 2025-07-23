package com.casestudy.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.casestudy.model.AdminModel;
import com.casestudy.model.UserModel;
import com.casestudy.repository.AdminRepository;
import com.casestudy.repository.TrainRepository;
import com.casestudy.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private AdminRepository adminrepo;

    @Mock
    private TrainRepository trainrepo;

    @Mock
    private UserRepository userrepo;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void testAddAdmin() {
        AdminModel admin = new AdminModel(1L, "adminName", "pass");
        when(adminrepo.save(admin)).thenReturn(admin);

        String result = adminService.addAdmin(admin);
        assertEquals("Admin with Id: 1 have been Registered Successfully", result);
        System.out.println("✅ testAddAdmin ran successfully!");

        verify(adminrepo, times(1)).save(admin);
    }

    @Test
    void testGetAdmin() {
        AdminModel admin = new AdminModel(2L, "admin2", "pass2");
        when(adminrepo.findById(2L)).thenReturn(Optional.of(admin));

        Optional<AdminModel> result = adminService.getAdmin(2L);
        assertTrue(result.isPresent());
        assertEquals("admin2", result.get().getUsername());
    }

    @Test
    void testGetAllUsers() {
        UserModel[] mockUsers = {new UserModel(1L, "user1", "pass1")};
        when(restTemplate.getForObject(anyString(), eq(UserModel[].class))).thenReturn(mockUsers);

        List<UserModel> result = adminService.getAllUsers();
        assertEquals(1, result.size());
        assertEquals("user1", result.get(0).getUsername());
    }
}