package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import models.*;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
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
            return badRequest("Reply not added, expecting Json data");
        }

        long commentId = jsonNode.path("commentId").asLong();
        long fromUserId = jsonNode.path("fromUserId").asLong();
        long toUserId = jsonNode.path("toUserId").asLong();
        long timestamp = jsonNode.path("timestamp").asLong();
        String content = jsonNode.path("content").asText();
        Comment comment = commentRepository.findOne(commentId);
        if(comment==null){
            System.out.println("Cannot find comment!");
            return badRequest("Cannot find comment!");
        }
        User fromUser = userRepository.findOne(fromUserId);
        if(fromUser==null){
            System.out.println("Cannot find fromUser!");
            return badRequest("Cannot find fromUser!");
        }
        User toUser = userRepository.findOne(toUserId);
        if(toUser==null){
            System.out.println("Cannot find toUser!");
            return badRequest("Cannot find toUser!");
        }

        Reply reply = new Reply(fromUser, toUser, timestamp, content);
        Reply savedReply = replyRepository.save(reply);
        List<Reply> replyList = comment.getReplies();
        replyList.add(reply);
        comment.setReplies(replyList);
        commentRepository.save(comment);

        return ok(new Gson().toJson(savedReply.getId()));
    }

    public Result replyReply() {
        JsonNode jsonNode = request().body().asJson();
        if(jsonNode == null){
            System.out.println("Reply not added, expecting Json data");
            return badRequest("Reply not added, expecting Json data");
        }

        long replyId = jsonNode.path("replyId").asLong();
        long fromUserId = jsonNode.path("fromUserId").asLong();
        long toUserId = jsonNode.path("toUserId").asLong();
        long timestamp = jsonNode.path("timestamp").asLong();
        String content = jsonNode.path("content").asText();
        Reply reply = replyRepository.findOne(replyId);
        if(reply==null){
            System.out.println("Cannot find comment!");
            return badRequest("Cannot find comment!");
        }
        User fromUser = userRepository.findOne(fromUserId);
        if(fromUser==null){
            System.out.println("Cannot find fromUser!");
            return badRequest("Cannot find fromUser!");
        }
        User toUser = userRepository.findOne(toUserId);
        if(toUser==null){
            System.out.println("Cannot find toUser!");
            return badRequest("Cannot find toUser!");
        }

        Reply reReply = new Reply(fromUser, toUser, timestamp, content);
        Reply savedReply = replyRepository.save(reReply);
        List<Reply> replies = reply.getReplies();
        replies.add(reReply);
        replyRepository.save(reply);

        return ok(new Gson().toJson(savedReply.getId()));
    }

    public Result getReply(Long commentId) {
        try{
            if(commentId==null){
                System.out.println("Expecting comment id");
                return badRequest("Expecting comment id");
            }

            List<Reply> replies = replyRepository.findByCommentId(commentId);
            int size = replies.size();
            for(int i=0;i<size;i++){
                replies.addAll(replies.get(i).getReplies());
            }
            Collections.sort(replies);

            return ok(new Gson().toJson(replies));
        } catch (Exception e){
            e.printStackTrace();
            return badRequest("Fail to fetch replies");
        }
    }
}
