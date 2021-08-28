package com.endpoint.dummy.domain.service;

import com.endpoint.dummy.domain.model.Movie;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class MovieDBConsumerServiceTest {
    public final String url = "https://api.themoviedb.org/3/movie/latest?api_key=$cea3b7a0b210db1ea9f3707365849dd8&language=en-US";

    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private MovieDBConsumerService consumerService;

    @Before
    public void setUp() {
        consumerService.setUrl(url);
    }

    @Test
    public void whenLatestMovieCalled_MockObjectShouldBeReturned() {
        Movie movie = new Movie();
        movie.setId(1000);
        movie.setOriginalLanguage("en");
        movie.setOriginalTitle("Latest movie title");

        Mockito.when(restTemplate.getForObject(url, Movie.class))
                .thenReturn(movie);

        Movie latestMovie = consumerService.getLastMovie();

        Assert.assertEquals(latestMovie.getId(), movie.getId());
        Assert.assertEquals(latestMovie.getOriginalLanguage(), movie.getOriginalLanguage());
        Assert.assertEquals(latestMovie.getOriginalTitle(), movie.getOriginalTitle());
    }
}
