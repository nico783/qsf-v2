package com.adeo.qsf.web.rest;

import com.adeo.qsf.service.CommandReceiptQueryService;
import com.adeo.qsf.service.dto.CommandReceiptCriteria;
import com.adeo.qsf.service.dto.CommandReceiptDTO;
import io.github.jhipster.web.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommandReceiptController {

    private final Logger log = LoggerFactory.getLogger(CommandReceiptController.class);

    private final CommandReceiptQueryService commandReceiptQueryService;

    public CommandReceiptController(CommandReceiptQueryService commandReceiptQueryService) {
        this.commandReceiptQueryService = commandReceiptQueryService;
    }

    @GetMapping("/command-receipt")
    public ResponseEntity<List<CommandReceiptDTO>> getAllCommandReceipt(CommandReceiptCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Command-Receipt by criteria: {}", criteria);
        Page<CommandReceiptDTO> page = commandReceiptQueryService.findByCriteria(criteria, pageable);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.path("/api/command-receipt"), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
