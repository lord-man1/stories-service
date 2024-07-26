package com.place2rest.storiesservice.api.repository;

import com.place2rest.storiesservice.vo.domain.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoryRepository extends JpaRepository<Story, String> {
        Optional<Story> findByIdAndRestaurantId(String id, String restaurantId);
        List<Story> findAllByRestaurantId(String restaurantId);
}
