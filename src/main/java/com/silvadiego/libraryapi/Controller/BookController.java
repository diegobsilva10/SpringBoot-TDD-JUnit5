package com.silvadiego.libraryapi.Controller;

import com.silvadiego.libraryapi.DTO.BookDTO;
import com.silvadiego.libraryapi.Exceptions.ApiErrors;
import com.silvadiego.libraryapi.Exceptions.BusinessException;
import com.silvadiego.libraryapi.Model.Book;
import com.silvadiego.libraryapi.Service.BookService;
import org.modelmapper.ModelMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books/")
public class BookController {

    private BookService service;
    private ModelMapper modelMapper;

    public BookController (BookService service, ModelMapper mapper) {
        this.service = service;
        this.modelMapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@RequestBody @Valid BookDTO dto){

        Book entity = modelMapper.map(dto, Book.class);
        entity = service.save(entity);

        return modelMapper.map(entity, BookDTO.class);

    }

    @GetMapping("{id}")
    public BookDTO get(@PathVariable Long id){

        return service
                .getById(id)
                .map(book -> modelMapper.map(book, BookDTO.class) )
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") Long id) throws IllegalAccessException {
        Book book = service.getById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        service.delete(book);
    }

    @PutMapping("{id}")
    public BookDTO update (@PathVariable Long id, @RequestBody @Valid BookDTO dto) throws IllegalAccessException {
        Book book = service.getById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        book.setAuthor(dto.getAuthor());
        book.setTitle(dto.getTitle());
        service.update(book);
        return modelMapper.map(book,BookDTO.class);
    }

    @GetMapping
    public Page<BookDTO> find(BookDTO dto, Pageable pageableRequest){
       Book filter =  modelMapper.map(dto, Book.class);
       Page<Book> result =  service.find(filter, pageableRequest);
        List<BookDTO> list = result.getContent()
                .stream()
                .map(entity -> modelMapper.map(entity, BookDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<BookDTO>(list, pageableRequest, result.getTotalElements());

    }
}
