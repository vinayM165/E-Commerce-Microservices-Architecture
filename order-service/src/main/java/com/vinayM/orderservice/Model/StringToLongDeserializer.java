package com.vinayM.orderservice.Model;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;


public class StringToLongDeserializer extends StdDeserializer<Long> {
        public StringToLongDeserializer() {
            this(null);
        }

    public StringToLongDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Long deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            String stringValue = node.asText();
            // Add additional error handling as needed
            try {
                return Long.parseLong(stringValue);
            } catch (NumberFormatException e) {
                // Handle the error, e.g., return a default value or throw an exception
                throw new IllegalArgumentException("Invalid long format: " + stringValue);
            }
        }


}
