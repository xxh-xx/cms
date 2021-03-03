package com.xxh.cms.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxh.cms.article.entity.Notice;
import com.xxh.cms.article.mapper.NoticeMapper;
import com.xxh.cms.article.service.INoticeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xxh
 * @since 2021-02-19
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

}
