package com.weyland.synthetic_human_core_starter.command;

import com.weyland.synthetic_human_core_starter.exception.CoreException;
import com.weyland.synthetic_human_core_starter.exception.ErrorResponse;
import lombok.Getter;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Getter
public class SyntheticCommand {
    private static final int MAX_DESCRIPTION_LENGTH = 1000;
    private static final int MAX_AUTHOR_LENGTH = 100;
    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_INSTANT;

    private final CommandPriority priority;
    private final String description;
    private final String author;
    private final String timestamp;

    public SyntheticCommand(String description, CommandPriority priority,
                            String author, String timestamp) throws CoreException {
        validateDescription(description);
        validatePriority(priority);
        validateAuthor(author);
        validateTimestamp(timestamp);

        this.description = description;
        this.priority = priority;
        this.author = author;
        this.timestamp = timestamp;
    }

    private void validateDescription(String description) throws CoreException {
        if (description == null || description.isBlank()) {
            throw new CoreException(
                    new ErrorResponse("INVALID_COMMAND", "Description cannot be empty")
            );
        }
        if (description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new CoreException(
                    new ErrorResponse("INVALID_COMMAND",
                            "Description exceeds " + MAX_DESCRIPTION_LENGTH + " characters")
            );
        }
    }

    private void validatePriority(CommandPriority priority) throws CoreException {
        if (priority == null) {
            throw new CoreException(
                    new ErrorResponse("INVALID_COMMAND", "Priority is required")
            );
        }
    }

    private void validateAuthor(String author) throws CoreException {
        if (author == null || author.isBlank()) {
            throw new CoreException(
                    new ErrorResponse("INVALID_COMMAND", "Author cannot be empty")
            );
        }
        if (author.length() > MAX_AUTHOR_LENGTH) {
            throw new CoreException(
                    new ErrorResponse("INVALID_COMMAND",
                            "Author exceeds " + MAX_AUTHOR_LENGTH + " characters")
            );
        }
    }

    private void validateTimestamp(String timestamp) throws CoreException {
        if (timestamp == null || timestamp.isBlank()) {
            throw new CoreException(
                    new ErrorResponse("INVALID_COMMAND", "Timestamp is required")
            );
        }

        try {
            Instant.parse(timestamp);
        } catch (DateTimeParseException e) {
            throw new CoreException(
                    new ErrorResponse("INVALID_COMMAND",
                            "Timestamp must be in ISO-8601 format. Example: 2023-05-15T14:30:00Z")
            );
        }
    }

}