package com.teste.manager.systems.entities.enums;

public enum RoleList {
	
	CONVIDADO(1, "ROLE_CONVIDADO"),
	ADMIN(2, "ROLE_ADMIN");
	
	private int cod;
	private String descricao;
	
	private RoleList(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static RoleList toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(RoleList x : RoleList.values()) {
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id Invalid: " + cod);
	}

}
