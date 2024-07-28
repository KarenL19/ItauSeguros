package com.store.itauseguros.api;

import com.store.itauseguros.metrics.MetricsService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MetricsServiceTest {

    private MetricsService metricsService;
    private Counter productsGetCounter;
    private Timer productsGetTimer;
    private Counter productsPostCounter;
    private Timer productsPostTimer;
    private Counter productsProductIdPutCounter;
    private Timer productsProductIdPutTimer;

    @BeforeEach
    public void setUp() {
        MeterRegistry meterRegistry = Mockito.mock(MeterRegistry.class);
        productsGetCounter = Mockito.mock(Counter.class);
        productsGetTimer = Mockito.mock(Timer.class);
        productsPostCounter = Mockito.mock(Counter.class);
        productsPostTimer = Mockito.mock(Timer.class);
        productsProductIdPutCounter = Mockito.mock(Counter.class);
        productsProductIdPutTimer = Mockito.mock(Timer.class);

        // Configura o mock do MeterRegistry para retornar os mocks apropriados
        when(meterRegistry.counter("products_get_total")).thenReturn(productsGetCounter);
        when(meterRegistry.timer("products_get_timer")).thenReturn(productsGetTimer);
        when(meterRegistry.counter("products_post_total")).thenReturn(productsPostCounter);
        when(meterRegistry.timer("products_post_timer")).thenReturn(productsPostTimer);
        when(meterRegistry.counter("products_product_id_put_total")).thenReturn(productsProductIdPutCounter);
        when(meterRegistry.timer("products_product_id_put_timer")).thenReturn(productsProductIdPutTimer);

        metricsService = new MetricsService(meterRegistry);
    }

    @Test
    public void testIncrementProductsGetCounter() {
        metricsService.incrementProductsGetCounter();
        verify(productsGetCounter).increment();
    }

    @Test
    public void testRecordProductsGetTime() {
        Supplier<String> supplier = () -> "test";
        metricsService.recordProductsGetTime(supplier);
        verify(productsGetTimer).record(anyLong(), eq(TimeUnit.NANOSECONDS));
    }

    @Test
    public void testIncrementProductsPostCounter() {
        metricsService.incrementProductsPostCounter();
        verify(productsPostCounter).increment();
    }

    @Test
    public void testRecordProductsPostTime() {
        Supplier<String> supplier = () -> "test";
        metricsService.recordProductsPostTime(supplier);
        verify(productsPostTimer).record(anyLong(), eq(TimeUnit.NANOSECONDS));
    }

    @Test
    public void testIncrementProductsProductIdPutCounter() {
        metricsService.incrementProductsProductIdPutCounter();
        verify(productsProductIdPutCounter).increment();
    }

    @Test
    public void testRecordProductsProductIdPutTime() {
        Supplier<String> supplier = () -> "test";
        metricsService.recordProductsProductIdPutTime(supplier);
        verify(productsProductIdPutTimer).record(anyLong(), eq(TimeUnit.NANOSECONDS));
    }
}