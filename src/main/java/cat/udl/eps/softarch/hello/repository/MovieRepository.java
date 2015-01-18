package cat.udl.eps.softarch.hello.repository;

import cat.udl.eps.softarch.hello.model.Greeting;
// our import
import cat.udl.eps.softarch.hello.model.Movie;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by http://rhizomik.net/~roberto/
 */

public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {

    // PagingAndSortingRepository provides:
    // exists(ID id), delete(T entity), findAll(Pageable), findAll(Sort), findOne(ID id), save(T entity),...
    // http://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html

    List<Movie> findByContentContaining(@Param("content") String content);
}
