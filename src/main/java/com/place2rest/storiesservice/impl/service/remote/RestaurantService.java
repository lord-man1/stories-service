//package com.place2rest.storiesservice.impl.service.remote;
//
//import com.place2rest.storiesservice.api.service.remote.RestaurantRemoteService;
//import com.place2rest.storiesservice.vo.remote.story.ExistResponseDTO;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import place2.rest.toolbox.api.request.RequestV2Service;
//import place2.rest.toolbox.api.request.UriProvider;
//import place2.rest.toolbox.impl.annotation.IntegrationLog;
//import place2.rest.toolbox.impl.healthcheck.AlwaysUpHealthCheck;
//import place2.rest.toolbox.impl.request.ServiceRequestV2;
//import place2.rest.toolbox.impl.request.meta.v2.RequestMetaV2;
//import place2.rest.toolbox.impl.request.meta.v2.ResponseMetaV2;
//import place2.rest.toolbox.vo.toolbox.enums.JournalType;
//import place2.rest.toolbox.vo.toolbox.enums.TransportType;
//import place2.rest.toolbox.vo.toolbox.exception.RequestServiceException;
//
//import java.util.List;
//
//import static java.lang.String.format;
//
//@Service
//public class RestaurantService implements RestaurantRemoteService {
//    private final RequestV2Service requestService;
//    private final UriProvider uriProvider;
//
//    public RestaurantService(
//            @Value("${services.restaurant.url}") String url,
//            @Value("${services.restaurant.version}") String version,
//            @Value("${services.restaurant.healthcheck}") String healthCheck) {
//        this.requestService = new ServiceRequestV2(new AlwaysUpHealthCheck());
//        this.uriProvider = new place2.rest.toolbox.impl.request.uri.UriProvider(
//                version, url
//        );
//    }
//
//    @Override
//    @IntegrationLog(
//            event = "получение информации о существовании ресторана по его ID",
//            description = "получение информации о существовании ресторана по его ID",
//            destinationService = "restaurant-service",
//            sourceService = "story-service",
//            journalType = JournalType.INTEGRATION,
//            transportType = TransportType.REST
//    )
//    public boolean exists(String restaurantId) throws RequestServiceException {
//        var response = requestService.request(createExistRequest(restaurantId), ExistResponseDTO.class);
//        return response.getExists();
//
//    }
//
//    private RequestMetaV2 createExistRequest(String restaurantId) {
//        return RequestMetaV2.builder()
//                .method(HttpMethod.POST)
//                .uri(uriProvider.uri(
//                        format("/private/exist/%s", restaurantId))
//                )
//                .expectedStatuses(List.of(HttpStatus.OK))
//                .responseMeta(new ResponseMetaV2(true))
//                .needRetry(true)
//                .build();
//    }
//}
