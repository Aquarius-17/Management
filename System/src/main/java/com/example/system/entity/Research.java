package com.example.system.entity;

import java.io.Serializable;
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
 * @since 2022-11-26
 */
@Getter
@Setter
  @ApiModel(value = "Research对象", description = "")
public class Research implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer id;

    private String researchName;

    private String content;


}
