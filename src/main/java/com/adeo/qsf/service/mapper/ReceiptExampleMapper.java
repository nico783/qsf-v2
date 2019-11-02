package com.adeo.qsf.service.mapper;

import com.adeo.qsf.domain.*;
import com.adeo.qsf.service.dto.ReceiptExampleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReceiptExample} and its DTO {@link ReceiptExampleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ReceiptExampleMapper extends EntityMapper<ReceiptExampleDTO, ReceiptExample> {



    default ReceiptExample fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReceiptExample receiptExample = new ReceiptExample();
        receiptExample.setId(id);
        return receiptExample;
    }
}
