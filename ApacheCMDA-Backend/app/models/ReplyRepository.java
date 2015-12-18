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
public interface ReplyRepository extends CrudRepository<Reply, Long> {
    @Query(value = "select * from Reply where (ReplyId=?)", nativeQuery = true)
    List<Reply> findByCommentId(Long comment);
}
