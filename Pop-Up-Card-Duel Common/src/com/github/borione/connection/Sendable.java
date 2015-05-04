package com.github.borione.connection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Date;

import com.github.borione.crud.Player;
import com.github.borione.util.Consts;
import com.github.borione.util.StringUtils;

public interface Sendable {

	public abstract String formatData();
	
	public static Sendable reconstruct(String str) {
		String sep = Character.toString(Consts.SEPARATOR);
		String[] args = str.split(sep);
		
		try {
			Class c = Class.forName(args[0]);
			Constructor con = c.getConstructors()[0];
			
			if(con.getParameterCount() != args.length - 1) {
				// Error
				return null;
			}
			
			Object[] par = new Object[con.getParameterCount()];
			Class[] parTypes = con.getParameterTypes();
			
			for (int i = 0; i < parTypes.length; i++) {
				// TODO: It seems to work, but we need to let it be more dynamic.
				if(parTypes[i].equals(Timestamp.class)) {
					par[i] = StringUtils.toTimestamp(args[i+1]);
					continue;
				}
				if(parTypes[i].equals(int.class)) {
					par[i] = Integer.parseInt(args[i+1]);
					continue;
				}
				par[i] = parTypes[i].cast(args[i+1]);
			}
			
			Sendable obj = (Sendable) con.newInstance(par);
			
			return obj;
			
		} catch (ClassNotFoundException e) {
			// Error
			return null;
		} catch (InstantiationException e) {
			// Error
			return null;
		} catch (IllegalAccessException e) {
			// Error
			return null;
		} catch (IllegalArgumentException e) {
			// Error
			return null;
		} catch (InvocationTargetException e) {
			// Error
			return null;
		}
	}
	
	public static void main(String[] args) {
		Player p = Player.factory("LeaX_XIV");
		String s = p.formatData();
		Player p1 = (Player) Sendable.reconstruct(s);
		
		if(p.equals(p1)) {
			System.out.println("LOL");
		}
	}
}
