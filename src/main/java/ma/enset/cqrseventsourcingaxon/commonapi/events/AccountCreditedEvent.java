package ma.enset.cqrseventsourcingaxon.commonapi.events;

import lombok.Getter;
import ma.enset.cqrseventsourcingaxon.commonapi.enums.AccountStatus;

import java.math.BigDecimal;

public class AccountCreditedEvent extends BaseEvent<String> {
    @Getter  private  double amount;
    @Getter private  String currency;

    public AccountCreditedEvent(String id, double amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;
    }

}
