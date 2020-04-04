package com.app.aforo255.withdrawal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.aforo255.withdrawal.domain.Transaction;
import com.app.aforo255.withdrawal.producer.DepositEventProducer;
import com.app.aforo255.withdrawal.service.ITransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class DepositEventController {

	private Logger log = LoggerFactory.getLogger(DepositEventController.class);
	@Autowired
	private ITransactionService transactionService; 

	@Autowired
	DepositEventProducer depositEventProducer;
	@PostMapping("/v1/withdrawalEvent")
	public ResponseEntity<Transaction> postLibraryEvent(@RequestBody Transaction transactionEvent) throws JsonProcessingException{
		
		Transaction transSql = transactionService.save(transactionEvent);
		log.info("antes sendwithdrawalEvent_Approach3 ");
		depositEventProducer.sendDepositEvent_Approach3(transSql);
		log.info("despues sendwithdrawalEvent_Approach3 ");	
		
		return ResponseEntity.status(HttpStatus.CREATED).body(transSql);
		
	}
	
}
