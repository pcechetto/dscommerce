package com.example.dscommerce.services;

import com.example.dscommerce.dto.UserDTO;
import com.example.dscommerce.entities.User;
import com.example.dscommerce.projections.UserDetailsProjection;
import com.example.dscommerce.repositories.UserRepository;
import com.example.dscommerce.tests.UserDetailsFactory;
import com.example.dscommerce.tests.UserFactory;
import com.example.dscommerce.util.CustomUserUtil;
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
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CustomUserUtil customUserUtil;

    private String existingUsername;
    private String nonExistingUsername;
    private User user;
    private List<UserDetailsProjection> userDetails;

    @BeforeEach
    void setUp() {
        existingUsername = "maria@gmail.com";
        nonExistingUsername = "user@gmail.com";
        user = UserFactory.createCustomClientUser(1L, existingUsername);
        userDetails = UserDetailsFactory.createCustomAdmintUser(existingUsername);

        Mockito.when(userRepository.searchUserAndRolesByEmail(existingUsername)).thenReturn(userDetails);
        Mockito.when(userRepository.searchUserAndRolesByEmail(nonExistingUsername)).thenReturn(List.of());
        Mockito.when(userRepository.findByEmail(existingUsername)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findByEmail(nonExistingUsername)).thenReturn(Optional.empty());
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

    @Test
    public void authenticatedShouldReturnUserWhenUsernameExists() {
        Mockito.when(customUserUtil.getLoggedUsername()).thenReturn(existingUsername);
        User result = userService.authenticated();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(user.getId(), result.getId());
        Assertions.assertEquals(user.getEmail(), result.getEmail());
        Assertions.assertEquals(user.getName(), result.getName());
        Assertions.assertEquals(user.getPhone(), result.getPhone());
        Assertions.assertEquals(user.getBirthDate(), result.getBirthDate());
        Assertions.assertEquals(user.getRoles().size(), result.getRoles().size());
    }

    @Test
    public void authenticatedShouldThrowResourceNotFoundExceptionWhenUsernameDoesNotExist() {
        Mockito.doThrow(UsernameNotFoundException.class).when(customUserUtil).getLoggedUsername();

        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.authenticated());
    }

    @Test
    public void getMeShouldReturnUserDTOWhenUsernameExists() {
        Mockito.when(customUserUtil.getLoggedUsername()).thenReturn(existingUsername);
        UserDTO result = userService.getMe();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(user.getId(), result.getId());
        Assertions.assertEquals(user.getEmail(), result.getEmail());
        Assertions.assertEquals(user.getName(), result.getName());
        Assertions.assertEquals(user.getPhone(), result.getPhone());
        Assertions.assertEquals(user.getBirthDate(), result.getBirthDate());
    }

    @Test
    public void getMeShouldThrowResourceNotFoundExceptionWhenUsernameDoesNotExist() {
        Mockito.doThrow(UsernameNotFoundException.class).when(customUserUtil).getLoggedUsername();
    }
}
