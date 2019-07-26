package com.tsa.supplier.data.dao.api;

import com.tsa.supplier.service.entity.CompetitorGoodLink;

import java.util.List;

public interface ICompetitorGoodLinkDAO extends CRUD<CompetitorGoodLink> {

	List<CompetitorGoodLink> getAllByGoodId(long goodId);

	List<CompetitorGoodLink> getAllByCompetitorId(long competitorId);

}