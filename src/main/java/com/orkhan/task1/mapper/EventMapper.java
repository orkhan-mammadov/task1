package com.orkhan.task1.mapper;

import com.orkhan.task1.model.dto.CreateSportEventDTO;
import com.orkhan.task1.model.entity.SportEvent;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EventMapper {
    SportEvent toEntity(CreateSportEventDTO dto);
}
