package com.yuweilai.service.impl;

import com.github.pagehelper.PageHelper;
import com.yuweilai.dao.NoticeMapper;
import com.yuweilai.entity.Notice;
import com.yuweilai.entity.NoticeExample;
import com.yuweilai.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 87734 on 2017/11/27.
 */
@Service
public class NoticeServiceImpl implements INoticeService {
    @Autowired
    private NoticeMapper noticeMapper;
    /*
    @Method:插入通知
     */
    @Override
    public Integer insertNotice(Notice notice) {
        return noticeMapper.insert(notice);
    }
    /*
        @Method:获取通知
         */
    @Override
    public List<Notice> getNotice(int pageNum,int pageSize) {
        NoticeExample noticeExample=new NoticeExample();
        NoticeExample.Criteria criteria=noticeExample.createCriteria();
        String userId="123456";
        criteria.andUserIdEqualTo(userId);
        criteria.andStateEqualTo(1);
        noticeExample.setOrderByClause("TIME DESC");
        long maxPage=noticeMapper.countByExample(noticeExample);
        PageHelper.startPage(pageNum, pageSize);
        System.out.println(maxPage);
        if((pageNum-1)*pageSize>=maxPage){
            return null;
        }
        return noticeMapper.selectByExample(noticeExample);
    }
}
