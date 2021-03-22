package com.example.androidQr.mapper;

import com.example.androidQr.dto.ValueDTO;
import com.example.androidQr.entity.Value;

public class ValueMapper {

  public static Value mapToEntity(ValueDTO valueDTO){
    Value value = new Value();
    value.setValue(valueDTO.getValue());
    value.setParam(ParamMapper.mapToEntity(valueDTO.getParamDTO()));
    value.setMeasure(MeasureMapper.mapToEntity(valueDTO.getMeasureDTO()));

    return value;
  }

}
