package com.place2rest.storiesservice.impl.service.remote;

import com.place2rest.storiesservice.api.service.remote.RestaurantRemoteService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import place2.rest.toolbox.vo.toolbox.exception.RequestServiceException;

@Service
@Log4j2
public class RestaurantServiceStub implements RestaurantRemoteService {
    @Override
    public boolean exists(String restaurantId) throws RequestServiceException {
        return true;
    }
}
