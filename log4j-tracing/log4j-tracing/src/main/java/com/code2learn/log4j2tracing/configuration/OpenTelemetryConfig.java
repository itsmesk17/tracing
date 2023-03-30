package com.code2learn.log4j2tracing.configuration;

import com.code2learn.log4j2tracing.otel.Slf4jSpanExporter;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.exporter.otlp.metrics.OtlpGrpcMetricExporter;
import io.opentelemetry.exporter.otlp.metrics.OtlpGrpcMetricExporterBuilder;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporterBuilder;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import io.opentelemetry.semconv.resource.attributes.ResourceAttributes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.sleuth.autoconfig.otel.OtelExporterProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties({OtelExporterProperties.class})
public class OpenTelemetryConfig {

    @Bean
    public Slf4jSpanExporter slf4jSpanExporter(OtelExporterProperties properties) {
        // Create a SpanExporter that logs spans to the console
        return new Slf4jSpanExporter();
    }
    @Bean
    public OtlpGrpcSpanExporter otelOtlpGrpcSpanExporter(OtelExporterProperties properties) {
        OtlpGrpcSpanExporterBuilder builder = OtlpGrpcSpanExporter.builder();
        String endpoint = properties.getOtlp().getEndpoint();
        if (StringUtils.hasText(endpoint)) {
            builder.setEndpoint(endpoint);
        }

        Long timeout = properties.getOtlp().getTimeout();
        if (timeout != null) {
            builder.setTimeout(timeout, TimeUnit.MILLISECONDS);
        }

        Map<String, String> headers = properties.getOtlp().getHeaders();
        if (!headers.isEmpty()) {
            headers.forEach(builder::addHeader);
        }
        return builder.build();
    }

    @Bean
    public OtlpGrpcMetricExporter otelOtlpGrpcMetricExporter(OtelExporterProperties properties) {
        OtlpGrpcMetricExporterBuilder builder = OtlpGrpcMetricExporter.builder();
        String endpoint = properties.getOtlp().getEndpoint();
        if (StringUtils.hasText(endpoint)) {
            builder.setEndpoint(endpoint);
        }

        Long timeout = properties.getOtlp().getTimeout();
        if (timeout != null) {
            builder.setTimeout(timeout, TimeUnit.MILLISECONDS);
        }

        Map<String, String> headers = properties.getOtlp().getHeaders();
        if (!headers.isEmpty()) {
            headers.forEach(builder::addHeader);
        }
        return builder.build();
    }

    @Bean
    public OpenTelemetry openTelemetry(@Value("${spring.application.name}") String serviceName, Slf4jSpanExporter slf4jSpanExporter, OtlpGrpcSpanExporter otelOtlpGrpcSpanExporter, OtlpGrpcMetricExporter otelOtlpGrpcMetricExporter) {

        Resource resource = Resource.getDefault()
                .merge(Resource.create(Attributes.of(ResourceAttributes.SERVICE_NAME, serviceName)));


        // Create a TracerProvider with the logging exporter
        SdkTracerProvider sdkTracerProvider = SdkTracerProvider.builder()
                .addSpanProcessor(SimpleSpanProcessor.create(slf4jSpanExporter))
                .addSpanProcessor(BatchSpanProcessor.builder(otelOtlpGrpcSpanExporter).build())
                .setResource(resource)
                .build();

        SdkMeterProvider sdkMeterProvider = SdkMeterProvider.builder()
                .registerMetricReader(PeriodicMetricReader.builder(otelOtlpGrpcMetricExporter).build())
                .setResource(resource)
                .build();

        // Create an OpenTelemetry instance with the tracer provider
        return OpenTelemetrySdk.builder()
                .setTracerProvider(sdkTracerProvider)
                .setMeterProvider(sdkMeterProvider)
                .setPropagators(ContextPropagators.create(W3CTraceContextPropagator.getInstance()))
                .buildAndRegisterGlobal();
    }

}