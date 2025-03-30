package com.onixbyte.clearledger.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Configuration class for scheduled tasks.
 * <p>
 * This class enables support for scheduled task execution in a Spring application. By using the
 * {@link EnableScheduling} annotation, it activates the scheduling infrastructure, allowing
 * methods annotated with {@code @Scheduled} to be executed according to their defined schedules.
 *
 * @author zihluwang
 */
@Configuration
@EnableScheduling
public class ScheduleConfig {
}
