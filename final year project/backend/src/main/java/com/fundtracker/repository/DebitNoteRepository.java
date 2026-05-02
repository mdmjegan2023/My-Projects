package com.fundtracker.repository;

import com.fundtracker.model.DebitNote;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DebitNoteRepository extends MongoRepository<DebitNote, String> {
    Optional<DebitNote> findByIDbtCode(String iDbtCode);
    long count();
    List<DebitNote> findBySupName(String supName);
    List<DebitNote> findByEntryDateBetween(LocalDate from, LocalDate to);
    List<DebitNote> findByEntryDateBetweenAndSupName(LocalDate from, LocalDate to, String supName);
}
