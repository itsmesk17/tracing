package com.code2learn.log4j2tracing.logger;

import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.common.AttributesBuilder;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.impl.ThrowableProxy;

import java.time.Instant;

@Plugin(
        name = "SpanAppender",
        category = Core.CATEGORY_NAME,
        elementType = Appender.ELEMENT_TYPE)
public class SpanAppender extends AbstractAppender {
    protected SpanAppender(String name, Filter filter) {
        super(name, filter, null);
    }

    @PluginFactory
    public static SpanAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Filter") Filter filter) {
        return new SpanAppender(name, filter);
    }

    @Override
    public void append(LogEvent event) {
        final Span currentSpan = Span.current();
        AttributesBuilder builder = Attributes.builder();

        if (currentSpan != null) {
            builder.put("logger", event.getLoggerName())
                    .put("level", event.getLevel().toString());

            currentSpan.addEvent(event.getMessage().getFormattedMessage(), builder.build(), Instant.now());

            if (Level.ERROR.equals(event.getLevel())) {
                currentSpan.setStatus(StatusCode.ERROR);
            }

            ThrowableProxy thrownProxy = event.getThrownProxy();
            if (thrownProxy instanceof ThrowableProxy) {
                Throwable throwable = ((ThrowableProxy) thrownProxy).getThrowable();
                if (throwable != null) {
                    currentSpan.recordException(throwable);
                }
            }
        }
    }
}