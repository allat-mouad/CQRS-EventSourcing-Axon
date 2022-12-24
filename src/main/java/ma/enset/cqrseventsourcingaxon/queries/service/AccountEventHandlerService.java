package ma.enset.cqrseventsourcingaxon.queries.service;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import ma.enset.cqrseventsourcingaxon.commonapi.events.AccountCreatedEvent;
import ma.enset.cqrseventsourcingaxon.queries.entities.Account;
import ma.enset.cqrseventsourcingaxon.queries.queries.GetAllAcounts;
import ma.enset.cqrseventsourcingaxon.queries.repository.AccountRepository;
import ma.enset.cqrseventsourcingaxon.queries.repository.TransactionRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Slf4j // lombok logging

@Service
@Transactional
public class AccountEventHandlerService {
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

public AccountEventHandlerService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }
    @EventHandler // @Event Sourcing handler is for Aggregate
    public void on(AccountCreatedEvent accountCreatedEvent){
        log.info("Event Received: **| AccountCreatedEvent |** ");
        Account account =  Account
                .builder()
                .id(accountCreatedEvent.getId())
                .balance(accountCreatedEvent.getBalance())
                .currency(accountCreatedEvent.getCurrency())
                .status(accountCreatedEvent.getStatus())
                .build();
        Account savedAccount = accountRepository.save(account);
        log.info(String.format("New Account Created [ID: %s]", savedAccount.getId()));
    }
    @QueryHandler
    public List<Account> on(GetAllAcounts getAllAcounts){
        return accountRepository.findAll();
    }


}
