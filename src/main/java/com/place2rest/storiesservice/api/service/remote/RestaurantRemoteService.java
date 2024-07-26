package com.place2rest.storiesservice.api.service.remote;

import com.place2rest.storiesservice.vo.remote.story.ExistResponseDTO;
import place2.rest.toolbox.vo.toolbox.exception.RequestServiceException;

public interface RestaurantRemoteService {
    boolean exists(String restaurantId) throws RequestServiceException;
}
