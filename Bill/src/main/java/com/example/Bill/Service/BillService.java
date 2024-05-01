package com.example.Bill.Service;

import com.example.Bill.Entity.Bill;
import com.example.Bill.Repository.BillRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.xml.catalog.Catalog;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class BillService {
    @Autowired
    private final BillRepository billRepository;

    @Autowired
    public BillService(BillRepository billRepository) {

        this.billRepository = billRepository;
    }

    // Method to create a new bill
    // is the product
    public Bill createBill(Bill bill) {
        LocalDate startDate = bill.getReservation().getStartDate();
        LocalDate endDate = bill.getReservation().getEndDate();
        Long carId = bill.getReservation().getCarId(); // Assuming carId is associated with the reservation

        // Fetch car details including rental rate from the database // Order ????
        Catalogue catalogue = catalogueRepository.findById(carId).orElseThrow(() -> new IllegalArgumentException("Car not found"));
        double carRentalRatePerDay = catalogue.getRentalRate(); // Assuming rental rate is stored in Car entity

        // Calculate total based on reservation period and car rental rate
        long reservationPeriodInDays = endDate.toEpochDay() - startDate.toEpochDay();
        double total = reservationPeriodInDays * carRentalRatePerDay;

        // Set total and due date in the bill
        bill.setTotal(total);
        bill.setDueDate(endDate.plusDays(7)); // Due date is 7 days after end date of reservation

        // Save the bill in the database
        return billRepository.save(bill);
    }


    // Method to retrieve all bills
    public List<Bill> getAllBills() {
        // Simply fetch all bills from the database
        return billRepository.findAll();
    }

    // Method to retrieve a bill by ID
    public Bill getBillById(Long id) {
        // Retrieve the bill by its ID from the database
        Optional<Bill> optionalBill = billRepository.findById(id);

        // Check if the bill exists in the database
        if (optionalBill.isPresent()) {
            // If the bill exists, return it
            return optionalBill.get();
        } else {
            // If the bill does not exist, return null
            return null;
        }
    }

    // Method to update a bill
    public Bill updateBill(Long id, Bill updatedBill) {
        // Retrieve the existing bill from the database by its ID
        Bill existingBill = billRepository.findById(id).orElse(null);

        // If the bill with the given ID doesn't exist, return null
        if (existingBill == null) {
            return null;
        }

        // Update the attributes of the existing bill with the attributes of the updated bill
        // For example:
        // existingBill.setTotalAmount(updatedBill.getTotalAmount());
        // existingBill.setDueDate(updatedBill.getDueDate());

        // Save the updated bill to the database
        return billRepository.save(existingBill);
    }

    // Method to delete a bill
    public void deleteBill(Long id) {
        // Delete the bill from the database by its ID
        billRepository.deleteById(id);
    }

// Example methods for calculating total amount and due date:
// private BigDecimal calculateTotalAmount(Bill bill) {
//     // Implement logic to calculate the total amount for the bill
//     return ...;
// }
//
// private Date calculateDueDate(Bill bill) {
//     // Implement logic to calculate the due date for the bill
//     return ...;

}
