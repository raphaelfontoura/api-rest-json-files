package com.rdevelop.poc.jsonmultipartfile.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rdevelop.poc.jsonmultipartfile.dto.PersonDto;
import com.rdevelop.poc.jsonmultipartfile.model.Person;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class PersonService {

    private final Path root = Paths.get("resources");

    public PersonDto getJson(String person, MultipartFile photo, List<MultipartFile> files) throws JsonProcessingException {
        PersonDto personJson;
        ObjectMapper objectMapper = new ObjectMapper();
        personJson = objectMapper.readValue(person, PersonDto.class);
        personJson.setPhoto(photo);
        personJson.setFiles(files);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<PersonDto>> violations = validator.validate(personJson);
        for (ConstraintViolation<PersonDto> violation : violations ) {
            throw new RuntimeException(violation.getMessage());
        }
        return personJson;
    }

    public void save(Person person) {
        try  {
            Files.copy(person.getPhoto().getInputStream(), this.root.resolve(Objects.requireNonNull(person.getPhoto().getOriginalFilename())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
