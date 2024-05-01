package com.example.Bill.Entity;

import com.example.Bill.Enum.BillType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Bills")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    @Column(nullable = false, name = "account_number")
    private String accountNumber;
    @Column(nullable = false)
    private LocalDate billingDate;
    @Column(nullable = false)
    private LocalDate dueDate;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BillType type;
    @Column(nullable = false)
    private Double amount;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private String status; // e.g., "PENDING", "PAID", "OVERDUE"
    @ElementCollection
    @OneToMany(mappedBy = "bill", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Long> stats = new ArrayList<>();
}
