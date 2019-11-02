package com.adeo.qsf.service.mapper;

import com.adeo.qsf.domain.CommandExample;
import com.adeo.qsf.service.dto.CommandReceiptDTO;
import org.springframework.stereotype.Component;

@Component
public class CommandReceiptMapper {

    public CommandReceiptDTO toDto(CommandExample commandExample) {
        return new CommandReceiptDTO(
            commandExample.getId(),
            commandExample.getDescription(),
            commandExample.getReceiptExample().getId(),
            commandExample.getReceiptExample().getState()
        );
    }

}
