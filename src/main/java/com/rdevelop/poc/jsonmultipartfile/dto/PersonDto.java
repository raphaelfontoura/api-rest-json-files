package com.rdevelop.poc.jsonmultipartfile.dto;

import com.rdevelop.poc.jsonmultipartfile.model.Person;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
public class PersonDto {

  @NotBlank
  private String name;
  @Positive
  private Integer age;
  @NotNull
  private MultipartFile photo;

  @Size(max = 2)
  private List<MultipartFile> files;

  public PersonDto() {}

  public PersonDto(String name, Integer age, MultipartFile photo, List<MultipartFile> files) {
    this.name = name;
    this.age = age;
    this.photo = photo;
    this.files = files;
  }

  public Person toModel() {
    Person person = new Person();
    person.setName(name);
    person.setAge(age);
    person.setPhoto(photo);
    return person;
  }
}
