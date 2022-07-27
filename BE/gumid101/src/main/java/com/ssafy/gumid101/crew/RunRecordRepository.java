package com.ssafy.gumid101.crew;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.gumid101.entity.CrewEntity;
import com.ssafy.gumid101.entity.RunRecordEntity;
import com.ssafy.gumid101.entity.UserEntity;

public interface RunRecordRepository extends JpaRepository<RunRecordEntity, Long>  {
	List<RunRecordEntity> findAllByUserEntityAndCrewEntity(UserEntity userEntity, CrewEntity crewEntity);
}
