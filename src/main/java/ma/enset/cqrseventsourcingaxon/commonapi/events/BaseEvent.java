package ma.enset.cqrseventsourcingaxon.commonapi.events;

import lombok.Getter;
import org.springframework.context.annotation.Lazy;

public class BaseEvent<T> {
    @Getter
    private T id;
    public BaseEvent( T id) {
        this.id = id;
    }
}
