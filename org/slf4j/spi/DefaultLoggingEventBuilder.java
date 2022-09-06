package org.slf4j.spi;

import org.slf4j.*;
import java.util.function.*;
import org.slf4j.event.*;

public class DefaultLoggingEventBuilder implements LoggingEventBuilder
{
    DefaultLoggingEvent loggingEvent;
    Logger logger;
    
    public DefaultLoggingEventBuilder(final Logger logger, final Level level) {
        this.logger = logger;
        this.loggingEvent = new DefaultLoggingEvent(level, logger);
    }
    
    public DefaultLoggingEventBuilder addMarker(final Marker marker) {
        this.loggingEvent.addMarker(marker);
        return this;
    }
    
    public DefaultLoggingEventBuilder setCause(final Throwable t) {
        this.loggingEvent.setThrowable(t);
        return this;
    }
    
    public DefaultLoggingEventBuilder addArgument(final Object p) {
        this.loggingEvent.addArgument(p);
        return this;
    }
    
    public DefaultLoggingEventBuilder addArgument(final Supplier<Object> objectSupplier) {
        this.loggingEvent.addArgument(objectSupplier.get());
        return this;
    }
    
    public void log(final String message) {
        this.loggingEvent.setMessage(message);
        this.innerLog(this.loggingEvent);
    }
    
    public void log(final String message, final Object arg) {
        this.loggingEvent.setMessage(message);
        this.loggingEvent.addArgument(arg);
        this.innerLog(this.loggingEvent);
    }
    
    public void log(final String message, final Object arg0, final Object arg1) {
        this.loggingEvent.setMessage(message);
        this.loggingEvent.addArgument(arg0);
        this.loggingEvent.addArgument(arg1);
        this.innerLog(this.loggingEvent);
    }
    
    public void log(final String message, final Object... args) {
        this.loggingEvent.setMessage(message);
        this.loggingEvent.addArguments(args);
        this.innerLog(this.loggingEvent);
    }
    
    private void innerLog(final LoggingEvent logggingEvent) {
        if (this.logger instanceof LoggingEventAware) {
            ((LoggingEventAware)this.logger).log(logggingEvent);
        }
        else {
            this.logViaPublicLoggerAPI(logggingEvent);
        }
    }
    
    private void logViaPublicLoggerAPI(final LoggingEvent logggingEvent) {
        final Object[] argArray = logggingEvent.getArgumentArray();
        final int argLen = (argArray == null) ? 0 : argArray.length;
        final Throwable t = logggingEvent.getThrowable();
        final int tLen = (t != null) ? 1 : 0;
        String msg = logggingEvent.getMessage();
        final Object[] combinedArguments = new Object[argLen + tLen];
        if (argArray != null) {
            System.arraycopy(argArray, 0, combinedArguments, 0, argLen);
        }
        if (t != null) {
            combinedArguments[argLen] = t;
        }
        msg = this.mergeMarkersAndKeyValuePairs(logggingEvent, msg);
        switch (logggingEvent.getLevel()) {
            case TRACE: {
                this.logger.trace(msg, combinedArguments);
                break;
            }
            case DEBUG: {
                this.logger.debug(msg, combinedArguments);
                break;
            }
            case INFO: {
                this.logger.info(msg, combinedArguments);
                break;
            }
            case WARN: {
                this.logger.warn(msg, combinedArguments);
                break;
            }
            case ERROR: {
                this.logger.error(msg, combinedArguments);
                break;
            }
        }
    }
    
    private String mergeMarkersAndKeyValuePairs(final LoggingEvent logggingEvent, final String msg) {
        StringBuilder sb = null;
        if (this.loggingEvent.getMarkers() != null) {
            sb = new StringBuilder();
            for (final Marker marker : logggingEvent.getMarkers()) {
                sb.append(marker);
                sb.append(' ');
            }
        }
        if (logggingEvent.getKeyValuePairs() != null) {
            if (sb == null) {
                sb = new StringBuilder();
            }
            for (final KeyValuePair kvp : logggingEvent.getKeyValuePairs()) {
                sb.append(kvp.key);
                sb.append('=');
                sb.append(kvp.value);
                sb.append(' ');
            }
        }
        if (sb != null) {
            sb.append(msg);
            return sb.toString();
        }
        return msg;
    }
    
    public void log(final Supplier<String> messageSupplier) {
        if (messageSupplier == null) {
            this.log((String)null);
        }
        else {
            this.log(messageSupplier.get());
        }
    }
    
    public DefaultLoggingEventBuilder addKeyValue(final String key, final Object value) {
        this.loggingEvent.addKeyValue(key, value);
        return this;
    }
    
    public DefaultLoggingEventBuilder addKeyValue(final String key, final Supplier<Object> value) {
        this.loggingEvent.addKeyValue(key, value.get());
        return this;
    }
}
