package com.sbbc.mb.moviebookingapp.service;

import static com.sbbc.mb.moviebookingapp.util.Util.applyIfNotNull;

import com.sbbc.mb.moviebookingapp.dto.TheaterDTO;
import com.sbbc.mb.moviebookingapp.entity.Theater;
import com.sbbc.mb.moviebookingapp.exception.NotFoundException;
import com.sbbc.mb.moviebookingapp.repository.TheaterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TheaterService {
    private final TheaterRepository theaterRepository;

    public TheaterService(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }

    public Theater addTheater(TheaterDTO theaterDTO) {
        Theater theater = Theater.builder()
                .name(theaterDTO.getName())
                .location(theaterDTO.getLocation())
                .capacity(theaterDTO.getCapacity())
                .screenType(theaterDTO.getScreenType())
                .build();
        return theaterRepository.save(theater);
    }

    public List<Theater> getTheatersByLocation(String location) {
        return theaterRepository.findTheaterByLocation(location).orElseThrow(()-> new NotFoundException("No theater is found in location: " + location));
    }

    public Theater updateTheater(UUID id, TheaterDTO theaterDTO) {
        Theater existingTheater = theaterRepository.findById(id).orElseThrow(() -> new NotFoundException("There is no such theater with id: " + id));

        Theater.TheaterBuilder builder = existingTheater.toBuilder();
        applyIfNotNull(builder::name, theaterDTO.getName());
        applyIfNotNull(builder::location, theaterDTO.getLocation());
        applyIfNotNull(builder::capacity, theaterDTO.getCapacity());
        applyIfNotNull(builder::screenType, theaterDTO.getScreenType());

        Theater updatedTheater = builder.build();

        return theaterRepository.save(updatedTheater);
    }

    public void deleteTheater(UUID id) {
       if(!theaterRepository.existsById(id)) {
           throw new NotFoundException("There is no such theater with id: " + id);
       }
       theaterRepository.deleteById(id);
    }

}
