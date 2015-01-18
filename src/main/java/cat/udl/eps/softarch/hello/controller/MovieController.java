package cat.udl.eps.softarch.hello.controller;

import cat.udl.eps.softarch.hello.model.Greeting;
import cat.udl.eps.softarch.hello.repository.GreetingRepository;
//import the repository and the model
import cat.udl.eps.softarch.hello.repository.MovieRepository;
import cat.udl.eps.softarch.hello.model.Movie;

import cat.udl.eps.softarch.hello.service.UserGreetingsService;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;

/**
 * Created by http://rhizomik.net/~roberto/
 */

@Controller
@RequestMapping(value = "/movies")
public class MovieController {
    final Logger logger = LoggerFactory.getLogger(MovieController.class);

    @Autowired MovieRepository   movieRepository;
    @Autowired UserGreetingsService userGreetingsService;

// LIST
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Movie> list(@RequestParam(required=false, defaultValue="0") int page,
                                   @RequestParam(required=false, defaultValue="10") int size) {
        PageRequest request = new PageRequest(page, size);
        return movieRepository.findAll(request).getContent();
    }
    @RequestMapping(method = RequestMethod.GET, produces = "text/html")
    public ModelAndView listHTML(@RequestParam(required=false, defaultValue="0") int page,
                                 @RequestParam(required=false, defaultValue="10") int size) {
        return new ModelAndView("movies", "movies", list(page, size));
    }

// RETRIEVE
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Movie retrieve(@PathVariable("id") Long id) {
        logger.info("Retrieving movie number {}", id);
        Preconditions.checkNotNull(movieRepository.findOne(id), "Movie with id %s not found", id);
        return movieRepository.findOne(id);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "text/html")
    public ModelAndView retrieveHTML(@PathVariable( "id" ) Long id) {
        return new ModelAndView("movie", "movie", retrieve(id));
    }

// CREATE
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Movie create(@Valid @RequestBody Movie movie, HttpServletResponse response) {
        logger.info("Creating movie with content'{}'", movie.getContent());
        Movie newMovie = userGreetingsService.addMovieToUser(movie);
        response.setHeader("Location", "/movies/" + newMovie.getId());
        return newMovie;
    }
    @RequestMapping(method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded", produces="text/html")
    public String createHTML(@Valid @ModelAttribute("movie") Movie movie, BindingResult binding, HttpServletResponse response) {
        if (binding.hasErrors()) {
            logger.info("Validation error: {}", binding);
            return "form";
        }
        return "redirect:/movies/"+create(movie, response).getId();
    }
    // Create form
    @RequestMapping(value = "/form", method = RequestMethod.GET, produces = "text/html")
    public ModelAndView createForm() {
        logger.info("Generating form for movie creation");
        Movie emptyMovie = new Movie();
        emptyMovie.setDate(new Date());
        return new ModelAndView("form", "movie", emptyMovie);
    }

// UPDATE
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Movie update(@PathVariable("id") Long id, @Valid @RequestBody Movie movie) {
        logger.info("Updating movie {}, new content is '{}'", id, movie.getContent());
        Preconditions.checkNotNull(movieRepository.findOne(id), "Movie with id %s not found", id);
        return userGreetingsService.updateMovieFromUser(movie, id);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/x-www-form-urlencoded")
    @ResponseStatus(HttpStatus.OK)
    public String updateHTML(@PathVariable("id") Long id, @Valid @ModelAttribute("movie") Movie movie,
                         BindingResult binding) {
        if (binding.hasErrors()) {
            logger.info("Validation error: {}", binding);
            return "form";
        }
        return "redirect:/movies/"+update(id, movie).getId();
    }
    // Update form
    @RequestMapping(value = "/{id}/form", method = RequestMethod.GET, produces = "text/html")
    public ModelAndView updateForm(@PathVariable("id") Long id) {
        logger.info("Generating form for updating movie number {}", id);
        Preconditions.checkNotNull(movieRepository.findOne(id), "Movie with id %s not found", id);
        return new ModelAndView("form", "movie", movieRepository.findOne(id));
    }

// DELETE
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        logger.info("Deleting movie number {}", id);
        Preconditions.checkNotNull(movieRepository.findOne(id), "Movie with id %s not found", id);
        userGreetingsService.removeGreetingFromUser(id);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    @ResponseStatus(HttpStatus.OK)
    public String deleteHTML(@PathVariable("id") Long id) {
        delete(id);
        return "redirect:/movies";
    }
}
