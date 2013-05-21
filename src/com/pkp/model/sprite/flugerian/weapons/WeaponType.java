package com.pkp.model.sprite.flugerian.weapons;

public enum WeaponType {
	EMP(1, "EMP", ""),
	EMPLAUNCHER(1, "EMP Launcher", "");
	
	private final int id;
	private final String name;
	private final String description;
	
    WeaponType(int id, String name, String description) {
    	this.id = id;
    	this.name = name;
    	this.description = description;
    }
    
    public int getId() {
    	return id;
    }
    
    public String getName() {
    	return name;
    }
    
    public String getDescription() {
    	return description;
    }
    
    public static WeaponType getEnumById(int id)
    {
    	switch(id) {
    		case 1:
    			return EMP;
    		case 2:
    			return EMPLAUNCHER;
    	}
    	return null;
    }
    
    public static WeaponType getEnumByName(String name)
    {
    	if (name.equals("EMP")) return EMP;
    	if (name.equals("EMP Launcher")) return EMPLAUNCHER;
    	return null;
    }
}
