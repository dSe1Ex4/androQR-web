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

  public static ValueDTO mapToDTO(Value value){
    ValueDTO valueDTO = new ValueDTO();
    valueDTO.setValue(value.getValue());
    valueDTO.setMeasureDTO(MeasureMapper.mapToDTO(value.getMeasure()));
    valueDTO.setParamDTO(ParamMapper.mapToDTO(value.getParam()));

    return valueDTO;
  }

}
