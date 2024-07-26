package com.place2rest.storiesservice.impl.config;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import place2.rest.toolbox.api.storage.MinIOCredentialsSupplier;

import java.net.URL;

@Component
public class MinIOCredential implements MinIOCredentialsSupplier {
    @Override
    public String accessKey() {
        return "minio";
    }

    @Override
    public String privateKey() {
        return "root_password";
    }

    @SneakyThrows
    @Override
    public URL endpoint() {
        return new URL("http", "localhost", 9000, "");
    }
}
