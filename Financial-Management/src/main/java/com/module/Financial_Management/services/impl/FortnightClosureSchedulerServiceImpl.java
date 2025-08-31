package com.module.Financial_Management.services.impl;

import com.module.Financial_Management.schedulers.FortnightClosureScheduler;
import com.module.Financial_Management.services.IFortnightClosureSchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FortnightClosureSchedulerServiceImpl implements IFortnightClosureSchedulerService {

    private final FortnightClosureScheduler fortnightClosureScheduler;

    public void executeMilkSupplierFortnightClosure(LocalDate closureDate) {
        fortnightClosureScheduler.executeMilkSupplierFortnightClosure();
    }


}
