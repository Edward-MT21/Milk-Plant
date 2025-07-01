package com.module.Milk_Collection.services.client;

import org.springframework.stereotype.Component;

@Component
public class FinancialManagementFallback implements IFinancialManagementFeingClient{

    @Override
    public String getGreeting(String correlationId) {
        return null;
    }
}
