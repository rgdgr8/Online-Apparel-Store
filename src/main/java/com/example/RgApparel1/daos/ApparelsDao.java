package com.example.RgApparel1.daos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.RgApparel1.models.Apparels;

public interface ApparelsDao extends CrudRepository<Apparels, Integer>{
	@Query("from Apparels where price>=?1 and price<=?2 and type=?3")
	List<Apparels> getApparelsByUserPref(float bot, float top, String pref);
	
	@Query("from Apparels where icode in ?1")
	List<Apparels> getPurchasedApparels(List<Integer> icodes);
}
