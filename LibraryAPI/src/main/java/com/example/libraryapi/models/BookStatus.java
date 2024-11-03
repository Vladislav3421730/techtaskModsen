package com.example.libraryapi.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_status_id_seq")
    @SequenceGenerator(name = "book_status_id_seq", sequenceName = "book_status_id_seq", allocationSize = 1)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "borrowed_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime borrowedAt;

    @Column(name = "due_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime dueDate;

    @OneToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @PrePersist
    private void init() {
        this.borrowedAt = LocalDateTime.now();
        this.dueDate = LocalDateTime.now().plusDays(7);
    }
}
