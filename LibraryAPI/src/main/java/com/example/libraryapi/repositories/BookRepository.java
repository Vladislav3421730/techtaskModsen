package com.example.libraryapi.repositories;

import com.example.libraryapi.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    Optional<Book> findByISBN(String ISBN);

    @Query("SELECT b FROM Book b " +
            "LEFT JOIN b.bookStatus bs " +
            "WHERE bs.borrowedAt IS NULL OR CURRENT_TIMESTAMP NOT BETWEEN bs.borrowedAt AND bs.dueDate")
    List<Book> findAllFree();

}
