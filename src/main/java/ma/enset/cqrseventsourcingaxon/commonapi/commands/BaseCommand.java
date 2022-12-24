package ma.enset.cqrseventsourcingaxon.commonapi.commands;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.springframework.context.annotation.Lazy;

public class BaseCommand<T> {
    @TargetAggregateIdentifier
    @Getter
    private T id;

    public BaseCommand( T id) {
        this.id = id;
    }

}
