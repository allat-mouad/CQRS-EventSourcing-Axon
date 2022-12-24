package ma.enset.cqrseventsourcingaxon.queries.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.enset.cqrseventsourcingaxon.commonapi.enums.AccountStatus;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    private String id;
    private double balance;
    private String currency;
    private AccountStatus status;
    @OneToMany(mappedBy = "account")
    private List<AccountTransaction> transactions;

}
