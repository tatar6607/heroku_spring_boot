package com.bank.repository;

import com.bank.model.Recipient;
import org.springframework.data.repository.CrudRepository;

public interface RecipientRepo extends CrudRepository<Recipient, Long> {
     Recipient findByBankNumber(String bankNumber);
     Boolean existsByBankNumber(String bankNumber);
}
