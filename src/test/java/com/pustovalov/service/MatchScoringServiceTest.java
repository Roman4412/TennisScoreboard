package com.pustovalov.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public class MatchScoringServiceTest {

    private static final long PLAYER_ONE_ID = 1L;

    private OngoingMatchService ongoingMatchService;

    private MatchScoringService out;

    @BeforeEach
    void prepare() {
        PersistenceMatchService persistenceMatchService = Mockito.mock(PersistenceMatchService.class);
        ongoingMatchService = Mockito.mock(OngoingMatchService.class);
        out = MatchScoringService.getInstance(ongoingMatchService, persistenceMatchService);
    }

    @Test
    void countWhenMatchNotFoundThenThrowNoSuchElementException() {
        UUID uuid = UUID.randomUUID();
        Mockito.when(ongoingMatchService.getMatch(uuid)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> out.count(uuid, PLAYER_ONE_ID));
    }
}
