package com.place2rest.storiesservice.impl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import place2.rest.toolbox.api.storage.Storage;
import place2.rest.toolbox.impl.storage.MinIOStorageImplStub;

@Configuration
public class MinIOConfiguration {
    @Bean
    public Storage storyMediaStubStorage() {
        return new MinIOStorageImplStub();
    }
}
