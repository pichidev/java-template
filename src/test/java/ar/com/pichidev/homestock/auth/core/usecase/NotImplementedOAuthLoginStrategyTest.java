package ar.com.pichidev.homestock.auth.core.usecase;

import ar.com.pichidev.homestock.auth.core.exception.NotImplementedOAuthStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class NotImplementedOAuthLoginStrategyTest {

    private NotImplementedOAuthLoginStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new NotImplementedOAuthLoginStrategy();
    }

    @Test
    void getProviderIdReturnsNull() {
        assertNull(strategy.getProviderId());
    }

    @Test
    void loginThrowsNotImplementedOAuthStrategy() {
        Map<String, Object> providerInfo = new HashMap<>();

        assertThrows(NotImplementedOAuthStrategy.class, () -> strategy.login(providerInfo));
    }
}