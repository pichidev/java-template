package ar.com.pichidev.template.auth.core.service;

import ar.com.pichidev.template.auth.core.entity.OAuthProvider;
import ar.com.pichidev.template.auth.core.exception.InvalidOAuthProviderId;
import ar.com.pichidev.template.auth.core.interfaces.usecase.OAuthLoginStrategy;
import ar.com.pichidev.template.auth.core.usecase.NotImplementedOAuthLoginStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OAuthLoginStrategyResolverTest {

    @Mock
    private OAuthLoginStrategy googleStrategy;

    private NotImplementedOAuthLoginStrategy notImplementedStrategy;

    @BeforeEach
    void setUp() {
        notImplementedStrategy = new NotImplementedOAuthLoginStrategy();
    }

    @Test
    void resolveReturnsCorrectStrategyForExistingProvider() {
        when(googleStrategy.getProviderId()).thenReturn(OAuthProvider.GOOGLE);
        OAuthLoginStrategyResolver resolver = new OAuthLoginStrategyResolver(List.of(googleStrategy), notImplementedStrategy);

        OAuthLoginStrategy result = resolver.resolve(OAuthProvider.GOOGLE);

        assertNotNull(result);
        assertEquals(googleStrategy, result);
    }

    @Test
    void resolveReturnsNotImplementedStrategyForNonExistingProvider() {
        OAuthLoginStrategyResolver resolver = new OAuthLoginStrategyResolver(List.of(), notImplementedStrategy);

        OAuthLoginStrategy result = resolver.resolve(OAuthProvider.GOOGLE);

        assertNotNull(result);
        assertEquals(notImplementedStrategy, result);
    }

    @Test
    void resolveThrowsExceptionWhenProviderIdIsNull() {
        when(googleStrategy.getProviderId()).thenReturn(OAuthProvider.GOOGLE);
        OAuthLoginStrategyResolver resolver = new OAuthLoginStrategyResolver(List.of(googleStrategy), notImplementedStrategy);

        assertThrows(InvalidOAuthProviderId.class, () -> resolver.resolve(null));
    }

    @Test
    void constructorFiltersStrategiesWithNullProviderId() {
        when(googleStrategy.getProviderId()).thenReturn(OAuthProvider.GOOGLE);
        OAuthLoginStrategyResolver resolver = new OAuthLoginStrategyResolver(
                List.of(googleStrategy, notImplementedStrategy),
                notImplementedStrategy
        );

        OAuthLoginStrategy result = resolver.resolve(OAuthProvider.GOOGLE);

        assertEquals(googleStrategy, result);
    }

    @Test
    void constructorHandlesDuplicateProviderStrategies() {
        when(googleStrategy.getProviderId()).thenReturn(OAuthProvider.GOOGLE);
        OAuthLoginStrategy googleStrategy2 = org.mockito.Mockito.mock(OAuthLoginStrategy.class);
        when(googleStrategy2.getProviderId()).thenReturn(OAuthProvider.GOOGLE);

        OAuthLoginStrategyResolver resolver = new OAuthLoginStrategyResolver(
                List.of(googleStrategy, googleStrategy2),
                notImplementedStrategy
        );

        OAuthLoginStrategy result = resolver.resolve(OAuthProvider.GOOGLE);

        assertNotNull(result);
        assertEquals(googleStrategy2, result);
    }
}