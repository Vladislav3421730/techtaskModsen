package com.example.libraryapi.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_status_id_seq")
    @SequenceGenerator(name = "book_status_id_seq", sequenceName = "book_status_id_seq", allocationSize = 1)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "borrowed_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime borrowedAt;

    @Column(name = "due_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dueDate;

    @OneToOne(cascade = CascadeType.REFRESH,fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;

    @PrePersist
    private void init() {
        this.borrowedAt = LocalDateTime.now();
        this.dueDate = LocalDateTime.now().plusDays(7);
    }
}
