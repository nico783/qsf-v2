package com.adeo.qsf.service.mapper;

import com.adeo.qsf.domain.*;
import com.adeo.qsf.service.dto.CommandExampleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommandExample} and its DTO {@link CommandExampleDTO}.
 */
@Mapper(componentModel = "spring", uses = {ReceiptExampleMapper.class})
public interface CommandExampleMapper extends EntityMapper<CommandExampleDTO, CommandExample> {

    @Mapping(source = "receiptExample.id", target = "receiptExampleId")
    CommandExampleDTO toDto(CommandExample commandExample);

    @Mapping(source = "receiptExampleId", target = "receiptExample")
    CommandExample toEntity(CommandExampleDTO commandExampleDTO);

    default CommandExample fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommandExample commandExample = new CommandExample();
        commandExample.setId(id);
        return commandExample;
    }
}
