// File: /Trackademic/src/main/java/com/trackademic/service/interfaces/CommentService.java

package com.trackademic.service.interfaces;

import com.trackademic.nosql.document.Comment;
import org.bson.types.ObjectId;

import java.util.List;

public interface CommentService {

    /**
     * Retrieves all comments associated with a specific EvaluationPlan ID.
     *
     * @param planId The ObjectId of the evaluation plan.
     * @return A list of comments for the plan.
     */
    List<Comment> getCommentsByEvaluationPlanId(ObjectId planId);

    /**
     * Saves a new comment for a specific EvaluationPlan.
     *
     * @param planId      The ObjectId of the evaluation plan.
     * @param studentName The name of the student making the comment.
     * @param commentText The content of the comment.
     * @return The saved Comment object.
     */
    Comment addCommentToEvaluationPlan(ObjectId planId, String studentName, String commentText);

    /**
     * Deletes a comment by its ID.
     *
     * @param commentId The ObjectId of the comment to delete.
     */
    void deleteComment(ObjectId commentId);
}