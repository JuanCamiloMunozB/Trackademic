// File: /Trackademic/src/main/java/com/trackademic/service/imp/CommentServiceI.java

package com.trackademic.service.imp;

import com.trackademic.nosql.document.Comment;
import com.trackademic.nosql.repository.CommentRepository;
import com.trackademic.service.interfaces.CommentService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceI implements CommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentServiceI.class);

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> getCommentsByEvaluationPlanId(ObjectId planId) {
        logger.debug("Fetching comments for Evaluation Plan ID: {}", planId);
        return commentRepository.findByEvaluationPlanId(planId);
    }

    @Override
    public Comment addCommentToEvaluationPlan(ObjectId planId, String studentName, String commentText) {
        logger.debug("Adding comment for Evaluation Plan ID: {} by student: {}", planId, studentName);
        Comment comment = new Comment(null, planId, studentName, commentText, new Date());
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(ObjectId commentId) {
        logger.debug("Deleting comment with ID: {}", commentId);
        if (!commentRepository.existsById(commentId)) {
            throw new EntityNotFoundException("Comment not found with id: " + commentId);
        }
        commentRepository.deleteById(commentId);
    }
}