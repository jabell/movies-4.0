package cat.udl.eps.softarch.hello.service;

import cat.udl.eps.softarch.hello.model.Greeting;
import cat.udl.eps.softarch.hello.model.User;

//our import
import cat.udl.eps.softarch.hello.model.Movie;

/**
 * Created by http://rhizomik.net/~roberto/
 */
public interface UserGreetingsService {
    User getUserAndGreetings(Long userId);

    Greeting addGreetingToUser(Greeting greeting);
    //our
    Movie addMovieToUser(Movie movie);
    Movie updateMovieFromUser(Movie updateMovie, Long movieId);

    Greeting updateGreetingFromUser(Greeting updateGreeting, Long greetingId);

    void removeGreetingFromUser(Long greetingId);
}
