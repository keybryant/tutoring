package com.yuweilai.service;

import com.yuweilai.entity.Notice;
import com.yuweilai.entity.NoticeExample;

import java.util.List;

/**
 * Created by 87734 on 2017/11/27.
 */
public interface INoticeService {
    Integer insertNotice(Notice notice);
    List<Notice> getNotice(int pageNum,int pageSize);
}
