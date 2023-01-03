package com.example.system.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Lee
 * @since 2022-10-18
 */
@Getter
@Setter
  @ApiModel(value = "Time对象", description = "")
public class Time implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer timeId;

      private Integer id;

    private LocalDateTime times;


}
