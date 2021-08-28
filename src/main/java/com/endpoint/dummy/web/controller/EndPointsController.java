package com.endpoint.dummy.web.controller;

import com.endpoint.dummy.domain.model.Movie;
import com.endpoint.dummy.domain.service.MovieDBConsumerService;
import com.endpoint.dummy.web.response.MovieResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/endpoint")
public class EndPointsController {

    @Autowired
    MovieDBConsumerService movieDBConsumerService;

    ModelMapper modelMapper;

    EndPointsController() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @GetMapping
    public String responseWithHelloWorld() {
        return "Hello World";
    }

    @GetMapping(path = "/consuming-latest-movie",
            produces = {
                MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE
            })
    public ResponseEntity<MovieResponse> getLatestMovie() {
        Movie movie = movieDBConsumerService.getLastMovie();
        MovieResponse movieResponse = modelMapper.map(movie, MovieResponse.class);
        return new ResponseEntity<MovieResponse>(movieResponse, HttpStatus.OK);
    }
}
