package com.github.jlf1997.spring_boot_sdk.model.dialect;

public class FastCustomSQLDialect extends org.hibernate.dialect.MySQL57InnoDBDialect{
	
	 public FastCustomSQLDialect() {

		 super();
		 this.registerFunction("bitand", new BitAndFunction());
		 this.registerFunction("bitor", new BitOrFunction());
	}

}
