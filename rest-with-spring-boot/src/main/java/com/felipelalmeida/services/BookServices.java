package com.felipelalmeida.services;

import com.felipelalmeida.data.dto.BookDTO;
import com.felipelalmeida.exception.RequiredObjectIsNullException;
import com.felipelalmeida.exception.ResourceNotFoundException;
import static com.felipelalmeida.mapper.ObjectMapper.*;

import com.felipelalmeida.model.Book;
import com.felipelalmeida.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServices {

    private Logger logger = LoggerFactory.getLogger(BookServices.class.getName());

    @Autowired
    BookRepository repository;

    public List<BookDTO> findAll(){
        logger.info("All Books Found!");

        var books = parseListObjects(repository.findAll(), BookDTO.class);
        // add hateoas
        return books;
    }

    public BookDTO findById(Long id){
        logger.info("Book Found!");
        
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
        var dto = parseObject(entity, BookDTO.class);
        // add hateoas
        return dto;
    }

    public BookDTO create(BookDTO book){
        if (book == null) throw new RequiredObjectIsNullException();
        logger.info("Registered Book!");

        var entity = parseObject(book, Book.class);
        var dto = parseObject(repository.save(entity), BookDTO.class);
        // add hateoas
        return dto;
    }

    public BookDTO update(BookDTO book){
        if (book == null) throw new RequiredObjectIsNullException();
        logger.info("Altered Book Record!");

        var entity = parseObject(book, Book.class);
        var dto = parseObject(repository.save(entity), BookDTO.class);
        // add hateoas
        return dto;
    }

    public void delete(Long id){
        logger.info("Deleting one Book!");

        Book entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
        repository.delete(entity);
    }
}
