package com.yuweilai.service.impl;

import com.yuweilai.dao.FeedbackMapper;
import com.yuweilai.entity.Feedback;
import com.yuweilai.service.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 87734 on 2017/11/27.
 */
@Service
public class FeedbackService implements IFeedbackService {
    @Autowired
    private FeedbackMapper feedbackMapper;
    /*
    @Method:保存用户反馈
     */
    @Override
    public Integer insert(Feedback feedback) {
        return feedbackMapper.insert(feedback);
    }
}
