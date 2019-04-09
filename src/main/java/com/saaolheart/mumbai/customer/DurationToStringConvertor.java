package com.saaolheart.mumbai.customer;

import java.time.Duration;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply=true)
public class DurationToStringConvertor implements AttributeConverter<Duration, String>
{

    @Override
    public String convertToDatabaseColumn(Duration duration)
    {
        return duration == null ? null : duration.toString();
    }

    @Override
    public Duration convertToEntityAttribute(String dbData)
    {
        return dbData == null ? null : Duration.parse(dbData);
    }
}


