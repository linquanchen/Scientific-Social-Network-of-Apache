package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.*;
import play.mvc.Controller;
import play.mvc.Result;
import util.Common;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.List;

/**
 * Created by baishi on 11/24/15.
 */
@Named
@Singleton
public class CommentController extends Controller {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;

    @Inject
    public CommentController(final CommentRepository commentRepository,
                             UserRepository userRepository, ReplyRepository replyRepository){
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.replyRepository = replyRepository;
    }

    public Result addReply() {
        JsonNode jsonNode = request().body().asJson();
        if (jsonNode == null){
            System.out.println("Reply not added, expecting Json data");
            return Common.badRequestWrapper("Reply not added, expecting Json data");
        }

        long commentId = jsonNode.path("commentId").asLong();
        long fromUserId = jsonNode.path("fromUserId").asLong();
        long toUserId = jsonNode.path("toUserId").asLong();
        long timestamp = jsonNode.path("timestamp").asLong();
        String content = jsonNode.path("content").asText();
        Comment comment = commentRepository.findOne(commentId);
        if(comment==null){
            System.out.println("Cannot find comment!");
            return Common.badRequestWrapper("Cannot find comment!");
        }
        User fromUser = userRepository.findOne(fromUserId);
        if(fromUser==null){
            System.out.println("Cannot find fromUser!");
            return Common.badRequestWrapper("Cannot find fromUser!");
        }
        User toUser = userRepository.findOne(toUserId);
        if(toUser==null){
            System.out.println("Cannot find toUser!");
            return Common.badRequestWrapper("Cannot find toUser!");
        }

        Reply reply = new Reply(fromUser, toUser, timestamp, content);
        Reply savedReply = replyRepository.save(reply);
        List<Reply> replyList = comment.getReplies();
        replyList.add(reply);
        comment.setReplies(replyList);
        commentRepository.save(comment);

        return ok(new Gson().toJson(savedReply.getId()));
    }

    public Result getReply(Long commentId) {
        try{
            if(commentId==null){
                System.out.println("Expecting comment id");
                return Common.badRequestWrapper("Expecting comment id");
            }

            List<Reply> replies = replyRepository.findByCommentId(commentId);

            Collections.sort(replies);

            return ok(new GsonBuilder().excludeFieldsWithModifiers(Modifier.PROTECTED).create().toJson(replies));
        } catch (Exception e){
            e.printStackTrace();
            return Common.badRequestWrapper("Fail to fetch replies");
        }
    }

    public Result thumbUp(Long commentId) {
        try{
            if(commentId==null){
                System.out.println("Expecting comment id");
                return Common.badRequestWrapper("Expecting comment id");
            }
            Comment comment = commentRepository.findOne(commentId);
            comment.setThumb(comment.getThumb() + 1);
            commentRepository.save(comment);
            return ok("{\"success\":\"Success!\"}");
        }catch (Exception e){
            e.printStackTrace();
            return Common.badRequestWrapper("Fail to fetch replies");
        }
    }

    public Result thumbDown(Long commentId) {
        try{
            if(commentId==null){
                System.out.println("Expecting comment id");
                return Common.badRequestWrapper("Expecting comment id");
            }
            Comment comment = commentRepository.findOne(commentId);
            comment.setThumb(comment.getThumb() - 1);
            commentRepository.save(comment);
            return ok("{\"success\":\"Success!\"}");
        }catch (Exception e){
            e.printStackTrace();
            return Common.badRequestWrapper("Fail to fetch replies");
        }   
    }
}
