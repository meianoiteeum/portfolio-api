package br.com.portfolio.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.io.InputStream;

public class MessageErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder error = new Default();

    @Override
    public Exception decode(String key, Response response) {
        MessageException message;

        try (InputStream bodyIs = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            message = mapper.readValue(bodyIs, MessageException.class);
        } catch (IOException e) {
            return new Exception(e);
        }

        return switch (response.status()) {
            case 404 -> new NotFoundException(message.message());
            case 400 -> new BadRequestException(message.message());
            default -> error.decode(key, response);
        };

    }
}
