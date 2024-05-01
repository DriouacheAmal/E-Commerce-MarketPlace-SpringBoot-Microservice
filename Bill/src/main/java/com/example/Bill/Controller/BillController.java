package com.example.Bill.Controller;

import com.example.Bill.Entity.Bill;
import com.example.Bill.Service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bills")
public class BillController {
    @Autowired
    private BillService billService;

    // Endpoint to create a new bill
    @PostMapping("/")
    public Bill createBill(@RequestBody Bill bill) {
        return billService.createBill(bill);
    }

    // Endpoint to retrieve all bills
    @GetMapping("/")
    public List<Bill> getAllBills() {
        return billService.getAllBills();
    }

    // Endpoint to retrieve a bill by ID
    @GetMapping("/{id}")
    public Bill getBillById(@PathVariable Long id) {
        return billService.getBillById(id);
    }

    // Endpoint to update a bill
    @PutMapping("/{id}")
    public Bill updateBill(@PathVariable Long id, @RequestBody Bill updatedBill) {
        return billService.updateBill(id, updatedBill);
    }

    // Endpoint to delete a bill
    @DeleteMapping("/{id}")
    public void deleteBill(@PathVariable Long id) {
        billService.deleteBill(id);
    }
}
