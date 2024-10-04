package com.pustovalov.util;

import com.pustovalov.exception.InvalidRequestParamException;

import java.util.UUID;
import java.util.regex.Pattern;

public class ReqParamValidator {

    public static void validatePlayerName(String name) {
        if (name.isBlank()) {
            throw new InvalidRequestParamException("The player's name is missing from the input field");
        }
        if (!Pattern.matches("^[a-zA-Zа-яА-ЯёЁ\\s]+$", name)) {
            throw new InvalidRequestParamException(
                    "The player's name can contain only Russian or Latin letters and spaces");
        }
    }

    public static void validatePlayerNameFilter(String filter) {
        if (!Pattern.matches("^[a-zA-Zа-яА-ЯёЁ\\s]+$", filter)) {
            throw new InvalidRequestParamException(
                    "The player's name can contain only Russian or Latin letters and spaces");
        }
    }

    public static void validatePageNumber(String page) {
        int intPage;

        try {
            intPage = Integer.parseInt(page);
        } catch (NumberFormatException e) {
            throw new InvalidRequestParamException("The page number must be an integer");
        }

        if (intPage < 0) {
            throw new InvalidRequestParamException("The page number cannot be a negative number");
        }
    }

    public static void validateUuid(String uuid) {
        try {
            UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestParamException("Invalid UUID");
        }
    }

    public static void validatePlayerId(String playerIdParam) {
        long id;

        try {
            id = Long.parseLong(playerIdParam);
        } catch (NumberFormatException e) {
            throw new InvalidRequestParamException("The player id must be an integer");
        }

        if (id < 0) {
            throw new InvalidRequestParamException("The player id cannot be a negative number");
        }
    }
}
