package com.xxh.cms.article.service.impl;

import com.xxh.cms.article.entity.Info;
import com.xxh.cms.article.mapper.InfoMapper;
import com.xxh.cms.article.service.IInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class InfoServiceImpl extends ServiceImpl<InfoMapper, Info> implements IInfoService {

}
