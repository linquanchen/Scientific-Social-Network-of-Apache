package models;

import org.springframework.data.repository.CrudRepository;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Created by baishi on 11/24/15.
 */
@Named
@Singleton
public interface ReplyRepository extends CrudRepository<Reply, Long> {
}
