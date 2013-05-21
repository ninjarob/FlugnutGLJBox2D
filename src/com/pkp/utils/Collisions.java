package com.pkp.utils;

import java.util.List;

import com.pkp.model.sprite.snostreblaian.Bomb;
import org.jbox2d.common.Vec2;
import com.pkp.model.Zig;
import com.pkp.model.sprite.flugerian.Building;
import com.pkp.model.sprite.flugerian.BuildingPiece;
import com.pkp.model.sprite.flugerian.weapons.EMP;
import com.pkp.model.sprite.flugerian.weapons.EMPCircle;
import com.pkp.model.sprite.flugerian.weapons.EMPHoriz;
import com.pkp.model.sprite.flugerian.weapons.EMPSuperCircle;
import com.pkp.model.sprite.flugerian.weapons.EMPZigZag;

public class Collisions {

	public static BuildingPiece missileBuildingCollission(Bomb bomb, List<Building> buildings) {
		if (!bomb.done) {
			for (int j = 0; j < buildings.size(); j++) {
				List<BuildingPiece> bps = buildings.get(j).buildingPieces;
				for (BuildingPiece bp : bps) {
					//if (bp.health>0) {
//						double dist = Math.sqrt(Math.pow(bomb.def.position.x - (bp.body.getPosition().x+bp.cs.m_radius), 2) +
//                                Math.pow(bomb.def.position.y - (bp.body.getPosition().y+bp.cs.m_radius), 2));
//						if (dist <= bp.cs.m_radius - 1) {
//							return bp;
//						}
					//}
				}
			}
		}
		return null;
	}

	public static EMP missileEMPCollisions(Bomb bomb, List<EMPCircle> emps) {
		if (!bomb.done) {
			for (int j = 0; j < emps.size(); j++) {
				EMPCircle emp = emps.get(j);
				double dist = Math.sqrt(Math.pow(bomb.def.position.x - (emp.def.position.x+emp.radius/2), 2) + Math.pow(bomb.def.position.y - (emp.def.position.y+emp.radius/2), 2));
				if (dist <= emp.radius + 5) {
					if (bomb.previousEMP != emp) {
						bomb.previousEMP = emp;
						bomb.health -= 1;
						return emp;
					}
				}
			}
		}
		return null;
	}
	
	public static EMP missileEMPSuperCollisions(Bomb bomb, List<EMPSuperCircle> emps) {
		if (!bomb.done) {
			for (int j = 0; j < emps.size(); j++) {
				EMPSuperCircle emp = emps.get(j);
				double dist = Math.sqrt(Math.pow(bomb.def.position.x - (emp.def.position.x+emp.radius/2), 2) + Math.pow(bomb.def.position.y - (emp.def.position.y+emp.radius/2), 2));
				if (dist <= emp.radius + 5) {
					if (bomb.previousEMP != emp) {
						bomb.previousEMP = emp;
						bomb.health -= 1;
						return emp;
					}
				}
			}
		}
		return null;
	}
	
	public static EMP missileHorizEmpCollisions(Bomb bomb, List<EMPHoriz> emps) {
		if (!bomb.done) {
			for (int j = 0; j < emps.size(); j++) {
				EMPHoriz emp = emps.get(j);
				double dist = Math.abs(bomb.def.position.y - emp.def.position.y);
				if (dist <= 6) {
					if (bomb.previousEMP != emp) {
						bomb.previousEMP = emp;
						int missileHealth = bomb.health;
						int empHealth = emp.health;
						bomb.health -= missileHealth > empHealth?empHealth:missileHealth;
						emp.health -= missileHealth > empHealth?empHealth:missileHealth;
						return emp;
					}
				}
			}
		}
		return null;
	}

	public static EMP missileZigCollisions(Bomb bomb, List<EMPZigZag> empZigZags) {
		if (!bomb.done) {
			for (int i = 0; i < empZigZags.size(); i++) {
				EMPZigZag he = empZigZags.get(i);
				for (int j = 0; j < he.zigs.size(); j++) {
					Zig zig = he.zigs.get(j);
					if (zig.health > 0) { //zig must have some health left
						Vec2 upperLeft = new Vec2(zig.sv.x, zig.sv.y+2);
						Vec2 lowerLeft = new Vec2(zig.sv.x, zig.sv.y-2);
						Vec2 upperRight = new Vec2(zig.ev.x, zig.ev.y+2);
						Vec2 lowerRight = new Vec2(zig.ev.x, zig.ev.y-2);
						if (inBoxCollision(upperLeft, lowerLeft, upperRight, lowerRight, bomb.def.position)) {
							bomb.previousEMP = zig;
							int missileHealth = bomb.health;
							int empHealth = zig.health;
							bomb.health -= missileHealth > empHealth?empHealth:missileHealth;
							zig.health -= missileHealth > empHealth?empHealth:missileHealth;
							return zig;
						}
					}
				}
			}
		}
		return null;
	}
	
	private static boolean inBoxCollision(Vec2 ul, Vec2 ll, Vec2 ur, Vec2 lr, Vec2 collidePoint) {
		
		return false;
	}
}
