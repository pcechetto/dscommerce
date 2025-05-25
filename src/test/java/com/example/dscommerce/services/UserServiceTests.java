package com.example.dscommerce.services;

import com.example.dscommerce.entities.User;
import com.example.dscommerce.projections.UserDetailsProjection;
import com.example.dscommerce.repositories.UserRepository;
import com.example.dscommerce.tests.UserDetailsFactory;
import com.example.dscommerce.tests.UserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private String existingUsername;
    private String nonExistingUsername;
    private User user;
    private List<UserDetailsProjection> userDetails;

    @BeforeEach
    void setUp() throws Exception {
        existingUsername = "maria@gmail.com";
        nonExistingUsername = "user@gmail.com";
        user = UserFactory.createCustomClientUser(1L, existingUsername);
        userDetails = UserDetailsFactory.createCustomAdmintUser(existingUsername);

        Mockito.when(userRepository.searchUserAndRolesByEmail(existingUsername)).thenReturn(userDetails);
        Mockito.when(userRepository.searchUserAndRolesByEmail(nonExistingUsername)).thenReturn(List.of());
    }

    @Test
    public void loadUserByUsernameShouldReturnUserDetailsWhenUsernameExists() {
        UserDetails result = userService.loadUserByUsername(existingUsername);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(existingUsername, result.getUsername());
        Assertions.assertEquals(userDetails.getFirst().getPassword(), result.getPassword());
        Assertions.assertEquals(userDetails.getFirst().getAuthority(), result.getAuthorities().iterator().next().getAuthority());
    }

    @Test
    public void loadUserByUsernameShouldThrowResourceNotFoundExceptionWhenUsernameDoesNotExist() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(nonExistingUsername));
    }
}
