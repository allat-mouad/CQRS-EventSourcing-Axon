package ma.enset.cqrseventsourcingaxon.queries.Controllers;

import ma.enset.cqrseventsourcingaxon.queries.entities.Account;
import ma.enset.cqrseventsourcingaxon.queries.queries.GetAllAcounts;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/query/accounts")
public class AccountQueryController {
    private QueryGateway queryGateway;

    public AccountQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }
    @GetMapping("/list")
    public List<Account> AccountList(){
        return queryGateway.query(new GetAllAcounts(),ResponseTypes.multipleInstancesOf(Account.class)).join();

    }



}
