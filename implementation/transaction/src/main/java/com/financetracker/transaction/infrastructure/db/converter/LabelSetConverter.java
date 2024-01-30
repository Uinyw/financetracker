package com.financetracker.transaction.infrastructure.db.converter;

import com.financetracker.transaction.logic.model.Label;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class LabelSetConverter implements AttributeConverter<Set<Label>, String> {
    private static final String SPLIT_CHAR = ";";

    @Override
    public String convertToDatabaseColumn(Set<Label> labels) {
        return labels != null && !labels.isEmpty() ? String.join(SPLIT_CHAR, labels.stream().map(Label::name).collect(Collectors.toSet())) : "";
    }

    @Override
    public Set<Label> convertToEntityAttribute(String string) {
        return string != null && !string.isEmpty() ? Arrays.stream((string.split(SPLIT_CHAR))).map(Label::new).collect(Collectors.toSet()) : Collections.emptySet();
    }
}
