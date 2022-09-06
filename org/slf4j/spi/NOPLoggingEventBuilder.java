package org.slf4j.spi;

import org.slf4j.*;
import java.util.function.*;

public class NOPLoggingEventBuilder implements LoggingEventBuilder
{
    static final NOPLoggingEventBuilder SINGLETON;
    
    static {
        SINGLETON = new NOPLoggingEventBuilder();
    }
    
    public static LoggingEventBuilder singleton() {
        return NOPLoggingEventBuilder.SINGLETON;
    }
    
    @Override
    public LoggingEventBuilder addMarker(final Marker marker) {
        return singleton();
    }
    
    @Override
    public LoggingEventBuilder addArgument(final Object p) {
        return singleton();
    }
    
    @Override
    public LoggingEventBuilder addArgument(final Supplier<Object> objectSupplier) {
        return singleton();
    }
    
    @Override
    public LoggingEventBuilder addKeyValue(final String key, final Object value) {
        return singleton();
    }
    
    @Override
    public LoggingEventBuilder addKeyValue(final String key, final Supplier<Object> value) {
        return singleton();
    }
    
    @Override
    public LoggingEventBuilder setCause(final Throwable cause) {
        return singleton();
    }
    
    @Override
    public void log(final String message) {
    }
    
    @Override
    public void log(final Supplier<String> messageSupplier) {
    }
    
    @Override
    public void log(final String message, final Object arg) {
    }
    
    @Override
    public void log(final String message, final Object arg0, final Object arg1) {
    }
    
    @Override
    public void log(final String message, final Object... args) {
    }
}
