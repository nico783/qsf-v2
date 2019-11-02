package com.adeo.qsf.repository;

import com.adeo.qsf.domain.ReceiptExample;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ReceiptExample entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReceiptExampleRepository extends JpaRepository<ReceiptExample, Long>, JpaSpecificationExecutor<ReceiptExample> {

}
