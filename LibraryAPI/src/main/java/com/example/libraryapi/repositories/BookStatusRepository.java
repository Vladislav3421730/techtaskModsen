package com.example.libraryapi.repositories;
import com.example.libraryapi.models.BookStatus;
import org.springframework.data.repository.CrudRepository;


public interface BookStatusRepository extends CrudRepository<BookStatus,Long> {
}
