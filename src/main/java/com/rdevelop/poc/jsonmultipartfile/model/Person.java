package com.rdevelop.poc.jsonmultipartfile.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Person {
  private String name;
  private Integer age;
  @ToString.Exclude
  private MultipartFile photo;
}
