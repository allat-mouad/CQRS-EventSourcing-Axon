package ma.enset.cqrseventsourcingaxon.commands.controllers;

import ma.enset.cqrseventsourcingaxon.commonapi.commands.CreateAccountCommand;
import ma.enset.cqrseventsourcingaxon.commonapi.commands.CreditAccountCommand;
import ma.enset.cqrseventsourcingaxon.commonapi.commands.DebitAccountCommand;
import ma.enset.cqrseventsourcingaxon.commonapi.dtos.CreateAccountRequestDTO;
import ma.enset.cqrseventsourcingaxon.commonapi.dtos.CreditAccountRequestDTO;
import ma.enset.cqrseventsourcingaxon.commonapi.dtos.DebitAccountRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;


@RestController
@RequestMapping(path="/commands")
public class AccountCommandController {
    private CommandGateway commandGateway;

    private EventStore eventStore;

    public AccountCommandController( CommandGateway commandGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }

    @PostMapping(path = "/accounts/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDTO accountRequestDTO){
        return commandGateway.send(
                new CreateAccountCommand(
                        UUID.randomUUID().toString(),
                        accountRequestDTO.getBalance(),
                        accountRequestDTO.getCurrency()));
    }
    @PutMapping(path = "/accounts/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO debitAccountRequestDTO){
        return commandGateway.send(new DebitAccountCommand(
                debitAccountRequestDTO.getAccountId(),
                debitAccountRequestDTO.getAmount(),
                debitAccountRequestDTO.getCurrency()
        ));
    }
    @PutMapping(path = "/accounts/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO creditAccountRequestDTO){
        return commandGateway.send(new CreditAccountCommand(
                creditAccountRequestDTO.getAccountId(),
                creditAccountRequestDTO.getAmount(),
                creditAccountRequestDTO.getCurrency()
        ));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception){
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/eventStore/{id}")
    public Stream eventStore (@PathVariable String id){
        return eventStore.readEvents(id).asStream();

    }
    }
