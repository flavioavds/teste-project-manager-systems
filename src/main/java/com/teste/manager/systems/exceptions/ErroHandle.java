package com.teste.manager.systems.exceptions;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErroHandle {
    private Map<String, String> errors;
}
