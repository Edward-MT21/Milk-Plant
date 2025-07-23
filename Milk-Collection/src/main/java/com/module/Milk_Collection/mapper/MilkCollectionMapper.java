package com.module.Milk_Collection.mapper;

import com.module.Milk_Collection.model.dtos.MilkCollectionDetailsDto;
import com.module.Milk_Collection.model.dtos.MilkCollectionDto;
import com.module.Milk_Collection.model.entities.MilkCollection;

public class MilkCollectionMapper {

    public static MilkCollectionDto mapToMilkCollectionDto(MilkCollection milkCollection) {

        return MilkCollectionDto.builder()
                .milkCollectionId(milkCollection.getMilkCollectionId()).
                milkSupplierId(milkCollection.getMilkSupplierId()).
                litersMilk(milkCollection.getLitersMilk()).
                build();
    }

    public static MilkCollection mapToMilkCollection(MilkCollectionDto milkCollectionDto) {
        MilkCollection milkCollection = new MilkCollection();
        milkCollection.setMilkCollectionId(milkCollectionDto.getMilkCollectionId());
        milkCollection.setMilkSupplierId(milkCollectionDto.getMilkSupplierId());
        milkCollection.setLitersMilk(milkCollectionDto.getLitersMilk());
        return milkCollection;

    }

}
