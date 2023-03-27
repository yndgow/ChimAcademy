package kr.co.ChimAcademy.util;

public class GunbunName {
	
	public static String getGbName(int lecGubun) {
		
		String gbName = null;
		switch (lecGubun) {
			case 0: gbName = "-"; break;
			case 1: gbName = "전공필수"; break;
			case 2: gbName = "전공선택"; break;
			case 3: gbName = "교양"; break;
			default: gbName = ""; break;
		}
		
		return gbName;
	}

}
