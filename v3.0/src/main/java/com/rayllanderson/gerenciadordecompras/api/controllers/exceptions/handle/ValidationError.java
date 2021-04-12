package com.rayllanderson.gerenciadordecompras.api.controllers.exceptions.handle;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidationError extends StandardError {
    private final String fields;
    private final String fieldsMessage;
}