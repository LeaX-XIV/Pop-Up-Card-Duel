package com.github.borione.util;

import java.util.List;

public class ListUtils {

	public static <E> List<E> deleteNull(List<E> l) {
		if(l != null && !l.isEmpty()) {
			while(l.contains(null)) {
				l.remove(null);
			}
		}

		return l;
	}

	public static <E> String toString(List<E> l, String separator) {
		StringBuilder sb = new StringBuilder();

		if(l != null && !l.isEmpty()) {
			for (E e : l) {
				sb.append(e);
				sb.append(separator);
			}

			sb.delete(sb.length() - separator.length(), sb.length());
		}

		return sb.toString();
	}

}
