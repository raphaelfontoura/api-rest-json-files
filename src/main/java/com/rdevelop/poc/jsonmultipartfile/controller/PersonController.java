package com.rdevelop.poc.jsonmultipartfile.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdevelop.poc.jsonmultipartfile.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.rdevelop.poc.jsonmultipartfile.dto.PersonDto;
import com.rdevelop.poc.jsonmultipartfile.model.Person;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

  @Autowired
  private PersonService service;
  
  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<String> save(@RequestPart("person") String person,
                                     @RequestPart("photo") MultipartFile photo,
                                     @RequestPart("files")List<MultipartFile> files) throws JsonProcessingException {

    PersonDto json = service.getJson(person, photo, files);
    service.save(json.toModel());

    return ResponseEntity.ok(json.toModel().toString());
  }

  @PostMapping(path = "/v2", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<String> saveModel(@Valid @ModelAttribute PersonDto personDto) {
    System.out.println(personDto.getName());
    service.save(personDto.toModel());
    return ResponseEntity.ok(personDto.toModel().toString());
  }
}
