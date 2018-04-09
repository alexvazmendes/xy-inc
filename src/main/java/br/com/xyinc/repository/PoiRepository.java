package br.com.xyinc.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.xyinc.model.Poi;

public interface PoiRepository extends PagingAndSortingRepository<Poi, Long>{

}
