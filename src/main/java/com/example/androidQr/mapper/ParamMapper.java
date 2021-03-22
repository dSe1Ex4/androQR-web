package com.example.androidQr.mapper;

import com.example.androidQr.dto.ParamDTO;
import com.example.androidQr.entity.Param;

public class ParamMapper {

  public static Param mapToEntity(ParamDTO paramDTO){
    Param param = new Param();
    param.setName(paramDTO.getName());

    return param;
  }

  public static ParamDTO mapToDTO(Param param){
    ParamDTO paramDTO = new ParamDTO();
    paramDTO.setName(param.getName());

    return paramDTO;
  }

}
