package com.soa.registry.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.core.AppenderBase;
import com.soa.registry.entity.LogEntry;
import com.soa.registry.repository.LogEntryRepository;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author Stanislav Hlova
 */
@Setter
@Component
public class CustomDatabaseAppender extends AppenderBase<ILoggingEvent> implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(CustomDatabaseAppender.class);
    private static ApplicationContext applicationContext; // Spring ApplicationContext
    private LogEntryRepository logEntryRepository;


    @Override
    protected void append(ILoggingEvent event) {
        try {
            if(logEntryRepository == null){
                logEntryRepository = applicationContext.getBean(LogEntryRepository.class);
            }
            String stackTrace = null;
            if (event.getThrowableProxy() != null) {
                StringBuilder stackTraceBuilder = new StringBuilder();
                for (StackTraceElementProxy element : event.getThrowableProxy().getStackTraceElementProxyArray()) {
                    stackTraceBuilder.append(element.getStackTraceElement().toString()).append(System.lineSeparator());
                }
                stackTrace = stackTraceBuilder.toString();
            }

            String serviceId = event.getMDCPropertyMap().get("service_id");

            // Construct and persist the log entry
            LogEntry logEntry = new LogEntry();
            logEntry.setLevel(event.getLevel().toString());
            logEntry.setLogger(event.getLoggerName());
            logEntry.setMessage(event.getFormattedMessage());
            logEntry.setStacktrace(stackTrace);
            logEntry.setServiceId(serviceId != null ? Long.parseLong(serviceId) : null);
            logEntryRepository.save(logEntry);

        } catch (Exception e) {
            logger.error("Failed to write log to database", e);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context; // Save the Spring ApplicationContext
    }
}
