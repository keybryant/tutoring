package com.yuweilai.controller;

import com.yuweilai.entity.Feedback;
import com.yuweilai.entity.UserBase;
import com.yuweilai.service.IFeedbackService;
import com.yuweilai.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by 87734 on 2017/11/27.
 */
@RestController
public class FeedbackController {
    @Autowired
    private IFeedbackService feedbackService;
    @RequestMapping(value="/insertFeedback",method = RequestMethod.POST)
    public @ResponseBody
    Object insertFeedback(HttpServletRequest request, Feedback feedback){
        feedback.setTime(new Date(System.currentTimeMillis()));
        feedback.setState(1);
        UserBase userBase=(UserBase)request.getSession().getAttribute("user");
        feedback.setUserId(userBase.getUserId());
        feedbackService.insert(feedback);
        return ResultUtil.ok();
    }
}
