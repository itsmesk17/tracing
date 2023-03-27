package com.code2learn.log4j2tracing.otel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.opentelemetry.sdk.common.CompletableResultCode;
import io.opentelemetry.sdk.trace.data.SpanData;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Iterator;

public class Slf4jSpanExporter implements SpanExporter {
    private static final Logger logger = LoggerFactory.getLogger(Slf4jSpanExporter.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public Slf4jSpanExporter() {
    }

    @Override
    public CompletableResultCode export(Collection<SpanData> spans) {
        Iterator spanDataIterator = spans.iterator();

        while (spanDataIterator.hasNext()) {
            SpanData span = (SpanData) spanDataIterator.next();
            try {
                logger.info("{}", OBJECT_MAPPER.writeValueAsString(span));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return CompletableResultCode.ofSuccess();
    }

    @Override
    public CompletableResultCode flush() {
        return CompletableResultCode.ofSuccess();
    }

    @Override
    public CompletableResultCode shutdown() {
        return this.flush();
    }
}
