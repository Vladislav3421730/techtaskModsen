package com.example.libraryapi.repositories;
import com.example.libraryapi.models.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookStatusRepository extends JpaRepository<BookStatus,Long> {
}
