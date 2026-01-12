package com.felipelalmeida.controllers;

import com.felipelalmeida.data.dto.BookDTO;
import com.felipelalmeida.services.BookServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book/v1")
public class BookController {

    @Autowired
    private BookServices service;

    @GetMapping(value = "/all",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        }
    )
    public List<BookDTO> findAll(){
       return service.findAll();
    }

    @GetMapping(value = "/{id}",
        produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
        })
    public BookDTO findById(@PathVariable("id") Long id){
        return service.findById(id);
    }

    @PostMapping(
        produces = {
                MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE,
                MediaType.APPLICATION_YAML_VALUE
        },
        consumes = {
                MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE,
                MediaType.APPLICATION_YAML_VALUE
        }
    )
    public BookDTO create(@RequestBody BookDTO book){
        return service.create(book);
    }

    @PutMapping(
        produces = {
                MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE,
                MediaType.APPLICATION_YAML_VALUE
        },
        consumes = {
                MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE,
                MediaType.APPLICATION_YAML_VALUE
        }
    )
    public BookDTO update(@RequestBody BookDTO book){
        return service.update(book);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
