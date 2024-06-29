package com.gabrielluciano.squadchat.model.snowflake;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SnowflakeConverter implements AttributeConverter<Snowflake, Long> {

    @Override
    public Long convertToDatabaseColumn(Snowflake snowflake) {
        if (snowflake != null)
            return snowflake.getRawId();
        return null;
    }

    @Override
    public Snowflake convertToEntityAttribute(Long id) {
        return new SnowflakeImpl(id);
    }

}
