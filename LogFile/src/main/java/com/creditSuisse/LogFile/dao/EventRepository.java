package com.creditSuisse.LogFile.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.creditSuisse.LogFile.models.Event;

@Repository
public interface EventRepository extends CrudRepository<Event, String>{

}
