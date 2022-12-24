package ma.enset.cqrseventsourcingaxon.queries.repository;

import ma.enset.cqrseventsourcingaxon.queries.entities.Account;
import ma.enset.cqrseventsourcingaxon.queries.entities.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<AccountTransaction, Long> {


}
