package ma.enset.cqrseventsourcingaxon.queries.repository;

import ma.enset.cqrseventsourcingaxon.queries.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AccountRepository extends JpaRepository<Account, String> {


}
