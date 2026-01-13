package com.felipelalmeida.repository;

import com.felipelalmeida.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
