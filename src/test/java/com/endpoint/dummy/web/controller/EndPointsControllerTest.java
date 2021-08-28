package com.endpoint.dummy.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.endpoint.dummy.domain.model.Movie;
import com.endpoint.dummy.domain.service.MovieDBConsumerService;
import com.endpoint.dummy.web.response.MovieResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class EndPointsControllerTest {

    @Mock
    private MovieDBConsumerService consumerService;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private EndPointsController endPointsController;

    @Test
    public void whenRootEndPointCalled_HelloWordMessageShouldBeReturned() {
        String message = endPointsController.responseWithHelloWorld();
        assertEquals("Hello World", message);
    }

    @Test
    public void whenConsumingLatestMovieCalled_MockObjectShouldBeReturned() {
        Movie movie = new Movie();
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setId(1000);
        movieResponse.setOriginalLanguage("en");
        movieResponse.setOriginalTitle("Latest movie title");

        Mockito.when(consumerService.getLastMovie())
                .thenReturn(movie);
        Mockito.when(modelMapper.map(movie, MovieResponse.class))
                .thenReturn(movieResponse);

        ResponseEntity<MovieResponse> movieResponseResponseEntity = endPointsController.getLatestMovie();
        MovieResponse latestMovieRespond = movieResponseResponseEntity.getBody();

        assertEquals(latestMovieRespond.getId(), movieResponse.getId());
        assertEquals(latestMovieRespond.getOriginalLanguage(), movieResponse.getOriginalLanguage());
        assertEquals(latestMovieRespond.getOriginalTitle(), movieResponse.getOriginalTitle());
    }
}
