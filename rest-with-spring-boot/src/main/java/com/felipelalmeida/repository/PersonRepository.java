package com.felipelalmeida.repository;

import com.felipelalmeida.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
