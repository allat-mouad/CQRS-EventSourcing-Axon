package ma.enset.cqrseventsourcingaxon.commands.aggregates;

import lombok.extern.slf4j.Slf4j;
import ma.enset.cqrseventsourcingaxon.commonapi.commands.CreateAccountCommand;
import ma.enset.cqrseventsourcingaxon.commonapi.commands.CreditAccountCommand;
import ma.enset.cqrseventsourcingaxon.commonapi.commands.DebitAccountCommand;
import ma.enset.cqrseventsourcingaxon.commonapi.enums.AccountStatus;
import ma.enset.cqrseventsourcingaxon.commonapi.events.AccountCreatedEvent;
import ma.enset.cqrseventsourcingaxon.commonapi.events.AccountCreditedEvent;
import ma.enset.cqrseventsourcingaxon.commonapi.events.AccountDebitedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.context.annotation.Lazy;

import java.math.BigDecimal;
@Slf4j // lombok logging

@Aggregate
//snape shot of the actual state of my account

public class AccountAggregate  {
    @AggregateIdentifier
    private String id;
    private double balance;
    private String currency;
    private AccountStatus status;
    public AccountAggregate() {
    }
    //ce constructeur va etre appelé quand on va creer un compte
    @CommandHandler
    public AccountAggregate( CreateAccountCommand createAccountCommand) {

        if (createAccountCommand.getBalance()< 0) throw new IllegalArgumentException("Balance cannot be negative");

        AggregateLifecycle.apply(
                new AccountCreatedEvent(
                        createAccountCommand.getId(),
                        createAccountCommand.getBalance(),
                        createAccountCommand.getCurrency(),
                        AccountStatus.CREATED
                )
        );
    }
    //on recupere l'evenement et on le stocke dans le event store
    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        log.info(" test ");

        this.id=event.getId();
        this.balance=event.getBalance();
        this.currency=event.getCurrency();
        this.status=event.getStatus();
    }
    @CommandHandler
    public void on(CreditAccountCommand creditAccountCommand){
        AggregateLifecycle.apply(new AccountCreditedEvent(
                creditAccountCommand.getId(),
                creditAccountCommand.getAmount(),
                creditAccountCommand.getCurrency()
        ));
    }
   @EventSourcingHandler
    public void on(AccountCreditedEvent accountCreditedEvent){
        this.balance+=accountCreditedEvent.getAmount();

    }
    @CommandHandler
    public void on(DebitAccountCommand debitAccountCommand){
        if((this.balance>0)&&(this.balance<debitAccountCommand.getAmount())){
            throw new IllegalArgumentException("Insufficient Balance=>");
        } else
            AggregateLifecycle.apply(
                    new AccountDebitedEvent(
                            debitAccountCommand.getId(),
                            debitAccountCommand.getAmount(),
                            debitAccountCommand.getCurrency()
                    )
            );
    }
    @EventSourcingHandler
    public void on(AccountDebitedEvent accountDebitedEvent){
        this.balance-=accountDebitedEvent.getAmount();
    }


}

