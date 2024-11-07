package com.example.libraryapi.repositories;
import com.example.libraryapi.models.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookStatusRepository extends JpaRepository<BookStatus,Long> {
}
