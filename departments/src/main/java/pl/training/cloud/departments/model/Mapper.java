package pl.training.cloud.departments.model;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import pl.training.cloud.departments.dto.ExceptionDto;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class Mapper {

    private MessageSource messageSource;

    @Autowired
    public Mapper(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public <Source, Destination> void map(Source source, Destination destination) {
        new ModelMapper().map(source, destination);
    }

    public <Source, DestinationType> DestinationType map(Source source, Class<DestinationType> type) {
        return new ModelMapper().map(source, type);
    }

    public <SourceElement, DestinationType> List<DestinationType> map(List<SourceElement> source, Class<DestinationType> type) {
        ModelMapper modelMapper = new ModelMapper();
        return source.stream()
                .map(element -> modelMapper.map(element, type))
                .collect(Collectors.toList());
    }

    public ExceptionDto map(Exception ex, Locale locale) {
        String exceptionClassName = ex.getClass().getSimpleName();
        String description;
        try {
            description = messageSource.getMessage(exceptionClassName, null, locale);
        } catch (NoSuchMessageException e) {
            description = exceptionClassName;
        }
        return new ExceptionDto(description);
    }

}