package com.sdi.persistence;

import java.util.List;

import com.sdi.model.Rating;
import com.sdi.persistence.util.GenericDAO;

public interface RatingDao extends GenericDAO<Rating, Long> {

	Rating findByAboutFrom(Long aboutUserId, Long aboutTripId, Long fromUserId,
			Long fromTripId);

	List<Rating> findByAbout(Long userId);
}
