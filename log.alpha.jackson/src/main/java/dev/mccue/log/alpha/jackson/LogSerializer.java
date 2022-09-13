package dev.mccue.log.alpha.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import dev.mccue.log.alpha.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public final class LogSerializer extends JsonSerializer<Log> {
    private String throwableToString(Throwable t) {
        var sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    private void writeValue(JsonGenerator jsonGenerator, Log.Entry.Value value) throws IOException {
        switch (value) {
            case Log.Entry.Value.Null __ ->
                    jsonGenerator.writeNull();
            case Log.Entry.Value.Boolean b ->
                    jsonGenerator.writeBoolean(b.value());

            case Log.Entry.Value.String s ->
                    jsonGenerator.writeString(s.value());
            case Log.Entry.Value.Character character ->
                    jsonGenerator.writeString("" + character.value());

            case Log.Entry.Value.Byte b ->
                    jsonGenerator.writeNumber(b.value());
            case Log.Entry.Value.Short s ->
                    jsonGenerator.writeNumber(s.value());
            case Log.Entry.Value.Integer integer ->
                    jsonGenerator.writeNumber(integer.value());
            case Log.Entry.Value.Long l ->
                    jsonGenerator.writeNumber(l.value());

            case Log.Entry.Value.Float f ->
                    jsonGenerator.writeNumber(f.value());
            case Log.Entry.Value.Double d ->
                    jsonGenerator.writeNumber(d.value());

            case Log.Entry.Value.Duration duration ->
                    jsonGenerator.writeNumber(duration.value().toMillis());
            case Log.Entry.Value.Instant instant ->
                    jsonGenerator.writeString(DateTimeFormatter.ISO_INSTANT.format(instant.value()));
            case Log.Entry.Value.LocalTime localTime ->
                    jsonGenerator.writeString(DateTimeFormatter.ISO_LOCAL_TIME.format(localTime.value()));
            case Log.Entry.Value.LocalDate localDate ->
                    jsonGenerator.writeString(DateTimeFormatter.ISO_LOCAL_DATE.format(localDate.value()));
            case Log.Entry.Value.LocalDateTime localDateTime ->
                    jsonGenerator.writeString(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(localDateTime.value()));


            case Log.Entry.Value.URI uri ->
                    jsonGenerator.writeString(uri.value().toString());
            case Log.Entry.Value.UUID uuid ->
                    jsonGenerator.writeString(uuid.value().toString());

            case Log.Entry.Value.Throwable throwable ->
                    jsonGenerator.writeString(throwableToString(throwable.value()));


            case Log.Entry.Value.Lazy lazy ->
                    writeValue(jsonGenerator, lazy.value());

            case Log.Entry.Value.List list -> {
                jsonGenerator.writeStartArray();
                for (var v : list.value()) {
                    writeValue(jsonGenerator, v);
                }
                jsonGenerator.writeEndArray();
            }

            case Log.Entry.Value.Set set -> {
                jsonGenerator.writeStartArray();
                for (var v : set.value()) {
                    writeValue(jsonGenerator, v);
                }
                jsonGenerator.writeEndArray();
            }

            case Log.Entry.Value.Map m -> {
                for (var entry : m.value().entrySet()) {
                    if (entry.getKey() instanceof Log.Entry.Value.String s) {
                        jsonGenerator.writeFieldName(s.value());
                    } else {
                        throw new IllegalStateException("Only string keys allowed for JSON");
                    }
                    writeValue(jsonGenerator, entry.getValue());
                }
            }
        }
    }

    @Override
    public void serialize(
            Log log,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider
    ) throws IOException {
        jsonGenerator.writeStartObject();
        var category = log.category();
        jsonGenerator.writeStringField("log.alpha/namespace", category.namespace());
        jsonGenerator.writeStringField("log.alpha/name", category.name());
        jsonGenerator.writeStringField("log.alpha/level", switch (log.level()) {
            case TRACE -> "trace";
            case DEBUG -> "debug";
            case INFO -> "info";
            case WARN -> "warn";
            case ERROR -> "error";
        });
        jsonGenerator.writeStringField("log.alpha/flake", log.flake().toString());
        switch (log.occurrence()) {
            case Log.Occurrence.PointInTime pointInTime ->
                jsonGenerator.writeStringField(
                        "log.alpha/timestamp",
                        DateTimeFormatter.ISO_INSTANT.format(pointInTime.happenedAt())
                );
            case Log.Occurrence.SpanOfTime spanOfTime -> {
                jsonGenerator.writeStringField(
                        "log.alpha/timestamp",
                        DateTimeFormatter.ISO_INSTANT.format(spanOfTime.startedAt())
                );
                jsonGenerator.writeNumberField(
                        "log.alpha/duration",
                        spanOfTime.lasted().toMillis()
                );
            }
        }
        var map = new HashMap<String, Log.Entry.Value>();
        for (var entry : log) {
            map.put(entry.key(), entry.value());
        }

        for (var entry : map.entrySet()) {
            jsonGenerator.writeFieldName(entry.getKey());
            writeValue(jsonGenerator, entry.getValue());
        }

        jsonGenerator.writeEndObject();
    }
}
