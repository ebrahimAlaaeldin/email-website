package com.cse.mail;

import com.cse.mail.dal.model.Email;
import com.cse.mail.dal.model.User;
import com.cse.mail.dto.EmailDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // Define custom property mapping for Email to EmailDto
        modelMapper.typeMap(Email.class, EmailDto.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getSender().getUsername(), EmailDto::setSender);
                    mapper.map(Email::getId, EmailDto::setEmailId);
//                    mapper.map(src -> (src.getReceivers() == null ? Collections.emptyList() : src.getReceivers()).stream()
//                     .map(receiver -> ((User) receiver).getUsername())
//                     .collect(Collectors.toList()),
//           EmailDto::setReceivers);
                });

        return modelMapper;
    }
}