package com.example.realestate;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EnquiryRepository
        extends JpaRepository<Enquiry, Long> {
}