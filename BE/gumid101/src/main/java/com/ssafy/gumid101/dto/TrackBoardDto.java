package com.ssafy.gumid101.dto;

import java.io.Serializable;

import com.ssafy.gumid101.entity.TrackBoardEntity;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 직렬화 기능을 가진 User클래스
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrackBoardDto implements Serializable {

	private Long trackBoardSeq;
	
	private Long runRecordSeq;

	@ApiParam(value = "난이도 별점 (1 ~ 5)")
	private Integer trackBoardHardPoint;
	
	@ApiParam(value = "주변 환경 별점 (1 ~ 5)")
	private Integer trackBoardEnvironmentPoint;
	
	public static TrackBoardDto of(TrackBoardEntity trackBoard) {
		
		if(trackBoard == null)
			return null;
		
		
		return new TrackBoardDtoBuilder()
				.trackBoardSeq(trackBoard.getTrackBoardSeq())
				.trackBoardHardPoint(trackBoard.getTrackBoardHardPoint())
				.trackBoardEnvironmentPoint(trackBoard.getTrackBoardEnviromentPoint())
				.runRecordSeq(trackBoard.getRunRecordEntity().getRunRecordSeq())
				.build();
	}
}