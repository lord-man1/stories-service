package com.place2rest.storiesservice.impl.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import place2.rest.toolbox.api.storage.MinIOCredentialsSupplier;
import place2.rest.toolbox.api.storage.Storage;
import place2.rest.toolbox.impl.storage.MinIOStorageImpl;
import place2.rest.toolbox.impl.storage.MinIOStorageImplStub;
import place2.rest.toolbox.vo.toolbox.ProfileNames;

@Configuration
public class MinIOConfiguration {

    @Bean
    public MinIOCredentialsSupplier credentialsSupplier() {
        return new MinIOCredential();
    }

    @Bean
    public Storage restaurantGalleryStorage(MinIOCredentialsSupplier credentialsSupplier) {
        return new MinIOStorageImpl(credentialsSupplier, "stories", 3);
    }
}
