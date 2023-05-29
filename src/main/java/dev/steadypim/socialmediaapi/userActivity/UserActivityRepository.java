package dev.steadypim.socialmediaapi.userActivity;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.Collection;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivity, Integer> {
    Page<UserActivity> findByUserIdInOrderByTimestampDesc(Collection<Integer> userIds, Pageable pageable);

    Page<UserActivity> findByUserId(Integer userId, Pageable pageable);
}
