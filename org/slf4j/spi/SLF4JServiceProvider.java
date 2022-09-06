package org.slf4j.spi;

import org.slf4j.*;

public interface SLF4JServiceProvider
{
    ILoggerFactory getLoggerFactory();
    
    IMarkerFactory getMarkerFactory();
    
    MDCAdapter getMDCAdapter();
    
    String getRequesteApiVersion();
    
    void initialize();
}
