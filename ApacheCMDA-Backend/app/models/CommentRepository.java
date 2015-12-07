package models;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

/**
 * Created by baishi on 11/24/15.
 */
@Named
@Singleton
public interface CommentRepository extends CrudRepository<Comment, Long> {
    @Query(value = "select * from Comment where (CommentId = ?)", nativeQuery = true)
    List<Comment> findByWorkflowId(Long workflow);
}
