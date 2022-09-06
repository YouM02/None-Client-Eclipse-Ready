package org.slf4j.spi;

import org.slf4j.event.*;

public interface LoggingEventAware
{
    void log(final LoggingEvent p0);
}
