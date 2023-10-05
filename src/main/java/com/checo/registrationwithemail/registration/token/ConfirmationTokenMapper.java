package com.checo.registrationwithemail.registration.token;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ConfirmationTokenMapper extends BaseMapper<ConfirmationToken> {
}
