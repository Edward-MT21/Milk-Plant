package com.module.Financial_Management.schedulers;

import com.module.Financial_Management.services.IFortnightClosureSchedulerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class FortnightClosureScheduler {

    private static final Logger logger = LoggerFactory.getLogger(FortnightClosureScheduler.class);
    private final IFortnightClosureSchedulerService iFortnightClosureSchedulerService;

    @Autowired
    public FortnightClosureScheduler(IFortnightClosureSchedulerService iFortnightClosureSchedulerService) {
        this.iFortnightClosureSchedulerService = iFortnightClosureSchedulerService;
    }

    @Scheduled(cron = "0 59 23 15,28,30,31 * ?")
    public void executeMilkSupplierFortnightClosure() {
        logger.info("Start executeMilkSupplierFortnightClosure");

        LocalDate today = LocalDate.now();
        boolean isDay15 = today.getDayOfMonth() == 15;
        boolean isLastDay = today.equals(today.withDayOfMonth(today.lengthOfMonth()));

        if (isDay15 || isLastDay) {
            logger.info("Today is a closure day. Executing the process.");
            iFortnightClosureSchedulerService.executeMilkSupplierFortnightClosure(today);
        } else {
            logger.info("Today is not a closure day. Skipping execution.");
        }

        logger.info("End executeMilkSupplierFortnightClosure");
    }

}
