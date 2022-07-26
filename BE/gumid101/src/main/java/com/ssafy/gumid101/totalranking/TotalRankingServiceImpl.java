package com.ssafy.gumid101.totalranking;

import java.util.List;

import com.ssafy.gumid101.res.RankingDto;
import com.ssafy.gumid101.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TotalRankingServiceImpl implements TotalRankingService {

	private final CrewTotalRecordRepository totalRankingRepo;
	private final UserRepository userRepo;

	@Override
	public List<RankingDto> getRankingByType(String rankingType, Long size, Long offset) throws Exception {
		List<RankingDto> rankingList = null;
		//totalRankingRepo
		if(!"point".equals(rankingType)) {
			rankingList= totalRankingRepo.getUserTotalRankingOptionType(rankingType, size, offset);	
		}else if("point".equals(rankingType)) {
			rankingList= userRepo.getUserTotalPointRanking( size,  offset);
		}
		
		return rankingList;
	}


}