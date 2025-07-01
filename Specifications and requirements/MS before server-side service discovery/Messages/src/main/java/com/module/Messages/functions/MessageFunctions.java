package com.module.Messages.functions;


import com.module.Messages.dto.MilkCollectionMsgDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageFunctions {

    private static final Logger log = LoggerFactory.getLogger(MessageFunctions.class);

    @Bean
    public Function<MilkCollectionMsgDto,MilkCollectionMsgDto> email() {
        return milkCollectionMsgDto -> {
            log.info("Sending email with the details : " +  milkCollectionMsgDto.toString());
            return milkCollectionMsgDto;
        };
    }

    @Bean
    public Function<MilkCollectionMsgDto,Long> sms() {
        return milkCollectionMsgDto -> {
            log.info("Sending sms with the details : " +  milkCollectionMsgDto.toString());
            return milkCollectionMsgDto.milkSupplierId();
        };
    }
}
