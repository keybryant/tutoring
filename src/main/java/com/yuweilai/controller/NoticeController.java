package com.yuweilai.controller;

import com.yuweilai.entity.Notice;
import com.yuweilai.entity.Page;
import com.yuweilai.entity.UserBase;
import com.yuweilai.service.INoticeService;
import com.yuweilai.util.DateUtil;
import com.yuweilai.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by 87734 on 2017/11/27.
 */
@RestController
public class NoticeController {
    @Autowired
    private INoticeService noticeService;
    @RequestMapping(value="/insertNotice",method = RequestMethod.POST)
    public @ResponseBody
    Object insertNoticeList(HttpServletRequest request, Notice notice){
        notice.setState(1);
        notice.setTime(DateUtil.getCurrentDate());
        UserBase userBase=(UserBase)request.getSession().getAttribute("user");
        String userId=userBase.getUserId();
        notice.setUserId(userId);
        notice.setType(1);//1:普通通知
        if(noticeService.insertNotice(notice)!=1){
            return ResultUtil.build(101,"can not insert notice");
        }
        return ResultUtil.ok();
    }
    @RequestMapping(value="/getNotice",method = RequestMethod.POST)
    public @ResponseBody
    Object getNoticeList(HttpServletRequest request){
        List<Notice> noticeList=noticeService.getNotice(0,50);
        return ResultUtil.ok(noticeList);
    }
}
