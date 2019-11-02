package com.adeo.qsf.repository;

import com.adeo.qsf.domain.CommandExample;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CommandExample entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommandExampleRepository extends JpaRepository<CommandExample, Long>, JpaSpecificationExecutor<CommandExample> {

}
