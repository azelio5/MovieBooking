package com.sbbc.mb.moviebookingapp.controller;

import com.sbbc.mb.moviebookingapp.dto.TheaterDTO;
import com.sbbc.mb.moviebookingapp.entity.Theater;
import com.sbbc.mb.moviebookingapp.service.TheaterService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/theaters/")
public class TheaterController {
    private final TheaterService theaterService;

    public TheaterController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    @PostMapping("/addtheater")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Theater> addTheater(@RequestBody TheaterDTO theaterDTO) {
           return ResponseEntity.ok(theaterService.addTheater(theaterDTO));
    }

    @GetMapping("/gettheaterbylocation")
    public ResponseEntity<List<Theater>> getTheatersByLocation(@RequestParam String location) {
        return ResponseEntity.ok(theaterService.getTheatersByLocation(location));
    }

    @PatchMapping("/updatetheater/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Theater> updateTheater(@PathVariable UUID id, @RequestBody TheaterDTO theaterDTO) {
        return ResponseEntity.ok(theaterService.updateTheater(id, theaterDTO));
    }

    @DeleteMapping("/deletetheater/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTheater(@PathVariable UUID id) {
        theaterService.deleteTheater(id);
        return ResponseEntity.noContent().build();
    }
}
