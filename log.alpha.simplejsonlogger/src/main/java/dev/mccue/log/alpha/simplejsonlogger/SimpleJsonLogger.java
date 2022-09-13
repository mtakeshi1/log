package dev.mccue.log.alpha.simplejsonlogger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.mccue.log.alpha.Log;
import dev.mccue.log.alpha.Logger;
import dev.mccue.log.alpha.jackson.LogSerializer;

public final class SimpleJsonLogger implements Logger {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    static {
        var module = new SimpleModule();
        module.addSerializer(Log.class, new LogSerializer());
        OBJECT_MAPPER.registerModule(module);
    }

    @Override
    public void log(Log log) {
        try {
            System.out.println(OBJECT_MAPPER.writeValueAsString(log));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
