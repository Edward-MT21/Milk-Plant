package com.module.Milk_Collection.mapper;

import com.module.Milk_Collection.model.dtos.MilkCollectionDetailsDto;
import com.module.Milk_Collection.model.dtos.MilkCollectionDto;
import com.module.Milk_Collection.model.entities.MilkCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MilkCollectionMapper {

    private static final Logger logger = LoggerFactory.getLogger(MilkCollectionMapper.class);

    public static MilkCollectionDto mapToMilkCollectionDto(MilkCollection milkCollection) {
        logger.debug("Start mapToMilkCollectionDto");

        logger.debug("End mapToMilkCollectionDto");
        return MilkCollectionDto.builder()
                .milkCollectionId(milkCollection.getMilkCollectionId()).
                milkSupplierId(milkCollection.getMilkSupplierId()).
                litersMilk(milkCollection.getLitersMilk()).
                build();
    }

    public static MilkCollection mapToMilkCollection(MilkCollectionDto milkCollectionDto) {
        logger.debug("Start mapToMilkCollection");

        MilkCollection milkCollection = new MilkCollection();
        milkCollection.setMilkCollectionId(milkCollectionDto.getMilkCollectionId());
        milkCollection.setMilkSupplierId(milkCollectionDto.getMilkSupplierId());
        milkCollection.setLitersMilk(milkCollectionDto.getLitersMilk());
        milkCollection.setCollectionDate(milkCollectionDto.getCollectionDate());

        logger.debug("End mapToMilkCollection");
        return milkCollection;

    }

}
