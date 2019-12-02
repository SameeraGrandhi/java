/*
 * Kubernetes
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: v1.15.7
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package io.kubernetes.client.openapi.models;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.kubernetes.client.openapi.models.V1beta1RollingUpdateStatefulSetStrategy;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;

/**
 * StatefulSetUpdateStrategy indicates the strategy that the StatefulSet controller will use to perform updates. It includes any additional parameters necessary to perform the update for the indicated strategy.
 */
@ApiModel(description = "StatefulSetUpdateStrategy indicates the strategy that the StatefulSet controller will use to perform updates. It includes any additional parameters necessary to perform the update for the indicated strategy.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2019-11-28T14:07:30.308Z[Etc/UTC]")
public class V1beta1StatefulSetUpdateStrategy {
  public static final String SERIALIZED_NAME_ROLLING_UPDATE = "rollingUpdate";
  @SerializedName(SERIALIZED_NAME_ROLLING_UPDATE)
  private V1beta1RollingUpdateStatefulSetStrategy rollingUpdate;

  public static final String SERIALIZED_NAME_TYPE = "type";
  @SerializedName(SERIALIZED_NAME_TYPE)
  private String type;


  public V1beta1StatefulSetUpdateStrategy rollingUpdate(V1beta1RollingUpdateStatefulSetStrategy rollingUpdate) {
    
    this.rollingUpdate = rollingUpdate;
    return this;
  }

   /**
   * Get rollingUpdate
   * @return rollingUpdate
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public V1beta1RollingUpdateStatefulSetStrategy getRollingUpdate() {
    return rollingUpdate;
  }


  public void setRollingUpdate(V1beta1RollingUpdateStatefulSetStrategy rollingUpdate) {
    this.rollingUpdate = rollingUpdate;
  }


  public V1beta1StatefulSetUpdateStrategy type(String type) {
    
    this.type = type;
    return this;
  }

   /**
   * Type indicates the type of the StatefulSetUpdateStrategy.
   * @return type
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "Type indicates the type of the StatefulSetUpdateStrategy.")

  public String getType() {
    return type;
  }


  public void setType(String type) {
    this.type = type;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    V1beta1StatefulSetUpdateStrategy v1beta1StatefulSetUpdateStrategy = (V1beta1StatefulSetUpdateStrategy) o;
    return Objects.equals(this.rollingUpdate, v1beta1StatefulSetUpdateStrategy.rollingUpdate) &&
        Objects.equals(this.type, v1beta1StatefulSetUpdateStrategy.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(rollingUpdate, type);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class V1beta1StatefulSetUpdateStrategy {\n");
    sb.append("    rollingUpdate: ").append(toIndentedString(rollingUpdate)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

