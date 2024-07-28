package com.store.itauseguros.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class MetricsService {

    private final Counter productsGetCounter;
    private final Timer productsGetTimer;
    private final Counter productsPostCounter;
    private final Timer productsPostTimer;
    private final Counter productsProductIdPutCounter;
    private final Timer productsProductIdPutTimer;

    public MetricsService(MeterRegistry meterRegistry) {
        // Definição das métricas
        this.productsGetCounter = meterRegistry.counter("products_get_total");
        this.productsGetTimer = meterRegistry.timer("products_get_timer");

        this.productsPostCounter = meterRegistry.counter("products_post_total");
        this.productsPostTimer = meterRegistry.timer("products_post_timer");

        this.productsProductIdPutCounter = meterRegistry.counter("products_product_id_put_total");
        this.productsProductIdPutTimer = meterRegistry.timer("products_product_id_put_timer");
    }

    // Métodos para incrementar contadores e iniciar timers
    public void incrementProductsGetCounter() {
        productsGetCounter.increment();
    }

    public <T> T recordProductsGetTime(Supplier<T> supplier) {
        long startTime = System.nanoTime();
        try {
            return supplier.get();
        } finally {
            productsGetTimer.record(System.nanoTime() - startTime, java.util.concurrent.TimeUnit.NANOSECONDS);
        }
    }

    public void incrementProductsPostCounter() {
        productsPostCounter.increment();
    }

    public <T> T recordProductsPostTime(Supplier<T> supplier) {
        long startTime = System.nanoTime();
        try {
            return supplier.get();
        } finally {
            productsPostTimer.record(System.nanoTime() - startTime, java.util.concurrent.TimeUnit.NANOSECONDS);
        }
    }

    public void incrementProductsProductIdPutCounter() {
        productsProductIdPutCounter.increment();
    }

    public <T> T recordProductsProductIdPutTime(Supplier<T> supplier) {
        long startTime = System.nanoTime();
        try {
            return supplier.get();
        } finally {
            productsProductIdPutTimer.record(System.nanoTime() - startTime, java.util.concurrent.TimeUnit.NANOSECONDS);
        }
    }
}
