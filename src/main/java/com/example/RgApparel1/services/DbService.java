package com.example.RgApparel1.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.RgApparel1.daos.ApparelsDao;
import com.example.RgApparel1.daos.UsersRepo;
import com.example.RgApparel1.models.Apparels;
import com.example.RgApparel1.models.Users;

@Service
public class DbService {
	@Autowired
	private UsersRepo ur;
	@Autowired
	private ApparelsDao ad;
	
	public boolean save(Users u) {
		try {
			ur.save(u);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public Users findById(int id) {
		return ur.findById(id).orElse(null);
	}
	
	public List<Apparels> getApparelsbyUserPref(float bot, float top, String pref){
		return ad.getApparelsByUserPref(bot, top, pref);
	}
	
	public Iterable<Apparels> getAllApparels(){
		return ad.findAll();
	}
	
	public List<Apparels> getPurchasedApparels(String[] icodes){
		List<Integer> res = new ArrayList<>();
		for(String s : icodes) {
			res.add(Integer.valueOf(s));
		}
		//System.out.println(res);
		return ad.getPurchasedApparels(res);
	}
	
	public void updateRange(int id, String[] icodes) {
		List<Apparels> res = getPurchasedApparels(icodes);
		
		System.out.println(res);
		
		float min = Float.MAX_VALUE;
		float max = Float.MIN_VALUE;
		for(Apparels a : res) {
			if(a.getPrice()<min) {
				min = a.getPrice();
			}
			if(a.getPrice()>max) {
				max = a.getPrice();
			}
		}
		
		Users u = ur.findById(id).orElse(null);
		if(u==null)
			return;
		
		if(u.getRangeBot()<min)
			u.setRangeBot(min);
		if(u.getRangeTop()>max)
			u.setRangeTop(max);
		
		System.out.println(ur.save(u).toString());
		
	}
}
