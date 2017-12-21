package com.yuweilai.service.impl;

import com.yuweilai.dao.IndexPicMapper;
import com.yuweilai.entity.IndexPic;
import com.yuweilai.entity.IndexPicExample;
import com.yuweilai.service.IIndexPicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 87734 on 2017/11/27.
 */
@Service
public class IndexServiceImpl implements IIndexPicService {
    @Autowired
    private IndexPicMapper indexPicMapper;
    @Override
    /*
    @Method:获取前端轮播图
     */
    public List<IndexPic> getIndexPic() {
        IndexPicExample indexPicExample=new IndexPicExample();
        IndexPicExample.Criteria criteria=indexPicExample.createCriteria();
        criteria.andStateEqualTo(1);
        indexPicExample.setOrderByClause("RANKING ASC");
        List<IndexPic> picList=indexPicMapper.selectByExample(indexPicExample);
        return picList;
    }
}
