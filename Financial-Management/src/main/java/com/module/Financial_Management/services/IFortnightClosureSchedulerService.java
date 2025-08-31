package com.module.Financial_Management.services;

import java.time.LocalDate;

public interface IFortnightClosureSchedulerService {
    void executeMilkSupplierFortnightClosure(LocalDate closureDate);
}
