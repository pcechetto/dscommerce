package com.example.dscommerce.services;

import com.example.dscommerce.entities.User;
import com.example.dscommerce.services.exceptions.ForbiddenException;
import com.example.dscommerce.tests.UserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class AuthServiceTests {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserService userService;

    private User admin;
    private User otherClient;
    private User selfClient;

    @BeforeEach
    void setup() {
        admin = UserFactory.createAdminUser();
        otherClient = UserFactory.createCustomClientUser(1L, "other@gmail.com");
        selfClient = UserFactory.createCustomClientUser(2L, "self@gmail.com");

    }

    @Test
    public void validateSelfOrAdminShouldDoNothingWhenAdminLogged() {
        Mockito.when(userService.authenticated()).thenReturn(admin);
        Long userId = admin.getId();

        Assertions.assertDoesNotThrow(() -> authService.validateSelfOrAdmin(userId));
    }

    @Test
    public void validateSelfOrAdminShouldDoNothingWhenSelfLogged() {
        Mockito.when(userService.authenticated()).thenReturn(selfClient);
        Long userId = selfClient.getId();

        Assertions.assertDoesNotThrow(() -> authService.validateSelfOrAdmin(userId));
    }

    @Test
    public void validateSelfOrAdminShouldThrowForbiddenExceptionWhenOtherUserLogged() {
        Mockito.when(userService.authenticated()).thenReturn(selfClient);
        Long userId = otherClient.getId();

        Assertions.assertThrows(ForbiddenException.class, () -> authService.validateSelfOrAdmin(userId));
    }
}
