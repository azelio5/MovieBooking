package com.sbbc.mb.moviebookingapp.controller;

import com.sbbc.mb.moviebookingapp.dto.ShowDTO;
import com.sbbc.mb.moviebookingapp.entity.Show;
import com.sbbc.mb.moviebookingapp.service.ShowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/show")
public class ShowController {
    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @PostMapping("/createshow")
    public ResponseEntity<Show> createShow(@RequestBody ShowDTO showDTO) {
        return ResponseEntity.ok(showService.createShow(showDTO));
    }

    @GetMapping("/allshows")
    public ResponseEntity<List<Show>> getAllShows(){
        return ResponseEntity.ok(showService.getAllShows());
    }

    @GetMapping("/getshowsbymovie/")
    public ResponseEntity<List<Show>> getShowByMovie(@RequestParam String movie){
        return ResponseEntity.ok(showService.getAllShowsByMovie(movie));
    }

    @GetMapping("/getshowsbytheater/")
    public ResponseEntity<List<Show>> getShowsByTheater(@RequestParam String theater){
        return ResponseEntity.ok(showService.getAllShowsByTheater(theater));
    }

    @PatchMapping("/updateshow/{showId}")
    public ResponseEntity<Show> updateShow(@PathVariable UUID showId, @RequestBody ShowDTO showDTO){
        return ResponseEntity.ok(showService.updateShow(showId, showDTO));
    }

    @DeleteMapping("/deleteshow/{id}")
    public ResponseEntity<Void> deleteShow(@PathVariable UUID id){
        showService.deleteShow(id);
        return ResponseEntity.noContent().build();
    }




}
